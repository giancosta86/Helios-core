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

/**
 * A single-document workspace, a sort of "desktop" area that is able to contain
 * one document and to manage its life cycle (new/open/save/save as,...)
 */
public interface Workspace<TDocumentInterface extends Document> {

    /**
     * Closes the current document, if present, and creates a new one, setting
     * it as the current document.
     *
     * @return true if the operations have been performed successfully
     */
    boolean createNewDocument();

    /**
     * Closes the current document, if present, and opens an existing one,
     * setting it as the current document.
     *
     * @return true if the operations have been performed successfully
     */
    boolean openDocument();

    /**
     * Saves the current document and marks the workspace as "not modified"
     *
     * @return true if the document has been correctly saved
     */
    boolean saveDocument();

    /**
     * Saves the current document to another destination (for example, another
     * file path) and marks the workspace as "not modified"
     *
     * @return true if the document has been correctly saved
     */
    boolean saveDocumentAs();

    /**
     * Closes the current document
     *
     * @return true if the document has been correctly closed
     */
    boolean closeDocument();

    /**
     * @return true if there is no current document in the workspace
     */
    boolean isEmpty();

    /**
     * @return the current document, or null
     */
    TDocumentInterface getDocument();

    void addDocumentReplacedListener(TriggerListener listener);

    void removeDocumentReplacedListener(TriggerListener listener);
}
