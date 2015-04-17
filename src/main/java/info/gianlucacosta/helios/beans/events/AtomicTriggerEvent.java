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

package info.gianlucacosta.helios.beans.events;

/**
 * A TriggerEvent that introduces the concept of "atomic section": after a call
 * to startAtomicSection(), every call to fire() will have no effect; instead,
 * calling stopAtomicSection() will call fire() just once, if and only if fire()
 * was called at least once while the event was in the atomic section.
 */
public class AtomicTriggerEvent extends TriggerEvent {

    private static final long serialVersionUID = 1L;
    private boolean inAtomicSection;
    private int atomicActionsCount;

    /**
     * Starts an atomic section
     */
    public void startAtomicSection() {
        if (inAtomicSection) {
            throw new IllegalStateException();
        }

        inAtomicSection = true;
        atomicActionsCount = 0;
    }

    /**
     * Tells whether the event is in an atomic section
     *
     * @return true if an atomic section is currently active
     */
    public boolean isInAtomicSection() {
        return inAtomicSection;
    }

    /**
     * Closes the atomic section, and calls fire() just once if and only if that
     * method was called at least once during the atomic section.
     *
     * @return how many times fire() was called during the latest atomic section
     */
    public int stopAtomicSection() {
        if (!inAtomicSection) {
            throw new IllegalStateException();
        }

        inAtomicSection = false;

        if (atomicActionsCount > 0) {
            fire();
        }

        return atomicActionsCount;
    }

    @Override
    public void fire() {
        if (inAtomicSection) {
            atomicActionsCount++;
        } else {
            super.fire();
        }
    }

}
