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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * General XML (include HTML) brush.
 * @author Chan Wai Shing {@literal <cws1989@gmail.com> }
 */
object BrushXml {

  val brush = Brush({
    val _regExpRuleList = new ArrayList[RegExpRule]();
    _regExpRuleList.add(new RegExpRule("(\\&lt;|<)\\!\\[[\\w\\s]*?\\[(.|\\s)*?\\]\\](\\&gt;|>)", Pattern.MULTILINE, "color2")); // <![ ... [ ... ]]>
    _regExpRuleList.add(new RegExpRule(RegExpRule.xmlComments, "comments")); // <!-- ... -->

    // regular expression for highlighting the tag
    val tagRegExpRule = new RegExpRule("(?:&lt;|<)[\\s\\/\\?]*([:\\w-\\.]+)", Pattern.COMMENTS, "");
    val tagMatchesToStyleKey = new HashMap[Integer, Object]();
    // highlight the tag only, not including the symbols at the start, 1 means the group 1 of the matched results
    tagMatchesToStyleKey.put(1, "keyword");
    tagRegExpRule.setGroupOperations(tagMatchesToStyleKey);

    // regular expression for highlighting the variable assignment
    val valueRegExpRule = new RegExpRule("([\\w:\\-\\.]+)"
            + "\\s*=\\s*"
            + "(\".*?\"|'.*?'|\\w+)", Pattern.COMMENTS, "");
    val valueMatchesToStyleKey = new HashMap[Integer, Object]();
    // highlight the variable name, 1 means the group 1 of the matched results
    valueMatchesToStyleKey.put(1, "color1");
    // highlight the value, 2 means the group 2 of the matched results
    valueMatchesToStyleKey.put(2, "string");
    valueRegExpRule.setGroupOperations(valueMatchesToStyleKey);

    val _regExpRule = new RegExpRule("((?:&lt;|<)[\\s\\/\\?]*(?:\\w+))(.*?)[\\s\\/\\?]*(?:&gt;|>)", Pattern.DOTALL, "")
    val matchesToRegExp = new HashMap[Integer, Object]()
    // perform futher operation on the group 1 of the matched results
    matchesToRegExp.put(1, tagRegExpRule)
    // perform futher operation on the group 2 of the matched results
    matchesToRegExp.put(2, valueRegExpRule)
    _regExpRule.setGroupOperations(matchesToRegExp)
    _regExpRuleList.add(_regExpRule)

    _regExpRuleList
  },
    Arrays.asList("xml", "html", "xhtml", "xslt")
  )
}
