package info.gianlucacosta.helios.io

import java.io.Writer

/**
  * Basic Writer decorator
  *
  * @param targetWriter The decorated writer
  */
abstract class DecoratorWriter(targetWriter: Writer) extends Writer {
  override def flush(): Unit =
    targetWriter.flush()

  override def write(cbuf: Array[Char], off: Int, len: Int): Unit =
    targetWriter.write(cbuf, off, len)

  override def close(): Unit =
    targetWriter.close()
}
