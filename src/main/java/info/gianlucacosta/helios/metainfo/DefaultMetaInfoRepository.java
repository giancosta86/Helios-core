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
import org.apache.commons.lang3.ClassUtils;

import java.util.*;

/**
 * Basic implementation of MetaInfoRepository.
 * <p>
 * This class enforces the use of interfaces: whenever a "meta info class" is
 * required by a method (for example, getMetaInfo()), the passed class object
 * must be an interface extending MetaInfo.
 */
public class DefaultMetaInfoRepository implements MetaInfoRepository {

    private static final EqualMetaInfoRepositoriesPredicate EQUAL_META_INFO_REPOSITORIES_PREDICATE = new EqualMetaInfoRepositoriesPredicate();
    private final UUID id;
    private final Map<Object, Map<Class<? extends MetaInfo>, MetaInfo>> globalMetaInfoMap = new HashMap<>();

    public DefaultMetaInfoRepository() {
        id = UUID.randomUUID();
    }

    /**
     * Copies all the items from the source repository,
     * <em>as well as its id</em>
     *
     * @param source The source repository
     */
    public DefaultMetaInfoRepository(MetaInfoRepository source) {
        id = source.getId();

        for (Object handle : source.getHandles()) {
            Map<Class<? extends MetaInfo>, MetaInfo> handleMetaInfoMap = source.getHandleMetaInfoMap(handle);
            globalMetaInfoMap.put(handle, new HashMap<>(handleMetaInfoMap));
        }
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public <T extends MetaInfo> void putMetaInfo(Object handle, T metaInfo) {
        List<Class<?>> supportedInterfaces = ClassUtils.getAllInterfaces(metaInfo.getClass());
        List<Class<?>> metaInfoInterfaces = new ArrayList<>();

        for (Class<?> supportedInterface : supportedInterfaces) {
            if (MetaInfo.class.isAssignableFrom(supportedInterface) && (supportedInterface != MetaInfo.class)) {
                metaInfoInterfaces.add(supportedInterface);
            }
        }

        if (metaInfoInterfaces.isEmpty()) {
            throw new IllegalArgumentException("No metainfo interfaces supported");
        }

        for (Class<?> metaInfoInterface : metaInfoInterfaces) {
            putMetaInfo(handle, metaInfo, (Class<? extends MetaInfo>) metaInfoInterface);
        }
    }

    @Override
    public <T extends MetaInfo> void putMetaInfo(Object handle, T metainfo, Class<? extends MetaInfo> metaInfoClass) {
        if (!metaInfoClass.isInterface()) {
            throw new IllegalArgumentException("Metainfo can only be managed via interfaces");
        }

        if (!metaInfoClass.isAssignableFrom(metaInfoClass)) {
            throw new IllegalArgumentException();
        }

        Map<Class<? extends MetaInfo>, MetaInfo> handleMetaInfoMap = globalMetaInfoMap.get(handle);
        if (handleMetaInfoMap == null) {
            handleMetaInfoMap = new HashMap<>();
            globalMetaInfoMap.put(handle, handleMetaInfoMap);
        }

        handleMetaInfoMap.put(metaInfoClass, metainfo);
    }

    @Override
    public <T extends MetaInfo> boolean hasMetaInfo(Object handle, Class<T> metaInfoClass) {
        if (!metaInfoClass.isInterface()) {
            throw new IllegalArgumentException("Metainfo can only be managed via interfaces");
        }

        Map<Class<? extends MetaInfo>, MetaInfo> handleMetaInfoMap = globalMetaInfoMap.get(handle);
        if (handleMetaInfoMap == null) {
            return false;
        }

        return handleMetaInfoMap.containsKey(metaInfoClass);
    }

    @Override
    public <T extends MetaInfo> T getMetaInfo(Object handle, Class<T> metaInfoClass) {
        if (!metaInfoClass.isInterface()) {
            throw new IllegalArgumentException("Metainfo can only be managed via interfaces");
        }

        Map<Class<? extends MetaInfo>, MetaInfo> handleMetaInfoMap = globalMetaInfoMap.get(handle);

        if (handleMetaInfoMap != null && handleMetaInfoMap.containsKey(metaInfoClass)) {
            return (T) handleMetaInfoMap.get(metaInfoClass);
        } else {
            throw new MetaInfoException(String.format("The requested metainfo - '%s' - cannot be found attached to the specified handle of class '%s'", metaInfoClass.getName(), handle.getClass().getName()));
        }
    }

    @Override
    public void removeMetaInfo(Object handle) {
        globalMetaInfoMap.remove(handle);
    }

    @Override
    public <T extends MetaInfo> void removeMetaInfo(Object handle, Class<T> metaInfoClass) {
        if (!metaInfoClass.isInterface()) {
            throw new IllegalArgumentException("Metainfo can only be managed via interfaces");
        }

        Map<Class<? extends MetaInfo>, MetaInfo> handleMetaInfoMap = globalMetaInfoMap.get(handle);

        if (handleMetaInfoMap == null) {
            return;
        }

        handleMetaInfoMap.remove(metaInfoClass);

        if (handleMetaInfoMap.isEmpty()) {
            globalMetaInfoMap.remove(handle);
        }
    }

    @Override
    public Collection<Object> getHandles() {
        return Collections.unmodifiableCollection(globalMetaInfoMap.keySet());
    }

    @Override
    public Map<Class<? extends MetaInfo>, MetaInfo> getHandleMetaInfoMap(Object handle) {
        Map<Class<? extends MetaInfo>, MetaInfo> handleMetaInfoMap = globalMetaInfoMap.get(handle);
        return Collections.unmodifiableMap(handleMetaInfoMap);
    }

    protected Map<Object, Map<Class<? extends MetaInfo>, MetaInfo>> getGlobalMetaInfoMap() {
        return globalMetaInfoMap;
    }

    @Override
    public void clear() {
        globalMetaInfoMap.clear();
    }

    @Override
    public void addFrom(MetaInfoRepository source) {
        for (Object handle : source.getHandles()) {
            Map<Class<? extends MetaInfo>, MetaInfo> handleMetaInfoMap = source.getHandleMetaInfoMap(handle);

            for (Map.Entry<Class<? extends MetaInfo>, MetaInfo> handleMetaInfoEntry : handleMetaInfoMap.entrySet()) {
                Class<? extends MetaInfo> key = handleMetaInfoEntry.getKey();
                MetaInfo metainfo = handleMetaInfoEntry.getValue();

                putMetaInfo(handle, metainfo, key);
            }
        }
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
