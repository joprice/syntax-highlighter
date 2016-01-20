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

import scala.collection.JavaConverters._
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import syntaxhighlighter.Segment;
import syntaxhighlighter.brush.Brush;
import syntaxhighlighter.brush.RegExpRule;

/**
 * The parser of the syntax highlighter.
 *
 * @author Chan Wai Shing {@literal <cws1989@gmail.com> }
 */
class SyntaxHighlighter {
  //protected val htmlScriptBrushList: List[Brush] = new ArrayList[Brush]

  /**
   * Add matched result to {@code matches}.
   * @param matches the list of matches
   * @param match the matched result
   */
  protected def addMatch(
    matches: Map[Integer, List[MatchResult]],
    result: MatchResult
  ): Unit = {
    if (matches == null || result == null) {
      return
    }
    var matchList = matches.get(result.offset)
    if (matchList == null) {
      matchList = new ArrayList[MatchResult]()
      matches.put(result.offset, matchList)
    }
    matchList.add(result)
  }

  /**
   * Remove those matches that fufil the condition from {@code matches}.
   * @param matches the list of matches
   * @param start the start position in the document
   * @param end the end position in the document
   */
  protected def removeMatches(
    matches: Map[Integer, List[MatchResult]],
    start: Int,
    end: Int
  ): Unit = {
    if (matches == null) {
      return;
    }
    matches.keySet().asScala.foreach { offset =>
      val offsetMatches = matches.get(offset)
      val iterator = offsetMatches.listIterator()
      while (iterator.hasNext()) {
        val result = iterator.next()

        // the start and end position in the document for this matched result
        val _start = result.offset
        val _end = _start + result.length

        if (_start >= end || _end <= start) {
          // out of the range
          //continue;
        } else if (_start >= start && _end <= end) {
          // fit or within range
          iterator.remove();
        } else if (_end <= end) {
          // overlap with the start
          // remove the style within the range and remain those without the range
          iterator.set(MatchResult(_start, start - _start, result.styleKey, result.bold));
        } else if (_start >= start) {
          // overlap with the end
          // remove the style within the range and remain those without the range
          iterator.set(MatchResult(end, _end - end, result.styleKey, result.bold));
        }
      }
    }
  }

  /**
   * Parse the content start from {@code offset} with {@code length} and
   * return the result.
   *
   * @param brush the brush to use
   * @param htmlScript turn HTML-Script on or not
   * @param content the content to parse in char array
   * @param offset the offset
   * @param length the length
   *
   * @return the parsed result, the key of the map is style key
   */
  def parse(
    brush: Brush,
    htmlScript: Boolean,
    content: Array[Char],
    offset: Int,
    length: Int
  ): Map[Integer, List[MatchResult]] = {
    if (brush == null || content == null) {
      return null;
    }
    val matches = new TreeMap[Integer, List[MatchResult]]()
    parse(matches, brush, htmlScript, content, offset, length)
  }

  /**
   * Parse the content start from {@code offset} with {@code length} with the
   * brush and return the result. All new matches will be added to
   * {@code matches}.
   *
   * @param matches the list of matches
   * @param brush the brush to use
   * @param htmlScript turn HTML-Script on or not
   * @param content the content to parse in char array
   * @param offset the offset
   * @param length the length
   *
   * @return the parsed result, the key of the map is style key
   */
  def parse(matches: Map[Integer, List[MatchResult]], brush: Brush,
    htmlScript: Boolean, content: Array[Char],
    offset: Int, length: Int
  ): Map[Integer, List[MatchResult]] = {
    if (matches == null || brush == null || content == null) {
      return null;
    }
    // parse the RegExpRule in the brush first
    val regExpRuleList = brush.regExpRuleList
    regExpRuleList.foreach { regExpRule =>
      parse(matches, regExpRule, content, offset, length);
    }
    return matches;
  }

  /**
   * Parse the content start from {@code offset} with {@code length} using the
   * {@code regExpRule}. All new matches will be added to {@code matches}.
   *
   * @param matches the list of matches
   * @param regExpRule the RegExp rule to use
   * @param content the content to parse in char array
   * @param offset the offset
   * @param length the length
   */
  def parse(
    matches: Map[Integer, List[MatchResult]],
    regExpRule: RegExpRule,
    content: Array[Char],
    offset: Int,
    length: Int
  ): Unit = {
    if (matches == null || regExpRule == null || content == null) {
      return;
    }
    val groupOperations = regExpRule.groupOperations
    val regExpPattern = regExpRule.pattern
    val matcher = regExpPattern.matcher(new Segment(content, offset, length))
    while (matcher.find()) {
      // deal with the matched result
      groupOperations.foreach { case (groupId, operation) =>
        // the start and end position of the match
        var start = matcher.start(groupId)
        var end = matcher.end(groupId);
        if (start == -1 || end == -1) {
          //continue;
        } else {
          start += offset;
          end += offset;

          operation match {
            case s: String =>
              addMatch(matches, MatchResult(start, end - start, s, regExpRule.bold))
            // case rule: RegExpRule =>
            //   // parse the result using the <code>operation</code> RegExpRule
            //   parse(matches, rule, content, start, end - start)
            case _ => throw new Exception(s"Invalid operation type $operation")
          }
        }
      }
    }
  }

  // /**
  //  * Set HTML Script brushes. Note that this will clear all previous recorded
  //  * HTML Script brushes.
  //  *
  //  * @param htmlScriptBrushList the list that contain the brushes
  //  */
  // def setHTMLScriptBrushList(htmlScriptBrushList: List[Brush]) = {
  //   this.htmlScriptBrushList.clear()
  //   if (htmlScriptBrushList != null) {
  //     this.htmlScriptBrushList.addAll(htmlScriptBrushList)
  //   }
  // }
  //
  // /**
  //  * Add HTML Script brushes.
  //  * @param brush the brush to add
  //  */
  // def addHTMLScriptBrush(brush: Brush) = {
  //   if (brush != null) {
  //     htmlScriptBrushList.add(brush);
  //   }
  // }
}
