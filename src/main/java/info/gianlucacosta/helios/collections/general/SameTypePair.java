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

import java.io.Serializable;
import java.util.*;

/**
 * Pair whose items are of the same type.
 * <p>
 * It can be equal to both another SameTypePair and any other Collection,
 * provided that homologous items are equal.
 */
public class SameTypePair<T> implements Collection<T>, Serializable {

    private static final long serialVersionUID = 1L;
    private final List<T> items;

    public SameTypePair(T left, T right) {
        List<T> elementsInitializer = new ArrayList<>();
        elementsInitializer.add(left);
        elementsInitializer.add(right);

        this.items = Collections.unmodifiableList(elementsInitializer);
    }

    public SameTypePair(Collection<T> source) {
        if (source.size() != 2) {
            throw new IllegalArgumentException();
        }

        Iterator<T> sourceIterator = source.iterator();

        List<T> elementsInitializer = new ArrayList<>();
        elementsInitializer.add(sourceIterator.next());
        elementsInitializer.add(sourceIterator.next());

        this.items = Collections.unmodifiableList(elementsInitializer);
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return items.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }

    @Override
    public Object[] toArray() {
        return items.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return items.toArray(a);
    }

    @Override
    public boolean add(T e) {
        throw new UnsupportedOperationException("Cannot modify a pair!");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Cannot modify a pair!");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return items.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("Cannot modify a pair!");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Cannot modify a pair!");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Cannot modify a pair!");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Cannot modify a pair!");
    }

    public T getLeft() {
        return items.get(0);
    }

    public T getRight() {
        return items.get(1);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SameTypePair) {
            SameTypePair<T> other = (SameTypePair<T>) obj;
            return Objects.equals(getLeft(), other.getLeft())
                    && Objects.equals(getRight(), other.getRight());
        } else if (obj instanceof Collection) {
            Collection<T> other = (Collection<T>) obj;
            return CollectionItems.equals(items, other);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }
}
