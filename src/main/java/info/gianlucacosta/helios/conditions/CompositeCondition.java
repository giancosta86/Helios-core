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

package info.gianlucacosta.helios.conditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * A condition that applies, in the same sequence, all the conditions passed
 * into its constructor
 */
public class CompositeCondition<TSubject> implements Condition<TSubject> {

    private final Collection<Condition<TSubject>> conditions;

    public CompositeCondition(Condition<TSubject>... conditions) {
        this(Arrays.asList(conditions));
    }

    public CompositeCondition(Collection<Condition<TSubject>> conditions) {
        this.conditions = new ArrayList<>(conditions);
    }

    @Override
    public void verify(TSubject subject) throws UnsatisfiedConditionException {
        for (Condition<TSubject> condition : conditions) {
            condition.verify(subject);
        }
    }

}
