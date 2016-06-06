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

package info.gianlucacosta.helios.fx.workspace

import java.io.File
import javafx.beans.property.{SimpleBooleanProperty, SimpleObjectProperty}
import javafx.event.EventHandler
import javafx.stage.{Stage, WindowEvent}

import info.gianlucacosta.helios.fx.dialogs.{Alerts, InputDialogs}
import info.gianlucacosta.helios.fx.dialogs.FileChooserExtensions._

import scalafx.stage.FileChooser

import scalafx.Includes._

/**
  * Single-document-interface (SDI) workspace.
  *
  * Main features:
  * <ul>
  *   <li>
  *     <i>documentFile</i> and <i>modified</i> Java & JavaFX properties, to handle the current
  *     document's status
  *   </li>
  *
  *   <li>
  *     <i>New/Open/Save/Save As</i> actions, that employ the abstract methods as well as the
  *     FileChooser (applying the extension methods provided by Helios) to suitably handle the
  *     document's lifecycle
  *   </li>
  *
  *   <li>
  *     Shows a stacktrace and an error dialog in case of exceptions, so you don't need
  *     to worry about exceptions in your implementations.
  *   </li>
  *
  *   <li>
  *     When the user closes the given stage and the document was modified,
  *     a confirmation dialog is shown
  *   </li>
  * </ul>
  *
  * @param stage The target stage for this workspace (usually the primary stage)
  * @param documentFileChooser The file chooser to open/save documents
  */
abstract class Workspace(stage: Stage, documentFileChooser: FileChooser) {
  val documentFileProperty = new SimpleObjectProperty[Option[File]](None)
  val modifiedProperty = new SimpleBooleanProperty(false)


  stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler[WindowEvent] {
    override def handle(event: WindowEvent): Unit = {
      if (!canLeaveDocument) {
        event.consume()
      }
    }
  })


  def documentFile: Option[File] = {
    documentFileProperty.get
  }

  private def documentFile_=(value: Option[File]) {
    documentFileProperty.set(value)
  }


  def modified: Boolean =
    modifiedProperty.get

  private def modified_=(value: Boolean) {
    modifiedProperty.set(value)
  }


  /**
    * Sets the workspace's "modified" flag to true
    */
  def setModified(): Unit = {
    modified = true
  }


  def newDocument(): Unit = {
    try {
      if (!canLeaveDocument) {
        return
      }

      if (!doNew()) {
        return
      }

      documentFile = None
      modified = false
    } catch {
      case ex: Exception =>
        Alerts.showException(ex)
    }
  }

  /**
    * Creates the new document
    *
    * @return true is the document was successfully created
    */
  protected def doNew(): Boolean


  def openDocument(): Unit = {
    try {
      if (!canLeaveDocument) {
        return
      }

      val selectedFile = documentFileChooser.smartOpen(stage)
      if (selectedFile == null) {
        return
      }


      if (!doOpen(selectedFile)) {
        return
      }

      documentFile = Some(selectedFile)
      modified = false
    } catch {
      case ex: Exception =>
        Alerts.showException(ex)
    }
  }


  /**
    * Opens a document from the given file
    *
    * @param sourceFile The source file
    * @return true if the document was correctly opened
    */
  protected def doOpen(sourceFile: File): Boolean


  def saveDocument(): Unit = {
    try {
      documentFile
        .map(file => {
          val saveResult = doSave(file)

          if (saveResult) {
            modified = false
          }

          saveResult
        })
        .getOrElse(
          saveAsDocument()
        )
    } catch {
      case ex: Exception =>
        Alerts.showException(ex)
    }
  }


  def saveAsDocument(): Unit = {
    try {
      val selectedFile = documentFileChooser.smartSave(stage)
      if (selectedFile == null) {
        return
      }

      if (!doSave(selectedFile)) {
        return
      }

      documentFile = Some(selectedFile)
      modified = false
    }
    catch {
      case ex: Exception =>
        Alerts.showException(ex)
    }
  }


  /**
    * Saves the document to the given file
    *
    * @param targetFile The target file
    *
    * @return true if the document was successfully saved
    */
  protected def doSave(targetFile: File): Boolean


  /**
    * Closes the stage, as if the used clicked on its close button.
    *
    * Consequently, a confirmation dialog will be shown if there are pending changes.
    */
  def closeStage() {
    val closeEvent = new WindowEvent(
      stage,
      WindowEvent.WINDOW_CLOSE_REQUEST
    )

    stage.fireEvent(closeEvent)
  }


  private def canLeaveDocument: Boolean = {
    if (!modified) {
      return true
    }

    InputDialogs.askYesNoCancel("Do you wish to save your work?") match {
      case Some(true) =>
        saveDocument()
        !modified
      case Some(false) =>
        true
      case _ =>
        false
    }
  }
}
