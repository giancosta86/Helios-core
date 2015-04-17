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

/**
 * Basic implementation of MetaInfoAgent.
 * <p>
 * If requested via the related constructor parameter, the act() method is
 * transactional: if that method returns false, the agent automatically restores
 * the metainfo repository to its previous state.
 */
public abstract class AbstractMetaInfoAgent<THandle> implements MetaInfoAgent<THandle> {

    private final boolean transactional;

    public AbstractMetaInfoAgent(boolean transactional) {
        this.transactional = transactional;
    }

    @Override
    public boolean act(MetaInfoRepository metaInfoRepository, THandle handle) {
        if (transactional) {
            MetaInfoRepository backupRepository = new DefaultMetaInfoRepository(metaInfoRepository);

            if (!doAct(metaInfoRepository, handle)) {
                metaInfoRepository.clear();
                metaInfoRepository.addFrom(backupRepository);

                return false;
            }

            return true;
        } else {
            return doAct(metaInfoRepository, handle);
        }
    }

    protected abstract boolean doAct(MetaInfoRepository metaInfoRepository, THandle handle);
}
