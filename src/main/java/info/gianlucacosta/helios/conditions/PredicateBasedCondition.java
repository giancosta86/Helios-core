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

import info.gianlucacosta.helios.predicates.Predicate;

/**
 * A condition that is satisfied only if a predicate evaluates to true
 */
public class PredicateBasedCondition<TSubject> implements Condition<TSubject> {

    private final Predicate<TSubject> predicate;
    private final String failureMessage;

    public PredicateBasedCondition(Predicate<TSubject> predicate, String failureMessage) {
        this.predicate = predicate;
        this.failureMessage = failureMessage;
    }

    @Override
    public void verify(TSubject subject) throws UnsatisfiedConditionException {
        if (!predicate.evaluate(subject)) {
            throw new UnsatisfiedConditionException(failureMessage);
        }
    }

}
