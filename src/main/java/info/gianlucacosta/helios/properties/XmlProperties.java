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

package info.gianlucacosta.helios.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Properties created by reading from an XML source complying with Java's XML
 * format for property files.
 */
public class XmlProperties extends Properties {
    /**
     * Creates the Properties object from an XML input stream
     *
     * @param xmlInputStream The XML input stream
     * @throws IOException Thrown in case of parsing errors
     */
    public XmlProperties(InputStream xmlInputStream) throws IOException {
        loadFromXML(xmlInputStream);
    }

    /**
     * Loads the properties from the given resource path, that must be absolute
     *
     * @param absoluteResourcePath The resource path passed to the classloader; it must start with "/"
     * @throws IOException In case of I/O errors
     */
    public XmlProperties(String absoluteResourcePath) throws IOException {
        if (!absoluteResourcePath.startsWith("/")) {
            throw new IllegalArgumentException();
        }

        loadFromXML(XmlProperties.class.getResourceAsStream(absoluteResourcePath));
    }
}
