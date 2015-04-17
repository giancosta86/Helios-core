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

import java.io.File;
import java.util.Collection;

/**
 * Preferences describing a list of recent files and its maximum size
 */
public interface RecentFilesPreferences {

    /**
     * Reads the collection of recent files from the preferences
     *
     * @return the collection of recent files, or null in case of missing data
     * or in case of exception
     */
    Collection<File> getRecentFiles();

    void setRecentFiles(Collection<File> recentFiles);

    int getRecentFilesMaxSize();

    void setRecentFilesMaxSize(int maxSize);

}
