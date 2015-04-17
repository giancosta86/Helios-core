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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * ClassLoader that reads class definitions from a collection of jar files.
 * <p>
 * If a class is not found in the given jar files, the parent classloader is
 * asked for it.
 */
public class JarClassLoader extends URLClassLoader {

    public static JarClassLoader create(File... jarFiles) {
        return create(Arrays.asList(jarFiles));
    }

    public static JarClassLoader create(Collection<File> jarFiles) {
        List<URL> jarUrls = new ArrayList<>();

        for (File jarFile : jarFiles) {
            try {
                URL jarUrl = new URL("jar", "", String.format("file:%s!/",
                        jarFile.getAbsolutePath()));

                jarUrls.add(jarUrl);
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }
        }

        return new JarClassLoader(jarUrls.toArray(new URL[0]));
    }

    private JarClassLoader(URL[] jarUrls) {
        super(jarUrls, JarClassLoader.class.getClassLoader());
    }
}
