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

package info.gianlucacosta.helios.fx.dialogs

import java.io.File
import javafx.stage.Window

import scala.collection.JavaConversions._
import scala.language.implicitConversions
import scalafx.stage.FileChooser


object FileChooserExtensions {
  private var latestChosenFiles = Map[FileChooser, File]()

  implicit def convertFileChooser(fileChooser: FileChooser): FileChooserExtensions =
    new FileChooserExtensions(fileChooser)
}


/**
  * Extension methods for FileChooser
  *
  * @param fileChooser
  */
class FileChooserExtensions private(fileChooser: FileChooser) {
  /**
    * Shows the <i>Open</i> dialog in the directory of its latest opened/saved file
    *
    * @param window
    * @return The chosen file, or null
    */
  def smartOpen(window: Window): File = {
    setupInitialDirectory()

    fileChooser.showOpenDialog(window)
  }


  /**
    * Shows the <i>Save</i> dialog:
    * <ol>
    * <li>In the directory of its latest opened/saved file</li>
    * <li>If no extension is provided, adds the first extension of the current extension filter,
    * unless it ends with * (as in *.*)</li>
    * </ol>
    *
    * @param window
    * @return The chosen file, or null
    */
  def smartSave(window: Window): File = {
    setupInitialDirectory()

    val chosenFile = fileChooser.showSaveDialog(window)
    if (chosenFile == null) {
      return null
    }

    FileChooserExtensions.latestChosenFiles += (fileChooser -> chosenFile)

    val fileHasExtension = chosenFile.getName.split('.').length > 1

    if (!fileHasExtension) {
      val selectedFilter = fileChooser.getSelectedExtensionFilter

      val filterMainExtension =
        selectedFilter.getExtensions
          .head
          .split('.')
          .last

      val defaultExtensionString =
        if (filterMainExtension.endsWith("*"))
          ""
        else
          "." + filterMainExtension

      new File(s"${chosenFile.getAbsolutePath}${defaultExtensionString}")
    } else {
      chosenFile
    }
  }


  private def setupInitialDirectory(): Unit = {
    val latestChosenFileOption = FileChooserExtensions.latestChosenFiles.get(fileChooser)

    latestChosenFileOption.foreach(latestChosenFile =>
      fileChooser.initialDirectory = latestChosenFile.getParentFile
    )
  }
}