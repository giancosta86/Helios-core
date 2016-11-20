/*^
  ===========================================================================
  Helios - Core
  ===========================================================================
  Copyright (C) 2013-2016 Gianluca Costa
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

package info.gianlucacosta.helios.apps

import java.io.InputStream
import java.lang.reflect.Method

/**
  * AppInfo implementation employing reflection to retrieve information from the objects
  * automatically generated by <a href="https://github.com/giancosta86/Aurora">Aurora</a>.
  *
  * @param artifactInfoObject ArtifactInfo object generated for the app
  * @param iconsObject        MainIcon object generated for the app's main icon
  */
case class AuroraAppInfo(artifactInfoObject: Any, iconsObject: Any) extends AppInfo {
  private lazy val iconsObjectClass: Class[_] = iconsObject.getClass
  private lazy val iconGetter: Method = iconsObjectClass.getMethod("get", classOf[Int])

  override val name: String = readField("name")
  override val version: Version = Version.parse(readField("version"))
  override val title: String = readField("title")

  override val license: String = readField("license")

  override val copyrightYears: String = readField("copyrightYears")
  override val copyrightHolder: String = readField("copyrightHolder")

  override val website: String = readField("website")
  override val facebookPage: String = readField("facebookPage")


  private def readField[T](fieldName: String): T = {
    artifactInfoObject.getClass.getMethod(fieldName).invoke(artifactInfoObject).asInstanceOf[T]
  }

  override def getMainIcon(size: Int): InputStream = {
    iconGetter.invoke(iconsObject, new Integer(size)).asInstanceOf[InputStream]
  }
}
