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

package info.gianlucacosta.helios.metainfo;

import info.gianlucacosta.helios.collections.general.SameTypePair;
import info.gianlucacosta.helios.predicates.Predicate;

import java.util.Objects;

/**
 * Returns true if two metainfo repositories are equal
 * <p>
 * Two metainfo repositories are equal if and only if their ids are equal.
 */
public class EqualMetaInfoRepositoriesPredicate implements Predicate<SameTypePair<MetaInfoRepository>> {

    @Override
    public boolean evaluate(SameTypePair<MetaInfoRepository> metaInfoRepositories) {
        MetaInfoRepository leftMetaInfoRepository = metaInfoRepositories.getLeft();
        MetaInfoRepository rightMetaInfoRepository = metaInfoRepositories.getRight();

        return Objects.equals(leftMetaInfoRepository.getId(), rightMetaInfoRepository.getId());
    }

}
