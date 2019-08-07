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

import de.gerdiproject.harvest.etls.AbstractETL;
import de.gerdiproject.harvest.etls.extractors.ImrStationVO;
import de.gerdiproject.harvest.imr.constants.ImrDataCiteConstants;
import de.gerdiproject.harvest.imr.constants.ImrStationConstants;
import de.gerdiproject.json.datacite.DataCiteJson;
import de.gerdiproject.json.datacite.Date;
import de.gerdiproject.json.datacite.DateRange;
import de.gerdiproject.json.datacite.Description;
import de.gerdiproject.json.datacite.GeoLocation;
import de.gerdiproject.json.datacite.Title;
import de.gerdiproject.json.datacite.abstr.AbstractDate;
import de.gerdiproject.json.datacite.enums.DateType;
import de.gerdiproject.json.datacite.enums.DescriptionType;
import de.gerdiproject.json.datacite.enums.TitleType;
import de.gerdiproject.json.datacite.extension.generic.ResearchData;
import de.gerdiproject.json.datacite.extension.generic.WebLink;
import de.gerdiproject.json.datacite.extension.generic.enums.WebLinkType;

/**
 * This {@linkplain AbstractIteratorTransformer} implementation transforms extracted
 * {@linkplain ImrStationVO}s to {@linkplain DataCiteJson} documents.
 *
 * @author Robin Weiss
 */
public class ImrStationTransformer extends AbstractIteratorTransformer<ImrStationVO, DataCiteJson>
{
    @Override
    public void init(final AbstractETL<?, ?> etl)
    {
        // nothing to retrieve from the ETL
    }


    @Override
    protected DataCiteJson transformElement(final ImrStationVO vo) throws TransformerException
    {
        final DataCiteJson document = new DataCiteJson(vo.getFeature().getProperties().getId());

        document.setPublisher(ImrDataCiteConstants.PROVIDER);
        document.setRepositoryIdentifier(ImrDataCiteConstants.REPOSITORY_ID);
        document.addResearchDisciplines(ImrDataCiteConstants.DISCIPLINES);
        document.setLanguage(ImrDataCiteConstants.LANGUAGE_NORWEGIAN);
        document.setResourceType(ImrDataCiteConstants.RESOURCE_TYPE);
        document.addCreators(ImrDataCiteConstants.CREATORS);
        document.addResearchDisciplines(ImrDataCiteConstants.DISCIPLINES);
        document.addRights(ImrDataCiteConstants.RIGHTS);
        document.addSubjects(ImrDataCiteConstants.STATION_SUBJECTS);
        document.addFormats(ImrDataCiteConstants.STATION_FORMATS);

        document.addTitles(getTitles(vo));
        document.addDescriptions(getDescriptions(vo));
        document.addWebLinks(getWebLinks(vo));
        document.addDates(getDates(vo));
        document.addGeoLocations(getGeoLocations(vo));
        document.addResearchData(getResearchData(vo));

        return document;
    }


    /**
     * Retrieves a {@linkplain List} of {@linkplain Title}s that are related to the
     * station.
     *
     * @param vo the value object that contains extracted station data
     *
     * @return a list of {@linkplain List} of {@linkplain Title}s
     */
    private List<Title> getTitles(final ImrStationVO vo)
    {
        final String stationName = vo.getFeature().getProperties().getName().trim();

        // use view page title as main title
        final Title titleNorwegian = new Title(
            String.format(ImrDataCiteConstants.STATION_TITLE_NORWEGIAN, stationName),
            null,
            ImrDataCiteConstants.LANGUAGE_NORWEGIAN);

        // translate main title to english
        final Title titleEnglish = new Title(
            String.format(ImrDataCiteConstants.STATION_TITLE_ENGLISH, stationName),
            TitleType.TranslatedTitle,
            ImrDataCiteConstants.LANGUAGE_ENGLISH);

        return Arrays.asList(titleNorwegian, titleEnglish);
    }


    /**
     * Retrieves a {@linkplain List} of {@linkplain ResearchData} that are related to the
     * station.
     *
     * @param vo the value object that contains extracted station data
     *
     * @return a list of {@linkplain List} of {@linkplain ResearchData}
     */
    private List<ResearchData> getResearchData(final ImrStationVO vo)
    {
        final String stationId = vo.getFeature().getProperties().getId();
        final List<ResearchData> researchDataList = new LinkedList<>();

        // add a link to all measured data
        researchDataList.add(new ResearchData(
                                 String.format(ImrStationConstants.DOWNLOAD_MEASUREMENTS_URL, stationId, new java.util.Date()),
                                 ImrDataCiteConstants.STATION_DATASET_TITLE,
                                 ImrDataCiteConstants.TXT_FORMAT));

        // add measurements of years
        for (final int measurementYear : vo.getMeasurementYears()) {
            // add salinity measurement
            researchDataList.add(new ResearchData(
                                     String.format(ImrStationConstants.SALINITY_OF_YEAR_URL, stationId, measurementYear),
                                     String.format(ImrDataCiteConstants.STATION_SALINITY_OF_YEAR_TITLE, measurementYear),
                                     ImrDataCiteConstants.JSON_FORMAT));

            // add mean salinity measurement
            researchDataList.add(new ResearchData(
                                     String.format(ImrStationConstants.MEAN_SALINITY_OF_YEAR_URL, stationId, measurementYear),
                                     String.format(ImrDataCiteConstants.STATION_MEAN_SALINITY_OF_YEAR_TITLE, measurementYear),
                                     ImrDataCiteConstants.JSON_FORMAT));

            // add temperature measurement
            researchDataList.add(new ResearchData(
                                     String.format(ImrStationConstants.TEMPERATURE_OF_YEAR_URL, stationId, measurementYear),
                                     String.format(ImrDataCiteConstants.STATION_TEMPERATURE_OF_YEAR_TITLE, measurementYear),
                                     ImrDataCiteConstants.JSON_FORMAT));

            // add mean temperature measurement
            researchDataList.add(new ResearchData(
                                     String.format(ImrStationConstants.MEAN_TEMPERATURE_OF_YEAR_URL, stationId, measurementYear),
                                     String.format(ImrDataCiteConstants.STATION_MEAN_TEMPERATURE_OF_YEAR_TITLE, measurementYear),
                                     ImrDataCiteConstants.JSON_FORMAT));
        }

        return researchDataList;
    }


    /**
     * Retrieves a {@linkplain List} of {@linkplain GeoLocation}s that are related to the
     * station.
     *
     * @param vo the value object that contains extracted station data
     *
     * @return a list of {@linkplain List} of {@linkplain GeoLocation}s
     */
    private List<GeoLocation> getGeoLocations(final ImrStationVO vo)
    {
        final List<GeoLocation> geoLocations;

        // check if a valid GeoJson exists
        if (vo.getFeature().getGeometry() != null) {
            final String stationName = vo.getFeature().getProperties().getName().trim();
            final GeoLocation stationLocation = new GeoLocation(stationName);
            stationLocation.setPoint(vo.getFeature().getGeometry());

            geoLocations = Arrays.asList(stationLocation);
        } else
            geoLocations = null;

        return geoLocations;
    }


    /**
     * Retrieves a {@linkplain List} of {@linkplain AbstractDate}s that are related to the
     * station.
     *
     * @param vo the value object that contains extracted station data
     *
     * @return a list of {@linkplain List} of {@linkplain AbstractDate}s
     */
    private List<AbstractDate> getDates(final ImrStationVO vo)
    {
        final List<AbstractDate> dates = new LinkedList<>();

        // if there is no earliest date, there can be no latest date either
        if (vo.getEarliestDate() != null) {
            // check if it is a single date, or a date range
            if (vo.getEarliestDate().equals(vo.getLatestDate()))
                dates.add(new Date(vo.getEarliestDate(), DateType.Collected));
            else
                dates.add(new DateRange(vo.getEarliestDate(), vo.getLatestDate(), DateType.Collected));
        }

        return dates;
    }


    /**
     * Retrieves a {@linkplain List} of {@linkplain Description}s that are related to the
     * station.
     *
     * @param vo the value object that contains extracted station data
     *
     * @return a list of {@linkplain List} of {@linkplain Description}s
     */
    private List<Description> getDescriptions(final ImrStationVO vo)
    {

        // get Norwegian description from VO
        final Description stationDescription = new Description(
            vo.getDescription(),
            DescriptionType.Abstract,
            ImrDataCiteConstants.LANGUAGE_NORWEGIAN);

        return Arrays.asList(stationDescription);
    }


    /**
     * Retrieves a {@linkplain List} of {@linkplain WebLink}s that are related to the
     * station.
     *
     * @param vo the value object that contains extracted station data
     *
     * @return a list of {@linkplain List} of {@linkplain WebLink}s
     */
    private List<WebLink> getWebLinks(final ImrStationVO vo)
    {
        final List<WebLink> webLinks = new LinkedList<>();

        // add static links
        webLinks.add(ImrDataCiteConstants.LOGO_WEB_LINK);
        webLinks.add(ImrDataCiteConstants.STATION_OVERVIEW_LINK);
        webLinks.add(ImrDataCiteConstants.STATION_MAP_IMAGE_LINK);
        webLinks.add(ImrDataCiteConstants.STATION_DOWNLOAD_LINK);

        // add View link
        final String stationName = vo.getFeature().getProperties().getName().trim();
        final WebLink viewLink = new WebLink(
            String.format(ImrStationConstants.VIEW_URL, stationName),
            String.format(ImrStationConstants.VIEW_NAME, stationName),
            WebLinkType.ViewURL);
        webLinks.add(viewLink);

        return webLinks;
    }


    @Override
    public void clear()
    {
        // nothing to clean up
    }
}
