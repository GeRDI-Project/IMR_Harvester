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
package de.gerdiproject.harvest.etls.transformers;

import java.util.LinkedList;
import java.util.List;

import de.gerdiproject.harvest.etls.extractors.ImrStationVO;
import de.gerdiproject.harvest.imr.constants.ImrDataCiteConstants;
import de.gerdiproject.json.datacite.DataCiteJson;
import de.gerdiproject.json.datacite.extension.generic.WebLink;
import de.gerdiproject.json.datacite.extension.generic.enums.WebLinkType;

/**
 * This {@linkplain AbstractIteratorTransformer} implementation transforms extracted
 * {@linkplain ImrStationVO}s to {@linkplain DataCiteJson} documents.
 * 
 * @author Robin Weiss
 */
public class ImrTransformer extends AbstractIteratorTransformer<ImrStationVO, DataCiteJson>
{
    @Override
    protected DataCiteJson transformElement(ImrStationVO vo) throws TransformerException
    {
        final DataCiteJson document = new DataCiteJson(vo.getFeature().getProperties().getId());
        
        document.setPublisher(ImrDataCiteConstants.PROVIDER);
        document.setRepositoryIdentifier(ImrDataCiteConstants.REPOSITORY_ID);
        document.addResearchDisciplines(ImrDataCiteConstants.DISCIPLINES);
        
        document.addWebLinks(getWebLinks(vo));
        return document;
    }
    
    protected List<WebLink> getWebLinks(ImrStationVO vo)
    {
        final List<WebLink> webLinks = new LinkedList<>();
        
        // add Logo link
        webLinks.add(ImrDataCiteConstants.LOGO_WEB_LINK);
        
        // add View link
        final String stationName = vo.getFeature().getProperties().getName();
        final WebLink viewLink = new WebLink(
                String.format(ImrDataCiteConstants.VIEW_URL, stationName),
                String.format(ImrDataCiteConstants.VIEW_NAME, stationName),
                WebLinkType.ViewURL);
        webLinks.add(viewLink);
        
        return webLinks;
    }
}
