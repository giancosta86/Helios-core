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

package info.gianlucacosta.helios.regex;

import java.io.File;
import java.util.Collection;

/**
 * CompositeRegex that processes its sub-regexes so as to replace occurrences of
 * "/" with regex-friendly versions of the current File.separator string.
 *
 * @since 1.3
 */
public class OsSpecificPathCompositeRegex extends CompositeRegex {

    private static final String regexCompatibleFileSeparator;

    static {
        switch (File.separator) {
            case "/":
                regexCompatibleFileSeparator = "/";
                break;
            case "\\":
                regexCompatibleFileSeparator = "\\\\\\\\";
                break;
            default:
                throw new RuntimeException("This operating system has an unknown path components separator");
        }
    }

    public OsSpecificPathCompositeRegex(Collection<String> subRegexes) {
        super(subRegexes);
    }

    public OsSpecificPathCompositeRegex(String... subRegexes) {
        super(subRegexes);
    }

    @Override
    protected String processSubRegex(String subRegex) {
        String result = super.processSubRegex(subRegex);

        result = result.replaceAll("/", regexCompatibleFileSeparator);

        return result;
    }
}
