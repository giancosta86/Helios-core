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

package info.gianlucacosta.helios.io;

import java.io.File;
import java.net.URI;

/**
 * Describes a directory
 *
 * @since 1.3
 */
public class Directory extends File {

    private static boolean deltree(File directory) {
        if (!directory.exists() || !directory.isDirectory()) {
            return true;
        }

        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                if (!file.delete()) {
                    return false;
                }
            } else if (file.isDirectory()) {
                if (!deltree(file)) {
                    return false;
                }
            }
        }

        if (!directory.delete()) {
            return false;
        }

        return true;
    }

    public Directory(File directoryFile) {
        super(directoryFile.getAbsolutePath());
    }

    public Directory(String pathname) {
        super(pathname);
    }

    public Directory(String parent, String child) {
        super(parent, child);
    }

    public Directory(File parent, String child) {
        super(parent, child);
    }

    public Directory(URI uri) {
        super(uri);
    }

    /**
     * Deletes the directory and all of its content, recursively
     *
     * @return true if the directory has been successfully deleted
     */
    public boolean deltree() {
        return deltree(this);
    }
}
