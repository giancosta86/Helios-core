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

import info.gianlucacosta.helios.mathutils.Numbers

import scalafx.scene.control._

/**
  * Shows common input dialogs
  */
case object InputDialogs {
  /**
    * Shows a dialog with "Yes", "No" and "Cancel" button
    * @param message
    * @param header
    * @return Some(true) if the user chooses "Yes", Some(false) if the user chooses "No", None otherwise
    */
  def askYesNoCancel(message: String, header: String = ""): Option[Boolean] = {
    val yesButton = new ButtonType("Yes")
    val noButton = new ButtonType("No")
    val cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CancelClose)

    val alert = new Alert(Alert.AlertType.Confirmation) {
      headerText = header

      contentText = message

      buttonTypes = List(
        yesButton,
        noButton,
        cancelButton
      )
    }

    val inputResult = alert.showAndWait()

    inputResult match {
      case Some(`yesButton`) =>
        Some(true)

      case Some(`noButton`) =>
        Some(false)

      case _ =>
        None
    }
  }

  /**
    * Asks for a string, automatically trimming it
    *
    * @param message
    * @param initialValue
    * @param header
    * @return Some(user string, trimmed) or None
    */
  def askForString(message: String, initialValue: String = "", header: String = ""): Option[String] = {
    val inputDialog = new TextInputDialog(initialValue) {
      headerText = header
      contentText = message
      resizable = true
    }

    val inputResult = inputDialog.showAndWait()

    inputResult
      .map(_.trim)
  }


  /**
    * Keeps asking for a double value, checking that it is between the given bounds and
    * showing warnings if the user inputs an invalid value.
    *
    * @param message
    * @param initialValue
    * @param minValue
    * @param maxValue
    * @param header
    * @param formatter The Double=>String function used to format numbers. Defaults to Numbers.smartString
    * @return Some(input double) or None if the user canceled the dialog
    */
  def askForDouble(
                    message: String,
                    initialValue: Double = 0,
                    minValue: Double = Double.MinValue,
                    maxValue: Double = Double.MaxValue,
                    header: String = "",
                    formatter: Double=>String = Numbers.smartString
                  ): Option[Double] = {
    while (true) {
      val inputString = askForString(
        message,
        formatter(initialValue),
        header
      )

      if (inputString.isEmpty) {
        return None
      }


      try {
        val value = inputString.get.toDouble

        if (value < minValue || value > maxValue) {
          Alerts.showWarning(
            s"Please, enter a number in the range [${formatter(minValue)}; ${formatter(maxValue)}]", header)
        }

        return Some(value)

      } catch {
        case _: NumberFormatException =>
          Alerts.showWarning("Please, enter a numeric value", header)
      }
    }

    throw new AssertionError()
  }


  /**
    * Keeps asking for a Long value, checking that it is between the given bounds and
    * showing warnings if the user inputs an invalid value.
    *
    * @param message
    * @param initialValue
    * @param minValue
    * @param maxValue
    * @param header
    * @return Some(input long) or None if the user canceled the dialog
    */
  def askForLong(
                  message: String,
                  initialValue: Long = 0,
                  minValue: Long = Long.MinValue,
                  maxValue: Long = Long.MaxValue,
                  header: String = ""
                ): Option[Long] = {
    while (true) {
      val inputDoubleResult = askForDouble(message, initialValue, minValue, maxValue, header)

      if (inputDoubleResult.isEmpty) {
        return None
      }

      val longValueOption = Numbers.asLong(inputDoubleResult.get)

      longValueOption match {
        case Some(longValue) =>
          return longValueOption

        case None =>
          Alerts.showWarning("Please, input an integer number", header)
      }
    }

    throw new AssertionError()
  }


  /**
    * Asks the user to choose an item from a given list
    *
    * @param message The message
    * @param items The items list. Must not be empty
    * @param initialItem The initial item. If None, the first item in the list will be used
    * @param header The dialog's header
    * @tparam T The type of the items
    * @return Some(chosen item) or None
    */
  def askForItem[T](message: String, items: Seq[T], initialItem: Option[T] = None, header: String = ""): Option[T] = {
    require(items.nonEmpty)

    val choiceDialog = new ChoiceDialog[T](initialItem.getOrElse(items.head), items) {
      headerText = header

      contentText = message
    }

    choiceDialog.showAndWait()
  }
}
