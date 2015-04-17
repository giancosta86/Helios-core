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

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * A repository of metainfo: its purpose is to bind instances of MetaInfo to any
 * object, named "handle"
 */
public interface MetaInfoRepository {

    /**
     * The unique identifier of the MetaInfoRepository
     *
     * @return The repository identifier
     */
    UUID getId();

    /**
     * Associates an instance of Metainfo with a given handle, registering it
     * several times, once for each interface extending MetaInfo implemented by
     * the metainfo instance, both directly and indirectly (via inheritance)
     *
     * @param <T>      The metainfo class
     * @param handle   The object to be described
     * @param metainfo The related metainfo
     */
    <T extends MetaInfo> void putMetaInfo(Object handle, T metainfo);

    /**
     * Associates an instance of Metainfo with a given handle, forcing the
     * metainfo to be registered using a specific metainfo class, which will
     * have to be used when retrieving the metainfo.
     *
     * @param <T>           The metainfo class
     * @param handle        The object to be described
     * @param metainfo      The related metainfo
     * @param metaInfoClass The metainfo class that will have to be used in
     *                      order to retrieve that metainfo instance for the handle
     */
    <T extends MetaInfo> void putMetaInfo(Object handle, T metainfo, Class<? extends MetaInfo> metaInfoClass);

    /**
     * Checks if the given metainfo class has been associated with the given
     * handle
     *
     * @param <T>           The metainfo class
     * @param handle        The handle object
     * @param metaInfoClass The metainfo class used to register the metainfo
     *                      instance
     * @return true if an instance of the given metainfo class has been
     * registered for the given handle
     */
    <T extends MetaInfo> boolean hasMetaInfo(Object handle, Class<T> metaInfoClass);

    /**
     * Returns the metainfo instance associated with a given handle and
     * registered using the given metainfo class
     *
     * @param <T>           The metainfo class
     * @param handle        The handle object
     * @param metaInfoClass The metainfo class used to register the metainfo
     *                      instance
     * @return The metainfo instance itself, if found. Otherwise, a
     * MetaInfoException is thrown
     */
    <T extends MetaInfo> T getMetaInfo(Object handle, Class<T> metaInfoClass);

    /**
     * Removes all the metainfo associated with the given handle
     *
     * @param handle The handle to remove
     */
    void removeMetaInfo(Object handle);

    /**
     * Removes the metainfo associated with the given handle and registered via
     * the given metainfo class
     *
     * @param <T>           The metainfo class
     * @param handle        The handle to act on
     * @param metaInfoClass The class of the registered metainfo instance that
     *                      must be removed (remember that the same metainfo instance can be
     *                      registered multiple times, using different metainfo classes as different
     *                      keys)
     */
    <T extends MetaInfo> void removeMetaInfo(Object handle, Class<T> metaInfoClass);

    /**
     * @return a collection of all the handles registered into this repository
     */
    Collection<Object> getHandles();

    /**
     * Converts the metainfo for a given handle to a Map&lt;Class, MetaInfo&gt;
     *
     * @param handle the handle for which the metainfo map must be returned
     * @return a metainfo map
     */
    Map<Class<? extends MetaInfo>, MetaInfo> getHandleMetaInfoMap(Object handle);

    /**
     * Removes every registered metainfo instance
     */
    void clear();

    /**
     * Adds every metainfo instance from the given repository, without deleting
     * what's already within this repository (but overwriting is still possible)
     *
     * @param source The source repository
     */
    void addFrom(MetaInfoRepository source);

}
