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
