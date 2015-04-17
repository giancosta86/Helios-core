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

import info.gianlucacosta.helios.serialization.LoadingContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * LoadingContext supporting binary serialization
 */
class BinaryLoadingContext implements LoadingContext {

    private final ClassLoaderBasedObjectInputStream inputStream;

    public BinaryLoadingContext(InputStream inputStream) {
        try {
            this.inputStream = new ClassLoaderBasedObjectInputStream(inputStream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    @Override
    public ClassLoader getClassLoader() {
        return inputStream.getClassLoader();
    }

    @Override
    public void setClassLoader(ClassLoader classLoader) {
        inputStream.setClassLoader(classLoader);
    }

    @Override
    public void close() throws Exception {
        inputStream.close();
    }

}
