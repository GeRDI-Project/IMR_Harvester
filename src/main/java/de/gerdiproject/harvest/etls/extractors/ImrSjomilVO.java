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

import org.jsoup.nodes.Document;

import lombok.Value;

/**
 * This value object contains extracted (meta-)data of an IMR
 * sea and environment dataset.
 *
 * @author Robin Weiss
 */
@Value
public class ImrSjomilVO
{
    private int id;
    private Document viewPage;


    /**
     * The original {@linkplain Document#equals(Object)} function does not
     * compare the actual content of the HTML objects. In order to compare
     * the {@linkplain ImrSjomilVO#viewPage}, the {@linkplain Document#hasSameValue(Object)}
     * function must be used instead.
     *
     * @param obj the object that is to be compared to the VO
     * @return true if the object is equal to this VO
     */
    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        final ImrSjomilVO other = (ImrSjomilVO) obj;

        if (id != other.id)
            return false;

        if (viewPage == null) {
            if (other.viewPage != null)
                return false;
        } else if (!viewPage.hasSameValue(other.viewPage))
            return false;

        return true;
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((viewPage == null) ? 0 : viewPage.hashCode());
        return result;
    }


}
