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

import java.util.Iterator;

import org.jsoup.nodes.Document;

import de.gerdiproject.harvest.etls.AbstractETL;
import de.gerdiproject.harvest.imr.constants.ImrSjomilConstants;
import de.gerdiproject.harvest.utils.data.HttpRequester;

/**
 * This {@linkplain AbstractIteratorExtractor} implementation iterates through IMR
 * Sea and Environment (SJØMIL) datasets, and returns them as {@linkplain ImrSjomilVO}s.
 *
 * @author Robin Weiss
 */
public class ImrSjomilExtractor extends AbstractIteratorExtractor<ImrSjomilVO>
{
    protected final HttpRequester httpRequester = new HttpRequester();


    @Override
    public void init(final AbstractETL<?, ?> etl)
    {
        super.init(etl);

        httpRequester.setCharset(etl.getCharset());
    }


    @Override
    public String getUniqueVersionString()
    {
        // there is no shared version string that represents the state of IMR Sjomil datasets
        return null;
    }


    @Override
    public int size()
    {
        return ImrSjomilConstants.SEA_AND_ENVIRONMENT_DOC_COUNT;
    }


    @Override
    protected Iterator<ImrSjomilVO> extractAll() throws ExtractorException
    {
        return new ImrIterator();
    }


    /**
     * This class represents an {@linkplain Iterator} that iterates through
     * {@linkplain ImrSjomilVO}s used for harvesting IMR SJØMIL datasets by
     * trying out all IDs in a range of 0000 to 9999.
     *
     * @author Robin Weiss
     */
    private class ImrIterator implements Iterator<ImrSjomilVO>
    {
        int id = 0; // NOPMD the ID starts explicitly with 0

        @Override
        public boolean hasNext()
        {
            return id < size();
        }


        @Override
        public ImrSjomilVO next()
        {
            final String url = String.format(ImrSjomilConstants.VIEW_URL, id);

            // check if a dataset page exists for the url
            final Document viewPage = httpRequester.getHtmlFromUrl(url);

            // assemble VO or return null if the dataset does not exist
            final ImrSjomilVO vo =
                viewPage == null
                ? null
                : new ImrSjomilVO(id, viewPage);

            // increment id for the next request
            id++;
            return vo;
        }
    }


    @Override
    public void clear()
    {
        // nothing to clean up

    }
}
