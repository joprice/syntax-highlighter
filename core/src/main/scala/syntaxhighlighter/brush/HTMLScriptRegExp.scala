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
 * Regular expression for HTML script. This will be used to determine if the
 * language was implanted into the HTML using {@code left} and {@code right}.
 * e.g. left is {@literal "&lt;script>"} and right is {@literal "&lt;/script>" }, if there is any
 * content start with {@literal "&lt;script>" } and {@literal "&lt;/script>" }, the content in between
 * these two will be parsed by using this brush.
 *
 * @author Chan Wai Shing {@literal <cws1989@gmail.com> }
 */
object HTMLScriptRegExp {

  /**
   * Common HTML script RegExp.
   */
  val phpScriptTags = HTMLScriptRegExp("(?:&lt;|<)\\?=?", "\\?(?:&gt;|>)")
  /**
   * Common HTML script RegExp.
   */
  val aspScriptTags = HTMLScriptRegExp("(?:&lt;|<)%=?", "%(?:&gt;|>)")
  /**
   * Common HTML script RegExp.
   */
  val scriptScriptTags = HTMLScriptRegExp("(?:&lt;|<)\\s*script.*?(?:&gt;|>)", "(?:&lt;|<)\\/\\s*script\\s*(?:&gt;|>)")
}

case class HTMLScriptRegExp(left: String, right: String) {

  /**
   * The pattern of this HTML script RegExp.
   * It is a combination of left and right tag and some pattern to match the
   * in-between content. Group 1 is the left tag, group 2 is the inner content,
   * group 3 is the right tag.
   *
   * @return the pattern with flags: CASE_INSENSITIVE and DOTALL
   */
  val pattern = Pattern.compile("(" + left + ")(.*?)(" + right + ")", Pattern.CASE_INSENSITIVE | Pattern.DOTALL)
}
