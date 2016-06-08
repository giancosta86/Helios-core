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

import info.gianlucacosta.helios.apps.AppInfo
import info.gianlucacosta.helios.fx.stages.StageUtils

import scalafx.Includes._
import scalafx.application.Platform
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Label, ProgressIndicator}
import scalafx.scene.image.ImageView
import scalafx.scene.layout._
import scalafx.scene.paint.Color
import scalafx.stage.{Stage, StageStyle}

/**
  * A simple stage showing the application's icon and a status text while the app starts running.
  *
  * Its most important property is "statusText", which can be set from within any thread.
  *
  * @param appInfo The AppInfo object
  */
class SplashStage private[apps](appInfo: AppInfo) extends Stage(StageStyle.Undecorated) {
  title = s"Loading ${appInfo.title}..."

  private val statusLabel = Label {
    "Initializing the app..."
  }

  scene = new Scene {
    content = new VBox {
      background = new Background(Array(new BackgroundFill(Color.White, null, null)))

      spacing = 30
      padding = Insets(30)
      alignment = Pos.Center

      border = new javafx.scene.layout.Border(new BorderStroke(Color.DarkGrey, BorderStrokeStyle.Solid, null, null))

      children = Seq(
        new ImageView {
          image = appInfo.getMainIconImage(512)
        },

        statusLabel,

        new ProgressIndicator() {
          prefWidth = 64
          prefHeight = 64
        }
      )
    }
  }

  StageUtils.setMainIcon(this, appInfo)

  sizeToScene()

  centerOnScreen()


  def statusText: String =
    statusLabel.text()


  def statusText_=(statusText: String): Unit = {
    Platform.runLater {
      statusLabel.text = statusText
    }
  }
}
