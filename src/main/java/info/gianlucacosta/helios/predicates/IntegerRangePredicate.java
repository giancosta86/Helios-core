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

import java.util.Objects;

/**
 * Predicate that returns true if the passed integer value is in the range
 * [minimum, maximum] (bounds included)
 */
public class IntegerRangePredicate implements Predicate<Integer> {

    private final int minimum;
    private final int maximum;

    public IntegerRangePredicate() {
        this(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public IntegerRangePredicate(int minimum, int maximum) {
        if (minimum > maximum) {
            throw new IllegalArgumentException("It must be minimum <= maximum");
        }

        this.minimum = minimum;
        this.maximum = maximum;
    }

    @Override
    public boolean evaluate(Integer subject) {
        Objects.requireNonNull(subject);

        return (minimum <= subject) && (subject <= maximum);
    }

}
