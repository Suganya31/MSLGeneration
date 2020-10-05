package jsonparser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jsonparser.PapersPojo;

/**
 * This file contains the logic to convert PapersPojo objects into MSL files.
 * 
 * @author Suganya Kannan
 * @version 1.0
 * @since 2020-01-30
 */
@SuppressWarnings("all")
public class test {
  public static Set<String> filtervenues = new HashSet<String>();
  
  /**
   * This method contains the method calls to extractData method to parse the JSON dataset and convert it into POJO class.
   * This method also contains calls to generate the MSL files such as generateAuthorsMSL, generateVenuesMSL and generateAuthorsMSL
   * 
   * @param  papers  a list containing all the PapersPojo objects.
   * @param  year one of the years contained in the dataset.
   */
  public static void main(final String[] args) {
    throw new Error("Unresolved compilation problems:"
      + "\n!= cannot be resolved."
      + "\n+ cannot be resolved."
      + "\n+ cannot be resolved");
  }
  
  /**
   * This method generates multiple Paper.msl based on the year by adding an entry for every PapersPojo objects in the list of papers.
   * 
   * @param  papers  a list containing all the PapersPojo objects.
   * @param  year one of the years contained in the dataset.
   */
  public String generatePapersMSL(final List<PapersPojo> papers, final Integer year) {
    throw new Error("Unresolved compilation problems:"
      + "\n!== cannot be resolved."
      + "\n! cannot be resolved."
      + "\n== cannot be resolved."
      + "\n&& cannot be resolved");
  }
  
  /**
   * This method generates the Authors.msl file.
   * 
   * @param  papers  a list containing all the PapersPojo objects.
   * @param  year one of the years contained in the dataset.
   */
  public String generateAuthorsMSL(final List<PapersPojo> papers) {
    throw new Error("Unresolved compilation problems:"
      + "\n>= cannot be resolved.");
  }
  
  /**
   * This method generates the Venues.msl file.
   * 
   * @param  papers  a list containing all the PapersPojo objects.
   */
  public String generateVenuesMSL(final List<PapersPojo> papers) {
    throw new Error("Unresolved compilation problems:"
      + "\n! cannot be resolved.");
  }
  
  /**
   * This method contains regex to filter numbers and special characters.
   * 
   * @param  s  a word with possible occurences of numerical and special characters.
   * @param  name  the word after the numerical and special characters are removed.
   */
  public static String getOnlyStrings(final String s) {
    Pattern pattern = Pattern.compile("[^a-z A-Z\\s]");
    Matcher matcher = pattern.matcher(s);
    String name = matcher.replaceAll("");
    return name;
  }
}
