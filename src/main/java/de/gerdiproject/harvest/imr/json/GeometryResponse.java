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
 * This class represents a JSON object that is part of the response to a Imr getpositions request.
 * <br>e.g. see http://www.imr.no/forskning/forskningsdata/stasjoner/view/getpositions
 *
 * @author Arnd Plumhoff
 */
public final class GeometryResponse
{
	private String type;
	private List<Double> coordinates;


	public String getType()
	{
		return type;
	}


	public void setType(String value)
	{
		this.type = value;
	}


	public List<Double> getCoordinates()
	{
		return coordinates;
	}


	public void setCoordinates(List<Double> value)
	{
		this.coordinates = value;
	}
}