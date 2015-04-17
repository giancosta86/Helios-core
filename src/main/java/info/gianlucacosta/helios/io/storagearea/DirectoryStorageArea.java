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

package info.gianlucacosta.helios.io.storagearea;

import info.gianlucacosta.helios.io.Directory;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * Storage area supported by a directory: its entries are the files contained in
 * the directory itself and its subdirectories
 *
 * @since 1.3
 */
public class DirectoryStorageArea implements StorageArea {

    private final Directory rootDirectory;

    public DirectoryStorageArea(Directory rootDirectory) {
        if (rootDirectory == null) {
            throw new IllegalArgumentException();
        }

        this.rootDirectory = rootDirectory;
    }

    /**
     * Returns the requested entry, reading it from a file
     *
     * @param entryPathComponents the strings making up the relative path of the
     *                            file within the root directory. They are joined using File.separator. .
     * @return the requested entry
     * @throws IOException In case of I/O errors
     */
    @Override
    public StorageAreaEntry getEntry(String... entryPathComponents) throws IOException {
        String entryPath = StringUtils.join(entryPathComponents, File.separator);

        File entryFile = new File(rootDirectory, entryPath);

        return new DirectoryStorageAreaEntry(entryFile);
    }

    @Override
    public void clear() throws IOException {
        rootDirectory.deltree();

        if (rootDirectory.exists()) {
            throw new IOException();
        }
    }
}
