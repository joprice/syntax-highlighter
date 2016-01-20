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
package syntaxhighlighter.parser;

/**
 * Matched result, it will be generated when parsing the content.
 *
 * @author Chan Wai Shing {@literal <cws1989@gmail.com> }
 * @param offset The position in the document for this matched result.
 * @param length The length of the matched result.
 * @param styleKey The style key for this matched result
 * @param bold Indicate whether this match should be bolded or not.
 * This will override the 'bold' setting of the style (by styleKey).
 * If it is null, there will be nothing done on the 'bold' of the style.
 */
case class MatchResult(
  offset: Int,
  length: Int,
  styleKey: String,
  bold: Option[Boolean]
) {
  // this ugly hack is required because the origninal write used a boxed boolean
  // to represent 3 states
  def this(offset: Int, length: Int, styleKey: String, isBold: java.lang.Boolean) = {
    this(offset, length, styleKey, Option(isBold).map(_.booleanValue))
  }

  def isBold(): java.lang.Boolean = bold.map(java.lang.Boolean.valueOf).orNull

  require(styleKey != null)
}
