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

package info.gianlucacosta.helios.serialization.binary;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * An InputStream that instantiates objects using the provided ClassLoader
 * (which can be changed anytime using setClassLoader())
 */
class ClassLoaderBasedObjectInputStream extends ObjectInputStream {

    private ClassLoader classLoader = this.getClass().getClassLoader();

    public ClassLoaderBasedObjectInputStream(InputStream in) throws IOException {
        super(in);
    }

    public ClassLoaderBasedObjectInputStream() throws IOException, SecurityException {
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
        try {
            return classLoader.loadClass(desc.getName());
        } catch (ClassNotFoundException ex) {
            return super.resolveClass(desc);
        }
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

}
