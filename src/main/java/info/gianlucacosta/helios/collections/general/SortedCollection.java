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

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A collection that always ensures to be sorted
 */
public class SortedCollection<T extends Comparable<? super T>> implements Collection<T> {

    private static final long serialVersionUID = 1L;
    private final LinkedList<T> internalList = new LinkedList<>();

    public SortedCollection() {
    }

    public SortedCollection(Collection<? extends T> c) {
        addAll(c);
    }

    @Override
    public int size() {
        return internalList.size();
    }

    @Override
    public boolean isEmpty() {
        return internalList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return internalList.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    public Object[] toArray() {
        return internalList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return internalList.toArray(a);
    }

    @Override
    public boolean add(T e) {
        int insertionIndex = Collections.binarySearch(internalList, e);

        if (insertionIndex < 0) {
            insertionIndex = -insertionIndex - 1;
        }

        internalList.add(insertionIndex, e);

        return true;
    }

    @Override
    public boolean remove(Object o) {
        return internalList.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return internalList.containsAll(c);
    }

    @Override
    public final boolean addAll(Collection<? extends T> c) {
        boolean itemsAdded = false;

        for (T item : c) {
            if (add(item)) {
                itemsAdded = true;
            }
        }

        return itemsAdded;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return internalList.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return internalList.retainAll(c);
    }

    @Override
    public void clear() {
        internalList.clear();
    }

    /**
     * This collection is equal to another Collection if and only if the two
     * collections have the same number of items, and homologous items are equal
     *
     * @param obj the other object
     * @return true if the two collections are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Collection)) {
            return false;
        }

        Collection<T> other = (Collection<T>) obj;

        return CollectionItems.equals(this, other);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

}
