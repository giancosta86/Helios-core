package info.gianlucacosta.helios.files

import java.io.IOException
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{FileVisitResult, Files, Path, SimpleFileVisitor}

/**
  * File-related utilities
  */
object FileUtils {

  private class DeltreeVisitor extends SimpleFileVisitor[Path]() {
    var _errorsFound = false

    def errorsFound = _errorsFound

    override def visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult = {
      try {
        Files.delete(file)
        FileVisitResult.CONTINUE
      } catch {
        case _: Exception =>
          _errorsFound = true
          FileVisitResult.TERMINATE
      }
    }

    override def postVisitDirectory(dir: Path, exc: IOException): FileVisitResult = {
      try {
        Files.delete(dir)
        FileVisitResult.CONTINUE
      } catch {
        case _: Exception =>
          _errorsFound = true
          FileVisitResult.TERMINATE
      }
    }
  }


  /**
    * Deletes the given directory and its subtree
    *
    * @param directory The directory to delete
    * @return Returns true if the directory did not exist or was successfully deleted
    */
  def deltree(directory: Path): Boolean = {
    if (!Files.exists(directory)) {
      return true
    }

    require(Files.isDirectory(directory))

    val visitor = new DeltreeVisitor

    Files.walkFileTree(directory, visitor)

    !visitor.errorsFound
  }
}
