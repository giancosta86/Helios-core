package info.gianlucacosta.helios.desktop

import java.awt.Desktop
import java.io.File
import java.net.URI

/**
  * Desktop utilities
  */
object DesktopUtils {
  /**
    * Invoked when an exception occurs
    */
  type ExceptionCallback = (Exception) => Unit

  private val EmptyExceptionCallBack: ExceptionCallback = (ex: Exception) => {}


  private def runInThread(action: (Desktop) => Unit, exceptionCallback: ExceptionCallback): Unit = {
    val externalThread = new Thread() {
      override def run(): Unit = {
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
  def openBrowser(url: String, exceptionCallback: ExceptionCallback = EmptyExceptionCallBack): Unit = {
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
