package info.gianlucacosta.helios.mathutils

import java.text.NumberFormat
import java.util.Locale

/**
  * Number-related utilities
  */
case object Numbers {
  private val defaultFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH)
  defaultFormatter.setMaximumFractionDigits(2)
  defaultFormatter.setGroupingUsed(false)

  /**
    * Tries to convert a Double to a Long - succeeding only if the Double
    * actually contains a Long value.
    *
    * @param value The Double value to test
    * @return Some(Long value) if the test succeeded, None otherwise
    */
  def asLong(value: Double): Option[Long] = {
    val roundedValue: Long = math.round(value)

    if (value - roundedValue == 0) {
      Some(roundedValue)
    } else {
      None
    }
  }


  /**
    * Prints a double in a user-friendly way:
    * <ul>
    * <li>If the fraction digits are all 0, do not print them</li>
    * <li>Otherwise, print at most the given number of fraction digits</li>
    * </ul>
    *
    * @param value             The value to print
    * @param maxFractionDigits The maximum number of fraction digits
    * @return A user-friendly string representation
    */
  def smartString(value: Double, maxFractionDigits: Int): String = {
    val formatter = NumberFormat.getNumberInstance(Locale.ENGLISH)
    formatter.setMaximumFractionDigits(maxFractionDigits)
    formatter.setGroupingUsed(false)

    formatter.format(value)
  }


  /**
    * Prints a double in a user-friendly way:
    * <ul>
    * <li>If the fraction digits are all 0, do not print them</li>
    * <li>Otherwise, print at most 2 fraction digits</li>
    * </ul>
    *
    * @param value The value to print
    * @return A user-friendly string representation
    */
  def smartString(value: Double): String = {
    defaultFormatter.format(value)
  }
}
