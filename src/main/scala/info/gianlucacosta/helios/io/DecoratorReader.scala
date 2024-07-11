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
