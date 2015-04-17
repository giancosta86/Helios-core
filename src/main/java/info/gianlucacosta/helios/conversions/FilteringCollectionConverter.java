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

import info.gianlucacosta.helios.predicates.Predicate;

/**
 * Collection converter that, given a collection, returns a new collection
 * having just the items satisfying a given predicate.
 */
public class FilteringCollectionConverter<T> extends CollectionToCollectionConverter<T, T> {

    private final Predicate<T> includingPredicate;

    public FilteringCollectionConverter(final Predicate<T> includingPredicate) {
        this.includingPredicate = includingPredicate;
    }

    @Override
    protected T convertItem(T sourceItem) {
        if (includingPredicate.evaluate(sourceItem)) {
            return sourceItem;
        } else {
            return null;
        }
    }

}
