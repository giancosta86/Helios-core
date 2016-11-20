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

package info.gianlucacosta.helios.io

import java.io.{BufferedReader, Reader}

import scala.annotation.tailrec

/**
  * Basic Reader decorator, also providing utility functions
  *
  * @param sourceReader The decorated reader
  */
abstract class DecoratorReader(sourceReader: BufferedReader) extends Reader {
  override def close(): Unit =
    sourceReader.close()


  override def read(cbuf: Array[Char], off: Int, len: Int): Int =
    sourceReader.read(cbuf, off, len)

  /**
    * Reads lines, trims them and passes them to the given mapper, stopping when a trimmed line is empty
    *
    * @param lineMapper A function (trimmed, non-empty line) => T
    * @tparam T The class of the result items
    * @return The list of objects returned by lineMapper
    */
  protected def parseLineBlock[T](lineMapper: (String => T)): List[T] =
    parseLineBlock(lineMapper, List())


  @tailrec
  private def parseLineBlock[T](lineMapper: (String => T), cumulatedItems: List[T]): List[T] = {
    val line =
      sourceReader.readLine()

    if (line == null)
      cumulatedItems.reverse
    else {
      val trimmedLine =
        line.trim

      if (trimmedLine == "")
        cumulatedItems.reverse
      else {
        val item =
          lineMapper(trimmedLine)

        parseLineBlock(
          lineMapper,
          item :: cumulatedItems
        )
      }
    }
  }
}
