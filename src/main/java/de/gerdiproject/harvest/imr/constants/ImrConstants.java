/**
 * Copyright © 2017 Robin Weiss (http://www.gerdi-project.de)
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

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * This class contains constants of IMR parameters.
 *
 * @author Robin Weiss
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImrConstants
{
    // STATIONS
    private static final String VIEW_STATION = "http://www.imr.no/forskning/forskningsdata/stasjoner/view/";

    public static final String STATION_POSITIONS_URL = VIEW_STATION + "getpositions";
    public static final String STATION_YEARS_URL = VIEW_STATION + "getyears/%s";
    public static final String STATION_DATES_IN_YEAR_URL = VIEW_STATION + "getdates/%s/%d";
    public static final String STATION_DESCRIPTION_URL = VIEW_STATION + "getstationtext/%s";

    public static final String STATION_SALINITY_ON_DATE_URL = VIEW_STATION + "getdata/%s/salinity/%s";
    public static final String STATION_SALINITY_OF_YEAR_URL = VIEW_STATION + "getyeardata/%s/salinity/%d/1";
    public static final String STATION_MEAN_SALINITY_OF_YEAR_URL = VIEW_STATION + "getmeanforyear/%s/1/salinity/%d";

    public static final String STATION_TEMPERATURE_ON_DATE_URL = VIEW_STATION + "getdata/%s/temperature/%s";
    public static final String STATION_TEMPERATURE_OF_YEAR_URL = VIEW_STATION + "getyeardata/%s/temperature/%d/1";
    public static final String STATION_MEAN_TEMPERATURE_OF_YEAR_URL = VIEW_STATION + "getmeanforyear/%s/1/temperature/%d";

    // SJOMIL
    public static final String SJOMIL_URL = "http://www.imr.no/sjomil/metadata.html?id=%d&storleik=stor";
}
