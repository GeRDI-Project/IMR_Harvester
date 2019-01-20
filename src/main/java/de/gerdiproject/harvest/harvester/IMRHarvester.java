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
package de.gerdiproject.harvest.harvester;

import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;
import de.gerdiproject.harvest.fao.json.DomainsResponse.Domain;

import de.gerdiproject.harvest.IDocument;


/**
 * A harvester for IMR (http://www.imr.no).
 *
 * @author Arnd Plumhoff
 */
public class IMRHarvester extends AbstractListHarvester<Domain> // TODO choose an AbstractHarvester implementation that suits your needs
{
    /**
     * Default Constructor that is called by the MainContext.
     */
    public IMRHarvester()
    {
    	super(1);
    	// TODO initialize final fields
    }

	@Override
	protected boolean harvestInternal(int startIndex, int endIndex) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected int initMaxNumberOfDocuments() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected String initHash() throws NoSuchAlgorithmException, NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Collection<Domain> loadEntries() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<IDocument> harvestEntry(Domain entry) {
		// TODO Auto-generated method stub
		return null;
	}
}
