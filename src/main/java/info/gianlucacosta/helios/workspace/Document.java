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

import info.gianlucacosta.helios.beans.events.TriggerListener;

/**
 * A workspace document
 */
public interface Document {

    void addModifiedChangedListener(TriggerListener listener);

    void removeModifiedChangedListener(TriggerListener listener);

    /**
     * @return true if the document was marked as <i>modified</i>
     */
    boolean isModified();

    /**
     * Sets the <i>modified</i> state
     *
     * @param modified The new value for the "modified" state
     */
    void setModified(boolean modified);
}
