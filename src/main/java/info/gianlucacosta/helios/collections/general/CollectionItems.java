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

package info.gianlucacosta.helios.collections.general;

import java.util.*;

/**
 * A static class containing useful methods related to managing collection items
 */
public class CollectionItems {

    private CollectionItems() {
    }

    public static <T> T getSingle(Collection<T> source) {
        Iterator<T> iterator = source.iterator();

        T result = iterator.next();

        if (iterator.hasNext()) {
            throw new IllegalArgumentException("The specified collection has more than one item!");
        }

        return result;
    }

    public static <T> T getFirst(Collection<T> source) {
        return source.iterator().next();
    }

    public static <T> Collection<T> getFirst(Collection<T> source, int numberOfItems) {
        if (numberOfItems < 0) {
            throw new IllegalArgumentException();
        }

        if (numberOfItems == 0) {
            return Collections.<T>emptyList();
        }

        if (numberOfItems > source.size()) {
            throw new IllegalArgumentException();
        }

        List<T> result = new ArrayList<>(numberOfItems);

        Iterator<T> sourceIterator = source.iterator();

        for (int i = 1; i <= numberOfItems; i++) {
            result.add(sourceIterator.next());
        }

        return result;
    }

    public static <T> Collection<T> getFirstOrFewer(Collection<T> source, int numberOfItems) {
        if (numberOfItems < 0) {
            throw new IllegalArgumentException();
        }

        if (numberOfItems == 0) {
            return Collections.<T>emptyList();
        }

        List<T> result = new ArrayList<>(numberOfItems);

        Iterator<T> sourceIterator = source.iterator();

        for (int i = 1; i <= numberOfItems && sourceIterator.hasNext(); i++) {
            result.add(sourceIterator.next());
        }

        return result;
    }

    public static <T> T getFirstOrDefault(Collection<T> source) {
        return getFirstOrDefault(source, null);
    }

    public static <T> T getFirstOrDefault(Collection<T> source, T defaultValue) {
        Iterator<T> iterator = source.iterator();

        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return defaultValue;
        }
    }

    public static <T> boolean equals(Collection<T> first, Collection<T> second) {
        if (first.size() != second.size()) {
            return false;
        }

        Iterator<T> firstIterator = first.iterator();
        Iterator<T> secondIterator = second.iterator();

        while (firstIterator.hasNext()) {
            if (!secondIterator.hasNext()) {
                return false;
            }

            if (!Objects.equals(firstIterator.next(), secondIterator.next())) {
                return false;
            }
        }

        return !secondIterator.hasNext();
    }
}
