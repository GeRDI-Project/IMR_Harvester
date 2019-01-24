/**
 * Copyright © 2019 Robin Weiss (http://www.gerdi-project.de)
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
public class ImrStationConstants
{
    private static final String PREFIX = "http://www.imr.no/forskning/forskningsdata/stasjoner/view";

    public static final String VIEW_URL = PREFIX + "?station=%s";
    public static final String VIEW_NAME = "View %s";

    public static final String POSITIONS_URL = PREFIX + "/getpositions";
    public static final String YEARS_URL = PREFIX + "/getyears/%s";
    public static final String DATES_IN_YEAR_URL = PREFIX + "/getdates/%s/%d";
    public static final String DESCRIPTION_URL = PREFIX + "/getstationtext/%s";

    public static final String SALINITY_ON_DATE_URL = PREFIX + "/getdata/%s/salinity/%s";
    public static final String SALINITY_OF_YEAR_URL = PREFIX + "/getyeardata/%s/salinity/%d/1";
    public static final String MEAN_SALINITY_OF_YEAR_URL = PREFIX + "/getmeanforyear/%s/1/salinity/%d";

    public static final String TEMPERATURE_ON_DATE_URL = PREFIX + "/getdata/%s/temperature/%s";
    public static final String TEMPERATURE_OF_YEAR_URL = PREFIX + "/getyeardata/%s/temperature/%d/1";
    public static final String MEAN_TEMPERATURE_OF_YEAR_URL = PREFIX + "/getmeanforyear/%s/1/temperature/%d";
}
