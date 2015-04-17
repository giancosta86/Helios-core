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

package info.gianlucacosta.helios.predicates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Predicate that evaluates to true if and only if all the predicates passed
 * into its constructor evaluate to true
 */
public class CompositePredicate<TSubject> implements Predicate<TSubject> {

    private final Collection<Predicate<TSubject>> predicates;

    public CompositePredicate(Predicate<TSubject>... predicates) {
        this(Arrays.asList(predicates));
    }

    public CompositePredicate(Collection<Predicate<TSubject>> predicates) {
        this.predicates = new ArrayList<>(predicates);
    }

    @Override
    public boolean evaluate(TSubject subject) {
        for (Predicate<TSubject> predicate : predicates) {
            if (!predicate.evaluate(subject)) {
                return false;
            }
        }

        return true;
    }

}
