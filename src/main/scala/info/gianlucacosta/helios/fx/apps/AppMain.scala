/*§
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

package info.gianlucacosta.helios.fx.apps

import java.io.InputStream
import java.net.URL
import javafx.application.Application

import scalafx.scene.image.Image

/**
  * Abstract class designed to bootstrap a JavaFX application.
  * <p>
  * To use it in practice, just create a Scala object inheriting from it - closing its generic
  * parameter with a subclass of Application (in particular, of AppBase); no class body is needed:
  * such class can be employed as startup class in a Gradle script or any IDE.
  * <p>
  * Finally, it provides useful methods to access global app resources.
  *
  * @param appClass The application's class (required as generics are not reified)
  * @tparam TAppClass The application's class
  */
abstract class AppMain[TAppClass <: Application](appClass: Class[TAppClass]) {
  def main(args: Array[String]): Unit = {
    Application.launch(appClass, args: _*)
  }


  def getResource(path: String): URL =
    getClass.getResource(path)


  def getResourceAsStream(path: String): InputStream =
    getClass.getResourceAsStream(path)


  def getResourceImage(path: String): Image =
    new Image(getClass.getResourceAsStream(path))
}
