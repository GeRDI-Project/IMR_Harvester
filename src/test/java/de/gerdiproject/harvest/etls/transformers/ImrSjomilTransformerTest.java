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
package de.gerdiproject.harvest.etls.transformers;

import java.io.File;
import java.nio.charset.StandardCharsets;

import de.gerdiproject.harvest.ImrContextListener;
import de.gerdiproject.harvest.application.ContextListener;
import de.gerdiproject.harvest.etls.AbstractIteratorETL;
import de.gerdiproject.harvest.etls.ImrSjomilETL;
import de.gerdiproject.harvest.etls.extractors.ImrSjomilVO;
import de.gerdiproject.harvest.utils.data.DiskIO;
import de.gerdiproject.json.GsonUtils;
import de.gerdiproject.json.datacite.DataCiteJson;

/**
 * This class provides Unit Tests for the {@linkplain ImrSjomilTransformer}.
 *
 * @author Robin Weiss
 */
public class ImrSjomilTransformerTest extends AbstractIteratorTransformerTest<ImrSjomilVO, DataCiteJson>
{
    private static final int VIEW_ID = 0;
    private static final String OUTPUT_RESOURCE = "output.json";
    private static final String VIEW_PAGE_RESOURCE = "inputViewPage.html";

    final DiskIO diskReader = new DiskIO(GsonUtils.createGerdiDocumentGsonBuilder().create(), StandardCharsets.UTF_8);


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
    protected ImrSjomilVO getMockedInput()
    {
        final String resourcePath = getResource(VIEW_PAGE_RESOURCE).toString();
        return new ImrSjomilVO(VIEW_ID, diskReader.getHtml(resourcePath));
    }


    @Override
    protected DataCiteJson getExpectedOutput()
    {
        final File resource = getResource(OUTPUT_RESOURCE);
        return diskReader.getObject(resource, DataCiteJson.class);
    }
}
