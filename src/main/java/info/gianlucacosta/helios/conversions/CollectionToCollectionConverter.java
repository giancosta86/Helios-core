/*§
  ===========================================================================
  Helios - Core
  ===========================================================================
  Copyright (C) 2013-2015 Gianluca Costa
  ===========================================================================
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ===========================================================================
*/

package info.gianlucacosta.helios.conversions;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Converts a collection to another collection, by converting each item via
 * convertItem().
 * <p>
 * The created collection is, by default, an ArrayList, but this can be
 * customized by overriding createResultCollection().
 */
public abstract class CollectionToCollectionConverter<TSourceItem, TTargetItem> implements Converter<Collection<TSourceItem>, Collection<TTargetItem>> {

    /**
     * @param sourceItem each item in the source collection
     * @return the item in the new collection, or null to skip the current
     * source item
     */
    protected abstract TTargetItem convertItem(TSourceItem sourceItem);

    @Override
    public Collection<TTargetItem> convert(Collection<TSourceItem> source) {
        Collection<TTargetItem> result = createResultCollection();

        for (TSourceItem sourceItem : source) {
            TTargetItem targetItem = convertItem(sourceItem);

            if (targetItem != null) {
                result.add(targetItem);
            }
        }

        return result;
    }

    protected Collection<TTargetItem> createResultCollection() {
        return new ArrayList<>();
    }

}
