/*
 *  Copyright © 2018 Arnd Plumhoff (http://www.gerdi-project.de/)
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
package de.gerdiproject.harvest.imr.json;

import java.util.List;

/**
 * This class represents a JSON response to a Imr 15.11.2018 request.
 * <br>e.g. http://www.imr.no/forskning/forskningsdata/stasjoner/view/getdata/b3563862c7e84ebf887d00b15a57ff4d/temperature/15.11.2018
 *
 * @author Arnd Plumhoff
 */
public final class TimelineResponse
{
	private List<ValueResponse> min;
	private List<ValueResponse> max;
	private List<ValueResponse> value;


	public List<ValueResponse> getMin()
	{
		return min;
	}


	public void setMin(List<ValueResponse> value)
	{
		this.min = value;
	}


	public List<ValueResponse> getMax()
	{
		return max;
	}


	public void setMax(List<ValueResponse> value)
	{
		this.max = value;
	}


	public List<ValueResponse> getValue()
	{
		return value;
	}


	public void setValue(List<ValueResponse> value)
	{
		this.value = value;
	}
}