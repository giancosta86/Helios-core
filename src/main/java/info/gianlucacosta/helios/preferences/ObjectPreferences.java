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

package info.gianlucacosta.helios.preferences;

import info.gianlucacosta.helios.conversions.ByteArrayToObjectConverter;
import info.gianlucacosta.helios.conversions.ObjectToByteArrayConverter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.prefs.BackingStoreException;
import java.util.prefs.NodeChangeListener;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;

/**
 * Decorator adding the capability of reading and writing objects to the
 * underlying Preferences instance
 */
public class ObjectPreferences extends Preferences {

    private static final ObjectToByteArrayConverter objectToByteArrayConverter = new ObjectToByteArrayConverter();
    private final Preferences decoratedPreferences;

    public ObjectPreferences(Preferences decoratedPreferences) {
        this.decoratedPreferences = decoratedPreferences;
    }

    /**
     * Reads an object from this Preferences
     *
     * @param <T> The return-value type
     * @param key The key to retrieve the object
     * @return The deserialized object, or null if the key was not found, or if
     * the value was set to null
     * @throws ObjectFromPreferencesException If any deserialization error
     *                                        occurs
     */
    public <T> T getObject(String key) throws ObjectFromPreferencesException {
        byte[] serializedObject = getByteArray(key, null);

        if (serializedObject == null) {
            return null;
        }

        try {
            ByteArrayToObjectConverter<T> byteArrayToObjectConverter = new ByteArrayToObjectConverter<>();
            return byteArrayToObjectConverter.convert(serializedObject);
        } catch (Exception ex) {
            throw new ObjectFromPreferencesException(ex);
        }
    }

    /**
     * Stores an object as an array of bytes, using serialization
     *
     * @param key   The key to store the object
     * @param value The object to serialize, or just null (in this case, null
     *              will be stored)
     * @throws ObjectToPreferencesException If any serialization error occurs
     */
    public void putObject(String key, Object value) throws ObjectToPreferencesException {
        if (value == null) {
            decoratedPreferences.put(key, null);
            return;
        }

        try {
            decoratedPreferences.putByteArray(key, objectToByteArrayConverter.convert(value));
        } catch (Exception ex) {
            throw new ObjectToPreferencesException(ex);
        }
    }

    @Override
    public void put(String key, String value) {
        decoratedPreferences.put(key, value);
    }

    @Override
    public String get(String key, String def) {
        return decoratedPreferences.get(key, def);
    }

    @Override
    public void remove(String key) {
        decoratedPreferences.remove(key);
    }

    @Override
    public void clear() throws BackingStoreException {
        decoratedPreferences.clear();
    }

    @Override
    public void putInt(String key, int value) {
        decoratedPreferences.putInt(key, value);
    }

    @Override
    public int getInt(String key, int def) {
        return decoratedPreferences.getInt(key, def);
    }

    @Override
    public void putLong(String key, long value) {
        decoratedPreferences.putLong(key, value);
    }

    @Override
    public long getLong(String key, long def) {
        return decoratedPreferences.getLong(key, def);
    }

    @Override
    public void putBoolean(String key, boolean value) {
        decoratedPreferences.putBoolean(key, value);
    }

    @Override
    public boolean getBoolean(String key, boolean def) {
        return decoratedPreferences.getBoolean(key, def);
    }

    @Override
    public void putFloat(String key, float value) {
        decoratedPreferences.putFloat(key, value);
    }

    @Override
    public float getFloat(String key, float def) {
        return decoratedPreferences.getFloat(key, def);
    }

    @Override
    public void putDouble(String key, double value) {
        decoratedPreferences.putDouble(key, value);
    }

    @Override
    public double getDouble(String key, double def) {
        return decoratedPreferences.getDouble(key, def);
    }

    @Override
    public void putByteArray(String key, byte[] value) {
        decoratedPreferences.putByteArray(key, value);
    }

    @Override
    public byte[] getByteArray(String key, byte[] def) {
        return decoratedPreferences.getByteArray(key, def);
    }

    @Override
    public String[] keys() throws BackingStoreException {
        return decoratedPreferences.keys();
    }

    @Override
    public String[] childrenNames() throws BackingStoreException {
        return decoratedPreferences.childrenNames();
    }

    @Override
    public Preferences parent() {
        return decoratedPreferences.parent();
    }

    @Override
    public Preferences node(String pathName) {
        return decoratedPreferences.node(pathName);
    }

    @Override
    public boolean nodeExists(String pathName) throws BackingStoreException {
        return decoratedPreferences.nodeExists(pathName);
    }

    @Override
    public void removeNode() throws BackingStoreException {
        decoratedPreferences.removeNode();
    }

    @Override
    public String name() {
        return decoratedPreferences.name();
    }

    @Override
    public String absolutePath() {
        return decoratedPreferences.absolutePath();
    }

    @Override
    public boolean isUserNode() {
        return decoratedPreferences.isUserNode();
    }

    @Override
    public String toString() {
        return decoratedPreferences.toString();
    }

    @Override
    public void flush() throws BackingStoreException {
        decoratedPreferences.flush();
    }

    @Override
    public void sync() throws BackingStoreException {
        decoratedPreferences.sync();
    }

    @Override
    public void addPreferenceChangeListener(PreferenceChangeListener pcl) {
        decoratedPreferences.addPreferenceChangeListener(pcl);
    }

    @Override
    public void removePreferenceChangeListener(PreferenceChangeListener pcl) {
        decoratedPreferences.removePreferenceChangeListener(pcl);
    }

    @Override
    public void addNodeChangeListener(NodeChangeListener ncl) {
        decoratedPreferences.addNodeChangeListener(ncl);
    }

    @Override
    public void removeNodeChangeListener(NodeChangeListener ncl) {
        decoratedPreferences.removeNodeChangeListener(ncl);
    }

    @Override
    public void exportNode(OutputStream os) throws IOException, BackingStoreException {
        decoratedPreferences.exportNode(os);
    }

    @Override
    public void exportSubtree(OutputStream os) throws IOException, BackingStoreException {
        decoratedPreferences.exportSubtree(os);
    }

    @Override
    public int hashCode() {
        return decoratedPreferences.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return decoratedPreferences.equals(obj);
    }

}
