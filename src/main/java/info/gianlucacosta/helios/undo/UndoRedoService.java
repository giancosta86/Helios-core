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

package info.gianlucacosta.helios.undo;

import info.gianlucacosta.helios.beans.events.TriggerListener;

/**
 * Undo/redo service
 */
public interface UndoRedoService {

    /**
     * Checks if there are available undo snapshots
     *
     * @return true if it's possible to undo
     */
    boolean canUndo();

    /**
     * Checks if there are available redo snapshots
     *
     * @return true if it's possible to redo
     */
    boolean canRedo();

    /**
     * Restores the latest undo snapshot available
     *
     * @throws IllegalStateException if there are no undo snapshots
     */
    void undo();

    /**
     * Restores the first redo snapshot available
     *
     * @throws IllegalStateException if there are no redo snapshots
     */
    void redo();

    /**
     * Takes a snapshot of the observed model, and stores it as the latest undo
     * snapshot.
     * <p>
     * If the internal list of undo snapshots exceeds the specified max size, it
     * shifts to the left: the older snapshots are discarded.
     */
    void takeSnapshot();

    /**
     * This parameter is the maximum size allowed for the list of undo
     * snapshots, as well as for the list of redo snapshots
     *
     * @return the maximum size allowed
     */
    int getMaxSize();

    /**
     * Sets the maximum size allowed for both the list of undo snapshots and the
     * list of redo snapshots.
     * <p>
     * If the passed size is less then the current size:
     * <ul>
     * <li> the list of undo snapshots shifts <em>to the left</em> - that is, the oldest items are deleted </li>
     * <li> the list of redo snapshots shift <em>to the right</em> - that is, the newest items are deleted </li>
     * </ul>
     *
     * @param maxSize The maximum size
     */
    void setMaxSize(int maxSize);

    void addSnapshotTakenListener(TriggerListener listener);

    void removeSnapshotTakenListener(TriggerListener listener);

    void addSnapshotRestoredListener(TriggerListener listener);

    void removeSnapshotRestoredListener(TriggerListener listener);

    void addTrimmedListener(TriggerListener listener);

    void removeTrimmedListener(TriggerListener listener);

}
