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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Metainfo agent that sequentially executes all of its composing metainfo
 * agents.
 * <p>
 * It is always transactional.
 */
public class CompositeMetaInfoAgent<THandle> extends AbstractMetaInfoAgent<THandle> {

    private final Collection<MetaInfoAgent<THandle>> metaInfoAgents;

    public CompositeMetaInfoAgent(MetaInfoAgent<THandle>... metaInfoAgents) {
        this(Arrays.asList(metaInfoAgents));
    }

    public CompositeMetaInfoAgent(Collection<MetaInfoAgent<THandle>> metaInfoAgents) {
        super(true);
        this.metaInfoAgents = new ArrayList<>(metaInfoAgents);
    }

    @Override
    protected boolean doAct(MetaInfoRepository metaInfoRepository, THandle handle) {
        for (MetaInfoAgent<THandle> metaInfoAgent : metaInfoAgents) {
            if (!metaInfoAgent.act(metaInfoRepository, handle)) {
                return false;
            }
        }

        return true;
    }
}
