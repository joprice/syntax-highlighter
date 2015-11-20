// Copyright (c) 2011 Chan Wai Shing
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
package syntaxhighlighter.brush;

import java.util.ArrayList
import java.util.List
import scala.beans.BeanProperty

/**
 * Brush for syntax highlighter.
 *
 * In syntax highlighter, every supported programming language has its own
 * brush. Brush contain a set of rules, the highlighter/parser will use these
 * rules to determine the structure of the code and apply different color to
 * different group of component.
 *
 * @author Chan Wai Shing {@literal <cws1989@gmail.com> }
 * @param regExpRuleList Regular expression rules list. It will be executed in sequence.
 * @param commonFileExtensionList The list of common file extension for this language. It is no use so far,
 * just for reference.
 * @param htmlScriptRegExp HTML script RegExp, null means no HTML script RegExp for this brush. If
 * this language will not be implanted into HTML, leave it null.
 *
 */

object Brush {
  /**
   * Similar function in JavaScript SyntaxHighlighter for making string of
   * keywords separated by space into regular expression.
   * @param str the keywords separated by space
   * @return the treated regexp string
   */
  def keywords(str: String): String = {
    if (str == null) {
      throw new NullPointerException("argument 'str' cannot be null");
    }
    "\\b(?:" + str.replaceAll("^\\s+|\\s+$", "").replaceAll("\\s+", "|") + ")\\b";
  }

}

case class Brush(
  @BeanProperty regExpRuleList: List[RegExpRule],
  commonFileExtensionList: List[String],
  htmlScriptRegExp: Option[HTMLScriptRegExp] = None
) {
  def getHTMLScriptRegExp() = htmlScriptRegExp.orNull

}
