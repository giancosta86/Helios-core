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

import java.io.*;

/**
 * Entry of a DirectoryStorageArea
 *
 * @since 1.3
 */
public class DirectoryStorageAreaEntry implements StorageAreaEntry {

    private final File file;

    DirectoryStorageAreaEntry(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    @Override
    public boolean exists() throws IOException {
        return file.exists();
    }

    @Override
    public InputStream openInputStream() throws IOException {
        return new FileInputStream(file);
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        File parentDir = file.getParentFile();

        if (!parentDir.isDirectory() && !parentDir.mkdirs()) {
            throw new IOException(String.format("Could not create the parent directory chain for '%s'", file));
        }

        return new FileOutputStream(file);
    }

    @Override
    public void remove() throws IOException {
        if (file.isDirectory()) {
            new Directory(file).deltree();
        } else {
            file.delete();
        }

        if (file.exists()) {
            throw new IOException();
        }
    }

    @Override
    public String toString() {
        return file.toString();
    }
}
