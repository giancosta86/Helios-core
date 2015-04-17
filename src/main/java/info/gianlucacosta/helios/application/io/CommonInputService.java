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

package info.gianlucacosta.helios.application.io;

import info.gianlucacosta.helios.conditions.Condition;

import java.util.Collection;

/**
 * Service that asks the user for several common kinds of input
 */
public interface CommonInputService {

    String askForString(String prompt, String initialValue);

    String askForString(String prompt, String initialValue, Condition<String> valueCondition);

    Integer askForInteger(String prompt, Integer initialValue);

    Integer askForInteger(String prompt, Integer initialValue, Condition<Integer> valueCondition);

    Double askForDouble(String prompt, Double initialValue);

    Double askForDouble(String prompt, Double initialValue, Condition<Double> valueCondition);

    <TValue> TValue askForItem(String prompt, Collection<? extends TValue> sourceValues);

    <TValue> TValue askForItem(String prompt, Collection<? extends TValue> sourceValues, TValue initialValue);

    <TValue> TValue askForItem(String prompt, Collection<? extends TValue> sourceValues, TValue initialValue, Condition<TValue> valueCondition);

    CommonQuestionOutcome askYesNoQuestion(String message);

    CommonQuestionOutcome askYesNoQuestion(String title, String message);

    CommonQuestionOutcome askYesNoCancelQuestion(String message);

    CommonQuestionOutcome askYesNoCancelQuestion(String title, String message);

}
