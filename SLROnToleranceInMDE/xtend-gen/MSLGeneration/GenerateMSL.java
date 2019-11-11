package MSLGeneration;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class GenerateMSL {
  public static List<String> venues = new ArrayList<String>();
  
  public static void main(final String[] args) {
    try {
      final GenerateMSL instance = new GenerateMSL();
      File _file = new File("venuescode.txt");
      Scanner scanner = new Scanner(_file);
      while (scanner.hasNext()) {
        GenerateMSL.venues.add(scanner.next());
      }
      scanner.close();
      PrintWriter writer = new PrintWriter("C:\\Users\\Suganya\\Desktop\\MSL_Generation\\GeneratedVenues.msl", "UTF-8");
      writer.println(instance.generateVenuesMSL());
      InputOutput.<String>println(instance.generateVenuesMSL());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public String generateVenuesMSL() {
    String _xblockexpression = null;
    {
      int i = 1;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("import \"platform:/resource/SLROnToleranceInMDE/src/Language.msl\"");
      _builder.newLine();
      _builder.newLine();
      _builder.append("model AllVenues {");
      _builder.newLine();
      {
        for(final String v : GenerateMSL.venues) {
          _builder.append("\t");
          _builder.newLine();
          _builder.append("            ");
          _builder.append("venue");
          int _plusPlus = i++;
          _builder.append(_plusPlus, "            ");
          _builder.append(":Venue {");
          _builder.newLineIfNotEmpty();
          _builder.append("            \t");
          _builder.append(".name : \"");
          _builder.append(v, "            \t");
          _builder.append("\"");
          _builder.newLineIfNotEmpty();
          _builder.append("            ");
          _builder.append("}");
          _builder.newLine();
        }
      }
      _builder.append("}");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
}
