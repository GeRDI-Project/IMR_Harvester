/**
 * Copyright © 2018 Arnd Plumhoff (http://www.gerdi-project.de)
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

import de.gerdiproject.harvest.etls.extractors.ImrStationExtractor;
import de.gerdiproject.harvest.etls.extractors.ImrStationVO;
import de.gerdiproject.harvest.etls.transformers.ImrStationTransformer;
import de.gerdiproject.json.datacite.DataCiteJson;


/**
 * An ETL for harvesting IMR (http://www.imr.no).
 *
 * @author Arnd Plumhoff
 */
public class ImrStationETL extends StaticIteratorETL<ImrStationVO, DataCiteJson>
{
    /**
     * Default Constructor that is called by the MainContext.
     */
    public ImrStationETL()
    {
        super(new ImrStationExtractor(), new ImrStationTransformer());
    }
}
