/*^
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

package info.gianlucacosta.helios.time

import java.time.Duration

/**
  * Simple and useful extensions for Duration - in particular:
  * <ul>
  * <li>
  * <i>+</i> and <i>-</i> operators
  * </li>
  *
  * <li>
  * comparison operators
  * </li>
  *
  * <li>
  * a <i>digitalFormat</i> method, for elegant formatting
  * </li>
  * </ul>
  *
  * @param duration
  */
case class DurationExtensions private(duration: Duration) extends Ordered[DurationExtensions] {
  def +(that: DurationExtensions): DurationExtensions =
    DurationExtensions(duration.plus(that.duration))


  def -(that: DurationExtensions): DurationExtensions =
    DurationExtensions(duration.minus(that.duration))


  override def compare(that: DurationExtensions): Int =
    duration.compareTo(that.duration)


  lazy val digitalFormat: String = {
    val hours =
      duration.toHours

    val minutes =
      duration.toMinutes % 60

    val seconds =
      duration.getSeconds % 60


    if (hours > 0)
      f"${hours}%02d:${minutes}%02d:${seconds}%02d"
    else
      f"${minutes}%02d:${seconds}%02d"
  }
}
