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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * An event, keeping track of its listeners.
 * <p>
 * This means that, in your Java beans, you just have to declare an event as a
 * private field whose type is a subclass of Event, and your addXXXListener()
 * and removeXXXListener() should just call its addListener() and
 * removeListener() methods. Similarly, to notify the listeners, you don't need
 * to manually iterate over a collection of listeners, but you should call the
 * fire(...) method of your event class (every event class has specific
 * parameters for the fire() methods).
 * <p>
 * This class can be serialized, but the list of its listeners is, by design,
 * transient, and will be recreated when the event is deserialized.
 */
public abstract class AbstractEvent<TListener extends EventListener> implements Event<TListener>, Serializable {

    private static final long serialVersionUID = 1L;

    private transient List<TListener> listeners = new ArrayList<>();

    protected List<TListener> getListeners() {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }

        return listeners;
    }

    @Override
    public void addListener(TListener listener) {
        getListeners().add(listener);
    }

    @Override
    public void removeListener(TListener listener) {
        getListeners().remove(listener);
    }

}
