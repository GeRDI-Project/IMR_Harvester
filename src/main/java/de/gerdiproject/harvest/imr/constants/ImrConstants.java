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
    public static final String GET_POSITIONS_URL = "http://www.imr.no/forskning/forskningsdata/stasjoner/view/getpositions";
    public static final String GET_YEARS_URL = "http://www.imr.no/forskning/forskningsdata/stasjoner/view/getyears/%s";
}
