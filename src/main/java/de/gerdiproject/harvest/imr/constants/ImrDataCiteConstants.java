/**
 * Copyright © 2019 Arnd Plumhoff, Robin Weiss (http://www.gerdi-project.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.gerdiproject.harvest.imr.constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import de.gerdiproject.json.datacite.Creator;
import de.gerdiproject.json.datacite.ResourceType;
import de.gerdiproject.json.datacite.Rights;
import de.gerdiproject.json.datacite.Subject;
import de.gerdiproject.json.datacite.enums.NameType;
import de.gerdiproject.json.datacite.enums.ResourceTypeGeneral;
import de.gerdiproject.json.datacite.extension.generic.AbstractResearch;
import de.gerdiproject.json.datacite.extension.generic.WebLink;
import de.gerdiproject.json.datacite.extension.generic.constants.ResearchDisciplineConstants;
import de.gerdiproject.json.datacite.extension.generic.enums.WebLinkType;
import de.gerdiproject.json.datacite.nested.PersonName;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * This class contains constants that are used for creating DataCite
 * documents of IMR.
 *
 * @author Arnd Plumhoff
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImrDataCiteConstants
{

    // RESOURCE TYPE
    public static final ResourceType RESOURCE_TYPE = createResourceType();

    // CREATOR
    public static final List<Creator> CREATORS = createCreators();

    // SOURCE
    public static final String PROVIDER = "Havforskningsinstituttet - Institute for Marine Research (IMR)";
    public static final String PROVIDER_URI = "https://www.imr.no/en";
    public static final String REPOSITORY_ID = "IMR";
    public static final List<AbstractResearch> DISCIPLINES = createResearchDisciplines();

    // TITLES
    public static final String STATION_TITLE_ENGLISH = "Norwegian Hydrographic Station: %s";
    public static final String STATION_TITLE_NORWEGIAN = "Stasjon: %s";
    public static final String SEA_AND_ENVIRONMENT_TITLE = "%s - %s";


    // WEB LINKS
    public static final WebLink LOGO_WEB_LINK = createLogoWebLink();
    public static final WebLink STATION_OVERVIEW_LINK = createStationOverviewWebLink();
    public static final WebLink STATION_MAP_IMAGE_LINK = createStationMapImageWebLink();
    public static final WebLink STATION_DOWNLOAD_LINK = createStationDownloadWebLink();
    public static final WebLink SJOMIL_OVERVIEW_LINK = createSeaAndEnvironmentOverviewWebLink();


    // DATES
    public static final String META_DATA_TIME_COVERAGE = "Time coverage";
    public static final String META_DATA_LAST_UPDATE = "Metadata last update";
    public static final Pattern TIME_COVERAGE_PATTERN =
        Pattern.compile("\\D+(\\d\\d\\d\\d)\\D(\\d\\d\\d\\d)[\\d\\D]+$");
    public static final String DATE_PARSE_ERROR = "Could not parse date: %s";

    // LANGUAGE
    public static final String LANGUAGE_NORWEGIAN = "no";
    public static final String LANGUAGE_ENGLISH = "en";

    // FORMATS
    public static final String JSON_FORMAT = "JSON";
    public static final String TXT_FORMAT = "TXT";
    public static final List<String> STATION_FORMATS = Collections.unmodifiableList(Arrays.asList(JSON_FORMAT, TXT_FORMAT));
    public static final List<String> SJOMIL_FORMATS = Collections.unmodifiableList(Arrays.asList(TXT_FORMAT));

    // SUBJECTS
    public static final List<Subject> STATION_SUBJECTS = createStationSubjects();

    // RIGHTS
    public static final List<Rights> RIGHTS = createRightsList();

    // RESEARCH DATA
    public static final String STATION_SALINITY_ON_DATE_TITLE = "Salinity on %s";
    public static final String STATION_SALINITY_OF_YEAR_TITLE = "Salinity in Year %d";
    public static final String STATION_MEAN_SALINITY_OF_YEAR_TITLE = "Mean Salinity in Year %d";
    public static final String STATION_TEMPERATURE_ON_DATE_TITLE = "Temperature on %s";
    public static final String STATION_TEMPERATURE_OF_YEAR_TITLE = "Temperature in Year %d";
    public static final String STATION_MEAN_TEMPERATURE_OF_YEAR_TITLE = "Mean Temperature in Year %d";
    public static final String STATION_DATASET_TITLE = "Complete Dataset";
    public static final String SEA_AND_ENVIRONMENT_DATASET_TITLE = "Complete Dataset";



    private static List<AbstractResearch> createResearchDisciplines()
    {
        return Collections.unmodifiableList(
                   Arrays.asList(
                       ResearchDisciplineConstants.OCEANOGRAPHY));
    }


    private static List<Rights> createRightsList()
    {
        final Rights rightsEnglish = new Rights(
            "IMR Data Policy",
            LANGUAGE_ENGLISH,
            "https://www.imr.no/filarkiv/2016/09/hi-datapolicy-revised2016-final-eng.pdf/en");

        final Rights rightsNorwegian = new Rights(
            "Datapolitikk for Havforskningsinstituttet",
            LANGUAGE_NORWEGIAN,
            "http://www.imr.no/filarkiv/2013/03/datapolitikk_nmd.pdf/nb-no");

        return Collections.unmodifiableList(Arrays.asList(
                                                rightsEnglish,
                                                rightsNorwegian));
    }


    private static List<Subject> createStationSubjects()
    {
        return Collections.unmodifiableList(Arrays.asList(
                                                new Subject("saltholdighet", LANGUAGE_NORWEGIAN),
                                                new Subject("salinity", LANGUAGE_ENGLISH),
                                                new Subject("temperatur", LANGUAGE_NORWEGIAN),
                                                new Subject("temperature", LANGUAGE_ENGLISH),
                                                new Subject("gjennomsnittlig normalverdi", LANGUAGE_NORWEGIAN),
                                                new Subject("average normal value", LANGUAGE_ENGLISH),
                                                new Subject("dybde", LANGUAGE_NORWEGIAN),
                                                new Subject("depth", LANGUAGE_ENGLISH),
                                                new Subject("år", LANGUAGE_NORWEGIAN),
                                                new Subject("year", LANGUAGE_ENGLISH)
                                            ));
    }


    /**
     * Initializes a WebLink that leads to the IMR logo.
     *
     * @return a link to the IMR logo
     */
    private static WebLink createLogoWebLink()
    {
        final WebLink logoLink = new WebLink(
            "http://www.jerico-ri.eu/wp-content/uploads/2016/02/IMR-logo.jpg");
        logoLink.setType(WebLinkType.ProviderLogoURL);
        return logoLink;
    }


    /**
     * Initializes a {@linkplain WebLink} that leads to an overview of all stations.
     *
     * @return a {@linkplain WebLink} to all hydrographic stations
     */
    private static WebLink createStationOverviewWebLink()
    {
        return new WebLink(
                   "http://www.imr.no/forskning/forskningsdata/stasjoner/",
                   "Faste hydrografiske stasjoner",
                   WebLinkType.Related);
    }


    /**
     * Initializes a {@linkplain WebLink} that leads to the index page of SJØMIL.
     *
     * @return a {@linkplain WebLink} to SJØMIL
     */
    private static WebLink createSeaAndEnvironmentOverviewWebLink()
    {
        return new WebLink(
                   "http://www.imr.no/sjomil/index.html",
                   "SJØMIL - Database for sjø og miljø",
                   WebLinkType.Related);
    }


    /**
     * Initializes a {@linkplain WebLink} that leads to a station overview map image.
     *
     * @return a {@linkplain WebLink} to the station map image
     */
    private static WebLink createStationMapImageWebLink()
    {
        return new WebLink(
                   "http://www.imr.no/forskning/forskningsdata/stasjoner/images/fastestasjoner.jpg",
                   "Oversikt over stasjoner",
                   WebLinkType.ThumbnailURL);
    }


    /**
     * Initializes a {@linkplain WebLink} that leads to the station download selector.
     *
     * @return a {@linkplain WebLink} that leads to the station download selector
     */
    private static WebLink createStationDownloadWebLink()
    {
        return new WebLink(
                   "http://www.imr.no/forskning/forskningsdata/stasjoner/view/initdownload",
                   "Last ned data",
                   WebLinkType.Related);
    }


    /**
     * Initializes an organisational Creator for all IMR documents.
     *
     * @return a Creator that has "IMR" as name
     */
    private static List<Creator> createCreators()
    {
        final Creator creator = new Creator(new PersonName(PROVIDER, NameType.Organisational));
        return Collections.unmodifiableList(Arrays.asList(creator));
    }


    /**
     * Initializes the ResourceType of all Forskning IMR documents.
     *
     * @return a ResourceType representing measurement data
     */
    private static ResourceType createResourceType()
    {
        final ResourceType resType = new ResourceType("time series measurements", ResourceTypeGeneral.Dataset);
        return resType;
    }
}
