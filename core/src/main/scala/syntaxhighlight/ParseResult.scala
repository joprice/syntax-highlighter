// Copyright (c) 2012 Chan Wai Shing
//
// Permission is hereby granted, free of charge, to any person obtaining
// a copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to
// permit persons to whom the Software is furnished to do so, subject to
// the following conditions:
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
// LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
// OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
// WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
package syntaxhighlight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import scala.collection.JavaConverters._
import scala.beans.BeanProperty

/**
 * The parser parsed result.
 *
 * This class include the information needed to highlight the syntax.
 * Information includes where the content located in the document (offset and
 * length) and what style(s) should be applied on that segment of content.
 *
 * @author Chan Wai Shing {@literal <cws1989@gmail.com> }
 * @param offset The start position of the content.
 * @param length The length of the content.
 * @param styleKeys The style keys of the content. The style at higher index of the list will
 * override the style of the lower index.
 */
case class ParseResult(
  @BeanProperty offset: Int,
  @BeanProperty length: Int,
  styleKeys: List[String]
) {
  def getStyleKeysString(): String = styleKeys.asScala.mkString(" ")
}
