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

package info.gianlucacosta.helios.jar;

import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Provides methods to inspect a jar file
 */
public class JarReflector implements AutoCloseable {

    private final JarFile jarFile;
    private final URLClassLoader classLoader;
    private final boolean usingInternalClassLoader;

    /**
     * Creates an internal class loader used by the inspection methods, that
     * will be automatically closed by the close() method
     *
     * @param jarFile The JAR file to inspect
     */
    public JarReflector(File jarFile) {
        this(jarFile, JarClassLoader.create(jarFile), true);
    }

    /**
     * Inspects the given JAR file using the given class loader, which will NOT
     * be closed when the close() method of this class is called.
     *
     * @param jarFile     The JAR file to inspect
     * @param classLoader The class loader to employ
     */
    public JarReflector(File jarFile, URLClassLoader classLoader) {
        this(jarFile, classLoader, false);
    }

    private JarReflector(File jarFile, URLClassLoader classLoader, boolean usingInternalClassLoader) {
        try {
            this.jarFile = new JarFile(jarFile);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        this.classLoader = classLoader;

        this.usingInternalClassLoader = usingInternalClassLoader;
    }

    @Override
    public void close() {
        try {
            jarFile.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        if (usingInternalClassLoader) {
            try {
                classLoader.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    /**
     * @return a list of fully-qualified class names, one for each class in the
     * JAR, in the same order as the JAR entries
     */
    public List<String> getFullClassNames() {
        List<String> result = new ArrayList<>();

        for (JarEntry jarEntry : Collections.list(jarFile.entries())) {
            String entryName = jarEntry.getName();

            if (entryName.endsWith(".class")) {
                entryName = entryName.replaceAll("/", ".");
                entryName = entryName.substring(0, entryName.length() - 6);

                result.add(entryName);
            }
        }

        return result;
    }

    /**
     * This method loads every class in the JAR, and returns a list of valid
     * Class objects, ready to be used for reflection.
     * <p>
     * Calling this method on a JAR with several classes might affect
     * performances.
     *
     * @return a list of all the classes in the jar
     */
    public List<Class<?>> getClasses() {
        List<Class<?>> result = new ArrayList<>();

        for (String fullClassName : getFullClassNames()) {
            try {
                Class<?> classObject = classLoader.loadClass(fullClassName);
                result.add(classObject);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }

        return result;
    }

}
