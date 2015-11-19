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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import syntaxhighlighter.brush.*;
import syntaxhighlighter.SyntaxHighlighterParser;
import java.util.List;
import syntaxhighlight.ParseResult;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.net.URISyntaxException;

/**
 * Usage example. This will just cover some of the functions. To know other
 * available functions, please read the JavaDoc.
 *
 * @author Chan Wai Shing {@literal <cws1989@gmail.com> }
 */
public class Example {

  private static final Logger LOG = Logger.getLogger(Example.class.getName());

  public static void main(String[] args) {
    SyntaxHighlighterParser parser = new SyntaxHighlighterParser(new BrushXml());
    parser.setBrush(new BrushScala());
    try {
      String resourcePath = "/example.scala";
      String content = new String(Files.readAllBytes(Paths.get(Example.class.getResource(resourcePath).toURI())));

      //Example.class.getResourceAsStream("/example.scala");
      //String content = new String(readResourceFile("/example.scala"));
      List<ParseResult> output = parser.parse(null, content);
      LOG.info(output.toString());
    } catch (IOException | URISyntaxException ex) {
      LOG.log(Level.SEVERE, null, ex);
    }
  }
}
