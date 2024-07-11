package info.gianlucacosta.helios.concurrency

/**
  * Provides a basic atomic buffer for writing and retrieving strings.
  * <p>
  * It is simpler than StringBuffer, but provides the extract() method.
  */
class AtomicStringBuilder {
  final private val internalBuffer: StringBuilder = new StringBuilder

  /**
    * Atomically appends a string to the buffer
    *
    * @param string The string to print out
    */
  def print(string: String) {
    synchronized {
      internalBuffer.append(string)
    }
  }

  /**
    * Atomically appends a string to the buffer, followed by the newline character
    *
    * @param string The line to print out
    */
  def println(string: String) {
    synchronized {
      print(string + "\n")
    }
  }

  /**
    * Atomically retrieves the text stored in the buffer, then clears the buffer
    *
    * @return The text within the buffer before clearing
    */
  def extract(): String = {
    synchronized {
      val result = internalBuffer.toString
      internalBuffer.setLength(0)
      result
    }
  }
}