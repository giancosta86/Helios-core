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
import info.gianlucacosta.helios.workspace.Document;

import java.io.File;
import java.util.Objects;

/**
 * Basic implementation of FileBasedWorkspace
 */
public abstract class AbstractFileBasedWorkspace<TDocument extends Document> extends AbstractWorkspace<TDocument> implements FileBasedWorkspace<TDocument> {

    private final TriggerEvent documentFileChangedEvent = new TriggerEvent();
    private File documentFile;

    @Override
    public File getDocumentFile() {
        return documentFile;
    }

    private void setDocumentFile(File documentFile) {
        boolean documentFileChanged = !Objects.equals(documentFile, this.documentFile);

        this.documentFile = documentFile;

        if (documentFileChanged) {
            documentFileChangedEvent.fire();
        }
    }

    @Override
    public void addDocumentFileChangedListener(TriggerListener listener) {
        documentFileChangedEvent.addListener(listener);
    }

    @Override
    public void removeDocumentFileChangedListener(TriggerListener listener) {
        documentFileChangedEvent.removeListener(listener);
    }

    @Override
    protected OpeningResult<TDocument> doOpenDocument() {
        File fileToOpen = askForFileToOpen();

        if (fileToOpen == null) {
            return null;
        }

        return processFileToOpen(fileToOpen);
    }

    @Override
    public boolean openDocument(File documentFile) {
        if (!closeDocument()) {
            return false;
        }

        OpeningResult<TDocument> openingResult = processFileToOpen(documentFile);

        if (openingResult == null) {
            return false;
        }

        setDocument(openingResult.getDocumentImplementation());

        openingResult.postAction();

        return true;
    }

    private OpeningResult<TDocument> processFileToOpen(File fileToOpen) {
        OpeningResult<TDocument> openingResult = doOpenDocument(fileToOpen);

        if (openingResult == null) {
            return null;
        }

        setDocumentFile(fileToOpen);

        return openingResult;
    }

    protected abstract File askForFileToOpen();

    protected abstract OpeningResult<TDocument> doOpenDocument(File fileToOpen);

    @Override
    protected boolean doSaveDocument() {
        return doSaveDocumentTo(documentFile);
    }

    @Override
    protected boolean doSaveDocumentAs() {
        File fileToSave = askForFileToSave();

        if (fileToSave == null) {
            return false;
        }

        boolean result = doSaveDocumentTo(fileToSave);
        if (result) {
            setDocumentFile(fileToSave);
        }

        return result;
    }

    @Override
    public boolean saveDocumentTo(File documentFile) {
        if (getDocument() == null) {
            throw new IllegalStateException("Cannot save an inexisting document");
        }

        return doSaveDocumentTo(documentFile);
    }

    protected abstract File askForFileToSave();

    protected abstract boolean doSaveDocumentTo(File fileToSave);

    @Override
    public boolean closeDocument() {
        boolean closeResult = super.closeDocument();

        if (closeResult) {
            setDocumentFile(null);
        }

        return closeResult;
    }

    @Override
    protected boolean canSaveDirectly() {
        return documentFile != null;
    }

}
