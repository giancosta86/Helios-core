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
import java.util.ArrayList;
import java.util.List;

/**
 * JList providing additional selection-related features
 *
 * @since 1.3
 */
public class AdvancedSelectionJList<T> extends JList {

    /**
     * Sets the model for the list
     *
     * @param model The new list model. It must be a subclass of
     *              AdvancedSelectionListModel
     */
    @Override
    public void setModel(ListModel model) {
        if (!(model instanceof AdvancedSelectionListModel)) {
            throw new IllegalArgumentException("Unsupported model class");
        }

        super.setModel(model);
    }

    /**
     * Makes the current selection contain all the provided items, exclusively
     *
     * @param itemsToSelect the items to select
     */
    public void setSelectedValues(List<T> itemsToSelect) {
        AdvancedSelectionListModel<T> model = (AdvancedSelectionListModel<T>) getModel();

        List<Integer> selectedIndexes = new ArrayList<>();

        for (T itemToSelect : itemsToSelect) {
            int itemIndex = model.indexOf(itemToSelect);
            selectedIndexes.add(itemIndex);
        }

        setSelectedIndices(ArrayUtils.toPrimitive(selectedIndexes.toArray(new Integer[0])));
    }

    /**
     * Moves up by one all the items in the current selection, if possible (for
     * example, the first item cannot be moved, and so on)
     */
    public void moveUpSelection() {
        AdvancedSelectionListModel<T> enhancedListModel = (AdvancedSelectionListModel<T>) getModel();

        int[] selectedIndexes = getSelectedIndices();
        List<T> selectedValues = enhancedListModel.getItems(selectedIndexes);

        enhancedListModel.moveUpItems(selectedIndexes);

        setSelectedValues(selectedValues);
    }

    /**
     * Moves down by one all the items in the current selection, if possible
     * (for example, the last item cannot be moved, and so on)
     */
    public void moveDownSelection() {
        AdvancedSelectionListModel<T> enhancedListModel = (AdvancedSelectionListModel<T>) getModel();

        int[] selectedIndexes = getSelectedIndices();
        List<T> selectedValues = enhancedListModel.getItems(selectedIndexes);

        enhancedListModel.moveDownItems(selectedIndexes);

        setSelectedValues(selectedValues);
    }

    /**
     * Removes from the underlying model all the items in the current selection
     */
    public void removeSelection() {
        AdvancedSelectionListModel<T> enhancedListModel = (AdvancedSelectionListModel<T>) getModel();

        int[] selectedIndexes = getSelectedIndices();
        List<T> selectedValues = enhancedListModel.getItems(selectedIndexes);

        for (Object selectedValue : selectedValues) {
            enhancedListModel.removeElement(selectedValue);
        }
    }
}
