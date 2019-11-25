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
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vividsolutions.jts.geom.Point;

import de.gerdiproject.harvest.etls.AbstractETL;
import de.gerdiproject.harvest.imr.constants.ImrStationConstants;
import de.gerdiproject.harvest.imr.json.StationProperties;
import de.gerdiproject.harvest.utils.data.HttpRequester;
import de.gerdiproject.json.GsonUtils;
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
    protected final HttpRequester httpRequester = createHttpRequester();
    protected final HttpRequester descriptionHttpRequester = new HttpRequester(new Gson(), StandardCharsets.ISO_8859_1);

    protected Iterator<Feature<StationProperties>> featureIterator;
    protected String today;

    private int featureCount = -1;


    @Override
    public void init(final AbstractETL<?, ?> etl)
    {
        super.init(etl);

        httpRequester.setCharset(etl.getCharset());

        final Type responseType = new TypeToken<FeatureCollection<StationProperties>>() {} .getType();
        final FeatureCollection<StationProperties> stationsResponse = httpRequester.getObjectFromUrl(
                                                                          ImrStationConstants.POSITIONS_URL,
                                                                          responseType);
        this.featureCount = stationsResponse.getFeatures().size();
        this.featureIterator = stationsResponse.getFeatures().iterator();
        this.today = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).format(new java.util.Date());
    }


    @Override
    public String getUniqueVersionString()
    {
        // there is no shared version string that represents the state of IMR stations
        return null;
    }


    @Override
    public int size()
    {
        return featureCount;
    }


    @Override
    protected Iterator<ImrStationVO> extractAll() throws ExtractorException
    {
        return new ImrIterator();
    }


    @Override
    public void clear()
    {
        // nothing to clean up
    }


    /**
     * Creates a {@linkplain HttpRequester} that is able to parse
     * null-coordinates in {@linkplain Point}s.
     *
     * @return a {@linkplain HttpRequester}
     */
    private HttpRequester createHttpRequester()
    {
        final Gson gson =
            GsonUtils.createGeoJsonGsonBuilder()
            .registerTypeAdapter(Point.class, new FailsafePointAdapter())
            .create();
        return new HttpRequester(gson, StandardCharsets.UTF_8);
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
                       measurementDates,
                       today);
        }


        /**
         * Retrieves a list of all years in which measurements were taken by the station.
         *
         * @param stationId the identifier of the station
         *
         * @return a list of all years in which measurements were taken
         */
        private List<Integer> getMeasurementYears(final String stationId)
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
        private String getDescription(final String stationId)
        {
            final String descriptionUrl = String.format(ImrStationConstants.DESCRIPTION_URL, stationId);
            return descriptionHttpRequester.getHtmlFromUrl(descriptionUrl).text();
        }


        /**
         * Retrieves the all measurement dates of a hydrographic station.
         *
         * @param stationId the identifier of the station
         * @param measurementYears the years during which measurements were taken
         *
         * @return all measurement dates as dd.mm.yyyy strings
         */
        private List<String> getMeasurementDates(final String stationId, final List<Integer> measurementYears)
        {
            final List<String> measurementDates = new LinkedList<>();

            // for each year, add all measurement dates to a list
            if (measurementYears != null) {
                final Type stringListType = new TypeToken<List<String>>() {} .getType();

                for (final int year : measurementYears) {

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
