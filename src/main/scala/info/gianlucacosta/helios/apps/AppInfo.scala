package info.gianlucacosta.helios.apps

import java.io.InputStream
import javafx.scene.image.Image

/**
  * Information about a generic application
  */
trait AppInfo {
  val name: String
  val version: Version

  val title: String

  val copyrightYears: String
  val copyrightHolder: String
  val license: String
  val website: String
  val facebookPage: String

  def getMainIcon(size: Int): InputStream

  def getMainIconImage(size: Int): Image =
    new Image(getMainIcon(size))
}