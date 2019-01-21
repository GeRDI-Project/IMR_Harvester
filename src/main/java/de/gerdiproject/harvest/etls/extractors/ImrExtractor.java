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

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import de.gerdiproject.harvest.etls.AbstractETL;
import de.gerdiproject.harvest.imr.constants.ImrConstants;
import de.gerdiproject.harvest.imr.json.Feature;
import de.gerdiproject.harvest.imr.json.PositionsResponse;
import de.gerdiproject.harvest.imr.json.StationProperties;
import de.gerdiproject.harvest.utils.data.HttpRequester;

/**
 * This {@linkplain AbstractIteratorExtractor} implementation iterates through IMR station
 * positions, enriches their positional data and returns it as {@linkplain ImrStationVO}s.
 * 
 * @author Robin Weiss
 */
public class ImrExtractor extends AbstractIteratorExtractor<ImrStationVO>
{
    private final HttpRequester httpRequester = new HttpRequester();
    

    @Override
    public void init(AbstractETL<?, ?> etl)
    {
        super.init(etl);
        httpRequester.setCharset(etl.getCharset());
    }


    @Override
    public String getUniqueVersionString()
    {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
    protected Iterator<ImrStationVO> extractAll() throws ExtractorException
    {
        final PositionsResponse response = httpRequester.getObjectFromUrl(
                ImrConstants.GET_POSITIONS_URL, 
                PositionsResponse.class);
        
        return new ImrIterator(response.getFeatures().iterator());
    }
    
    
    /**
     * This class represents an {@linkplain Iterator} that iterates through
     * {@linkplain ImrStationVO}s used for harvesting.
     * 
     * @author Robin Weiss
     */
    private class ImrIterator implements Iterator<ImrStationVO>
    {
        private final Iterator<Feature<StationProperties>> featureIterator;
        
        
        /**
         * Constructor that requires an iterator of {@linkplain Feature}s.
         * 
         * @param featureIterator an iterator of station {@linkplain Feature}s
         */
        public ImrIterator(Iterator<Feature<StationProperties>> featureIterator)
        {
            this.featureIterator = featureIterator;
        }

        
        @Override
        public boolean hasNext()
        {
            return featureIterator.hasNext();
        }
        

        @Override
        public ImrStationVO next()
        {
            // get station feature
            final Feature<StationProperties> feature = featureIterator.next();
            
            // get years
            final String url = String.format(ImrConstants.GET_YEARS_URL, feature.getProperties().getId());
            final Type listType = new TypeToken<List<Integer>>() {}.getType();
            final List<Integer> years = httpRequester.getObjectFromUrl(url, listType);
            
            return new ImrStationVO(feature, years);
        }
        
    }

}
