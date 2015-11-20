/**
 * This is part of the Java SyntaxHighlighter.
 *
 * It is distributed under MIT license. See the file 'readme.txt' for
 * information on usage and redistribution of this file, and for a
 * DISCLAIMER OF ALL WARRANTIES.
 *
 * @author Chan Wai Shing {@literal <cws1989@gmail.com> }
 */
package syntaxhighlighter.example;

import java.io.IOException
import java.util.logging.{Level, Logger}
import syntaxhighlighter.brush._
import syntaxhighlighter.SyntaxHighlighterParser
import java.util.List
import syntaxhighlight._
import java.nio.file.{Paths, Files}
import java.net.URISyntaxException
import scala.util.Try

/**
 * Usage example. This will just cover some of the functions. To know other
 * available functions, please read the JavaDoc.
 *
 * @author Chan Wai Shing {@literal <cws1989@gmail.com> }
 */
object Example {

  private val LOG = Logger.getLogger(getClass.getName())

  def main(args: Array[String]): Unit = {
    val parser = new SyntaxHighlighterParser(BrushScala.brush)

    val resourcePath = "/example.scala"

    Try {
      val content = new String(Files.readAllBytes(Paths.get(getClass.getResource(resourcePath).toURI())))
      val output = parser.parse(null, content);
      LOG.info(output.toString())
    }.recover {
      case ex: Throwable => LOG.log(Level.SEVERE, null, ex);
    }
  }
}
