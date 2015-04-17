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

import java.io.IOException;

/**
 * Provides an area for storing multiple entries; each entry is identified by
 * its unique path.
 *
 * @since 1.3
 */
public interface StorageArea {

    /**
     * Gets a specific entry
     *
     * @param entryPathComponents All the components making up the entry path.
     *                            The meaning of <i>path</i> and <i>path component</i> depends on the
     *                            technology of the specific implementation
     * @return the requested entry. It is never null, but that does not imply
     * that it actually exists in the underlying storage system.
     * @throws IOException In case of I/O errors
     */
    StorageAreaEntry getEntry(String... entryPathComponents) throws IOException;

    /**
     * Deletes all the entries
     *
     * @throws IOException In case of I/O errors
     */
    void clear() throws IOException;
}
