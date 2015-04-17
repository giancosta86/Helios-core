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

package info.gianlucacosta.helios.application;

import java.io.IOException;
import java.util.Properties;

/**
 * Implementation of AppInfoService reading its data from a property file.
 * <p>
 * The property file must be named "Application.properties" and must reside in
 * the same package as the reference class passed in the constructor.
 * <p>
 * The keys of the property file must be in the format
 * <i>app.&lt;propertyName&gt;</i>, where <i>&lt;propertyName&gt;</i> is one of
 * the properties exposed by this class (eg: <i>app.name</i>). An exception to
 * this rule is the Title property, which combines <i>app.name</i> with
 * <i>app.version</i>, without reading them from the properties.
 *
 * @deprecated Use PropertyProductInfoService instead.
 */
@Deprecated
public class PropertyBasedAppInfoService implements AppInfoService {

    private final Properties appProperties;

    public PropertyBasedAppInfoService(String referenceClassName) throws ClassNotFoundException {
        this(Class.forName(referenceClassName));
    }

    public PropertyBasedAppInfoService(Class<?> referenceClass) {
        appProperties = new Properties();

        loadAppProperties(referenceClass);
    }

    private void loadAppProperties(Class<?> referenceClass) {
        try {
            appProperties.load(referenceClass.getResourceAsStream("Application.properties"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String getName() {
        return appProperties.getProperty("app.name");
    }

    @Override
    public String getVersion() {
        return appProperties.getProperty("app.version");
    }

    @Override
    public String getTitle() {
        return String.format("%s %s", getName(), getVersion());
    }

    @Override
    public String getCopyrightYears() {
        return appProperties.getProperty("app.copyrightYears");
    }

    @Override
    public String getLicense() {
        return appProperties.getProperty("app.license");
    }

    @Override
    public String getWebsite() {
        return appProperties.getProperty("app.website");
    }

    public String getFacebookPage() {
        return appProperties.getProperty("app.facebookPage");
    }


    @Override
    @Deprecated
    public boolean isInDebugging() {
        return !isRelease();
    }

    @Override
    public boolean isRelease() {
        return Boolean.parseBoolean(appProperties.getProperty("app.release"));
    }

    protected String getAppProperty(String propertyName) {
        return appProperties.getProperty(propertyName);
    }
}
