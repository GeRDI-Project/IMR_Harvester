/**
 * Copyright © 2019 Robin Weiss (http://www.gerdi-project.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.gerdiproject.harvest.etls;

import de.gerdiproject.harvest.etls.extractors.ImrSjomilExtractor;
import de.gerdiproject.harvest.etls.extractors.ImrSjomilVO;
import de.gerdiproject.harvest.etls.transformers.ImrSjomilTransformer;
import de.gerdiproject.json.datacite.DataCiteJson;


/**
 * An ETL for harvesting the IMR Sea and Environment database.<br>
 * See: http://www.imr.no/sjomil/index.html
 *
 * @author Robin Weiss
 */
public class ImrSjomilETL extends StaticIteratorETL<ImrSjomilVO, DataCiteJson>
{
    /**
     * Default Constructor that initializes the
     * extractor and transformer.
     */
    public ImrSjomilETL()
    {
        super(new ImrSjomilExtractor(), new ImrSjomilTransformer());
    }
}
