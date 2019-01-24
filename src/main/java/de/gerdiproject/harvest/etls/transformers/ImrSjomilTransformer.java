/*
 *  Copyright © 2019 Robin Weiss (http://www.gerdi-project.de/)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package de.gerdiproject.harvest.etls.transformers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.gerdiproject.harvest.etls.extractors.ImrSjomilVO;
import de.gerdiproject.harvest.imr.constants.ImrDataCiteConstants;
import de.gerdiproject.harvest.imr.constants.ImrSjomilConstants;
import de.gerdiproject.harvest.utils.HtmlUtils;
import de.gerdiproject.json.datacite.Contributor;
import de.gerdiproject.json.datacite.DataCiteJson;
import de.gerdiproject.json.datacite.Date;
import de.gerdiproject.json.datacite.DateRange;
import de.gerdiproject.json.datacite.GeoLocation;
import de.gerdiproject.json.datacite.RelatedIdentifier;
import de.gerdiproject.json.datacite.Title;
import de.gerdiproject.json.datacite.abstr.AbstractDate;
import de.gerdiproject.json.datacite.enums.ContributorType;
import de.gerdiproject.json.datacite.enums.DateType;
import de.gerdiproject.json.datacite.enums.NameType;
import de.gerdiproject.json.datacite.enums.RelatedIdentifierType;
import de.gerdiproject.json.datacite.enums.RelationType;
import de.gerdiproject.json.datacite.enums.TitleType;
import de.gerdiproject.json.datacite.extension.generic.ResearchData;
import de.gerdiproject.json.datacite.extension.generic.WebLink;
import de.gerdiproject.json.datacite.extension.generic.enums.WebLinkType;
import de.gerdiproject.json.datacite.nested.PersonName;

/**
 * This {@linkplain AbstractIteratorTransformer} implementation transforms extracted
 * {@linkplain ImrSjomilVO}s to {@linkplain DataCiteJson} documents.
 *
 * @author Robin Weiss
 */
public class ImrSjomilTransformer extends AbstractIteratorTransformer<ImrSjomilVO, DataCiteJson>
{
    @Override
    protected DataCiteJson transformElement(ImrSjomilVO vo) throws TransformerException
    {
        final DataCiteJson document = new DataCiteJson(String.valueOf(vo.getId()));

        document.setPublisher(ImrDataCiteConstants.PROVIDER);
        document.setRepositoryIdentifier(ImrDataCiteConstants.REPOSITORY_ID);
        document.addResearchDisciplines(ImrDataCiteConstants.DISCIPLINES);
        document.setLanguage(ImrDataCiteConstants.LANGUAGE_ENGLISH);
        document.setResourceType(ImrDataCiteConstants.RESOURCE_TYPE);
        document.addCreators(ImrDataCiteConstants.CREATORS);
        document.addResearchDisciplines(ImrDataCiteConstants.DISCIPLINES);
        document.addRights(ImrDataCiteConstants.RIGHTS);
        document.addFormats(ImrDataCiteConstants.FORMATS);

        document.setPublicationYear(getPublicationYear(vo));
        document.addTitles(getTitles(vo));
        document.addContributors(getContributors(vo));
        document.addRelatedIdentifiers(getRelatedIdentifiers(vo));
        document.addWebLinks(getWebLinks(vo));
        document.addDates(getDates(vo));
        document.addGeoLocations(getGeoLocations(vo));
        document.addResearchData(getResearchData(vo));

        return document;
    }


    /**
     * Attempts to retrieve the publication year of a dataset
     * by retrieving its earliest measurement date.
     *
     * @param vo the value object that contains extracted dataset data
     *
     * @return the earliest measurement date or null, if it cannot be retrieved
     */
    private Integer getPublicationYear(ImrSjomilVO vo)
    {
        Integer publicationYear = null;

        final Element earliestDateElem =
            vo.getViewPage().selectFirst(ImrSjomilConstants.SELECT_DATE_ELEMENT);

        // retrieve year from the date selection component
        if (earliestDateElem != null) {
            final String yearString = earliestDateElem.text().substring(0, earliestDateElem.text().indexOf('-'));

            // convert year to integer
            try {
                publicationYear = Integer.parseUnsignedInt(yearString);
            } catch (NumberFormatException e) {
                publicationYear = null;
            }
        }

        return publicationYear;
    }


    /**
     * Retrieves a {@linkplain List} of {@linkplain Contributor}s of the
     * dataset.
     *
     * @param vo the value object that contains extracted dataset data
     *
     * @return a list of {@linkplain List} of {@linkplain Contributor}s
     */
    private List<Contributor> getContributors(ImrSjomilVO vo)
    {
        final List<Contributor> contributorList = new LinkedList<>();

        // retrieve the responsible scientist
        final Element scientistElem = vo
                                      .getViewPage()
                                      .selectFirst(ImrSjomilConstants.SELECT_RESPONSIBLE_SCIENTIST_ELEMENT);

        if (scientistElem != null) {
            final PersonName scientistName = new PersonName(scientistElem.text(), NameType.Personal);
            final Contributor scientist = new Contributor(scientistName, ContributorType.Researcher);
            contributorList.add(scientist);
        }

        return contributorList;
    }


    /**
     * Retrieves a {@linkplain List} of {@linkplain Title}s that are related to the
     * dataset.
     *
     * @param vo the value object that contains extracted dataset data
     *
     * @return a list of {@linkplain List} of {@linkplain Title}s
     */
    private List<Title> getTitles(ImrSjomilVO vo)
    {
        final List<Title> titleList = new LinkedList<>();

        // short description can be used as a readable English title
        final Element shortDescriptionElem = vo
                                             .getViewPage()
                                             .selectFirst(ImrSjomilConstants.SELECT_SHORT_DESCRIPTION_ELEMENT);

        titleList.add(new Title(
                          shortDescriptionElem.text(),
                          null,
                          ImrDataCiteConstants.LANGUAGE_ENGLISH));

        // dataset names can be combined to form another title
        final Element datasetNameElem = vo
                                        .getViewPage()
                                        .selectFirst(ImrSjomilConstants.SELECT_DATASET_NAME_ELEMENT);

        final Element parentNameElem = vo
                                       .getViewPage()
                                       .selectFirst(ImrSjomilConstants.SELECT_PARENT_NAME_ELEMENT);

        titleList.add(new Title(
                          String.format(
                              ImrDataCiteConstants.SEA_AND_ENVIRONMENT_TITLE,
                              parentNameElem.text(),
                              datasetNameElem.text()),
                          TitleType.Other,
                          ImrDataCiteConstants.LANGUAGE_ENGLISH));

        return titleList;
    }


    /**
     * Retrieves a {@linkplain List} of {@linkplain ResearchData} that are related to the
     * dateset.
     *
     * @param vo the value object that contains extracted dataset data
     *
     * @return a list of {@linkplain List} of {@linkplain ResearchData}
     */
    private List<ResearchData> getResearchData(ImrSjomilVO vo)
    {
        final String downloadUrl = String.format(ImrSjomilConstants.DOWNLOAD_URL, vo.getId());
        final ResearchData dataset = new ResearchData(
            downloadUrl,
            ImrDataCiteConstants.SEA_AND_ENVIRONMENT_DATASET_TITLE,
            ImrDataCiteConstants.JSON_FORMAT);

        return Arrays.asList(dataset);
    }


    /**
     * Retrieves a {@linkplain List} of {@linkplain GeoLocation}s that are related to the
     * dataset.
     *
     * @param vo the value object that contains extracted dataset data
     *
     * @return a list of {@linkplain List} of {@linkplain GeoLocation}s
     */
    private List<GeoLocation> getGeoLocations(ImrSjomilVO vo)
    {
        final List<GeoLocation> geoLocations = new LinkedList<>();

        // get the area name element
        final Element areaElem = vo
                                 .getViewPage()
                                 .selectFirst(ImrSjomilConstants.SELECT_AREA_ELEMENT);

        if (areaElem != null)
            geoLocations.add(new GeoLocation(areaElem.text()));

        return geoLocations;
    }


    /**
     * Retrieves a {@linkplain List} of {@linkplain AbstractDate}s that are related to the
     * dataset.
     *
     * @param vo the value object that contains extracted dataset data
     *
     * @return a list of {@linkplain List} of {@linkplain AbstractDate}s
     */
    private List<AbstractDate> getDates(ImrSjomilVO vo)
    {
        final List<AbstractDate> dates = new LinkedList<>();

        // retrieve the measurement date selector
        final Elements dateElements = vo.getViewPage().select(ImrSjomilConstants.SELECT_DATE_ELEMENT);

        // verify that there are dates
        if (!dateElements.isEmpty()) {
            final String earliestDate = dateElements.first().text();
            final String latestDate = dateElements.last().text();

            // check if it is a single date, or a date range
            if (earliestDate.equals(latestDate))
                dates.add(new Date(earliestDate, DateType.Collected));
            else
                dates.add(new DateRange(earliestDate, latestDate, DateType.Collected));
        }

        return dates;
    }


    /**
     * Retrieves a {@linkplain List} of {@linkplain RelatedIdentifier}s of the dataset.
     *
     * @param vo the value object that contains extracted dataset data
     *
     * @return a list of {@linkplain List} of {@linkplain RelatedIdentifier}s
     */
    private List<RelatedIdentifier> getRelatedIdentifiers(ImrSjomilVO vo)
    {
        final List<RelatedIdentifier> relatedList = new LinkedList<>();

        // get the long description PDF link
        final Element longDescriptionElem = vo
                                            .getViewPage()
                                            .selectFirst(ImrSjomilConstants.SELECT_LONG_DESCRIPTION_ELEMENT);

        if (longDescriptionElem != null) {
            final String url = HtmlUtils.getAttribute(longDescriptionElem, "href");
            final RelatedIdentifier ref = new RelatedIdentifier(url, RelatedIdentifierType.URL, RelationType.IsDescribedBy);
            relatedList.add(ref);
        }

        // get the references text
        final Element referencesElem = vo
                                       .getViewPage()
                                       .selectFirst(ImrSjomilConstants.SELECT_REFERENCES_ELEMENT);

        if (referencesElem != null) {
            final String refText = referencesElem.text();
            final RelatedIdentifierType refType = refText.startsWith("http")
                                                  ? RelatedIdentifierType.URL
                                                  : RelatedIdentifierType.Handle;
            final RelatedIdentifier ref = new RelatedIdentifier(refText, refType, RelationType.References);
            relatedList.add(ref);
        }

        return relatedList;
    }


    /**
     * Retrieves a {@linkplain List} of {@linkplain WebLink}s that are related to the
     * dataset.
     *
     * @param vo the value object that contains extracted dataset data
     *
     * @return a list of {@linkplain List} of {@linkplain WebLink}s
     */
    private List<WebLink> getWebLinks(ImrSjomilVO vo)
    {
        final List<WebLink> webLinks = new LinkedList<>();

        // add Logo link
        webLinks.add(ImrDataCiteConstants.LOGO_WEB_LINK);

        // add sea and environment database overview link
        webLinks.add(ImrDataCiteConstants.SJOMIL_OVERVIEW_LINK);

        // add View link
        final WebLink viewLink = new WebLink(
            String.format(ImrSjomilConstants.VIEW_URL,  vo.getId()),
            ImrSjomilConstants.VIEW_NAME,
            WebLinkType.ViewURL);
        webLinks.add(viewLink);

        return webLinks;
    }
}
