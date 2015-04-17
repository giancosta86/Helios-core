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

import info.gianlucacosta.helios.annotations.Experimental;
import info.gianlucacosta.helios.beans.events.ValueNotificationEvent;
import info.gianlucacosta.helios.beans.events.ValueNotificationListener;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/**
 * Wraps another collection and raises events when changed
 */
@Experimental
public class NotifyingCollection<T> implements Collection<T> {

    private final Collection<T> underlyingCollection;
    private final ValueNotificationEvent<CollectionChange> changedEvent = new ValueNotificationEvent<>();

    public NotifyingCollection(Collection<T> underlyingCollection) {
        Objects.requireNonNull(underlyingCollection);
        this.underlyingCollection = underlyingCollection;
    }

    @Override
    public int size() {
        return underlyingCollection.size();
    }

    @Override
    public boolean isEmpty() {
        return underlyingCollection.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return underlyingCollection.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return underlyingCollection.iterator();
    }

    @Override
    public Object[] toArray() {
        return underlyingCollection.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return underlyingCollection.toArray(a);
    }

    @Override
    public boolean add(T e) {
        boolean result = underlyingCollection.add(e);

        if (result) {
            changedEvent.fire(CollectionChange.ITEMS_ADDED);
        }

        return result;
    }

    @Override
    public boolean remove(Object o) {
        boolean result = underlyingCollection.remove(o);

        if (result) {
            changedEvent.fire(CollectionChange.ITEMS_REMOVED);
        }

        return result;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return underlyingCollection.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean result = underlyingCollection.addAll(c);

        if (result) {
            changedEvent.fire(CollectionChange.ITEMS_ADDED);
        }

        return result;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = underlyingCollection.removeAll(c);

        if (result) {
            changedEvent.fire(CollectionChange.ITEMS_REMOVED);
        }

        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean result = underlyingCollection.retainAll(c);

        if (result) {
            changedEvent.fire(CollectionChange.ITEMS_REMOVED);
        }

        return result;
    }

    @Override
    public void clear() {
        boolean wasEmptyBeforeClearing = isEmpty();

        underlyingCollection.clear();

        if (!wasEmptyBeforeClearing) {
            changedEvent.fire(CollectionChange.ITEMS_REMOVED);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return underlyingCollection.equals(obj);
    }

    @Override
    public int hashCode() {
        return underlyingCollection.hashCode();
    }

    public void addChangedListener(ValueNotificationListener<CollectionChange> listener) {
        changedEvent.addListener(listener);
    }

    public void removeChangedListener(ValueNotificationListener<CollectionChange> listener) {
        changedEvent.removeListener(listener);
    }

}
