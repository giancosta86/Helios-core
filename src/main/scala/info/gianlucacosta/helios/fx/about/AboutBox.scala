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

package info.gianlucacosta.helios.fx.about

import javafx.fxml.FXMLLoader

import info.gianlucacosta.helios.apps.AppInfo
import info.gianlucacosta.helios.fx.stages.StageUtils

import scalafx.application.Platform
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ButtonBar, ButtonType}

/**
  * Dialog showing the application's information.
  *
  * This class can be instantiated in ANY thread - GUI operations are automatically
  * run on the GUI thread.
  *
  * @param appInfo an AppInfo object - for example, an instance of AuroraAppInfo
  */
class AboutBox(appInfo: AppInfo) extends Alert(AlertType.None) {
  private val loader = new FXMLLoader(this.getClass.getResource("AboutBox.fxml"))
  private val root = loader.load[javafx.scene.layout.Pane]
  private val controller = loader.getController[AboutBoxController]


  Platform.runLater {
    controller.setup(appInfo)

    dialogPane().setContent(root)
  }


  Platform.runLater {
    buttonTypes = Seq(
      new ButtonType("OK", ButtonBar.ButtonData.OKDone)
    )

    title = s"About ${appInfo.name}..."
  }
}
