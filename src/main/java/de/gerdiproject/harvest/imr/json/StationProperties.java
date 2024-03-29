/*
 *  Copyright © 2018 Arnd Plumhoff (http://www.gerdi-project.de/)
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
package de.gerdiproject.harvest.imr.json;

import de.gerdiproject.json.geo.Feature;
import de.gerdiproject.json.geo.FeatureCollection;
import lombok.Data;

/**
 * This class represents properties of a {@linkplain Feature} object
 * that is part of the JSON response to an IMR getpositions-request.
 *
 * <br>e.g. see http://www.imr.no/forskning/forskningsdata/stasjoner/view/getpositions
 *
 * @see Feature
 * @see FeatureCollection
 * @author Arnd Plumhoff
 */
@Data
public final class StationProperties
{
    private String id;
    private String name;
}