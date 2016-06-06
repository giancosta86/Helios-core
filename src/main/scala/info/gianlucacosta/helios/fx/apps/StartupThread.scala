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

import javafx.stage.Stage

import info.gianlucacosta.helios.apps.AppInfo
import info.gianlucacosta.helios.fx.dialogs.Alerts

import scalafx.application.Platform


private class StartupThread(
                             appInfo: AppInfo,
                             splashStage: SplashStage,
                             primaryStage: Stage,
                             startupCallback: AppStartupCallback
                           ) extends Thread {
  setDaemon(true)

  override def run(): Unit = {
    try {
      startupCallback(appInfo, splashStage, primaryStage)

      Platform.runLater {
        primaryStage.show()
        splashStage.close()
      }
    } catch {
      case ex: Exception =>
        Platform.runLater {
          Alerts.showException(ex, "Startup error")

          System.exit(1)
        }
    }
  }
}