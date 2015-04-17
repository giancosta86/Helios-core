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
import java.io.InputStream;
import java.io.OutputStream;

/**
 * An entry in a StorageArea
 *
 * @since 1.3
 */
public interface StorageAreaEntry {

    /**
     * Checks for the existence of the entry in the underlying storage
     * technology
     *
     * @return true if the entry actually exists
     * @throws IOException In case of I/O errors
     */
    boolean exists() throws IOException;

    /**
     * Opens and input stream to read the entry contents
     *
     * @return the requested InputStream
     * @throws IOException for various reasons - most likely, if the entry does
     *                     not exist in the underlying storage system
     */
    InputStream openInputStream() throws IOException;

    /**
     * Opens an output stream to write the entry contents. If the entry does not
     * exist in the underlying storage system, this function creates it.
     *
     * @return the requested OutputStream
     * @throws IOException In case of I/O errors
     */
    OutputStream openOutputStream() throws IOException;

    /**
     * Deletes the entry from the underlying storage system
     *
     * @throws IOException if the entry cannot be successfully removed
     */
    void remove() throws IOException;
}
