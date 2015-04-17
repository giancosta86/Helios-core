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

package info.gianlucacosta.helios.product;

import java.util.Properties;

/**
 * Reads product information from a Properties object.
 */
public class PropertyProductInfoService implements ProductInfoService {
    private final Properties productProperties;

    /**
     * The service will read product information from the given Properties object.
     *
     * @param productProperties Contains the source properties
     */
    public PropertyProductInfoService(Properties productProperties) {
        this.productProperties = productProperties;
    }


    @Override
    public String getName() {
        return productProperties.getProperty("name");
    }

    @Override
    public String getVersion() {
        return productProperties.getProperty("version");
    }

    @Override
    public String getTitle() {
        return String.format("%s %s", getName(), getVersion());
    }

    @Override
    public String getCopyrightYears() {
        return productProperties.getProperty("copyrightYears");
    }

    @Override
    public String getLicense() {
        return productProperties.getProperty("license");
    }

    @Override
    public String getWebsite() {
        return productProperties.getProperty("website");
    }

    @Override
    public String getFacebookPage() {
        return productProperties.getProperty("facebookPage");
    }


    @Override
    public boolean isRelease() {
        return Boolean.parseBoolean(productProperties.getProperty("release"));
    }


    protected String getProductProperty(String key) {
        return productProperties.getProperty(key);
    }
}
