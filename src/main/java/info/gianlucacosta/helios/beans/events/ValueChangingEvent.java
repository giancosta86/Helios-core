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
 * Event that should be fired whenever a value is changing and that change could
 * be prevented.
 * <p>
 * In particular, the fire() method returns a boolean result, telling if the
 * value can be updated, which is true if and only if no listeners were
 * registered or if they all returned true.
 */
public class ValueChangingEvent<TValue> extends AbstractEvent<ValueChangingListener<TValue>> {

    private static final long serialVersionUID = 1L;

    public boolean fire(TValue currentValue, TValue newValue) {
        for (ValueChangingListener<TValue> listener : getListeners()) {
            if (!listener.onChanging(currentValue, newValue)) {
                return false;
            }
        }

        return true;
    }

}
