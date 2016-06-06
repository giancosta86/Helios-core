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

package info.gianlucacosta.helios.desktop

import java.awt.Desktop
import java.io.{File, IOException}
import java.net.URI
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{FileVisitResult, Files, Path, SimpleFileVisitor}

/**
  * Desktop utilities
  */
object DesktopUtils {
  /**
    * Invoked when an exception occurs
    */
  type ExceptionCallback = (Exception) => Unit

  private val EmptyExceptionCallBack: ExceptionCallback = (ex: Exception) => {}


  private def runInThread(action: (Desktop) => Unit, exceptionCallback: ExceptionCallback) {
    val externalThread = new Thread() {
      override def run() {
        try {
          val desktop = Desktop.getDesktop

          if (desktop == null) {
            throw new UnsupportedOperationException("Desktop not available")
          }

          action(desktop)
        } catch {
          case ex: Exception =>
            exceptionCallback(ex)
        }
      }
    }

    externalThread.start()
  }


  /**
    * Opens the given URL in a browser, without freezing the app.
    *
    * Throws an exception in case of errors.
    *
    * @param url               The url to open
    * @param exceptionCallback Callback invoked in case of exception
    */
  def openBrowser(url: String, exceptionCallback: ExceptionCallback = EmptyExceptionCallBack) {
    runInThread(
      desktop => desktop.browse(new URI(url)),
      exceptionCallback
    )
  }


  /**
    * Opens the given file using the user's desktop environment settings, without freezing the app.
    *
    * Throws an exception in case of errors.
    *
    * @param file              The file to open
    * @param exceptionCallback Callback invoked in case of exception
    */
  def openFile(file: File, exceptionCallback: ExceptionCallback = EmptyExceptionCallBack): Unit = {
    runInThread(
      desktop => desktop.open(file),
      exceptionCallback
    )
  }


  /**
    * Returns the user's home directory, if available
    *
    * @return Some(user home directory) or None
    */
  def homeDirectory: Option[File] = {
    val userHomeProperty = System.getProperty("user.home")

    if (userHomeProperty == null) {
      None
    } else {
      Some(new File(userHomeProperty))
    }
  }
}
