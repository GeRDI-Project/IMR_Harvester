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

import com.google.gson.JsonArray;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import de.gerdiproject.json.geo.adapters.AbstractGeometryAdapter;

/**
 * This class deserializes {@linkplain Point}s with null coordinates into null objects,
 * this fixes an edge case in parsing IMR stations.
 * @author Robin Weiss
 */
public class FailsafePointAdapter extends AbstractGeometryAdapter<Point>
{
    /**
     * Forwarded super Constructor.
     *
     * @param geoFactory a factory for creating {@linkplain Geometry} objects
     */
    public FailsafePointAdapter(final GeometryFactory geoFactory)
    {
        super(geoFactory);
    }


    /**
     * Simple constructor that builds a new {@linkplain GeometryFactory} for the
     * adapter.
     */
    public FailsafePointAdapter()
    {
        super(new GeometryFactory());
    }


    @Override
    protected JsonArray serializeCoordinates(final Point src)
    {
        return coordinateToJsonArray(src.getCoordinate());
    }


    @Override
    protected Point deserializeGeometry(final JsonArray jsonCoordinates, final GeometryFactory factory)
    {
        final Coordinate coordinate = jsonArrayToCoordinate(jsonCoordinates);
        return coordinate == null ? null : factory.createPoint(coordinate);
    }


    @Override
    protected Coordinate jsonArrayToCoordinate(final JsonArray jsonArray)
    {
        if (jsonArray == null || jsonArray.size() < 2 || jsonArray.get(0).isJsonNull() || jsonArray.get(1).isJsonNull())
            return null;
        else
            return super.jsonArrayToCoordinate(jsonArray);
    }
}
