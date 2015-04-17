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

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * A storage area fully contained in memory
 *
 * @since 1.3
 */
public class MemoryStorageArea implements StorageArea {

    private static final String DEFAULT_ENTRY_PATH_COMPONENT_SEPARATOR = "/";

    private class MemoryStorageAreaEntry implements StorageAreaEntry {

        private final String entryPath;
        private byte[] data = new byte[0];

        public MemoryStorageAreaEntry(String entryPath) {
            this.entryPath = entryPath;
        }

        @Override
        public boolean exists() throws IOException {
            return entries.containsKey(entryPath);
        }

        @Override
        public InputStream openInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }

        @Override
        public OutputStream openOutputStream() throws IOException {
            return new ByteArrayOutputStream() {
                @Override
                public void close() throws IOException {
                    flush();
                    data = toByteArray();

                    entries.put(entryPath, MemoryStorageAreaEntry.this);

                    super.close();
                }
            };
        }

        @Override
        public void remove() throws IOException {
            entries.remove(entryPath);
        }

        @Override
        public String toString() {
            return entryPath;
        }
    }

    private final Map<String, MemoryStorageAreaEntry> entries = new HashMap<>();
    private final String pathComponentSeparator;

    public MemoryStorageArea() {
        this(DEFAULT_ENTRY_PATH_COMPONENT_SEPARATOR);
    }

    public MemoryStorageArea(String pathComponentSeparator) {
        this.pathComponentSeparator = pathComponentSeparator;
    }

    @Override
    public StorageAreaEntry getEntry(String... entryPathComponents) throws IOException {
        String entryPath = StringUtils.join(entryPathComponents, pathComponentSeparator);

        return new MemoryStorageAreaEntry(entryPath);
    }

    @Override
    public void clear() throws IOException {
        entries.clear();
    }
}
