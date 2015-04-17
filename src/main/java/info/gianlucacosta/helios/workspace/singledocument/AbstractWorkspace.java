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

import info.gianlucacosta.helios.beans.events.TriggerEvent;
import info.gianlucacosta.helios.beans.events.TriggerListener;
import info.gianlucacosta.helios.workspace.CloseRequestOutcome;
import info.gianlucacosta.helios.workspace.Document;

import java.util.Objects;

/**
 * Basic implementation of Workspace
 */
public abstract class AbstractWorkspace<TDocument extends Document> implements Workspace<TDocument> {

    /**
     * <p>
     * The result of opening a document.
     * <p>
     * It provides both the document implementation itself and a callback called
     * by the framework as the last action of the opening process.
     *
     * @param <TDocumentImplementation> the implementation class of the document
     */
    protected interface OpeningResult<TDocumentImplementation> {

        TDocumentImplementation getDocumentImplementation();

        void postAction();

    }

    private final TriggerEvent documentReplacedEvent = new TriggerEvent();
    private TDocument document;

    @Override
    public boolean createNewDocument() {
        if (!canClose()) {
            return false;
        }

        TDocument newDocument = doCreateNewDocument();

        if (newDocument == null) {
            return false;
        }

        setDocument(newDocument);

        return true;
    }

    protected abstract TDocument doCreateNewDocument();

    @Override
    public boolean openDocument() {
        if (!canClose()) {
            return false;
        }

        OpeningResult<TDocument> openingResult = doOpenDocument();

        if (openingResult == null) {
            return false;
        }

        setDocument(openingResult.getDocumentImplementation());

        openingResult.postAction();

        return true;
    }

    protected abstract OpeningResult<TDocument> doOpenDocument();

    @Override
    public boolean saveDocument() {
        if (document == null) {
            throw new IllegalStateException("Cannot save an inexisting document");
        }

        boolean savingResult;

        if (canSaveDirectly()) {
            savingResult = doSaveDocument();
        } else {
            savingResult = doSaveDocumentAs();
        }

        if (savingResult) {
            document.setModified(false);
        }

        return savingResult;
    }

    protected abstract boolean doSaveDocument();

    @Override
    public boolean saveDocumentAs() {
        if (document == null) {
            throw new IllegalStateException("Cannot save an inexisting document");
        }

        boolean saveResult = doSaveDocumentAs();

        if (saveResult) {
            document.setModified(false);
        }

        return saveResult;
    }

    protected abstract boolean doSaveDocumentAs();

    private boolean canClose() {
        if (document == null) {
            return true;
        }

        if (document.isModified()) {
            switch (askToClose()) {
                case CLOSE_SAVING:
                    if (!saveDocument()) {
                        return false;
                    }
                    break;

                case CLOSE_WITHOUT_SAVING:
                    break;

                case CANCEL:
                    return false;
            }
        }

        return true;
    }

    @Override
    public boolean closeDocument() {
        if (!canClose()) {
            return false;
        }

        setDocument(null);

        return true;
    }

    protected abstract CloseRequestOutcome askToClose();

    @Override
    public boolean isEmpty() {
        return (document == null);
    }

    @Override
    public TDocument getDocument() {
        return document;
    }

    protected void setDocument(TDocument document) {
        boolean documentReplaced = !Objects.equals(document, this.document);

        this.document = document;

        if (documentReplaced) {
            documentReplacedEvent.fire();
        }
    }

    /**
     * @return true if the workspace has enough information to save the document
     * without resorting to the saveAs() method
     */
    protected abstract boolean canSaveDirectly();

    @Override
    public void addDocumentReplacedListener(TriggerListener listener) {
        documentReplacedEvent.addListener(listener);
    }

    @Override
    public void removeDocumentReplacedListener(TriggerListener listener) {
        documentReplacedEvent.removeListener(listener);
    }
}
