package info.gianlucacosta.helios.mathutils

/**
  * General-purpose mathematical functions
  */
object Maths {
  /**
    * Computes log<sub>base</sub>(x)
    *
    * @param base
    * @param x
    * @return
    */
  def log(base: Double)(x: Double): Double =
    math.log10(x) / math.log10(base)
}
