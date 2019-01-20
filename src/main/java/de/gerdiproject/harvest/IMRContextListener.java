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
package de.gerdiproject.harvest;

import de.gerdiproject.harvest.config.parameters.AbstractParameter;
import de.gerdiproject.harvest.config.parameters.StringParameter;
import de.gerdiproject.harvest.harvester.IMRHarvester;
import de.gerdiproject.harvest.imr.constants.IMRDataCiteConstants;
import de.gerdiproject.harvest.imr.constants.IMRParameterConstants;

import java.util.Arrays;
import java.util.List;

import javax.servlet.annotation.WebListener;

/**
 * This class initializes the {@linkplain IMRHarvester} and all required classes.
 *
 * @author Arnd Plumhoff
 */
@WebListener
public class IMRContextListener extends ContextListener<IMRHarvester>
{
	
	  @Override
	    protected List<AbstractParameter<?>> getHarvesterSpecificParameters()
	    {
	        StringParameter versionParam = new StringParameter(IMRParameterConstants.VERSION_KEY, IMRParameterConstants.VERSION_DEFAULT);
	        StringParameter languageParam = new StringParameter(IMRParameterConstants.LANGUAGE_KEY, IMRParameterConstants.LANGUAGE_DEFAULT);

	        return Arrays.asList(versionParam, languageParam);
	    }
}
