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

import java.util.function.{Consumer, Predicate}
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout.Region

import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

/**
  * Common alerts and related utilities
  */
case object Alerts {
  private def showAlert(alertType: AlertType, message: String, header: String): Unit = {
    val alert = new Alert(alertType) {
      headerText = header
      contentText = message
      resizable = true
    }

    fix(alert)

    alert.showAndWait()
  }


  /**
    * Fixes dialogs layout on some Linux window systems
    *
    * @param alert The alert to be fixed
    */
  def fix(alert: Alert): Unit = {
    alert
      .dialogPane()
      .getChildren
      .filtered(new Predicate[Node] {
        override def test(node: Node): Boolean = node.isInstanceOf[Label]
      })
      .forEach(new Consumer[Node] {
        override def accept(node: Node): Unit = {
          node.asInstanceOf[Label].setMinHeight(Region.USE_PREF_SIZE)
        }
      })
  }


  /**
    * Shows an information box
    *
    * @param message
    * @param header
    */
  def showInfo(message: String, header: String = ""): Unit =
    showAlert(AlertType.Information, message, header)


  /**
    * Shows a warning box
    *
    * @param message
    * @param header
    */
  def showWarning(message: String, header: String = ""): Unit =
    showAlert(AlertType.Warning, message, header)


  /**
    * Shows an error box
    *
    * @param message
    * @param header
    */
  def showError(message: String, header: String = ""): Unit =
    showAlert(AlertType.Error, message, header)


  /**
    * Shows an exception box. More precisely, its message is:
    * <ul>
    * <li>The exception's <i>localized message</i>, if it is not null or empty</li>
    * <li>The simple name of the exception's class otherwise</li>
    * </ul>
    *
    * Furthermore, if its alertType is set to <i>Error</i> (the default),
    * the exception's stack trace is printed out to stderr.
    *
    * @param exception
    * @param header
    * @param alertType
    */
  def showException(exception: Exception, header: String = "", alertType: AlertType = AlertType.Error): Unit = {
    if (alertType == AlertType.Error) {
      exception.printStackTrace(System.err)
    }

    val alertText =
      if (exception.getLocalizedMessage != null && exception.getLocalizedMessage.nonEmpty)
        exception.getLocalizedMessage
      else
        exception.getClass.getSimpleName


    showAlert(
      alertType,
      alertText,
      header
    )
  }
}
