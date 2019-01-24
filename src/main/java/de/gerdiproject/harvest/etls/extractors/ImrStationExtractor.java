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
package de.gerdiproject.harvest.etls.extractors;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import de.gerdiproject.harvest.etls.AbstractETL;
import de.gerdiproject.harvest.imr.constants.ImrStationConstants;
import de.gerdiproject.harvest.imr.json.StationProperties;
import de.gerdiproject.harvest.utils.data.HttpRequester;
import de.gerdiproject.json.geo.Feature;
import de.gerdiproject.json.geo.FeatureCollection;

/**
 * This {@linkplain AbstractIteratorExtractor} implementation iterates through IMR station
 * positions, enriches their positional data and returns it as {@linkplain ImrStationVO}s.
 *
 * @author Robin Weiss
 */
public class ImrStationExtractor extends AbstractIteratorExtractor<ImrStationVO>
{
    private final HttpRequester httpRequester = new HttpRequester();

    private Iterator<Feature<StationProperties>> featureIterator;
    private int size = -1;


    @Override
    public void init(AbstractETL<?, ?> etl)
    {
        super.init(etl);

        httpRequester.setCharset(etl.getCharset());

        final Type responseType = new TypeToken<FeatureCollection<StationProperties>>() {} .getType();
        final FeatureCollection<StationProperties> stationsResponse = httpRequester.getObjectFromUrl(
                                                                          ImrStationConstants.POSITIONS_URL,
                                                                          responseType);
        this.size = stationsResponse.getFeatures().size();
        this.featureIterator = stationsResponse.getFeatures().iterator();
    }


    @Override
    public String getUniqueVersionString()
    {
        return null;
    }


    @Override
    public int size()
    {
        return size;
    }


    @Override
    protected Iterator<ImrStationVO> extractAll() throws ExtractorException
    {
        return new ImrIterator();
    }


    /**
     * This class represents an {@linkplain Iterator} that iterates through
     * {@linkplain ImrStationVO}s used for harvesting.
     *
     * @author Robin Weiss
     */
    private class ImrIterator implements Iterator<ImrStationVO>
    {
        @Override
        public boolean hasNext()
        {
            return featureIterator.hasNext();
        }


        @Override
        public ImrStationVO next()
        {
            // get feature
            final Feature<StationProperties> feature = featureIterator.next();
            final String stationId = feature.getProperties().getId();

            // get measurement years
            final List<Integer> measurementYears = getMeasurementYears(stationId);

            // get measurement dates
            final List<String> measurementDates = getMeasurementDates(stationId, measurementYears);

            // get description
            final String description = getDescription(stationId);

            // assemble value object
            return new ImrStationVO(
                       feature,
                       description,
                       measurementYears,
                       measurementDates);
        }


        /**
         * Retrieves a list of all years in which measurements were taken by the station.
         *
         * @param stationId the identifier of the station
         *
         * @return a list of all years in which measurements were taken
         */
        private List<Integer> getMeasurementYears(String stationId)
        {
            final Type intListType = new TypeToken<List<Integer>>() {} .getType();
            final String yearsUrl = String.format(ImrStationConstants.YEARS_URL, stationId);

            return httpRequester.getObjectFromUrl(yearsUrl, intListType);
        }


        /**
         * Retrieves description text of a hydrographic station.
         *
         * @param stationId the identifier of the station
         *
         * @return a Norwegian description String of the station
         */
        private String getDescription(String stationId)
        {
            final String descriptionUrl = String.format(ImrStationConstants.DESCRIPTION_URL, stationId);
            return httpRequester.getHtmlFromUrl(descriptionUrl).text();
        }


        /**
         * Retrieves the all measurement dates of a hydrographic station.
         *
         * @param stationId the identifier of the station
         * @param measurementYears the years during which measurements were taken
         *
         * @return all measurement dates as dd.mm.yyyy strings
         */
        private List<String> getMeasurementDates(String stationId, List<Integer> measurementYears)
        {
            final List<String> measurementDates = new LinkedList<>();

            // for each year, add all measurement dates to a list
            if (measurementYears != null) {
                final Type stringListType = new TypeToken<List<String>>() {} .getType();

                for (int year : measurementYears) {

                    // get measurement dates of the specified year
                    final String datesUrl = String.format(ImrStationConstants.DATES_IN_YEAR_URL, stationId, year);
                    final List<String> datesOfYear = httpRequester.getObjectFromUrl(datesUrl, stringListType);

                    if (datesOfYear != null)
                        measurementDates.addAll(datesOfYear);
                }
            }

            return measurementDates;
        }
    }
}
