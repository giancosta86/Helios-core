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

package info.gianlucacosta.helios.workspace.singledocument;

import info.gianlucacosta.helios.beans.events.TriggerListener;
import info.gianlucacosta.helios.workspace.Document;

import java.io.File;

/**
 * A workspace whose documents are loaded from and saved to files
 */
public interface FileBasedWorkspace<TDocumentInterface extends Document> extends Workspace<TDocumentInterface> {

    /**
     * @return the file of the current document, or null if the workspace is
     * empty
     */
    File getDocumentFile();

    /**
     * Closes the current document, if present, and reads from the given file
     *
     * @param documentFile The document file to open
     * @return true if the document has been successfully opened
     */
    boolean openDocument(File documentFile);

    /**
     * Saves the current document to the given file
     *
     * @param documentFile The target file
     * @return true if the file has been correctly saved
     */
    boolean saveDocumentTo(File documentFile);

    void addDocumentFileChangedListener(TriggerListener listener);

    void removeDocumentFileChangedListener(TriggerListener listener);

}
