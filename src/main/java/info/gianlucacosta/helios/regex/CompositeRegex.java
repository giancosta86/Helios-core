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

import info.gianlucacosta.helios.collections.general.CollectionItems;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Pattern;

/**
 * A regex composed by multiple sub-regexes: if one of the sub-regexes matches,
 * the whole regex matches (the sub-regexes are joined by "|")
 *
 * @since 1.3
 */
public class CompositeRegex implements Serializable {

    private final Set<String> subRegexes;
    private transient Pattern pattern;

    public CompositeRegex(String... subRegexes) {
        this(Arrays.asList(subRegexes));
    }

    public CompositeRegex(Collection<String> subRegexes) {
        this.subRegexes = new LinkedHashSet<>(subRegexes);
    }

    public Pattern getPattern() {
        if (pattern == null) {
            Set<String> processedSubRegexes = new LinkedHashSet<>();

            for (String subRegex : subRegexes) {
                String processedSubRegex = processSubRegex(subRegex);
                processedSubRegexes.add(processedSubRegex);
            }

            String patternRegex = StringUtils.join(processedSubRegexes, "|");

            pattern = Pattern.compile(patternRegex);
        }

        return pattern;
    }

    /**
     * Retrieves the collection of sub-regexes
     *
     * @return the collection of sub-regexes, as passed to the constructor
     */
    public Collection<String> getSubRegexes() {
        return Collections.unmodifiableCollection(subRegexes);
    }

    /**
     * Processes a sub-regex before creating the Pattern object
     *
     * @param subRegex the sub-regex to process
     * @return the processed sub-regex that becomes part of the regex passed to
     * the Pattern constructor
     */
    protected String processSubRegex(String subRegex) {
        return subRegex;
    }

    /**
     * Shortcut method telling if there is a match between the composite regex
     * and the passed string
     *
     * @param subject the string to test
     * @return if there is a match
     */
    public boolean matches(String subject) {
        return getPattern().matcher(subject).matches();
    }

    /**
     * Two composite regexes are equal if and only if their collections of
     * sub-regexes are equal
     *
     * @param obj The object to test
     * @return true if the two composite regexes are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CompositeRegex)) {
            return false;
        }

        CompositeRegex other = (CompositeRegex) obj;

        return CollectionItems.equals(subRegexes, other.subRegexes);
    }

    @Override
    public int hashCode() {
        return getPattern().hashCode();
    }
}
