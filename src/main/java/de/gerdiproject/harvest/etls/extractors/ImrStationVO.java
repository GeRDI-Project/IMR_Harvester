/*
 *  Copyright © 2018 Robin Weiss (http://www.gerdi-project.de/)
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
package de.gerdiproject.harvest.etls.extractors;

import java.util.List;

import de.gerdiproject.harvest.imr.json.Feature;
import de.gerdiproject.harvest.imr.json.StationProperties;
import lombok.Value;

/**
 * This value object contains extracted (meta-)data of an IMR station.
 *
 * @author Robin Weiss
 */
@Value
public class ImrStationVO
{
    private Feature<StationProperties> feature;
    private String description;
    private List<Integer> measurementYears;
    private List<String> measurementDates;

    /**
     * Retrieves the earliest date of measurement from
     * the list of measurement dates.
     *
     * @return the earliest measurement date or null, if there are none
     */
    public String getEarliestDate()
    {
        return measurementDates.isEmpty()
               ? null
               : measurementDates.get(measurementDates.size() - 1);
    }


    /**
     * Retrieves the latest date of measurement from
     * the list of measurement dates.
     *
     * @return the latest measurement date or null, if there are none
     */
    public String getLatestDate()
    {
        return measurementDates.isEmpty()
               ? null
               : measurementDates.get(0);
    }
}
