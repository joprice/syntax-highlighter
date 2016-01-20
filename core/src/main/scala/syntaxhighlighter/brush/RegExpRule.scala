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

import java.util.regex.Pattern;

/**
 * The regular expression rule.
 *
 * @author Chan Wai Shing {@literal <cws1989@gmail.com> }
 */
object RegExpRule {

  /**
   * Common regular expression rule.
   */
  val multiLineCComments = Pattern.compile("\\/\\*[\\s\\S]*?\\*\\/", Pattern.MULTILINE);

  /**
   * Common regular expression rule.
   */
  val singleLineCComments = Pattern.compile("\\/\\/.*$", Pattern.MULTILINE);

  /**
   * Common regular expression rule.
   */
  val singleLinePerlComments = Pattern.compile("#.*$", Pattern.MULTILINE);

  /**
   * Common regular expression rule.
   */
  val doubleQuotedString = Pattern.compile("\"([^\\\\\"\\n]|\\\\.)*\"");

  /**
   * Common regular expression rule.
   */
  val singleQuotedString = Pattern.compile("'([^\\\\'\\n]|\\\\.)*'");

  /**
   * Common regular expression rule.
   */
  val multiLineDoubleQuotedString = Pattern.compile("\"([^\\\\\"]|\\\\.)*\"", Pattern.DOTALL);

  /**
   * Common regular expression rule.
   */
  val multiLineSingleQuotedString = Pattern.compile("'((\\\\.)|([^\\\\']))*+'", Pattern.DOTALL);

  /**
   * Common regular expression rule.
   */
  val xmlComments = Pattern.compile("\\w+:\\/\\/[\\w-.\\/?%&=:@;]*");

  /**
   * @param regExp the regular expression for this rule
   * @param styleKey the style key, the style to apply to the matched result
   */
  def apply(regExp: String, styleKey: String): RegExpRule =
    apply(regExp, 0, styleKey)

  /**
   * @param regExp the regular expression for this rule
   * @param regFlags the flags for the regular expression, see the flags in
   * {@link java.util.regex.Pattern}
   * @param styleKey the style key, the style to apply to the matched result
   */
  def apply(regExp: String, regFlags: Int, styleKey: String): RegExpRule =
    apply(Pattern.compile(regExp, regFlags), styleKey);

  /**
   * @param pattern the compiled regular expression
   * @param styleKey the style key, the style to apply to the matched result
   */
  def apply(pattern: Pattern, styleKey: String): RegExpRule =
    apply(pattern, Map(0 -> styleKey))

  def apply(regExp: String, regFlags: Int, groupOperations: Map[Int, Any]): RegExpRule =
    apply(Pattern.compile(regExp, regFlags), groupOperations);

  def apply(pattern: Pattern, groupOperations: Map[Int, Any]): RegExpRule =
    RegExpRule(pattern, groupOperations, None)

}

/**
 * @param groupOperations
 * The key is the group number (see {@link java.util.regex.Matcher}) of the
 * matched result.
 * <p>
 * The value can either be a string or a RegExpRule:
 * <ul>
 * <li>If it is a string, it should be one of the style key from a theme. <br>
 * The style will be applied to the 'strip of string related to the group
 * number'.</li>
 * <li>If it is a RegExpRule, the RegExpRule will be applied on the 'strip
 * of string related to the group number' for further operations/matching.</li>
 * </ul>
 */
case class RegExpRule(
  pattern: Pattern,
  groupOperations: Map[Int, Any],
  bold: Option[Boolean]
)
