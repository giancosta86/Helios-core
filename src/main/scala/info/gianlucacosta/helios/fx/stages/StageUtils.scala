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

package info.gianlucacosta.helios.fx.stages

import javafx.stage.Stage

import info.gianlucacosta.helios.apps.AppInfo

import scala.collection.JavaConversions._

/**
  * Stage-related utility functions
  */
object StageUtils {
  /**
    * Assigns the given stage several icon sizes of the application's main icon
    *
    * @param stage   The target stage
    * @param appInfo The AppInfo object for the application
    */
  def setMainIcon(stage: Stage, appInfo: AppInfo): Unit = {
    val iconImages = Seq(16, 32, 64, 128).map(size =>
      appInfo.getMainIconImage(size)
    )

    stage.getIcons.setAll(iconImages)
  }
}
