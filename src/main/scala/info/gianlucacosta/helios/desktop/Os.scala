package info.gianlucacosta.helios.desktop

/**
  * Provides information about the operating system
  */
object Os {
  private lazy val os = System.getProperty("os.name").toLowerCase()

  lazy val isWindows = os.contains("windows")

  lazy val isLinux = os.contains("linux")

  lazy val isMac = os.contains("mac")
}
