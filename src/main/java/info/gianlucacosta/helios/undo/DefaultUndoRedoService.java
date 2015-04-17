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

import info.gianlucacosta.helios.beans.events.TriggerEvent;
import info.gianlucacosta.helios.beans.events.TriggerListener;

import java.util.LinkedList;
import java.util.List;

/**
 * Basic implementation of UndoRedoService
 */
public class DefaultUndoRedoService<TSnapshot extends UndoRedoSnapshot> implements UndoRedoService {

    private final UndoRedoSnapshotProvider<TSnapshot> snapshotProvider;
    private final TriggerEvent snapshotTakenEvent = new TriggerEvent();
    private final TriggerEvent snapshotRestoredEvent = new TriggerEvent();
    private final TriggerEvent trimmedEvent = new TriggerEvent();
    private final LinkedList<TSnapshot> undoList = new LinkedList<>();
    private final LinkedList<TSnapshot> redoList = new LinkedList<>();
    private TSnapshot initialState;
    private int maxSize;

    public DefaultUndoRedoService(UndoRedoSnapshotProvider<TSnapshot> snapshotProvider) {
        this(snapshotProvider, 1200);
    }

    public DefaultUndoRedoService(UndoRedoSnapshotProvider<TSnapshot> snapshotProvider, int maxSize) {
        if (maxSize < 0) {
            throw new IllegalArgumentException();
        }

        this.snapshotProvider = snapshotProvider;
        this.initialState = snapshotProvider.createSnapshot();
        this.maxSize = maxSize;
    }

    @Override
    public boolean canUndo() {
        return !undoList.isEmpty();
    }

    @Override
    public boolean canRedo() {
        return !redoList.isEmpty();
    }

    @Override
    public void undo() {
        if (!canUndo()) {
            throw new IllegalStateException();
        }

        redoList.addFirst(undoList.removeLast());

        if (!undoList.isEmpty()) {
            undoList.getLast().restore();
        } else {
            initialState.restore();
        }

        snapshotRestoredEvent.fire();
    }

    @Override
    public void redo() {
        if (!canRedo()) {
            throw new IllegalStateException();
        }

        undoList.addLast(redoList.removeFirst());
        undoList.getLast().restore();

        snapshotRestoredEvent.fire();
    }

    @Override
    public void takeSnapshot() {
        TSnapshot snapshot = snapshotProvider.createSnapshot();

        undoList.addLast(snapshot);

        trimUndoList();

        redoList.clear();

        snapshotTakenEvent.fire();
    }

    private boolean trimUndoList() {
        if (undoList.size() > maxSize) {
            List<TSnapshot> undoSnapshotsToRemove = undoList.subList(0, undoList.size() - maxSize);
            initialState = undoSnapshotsToRemove.get(undoSnapshotsToRemove.size() - 1);

            undoSnapshotsToRemove.clear();
        }

        return false;
    }

    @Override
    public int getMaxSize() {
        return maxSize;
    }

    @Override
    public void setMaxSize(int maxSize) {
        if (maxSize < 0) {
            throw new IllegalArgumentException();
        }

        this.maxSize = maxSize;

        boolean trimmed = false;

        if (trimUndoList()) {
            trimmed = true;
        }

        if (redoList.size() > maxSize) {
            List<TSnapshot> redoSnapshotsToRemove = redoList.subList(redoList.size() - maxSize - 1, redoList.size() - 1);
            redoSnapshotsToRemove.clear();
            trimmed = true;
        }

        if (trimmed) {
            trimmedEvent.fire();
        }
    }

    @Override
    public void addSnapshotTakenListener(TriggerListener listener) {
        snapshotTakenEvent.addListener(listener);
    }

    @Override
    public void removeSnapshotTakenListener(TriggerListener listener) {
        snapshotTakenEvent.removeListener(listener);
    }

    @Override
    public void addSnapshotRestoredListener(TriggerListener listener) {
        snapshotRestoredEvent.addListener(listener);
    }

    @Override
    public void removeSnapshotRestoredListener(TriggerListener listener) {
        snapshotRestoredEvent.removeListener(listener);
    }

    @Override
    public void addTrimmedListener(TriggerListener listener) {
        trimmedEvent.addListener(listener);
    }

    @Override
    public void removeTrimmedListener(TriggerListener listener) {
        trimmedEvent.removeListener(listener);
    }

}
