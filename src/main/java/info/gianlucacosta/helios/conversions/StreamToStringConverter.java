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

package info.gianlucacosta.helios.conversions;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Converts an InputStream to a String
 */
public class StreamToStringConverter implements Converter<InputStream, String> {

    private final String encoding;

    /**
     * Creates the converter, setting the default encoding to UTF-8
     */
    public StreamToStringConverter() {
        this("UTF-8");
    }

    public StreamToStringConverter(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public String convert(InputStream source) {
        return new Scanner(source, encoding).useDelimiter("\\A").next();
    }

}
