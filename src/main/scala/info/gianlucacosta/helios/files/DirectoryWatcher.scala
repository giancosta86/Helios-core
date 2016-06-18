/*§
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

package info.gianlucacosta.helios.files

import java.nio.file._
import java.time.Duration
import java.util.concurrent.TimeUnit

import scala.collection.JavaConversions._

/**
  * Daemon thread checking for changes (create/modify/delete) in the given directory.
  *
  * To use it, just create a subclass and call its start() method
  *
  * @param directory The directory to monitor
  * @param timeout   The polling timeout, used by the internal NIO watcher and when the directory is no more available
  */
abstract class DirectoryWatcher(directory: Path, timeout: Duration) extends Thread {
  private val watcher = FileSystems.getDefault.newWatchService()

  setDaemon(true)

  /**
    * Method called when file events are triggered
    *
    * @param events A list of WatchEvent objects
    */
  def onEvents(events: List[WatchEvent[_]])


  override def run(): Unit = {
    while (true) {
      if (Files.isDirectory(directory)) {
        directory.register(
          watcher,
          StandardWatchEventKinds.ENTRY_CREATE,
          StandardWatchEventKinds.ENTRY_MODIFY,
          StandardWatchEventKinds.ENTRY_DELETE)

        var keyValid = true

        while (keyValid) {
          val watchKey = watcher.poll(timeout.toMillis, TimeUnit.MILLISECONDS)

          if (watchKey != null) {
            val events = watchKey.pollEvents().toList

            if (events.nonEmpty) {
              onEvents(events)
            }

            keyValid = watchKey.reset()
          } else {
            keyValid = Files.isDirectory(directory)
          }
        }
      }

      Thread.sleep(timeout.toMillis)
    }
  }
}
