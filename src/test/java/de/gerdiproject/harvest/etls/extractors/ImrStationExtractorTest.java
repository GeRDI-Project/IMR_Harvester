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

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.gerdiproject.harvest.ImrContextListener;
import de.gerdiproject.harvest.application.ContextListener;
import de.gerdiproject.harvest.etls.AbstractIteratorETL;
import de.gerdiproject.harvest.etls.ImrStationETL;
import de.gerdiproject.harvest.utils.data.DiskIO;
import de.gerdiproject.json.GsonUtils;
import de.gerdiproject.json.datacite.DataCiteJson;
import lombok.RequiredArgsConstructor;

/**
 * This class provides Unit Tests for the {@linkplain ImrStationExtractor}.
 *
 * @author Robin Weiss
 */
@RequiredArgsConstructor
@RunWith(Parameterized.class)
public class ImrStationExtractorTest extends AbstractIteratorExtractorTest<ImrStationVO>
{
    private static final String HTTP_RESOURCES = "mockedHttpResponses-%d";
    private static final String OUTPUT_RESOURCE = "output-%d.json";
    private static final String CONFIG_RESOURCE = "config.json";
    
    @Parameters(name = "station id: {0}")
    public static Object[] getParameters()
    {
        return new Object[] {42, 1337};
    }
    
    
    private final DiskIO diskReader = new DiskIO(GsonUtils.createGerdiDocumentGsonBuilder().create(), StandardCharsets.UTF_8);
    private final int id;
    

    @Override
    protected ContextListener getContextListener()
    {
        return new ImrContextListener();
    }


    @Override
    protected AbstractIteratorETL<ImrStationVO, DataCiteJson> getEtl()
    {
        return new ImrStationETL();
    }

    
    @Override
    protected File getConfigFile()
    {
        return getResource(CONFIG_RESOURCE);
    }
    

    @Override
    protected File getMockedHttpResponseFolder()
    {
        return getResource(String.format(HTTP_RESOURCES, id));
    }


    @Override
    protected ImrStationVO getExpectedOutput()
    {
        final File resource = getResource(String.format(OUTPUT_RESOURCE, id));
        return diskReader.getObject(resource, ImrStationVO.class);
    }
}
