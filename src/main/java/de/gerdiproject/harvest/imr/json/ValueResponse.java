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

import lombok.Data;

/**
 * This class represents a JSON object that is part of the response to a Imr 15.11.2018 request.
 * <br>e.g. see http://www.imr.no/forskning/forskningsdata/stasjoner/view/getdata/b3563862c7e84ebf887d00b15a57ff4d/temperature/15.11.2018
 *
 * @author Arnd Plumhoff
 */
@Data
public final class ValueResponse
{
    private int x;
    private double y;
    private String id;
    private String name;
}