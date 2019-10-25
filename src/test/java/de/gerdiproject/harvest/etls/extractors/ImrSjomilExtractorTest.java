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

import java.io.File;
import java.nio.charset.StandardCharsets;

import de.gerdiproject.harvest.ImrContextListener;
import de.gerdiproject.harvest.application.ContextListener;
import de.gerdiproject.harvest.etls.AbstractIteratorETL;
import de.gerdiproject.harvest.etls.ImrSjomilETL;
import de.gerdiproject.harvest.imr.constants.ImrSjomilConstants;
import de.gerdiproject.harvest.utils.data.DiskIO;
import de.gerdiproject.harvest.utils.data.HttpRequesterUtils;
import de.gerdiproject.json.GsonUtils;
import de.gerdiproject.json.datacite.DataCiteJson;

/**
 * This class provides Unit Tests for the {@linkplain ImrSjomilExtractor}.
 *
 * @author Robin Weiss
 */
public class ImrSjomilExtractorTest extends AbstractIteratorExtractorTest<ImrSjomilVO>
{
    private static final int VIEW_ID = 0;
    private static final String HTTP_RESOURCE_FOLDER = "mockedHttpResponses";
    private static final String CONFIG_RESOURCE = "config.json";
    private static final String VIEW_PAGE_URL = String.format(ImrSjomilConstants.VIEW_URL, VIEW_ID);
    
    
    private final DiskIO diskReader = new DiskIO(GsonUtils.createGerdiDocumentGsonBuilder().create(), StandardCharsets.UTF_8);


    @Override
    protected ContextListener getContextListener()
    {
        return new ImrContextListener();
    }


    @Override
    protected AbstractIteratorETL<ImrSjomilVO, DataCiteJson> getEtl()
    {
        return new ImrSjomilETL();
    }

    
    @Override
    protected File getConfigFile()
    {
        return getResource(CONFIG_RESOURCE);
    }
    

    @Override
    protected File getMockedHttpResponseFolder()
    {
        return getResource(HTTP_RESOURCE_FOLDER);
    }


    @Override
    protected ImrSjomilVO getExpectedOutput()
    {
        final File mockedFilePath = 
                HttpRequesterUtils.urlToFilePath(VIEW_PAGE_URL, getResource(HTTP_RESOURCE_FOLDER));
        
        return new ImrSjomilVO(VIEW_ID, diskReader.getHtml(mockedFilePath.toString()));
    }
}
