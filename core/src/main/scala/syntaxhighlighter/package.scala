
import syntaxhighlight.ParseResult
import syntaxhighlighter.brush.Brush
import scala.collection.JavaConverters._
import scala.Console._

package object syntaxhighlighter {

  def highlightColor(style: String): String = style match {
    case "keyword" => MAGENTA
    case "color2" => RED
    case "color3" => CYAN
    case "comments" => YELLOW
    case "value" => GREEN
    //TODO: handle string
    case "string" => RED
    case style =>
      println(s"unhandled style $style")
      ""
  }

  def asciiHighlight(intervals: List[(Int, Int, String)], input: String) = {
    val (result, rest, _) = intervals.foldLeft(("", input, 0)) { case ((processed, unprocessed, len), (start, end, label)) =>
      val newEnd = end - len
      val newStart = start - len
      //println(s"start: $start, end $end, $label, newStart $newStart, newEnd $newEnd, len $len")

      val highlighted = Seq(
        unprocessed.substring(0, newStart),
        highlightColor(label),
        unprocessed.substring(newStart, newEnd),
        RESET
      ).mkString

      (processed + highlighted, unprocessed.substring(newEnd), newEnd + len)
    }
    result + rest
  }

  def highlightIntervals(brush: Brush, content: String) = {
    def flattenIntervals[T](intervals: Seq[(Int, Int, T)]) =
      intervals.sortBy { case (l, r, _) => (l, r) }.foldLeft(List.empty[(Int, Int, T)]) {
        case (Nil, i) => List(i)
        case (acc@((l1, r1, t1) :: tail), i@(l2, r2, t2)) =>
          // remove containing
          if ((l2 >= l1) && (r2 <= r1)) {
            acc
          } else if (l2 < l1 || l2 < r1) {
            acc
          } else {
            i :: acc
          }
      }.reverse

    def toInterval(result: ParseResult) = {
      val start = result.offset
      val end = start + result.length
      val keys = result.styleKeys
      // just take the last style
      (start, end, keys.last)
    }

    val results = SyntaxHighlighterParser.parse(null, content, brush)
    val intervals = results.map(toInterval)
    flattenIntervals(intervals)
  }

}
