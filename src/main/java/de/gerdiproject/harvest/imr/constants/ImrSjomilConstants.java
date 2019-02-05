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
public class ImrSjomilConstants
{
    // URLS
    private static final String PREFIX = "http://www.imr.no/sjomil";
    public static final String DATASET_BROWSER_URL = PREFIX + "/index.html";

    public static final String VIEW_URL = PREFIX + "/metadata.html?id=%d&storleik=stor";
    public static final String VIEW_NAME = "Preview Dataset";

    public static final String DOWNLOAD_URL = PREFIX + "/getdata.html?datasets=%d&nullverdi=NULL";

    // SELECTION QUERIES
    public static final String SELECT_DATASET_NAME_ELEMENT = "div.right-margin:containsOwn(Dataset name:) + div.left_margin";
    public static final String SELECT_PARENT_NAME_ELEMENT = "div.right-margin:containsOwn(Parent dataset:) + div.left_margin";
    public static final String SELECT_SHORT_DESCRIPTION_ELEMENT = "div.right-margin:containsOwn(Description:) + div.left_margin";
    public static final String SELECT_AREA_ELEMENT = "div.right-margin:containsOwn(Area:) + div.left_margin";
    public static final String SELECT_TIMESTEP_ELEMENT = "div.right-margin:containsOwn(Timestep:) + div.left_margin";
    public static final String SELECT_REFERENCES_ELEMENT = "div.right-margin:containsOwn(References:) + div.left_margin";
    public static final String SELECT_LONG_DESCRIPTION_ELEMENT = "div.right-margin:containsOwn(Long description:) + div.left_margin a";
    public static final String SELECT_RESPONSIBLE_SCIENTIST_ELEMENT = "div.right-margin:containsOwn(Responsible scientist:) + div.left_margin";
    public static final String SELECT_DATE_ELEMENT = "select#date1 > option";

    // OTHER
    public static final int SEA_AND_ENVIRONMENT_DOC_COUNT = 10000; // determined empirically
}
