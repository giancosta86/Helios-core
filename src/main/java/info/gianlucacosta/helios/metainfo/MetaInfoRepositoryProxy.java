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
import info.gianlucacosta.helios.reflection.Locator;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Delegates every method to a metainfo repository obtained via a Locator,
 * <strong>except</strong> getId(), equals() and hashcode()
 */
public class MetaInfoRepositoryProxy implements MetaInfoRepository {

    private static final EqualMetaInfoRepositoriesPredicate EQUAL_META_INFO_REPOSITORIES_PREDICATE = new EqualMetaInfoRepositoriesPredicate();
    private final Locator<MetaInfoRepository> metaInfoRepositoryLocator;
    private final UUID id;

    public MetaInfoRepositoryProxy(Locator<MetaInfoRepository> metaInfoRepositoryLocator) {
        this.metaInfoRepositoryLocator = metaInfoRepositoryLocator;
        id = UUID.randomUUID();
    }

    private MetaInfoRepository getMetaInfoRepository() {
        return metaInfoRepositoryLocator.locate();
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public <T extends MetaInfo> void putMetaInfo(Object handle, T metainfo) {
        getMetaInfoRepository().putMetaInfo(handle, metainfo);
    }

    @Override
    public <T extends MetaInfo> void putMetaInfo(Object handle, T metainfo, Class<? extends MetaInfo> metaInfoClass) {
        getMetaInfoRepository().putMetaInfo(handle, metainfo, metaInfoClass);
    }

    @Override
    public <T extends MetaInfo> boolean hasMetaInfo(Object handle, Class<T> metaInfoClass) {
        return getMetaInfoRepository().hasMetaInfo(handle, metaInfoClass);
    }

    @Override
    public <T extends MetaInfo> T getMetaInfo(Object handle, Class<T> metaInfoClass) {
        return getMetaInfoRepository().getMetaInfo(handle, metaInfoClass);
    }

    @Override
    public void removeMetaInfo(Object handle) {
        getMetaInfoRepository().removeMetaInfo(handle);
    }

    @Override
    public <T extends MetaInfo> void removeMetaInfo(Object handle, Class<T> metaInfoClass) {
        getMetaInfoRepository().removeMetaInfo(handle, metaInfoClass);
    }

    @Override
    public Collection<Object> getHandles() {
        return getMetaInfoRepository().getHandles();
    }

    @Override
    public Map<Class<? extends MetaInfo>, MetaInfo> getHandleMetaInfoMap(Object handle) {
        return getMetaInfoRepository().getHandleMetaInfoMap(handle);
    }

    @Override
    public void clear() {
        getMetaInfoRepository().clear();
    }

    @Override
    public void addFrom(MetaInfoRepository source) {
        getMetaInfoRepository().addFrom(source);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MetaInfoRepository)) {
            return false;
        }

        MetaInfoRepository other = (MetaInfoRepository) obj;

        return EQUAL_META_INFO_REPOSITORIES_PREDICATE.evaluate(new SameTypePair<>(this, other));
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
