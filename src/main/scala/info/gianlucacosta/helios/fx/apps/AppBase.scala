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

import javafx.application.Application
import javafx.stage.Stage

import info.gianlucacosta.helios.apps.AppInfo
import info.gianlucacosta.helios.fx.stages.StageUtils

import scalafx.scene.image.Image

/**
  * Abstract app automatically showing a splash screen (displaying the app's main icon) during its
  * initialization phase.
  *
  * Furthermore, the primary stage is automatically assigned a title, icons, a default size and
  * its's centered on the screen.
  * <p>
  * The startup() method runs in a dedicated non-GUI thread and must be implemented to perform:
  * <ol>
  *   <li>Data initialization</li>
  *   <li>GUI initialization (via Platform.runLater{}). In particular, you should assign a scene
  *   to primaryStage</li>
  * </ol>
  * @param appInfo
  */
abstract class AppBase(appInfo: AppInfo) extends Application {
  override def start(primaryStage: Stage): Unit = {
    val splashStage = new SplashStage(appInfo)
    splashStage.show()

    initPrimaryStage(primaryStage)

    new StartupThread(appInfo, splashStage, primaryStage, startup) {
      start()
    }
  }


  /**
    * Method invoked to perform the app's startup tasks within a non-GUI thread.
    * <p>
    * Of course, Platform.runLater{} is recommended to run GUI operations except the dedicated
    * methods provided by SplashStage.
    * <p>
    * There is no need to check for exceptions; in case of exception:
    * <ol>
    *   <li>The stack trace is printed to stderr</li>
    *   <li>An error message is shown to the user</li>
    *   <li>The application exits</li>
    * </ol>
    *
    * @param appInfo Information about the app
    * @param splashStage The application's splash stage
    * @param primaryStage The application's provided stage
    */
  protected def startup(appInfo: AppInfo, splashStage: SplashStage, primaryStage: Stage)


  private def initPrimaryStage(primaryStage: Stage): Unit = {
    primaryStage.setTitle(appInfo.title)
    primaryStage.setWidth(800)
    primaryStage.setHeight(600)
    primaryStage.centerOnScreen()

    StageUtils.setMainIcon(primaryStage, appInfo)
  }
}