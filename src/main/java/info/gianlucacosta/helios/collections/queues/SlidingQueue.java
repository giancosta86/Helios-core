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

package info.gianlucacosta.helios.collections.queues;

import org.apache.commons.lang3.ArrayUtils;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * A sliding queue
 * <p>
 * If adding elements to the queue would exceed the maximum size declared in the
 * constructor, the first elements will be removed accordingly.
 * <p>
 * You can also request that, <em>before</em> adding elements and trimming the
 * queue, a check is performed in order to remove duplicates of the items being
 * added.
 * <p>
 * This queue supports a maximum size equal to 0 - which is handy in some cases,
 * for example if you are implementing a queue of recently-used files and want
 * to let the user choose the size of the queue - even leaving it always empty:
 * in this case, all the operations modifying or querying the queue will return
 * either false or null or will throw an Exception, according to the basic Queue
 * interface itself.
 * <p>
 * The queue is equal to another collection if they have the same size and their
 * elements are equal.
 *
 * @param <T> The type of the items in the queue
 */
public class SlidingQueue<T> implements Queue<T>, Serializable {

    private static final long serialVersionUID = 1L;
    private final ArrayBlockingQueue<T> internalQueue;
    private final int maxSize;
    private final boolean allowDuplicates;

    public SlidingQueue(int maxSize, boolean allowDuplicates) {
        if (maxSize < 0) {
            throw new IllegalArgumentException();
        }

        this.maxSize = maxSize;
        this.allowDuplicates = allowDuplicates;

        if (maxSize > 0) {
            internalQueue = new ArrayBlockingQueue<>(maxSize);
        } else {
            internalQueue = null;
        }
    }

    public SlidingQueue(int maxSize, boolean allowDuplicates, Collection<? extends T> source) {
        if (maxSize < 0) {
            throw new IllegalArgumentException();
        }

        this.maxSize = maxSize;
        this.allowDuplicates = allowDuplicates;

        if (maxSize > 0) {
            internalQueue = new ArrayBlockingQueue<>(maxSize, false, source);
        } else {
            internalQueue = null;
        }
    }

    @Override
    public boolean add(T e) {
        if (maxSize == 0) {
            return false;
        }

        if (!allowDuplicates && internalQueue.contains(e)) {
            internalQueue.remove(e);
        }

        if (internalQueue.remainingCapacity() == 0) {
            internalQueue.remove();
        }

        return internalQueue.add(e);
    }

    @Override
    public boolean offer(T e) {
        if (maxSize == 0) {
            return false;
        }

        return internalQueue.offer(e);
    }

    @Override
    public T remove() {
        if (maxSize == 0) {
            throw new NoSuchElementException();
        }

        return internalQueue.remove();
    }

    @Override
    public T poll() {
        if (maxSize == 0) {
            return null;
        }

        return internalQueue.poll();
    }

    @Override
    public T element() {
        if (maxSize == 0) {
            throw new NoSuchElementException();
        }

        return internalQueue.element();
    }

    @Override
    public T peek() {
        if (maxSize == 0) {
            return null;
        }

        return internalQueue.peek();
    }

    @Override
    public int size() {
        if (maxSize == 0) {
            return 0;
        }

        return internalQueue.size();
    }

    @Override
    public boolean isEmpty() {
        if (maxSize == 0) {
            return true;
        }

        return internalQueue.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        if (maxSize == 0) {
            return false;
        }

        return internalQueue.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        if (maxSize == 0) {
            return Collections.emptyIterator();
        }

        return internalQueue.iterator();
    }

    @Override
    public Object[] toArray() {
        if (maxSize == 0) {
            return ArrayUtils.EMPTY_OBJECT_ARRAY;
        }

        return internalQueue.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (maxSize == 0) {
            return Arrays.copyOf(a, 0);
        }

        return internalQueue.toArray(a);
    }

    @Override
    public boolean remove(Object o) {
        if (maxSize == 0) {
            return false;
        }

        return internalQueue.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (maxSize == 0) {
            return false;
        }

        return internalQueue.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (maxSize == 0) {
            return false;
        }

        for (T item : c) {
            add(item);
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (maxSize == 0) {
            return false;
        }

        return internalQueue.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (maxSize == 0) {
            return false;
        }

        return internalQueue.retainAll(c);
    }

    @Override
    public void clear() {
        if (maxSize == 0) {
            return;
        }

        internalQueue.clear();
    }

}
