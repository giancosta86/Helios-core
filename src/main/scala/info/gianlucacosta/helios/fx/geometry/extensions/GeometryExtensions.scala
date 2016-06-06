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

package info.gianlucacosta.helios.fx.geometry.extensions

import scala.language.implicitConversions
import scalafx.geometry.{Bounds, Point2D}
import scalafx.scene.input.MouseEvent

object GeometryExtensions {
  implicit def convertBounds(bounds: Bounds): BoundsExtensions =
    new BoundsExtensions(bounds)

  implicit def convertMouseEvent(mouseEvent: MouseEvent): MouseEventExtensions =
    new MouseEventExtensions(mouseEvent)

  implicit def convertPoint2D(source: Point2D): Point2DExtensions =
    new Point2DExtensions(source.x, source.y)
}
