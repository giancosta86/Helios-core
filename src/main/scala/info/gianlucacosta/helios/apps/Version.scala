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

package info.gianlucacosta.helios.apps

import java.util.regex.Pattern


object Version {
  private val VersionPattern = Pattern.compile(raw"(\d+)\.(\d+)(?:\.(\d+)(?:\.(\d+))?)?(-SNAPSHOT)?")

  /**
    * Parses a version string
    *
    * @param versionString The version string. Its format must be:
    *                      <b>major.minor(.build(.release))(-snapshot)</b>
    * @return The parsed version
    */
  def parse(versionString: String): Version = {
    val matcher = VersionPattern.matcher(versionString)

    require(matcher.matches(), s"Illegal version string: '${versionString}'")

    val major = matcher.group(1).toInt
    val minor = matcher.group(2).toInt

    val build = Option(matcher.group(3)).getOrElse("0").toInt
    val release = Option(matcher.group(4)).getOrElse("0").toInt

    val snapshot = matcher.group(5) != null

    Version(
      major,
      minor,
      build,
      release,

      snapshot
    )
  }
}

/**
  * A version class, following MoonDeploy's convention (plus an optional -SNAPSHOT suffix for
  * pre-release versions)
  */
case class Version(
                    major: Int,
                    minor: Int,
                    build: Int,
                    release: Int,
                    snapshot: Boolean
                  ) {
  override val toString: String = {
    val buildString =
      if (build > 0)
        s".${build}"
      else
        ""

    val releaseString =
      if (release > 0)
        s".${release}"
      else
        ""

    val snapshotString =
      if (snapshot)
        "-SNAPSHOT"
      else
        ""

    s"${major}.${minor}${buildString}${releaseString}${snapshotString}"
  }
}
