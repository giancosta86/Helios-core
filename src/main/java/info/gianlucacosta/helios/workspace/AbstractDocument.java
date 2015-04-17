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

package info.gianlucacosta.helios.workspace;

import info.gianlucacosta.helios.beans.events.TriggerEvent;
import info.gianlucacosta.helios.beans.events.TriggerListener;

/**
 * Basic implementation of Document
 */
public abstract class AbstractDocument implements Document {

    private final TriggerEvent modifiedChangedEvent = new TriggerEvent();
    private transient boolean modified;

    @Override
    public void addModifiedChangedListener(TriggerListener listener) {
        modifiedChangedEvent.addListener(listener);
    }

    @Override
    public void removeModifiedChangedListener(TriggerListener listener) {
        modifiedChangedEvent.removeListener(listener);
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void setModified(boolean modified) {
        boolean modifiedChanged = (modified != this.modified);

        this.modified = modified;

        if (modifiedChanged) {
            modifiedChangedEvent.fire();
        }
    }
}
