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

package info.gianlucacosta.helios.swing.jlist;

import org.apache.commons.lang3.ArrayUtils;

import javax.swing.*;
import java.util.*;

/**
 * ListModel providing additional selection-related features
 *
 * @since 1.3
 */
public class AdvancedSelectionListModel<T> extends DefaultListModel {

    /**
     * Moves up by one all the items having the passed indexes, if possible (for
     * example, the first item in the model cannot be moved)
     *
     * @param itemIndexes the indexes of the items to move
     */
    public void moveUpItems(int... itemIndexes) {
        Arrays.sort(itemIndexes);

        int smallerUnmovedIndex = -1;

        for (int currentIndex : itemIndexes) {
            if (currentIndex == smallerUnmovedIndex + 1) {
                smallerUnmovedIndex = currentIndex;
                continue;
            }

            T currentItem = (T) get(currentIndex);

            int previousIndex = currentIndex - 1;
            T previousItem = (T) get(previousIndex);

            set(previousIndex, currentItem);
            set(currentIndex, previousItem);
        }
    }

    /**
     * Moves down by one all the items having the passed indexes, if possible
     * (for example, the last item in the model cannot be moved)
     *
     * @param itemIndexes the indexes of the items to move
     */
    public void moveDownItems(int... itemIndexes) {
        Arrays.sort(itemIndexes);
        ArrayUtils.reverse(itemIndexes);

        int greaterUnmovedIndex = size();

        for (int currentIndex : itemIndexes) {
            if (currentIndex == greaterUnmovedIndex - 1) {
                greaterUnmovedIndex = currentIndex;
                continue;
            }

            T currentItem = (T) get(currentIndex);

            int nextIndex = currentIndex + 1;
            T nextItem = (T) get(nextIndex);

            set(nextIndex, currentItem);
            set(currentIndex, nextItem);
        }
    }

    /**
     * Retrieves all the items in the model
     *
     * @return the items in the model, as a List
     */
    public List<T> getItems() {
        return Collections.list(elements());
    }

    /**
     * Retrieves only some items in the model
     *
     * @param indexes the indexes of the items to retrieve
     * @return the list of requested items
     */
    public List<T> getItems(int... indexes) {
        List<T> result = new ArrayList<>();

        for (int i = 0; i < indexes.length; i++) {
            int currentIndex = indexes[i];

            T currentValue = (T) get(currentIndex);

            result.add(currentValue);
        }

        return result;
    }

    /**
     * Clears the model and adds items from the provided source collection
     *
     * @param source the source collection
     */
    public void setItems(Collection<T> source) {
        removeAllElements();

        for (T value : source) {
            addElement(value);
        }
    }
}
