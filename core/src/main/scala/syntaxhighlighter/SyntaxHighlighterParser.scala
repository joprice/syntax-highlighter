package syntaxhighlighter;

import scala.collection.JavaConverters._
import syntaxhighlight.ParseResult;
import syntaxhighlight.Parser;
import syntaxhighlighter.brush.Brush;
import syntaxhighlighter.parser.MatchResult;
import syntaxhighlighter.parser.SyntaxHighlighter;

/**
 * The SyntaxHighlighter parser for syntax highlight.
 * @author Chan Wai Shing {@literal <cws1989@gmail.com> }
 */
object SyntaxHighlighterParser {

  def parse(fileExtension: String, content: String, brush: Brush): List[ParseResult] = {
    val syntaxHighlighter = new SyntaxHighlighter
    //syntaxHighlighter.setHTMLScriptBrushList(htmlScriptBrushesList.asJava)
    val parsedResult = syntaxHighlighter.parse(
      brush,
      false,
      content.toCharArray(),
      0,
      content.length()
    )
    parsedResult.values().asScala.flatMap { resultList =>
      resultList.asScala.map { result =>
        val styleKeyList = List(result.styleKey) ++ (
          if (result.bold.exists(identity)) List("bold") else List.empty
        )
        ParseResult(
          result.offset,
          result.length,
          styleKeyList.asJava
        )
      }
    }.toList
  }
}
