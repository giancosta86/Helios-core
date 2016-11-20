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