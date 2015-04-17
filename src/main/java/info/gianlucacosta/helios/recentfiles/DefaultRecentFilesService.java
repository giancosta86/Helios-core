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

package info.gianlucacosta.helios.recentfiles;

import info.gianlucacosta.helios.beans.events.TriggerEvent;
import info.gianlucacosta.helios.beans.events.TriggerListener;
import info.gianlucacosta.helios.collections.queues.SlidingQueue;
import info.gianlucacosta.helios.preferences.PreferencesService;

import java.io.File;
import java.util.Collection;
import java.util.Collections;

/**
 * Implementation of RecentFilesService.
 * <p>
 * This service automatically updates its backing Preferences object by writing
 * both the recent files collection and its maximum size.
 */
class DefaultRecentFilesService implements RecentFilesService {

    private final TriggerEvent filesChangedEvent = new TriggerEvent();
    private final PreferencesService<? extends RecentFilesPreferences> preferencesService;
    private SlidingQueue<File> filesQueue;
    private int maxSize;

    public DefaultRecentFilesService(final PreferencesService<? extends RecentFilesPreferences> preferencesService) {
        this.preferencesService = preferencesService;

        RecentFilesPreferences preferences = preferencesService.getPreferences();

        maxSize = preferences.getRecentFilesMaxSize();

        Collection<File> storedRecentFiles = preferences.getRecentFiles();

        if (storedRecentFiles != null) {
            filesQueue = new SlidingQueue<>(maxSize, false, preferences.getRecentFiles());
        } else {
            filesQueue = new SlidingQueue<>(maxSize, false);
        }

        filesChangedEvent.addListener(new TriggerListener() {
            @Override
            public void onTriggered() {
                RecentFilesPreferences preferences = preferencesService.getPreferences();
                preferences.setRecentFiles(filesQueue);
            }
        });
    }

    @Override
    public void add(File file) {
        if (filesQueue.add(file)) {
            filesChangedEvent.fire();
        }
    }

    @Override
    public void remove(File file) {
        if (filesQueue.remove(file)) {
            filesChangedEvent.fire();
        }
    }

    @Override
    public void clear() {
        boolean filesChanged = !filesQueue.isEmpty();

        filesQueue.clear();

        if (filesChanged) {
            filesChangedEvent.fire();
        }
    }

    @Override
    public Collection<File> getFiles() {
        return Collections.unmodifiableCollection(filesQueue);
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

        boolean filesChanged = (maxSize < this.maxSize);

        this.maxSize = maxSize;
        preferencesService.getPreferences().setRecentFilesMaxSize(maxSize);

        filesQueue = new SlidingQueue<>(maxSize, false, filesQueue);

        if (filesChanged) {
            filesChangedEvent.fire();
        }
    }

    @Override
    public void addFilesChangedListener(TriggerListener listener) {
        filesChangedEvent.addListener(listener);
    }

    @Override
    public void removeFilesChangedListener(TriggerListener listener) {
        filesChangedEvent.removeListener(listener);
    }

}
