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

import info.gianlucacosta.helios.beans.events.TriggerListener;

import java.io.File;
import java.util.Collection;

/**
 * Service used to manage a list of recent files
 */
public interface RecentFilesService {

    void add(File file);

    void remove(File file);

    void clear();

    Collection<File> getFiles();

    int getMaxSize();

    void setMaxSize(int maxSize);

    void addFilesChangedListener(TriggerListener listener);

    void removeFilesChangedListener(TriggerListener listener);

}
