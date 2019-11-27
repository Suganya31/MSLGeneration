import java.io.File
import java.util.Scanner
import java.util.ArrayList
import java.util.List
import java.io.PrintWriter
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import com.fasterxml.jackson.databind.MappingJsonFactory
import java.util.Map
import com.fasterxml.jackson.core.TreeNode
import PapersPojo
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.Set
import java.util.HashSet

class test {

	public static var List<String> venues = new ArrayList

	def static void main(String[] args) {
		val instance = new test;
		var id_to_authors = new HashMap<String, String>();

//	var scanner = new Scanner(new File("venuescode.txt"));
//	while (scanner.hasNext()) {
//		venues.add(scanner.next());
//	}
//	scanner.close();
		// var writer = new PrintWriter("C:\\Users\\Suganya\\Desktop\\MSL_Generation\\GeneratedVenues.msl", "UTF-8");
//
		// var writer = new PrintWriter("src\\Papers.msl", "UTF-8");
//	println(instance.generateVenuesMSL)
		var List<PapersPojo> papers = new ArrayList
		var Set<Integer> years = new HashSet;

		papers = JsonParse.extractAuthors();

		// writer.println(instance.generatePapersMSL(papers))
		// writer.println(instance.generateAuthorsMSL(papers))
		var writer = new PrintWriter("src\\Papers\\Paperinitial.msl", "UTF-8");
				// writer.println(instance.generateVenuesMSL(papers))
							//writer.println(instance.generatePapersMSL(papers, 0000))
				
		//writer.close();

		for (paper : papers) {
			years.add(paper.getYear)

		}
		for (year : years) {
			writer = new PrintWriter("src\\Papers\\Paper" + year + ".msl", "UTF-8");
			writer.println(instance.generatePapersMSL(papers, year))
					writer.close();
			

		}

	// println(instance.generatePapersMSL(papers));
//println(instance.generateAuthorsMSL(id_to_authors));
	}

	def String generatePapersMSL(List<PapersPojo> papers, Integer year) {

		var i = 0
		var flag=true
				var Set<Integer> years = new HashSet;
		
		''' 
import "platform:/resource/SLROnToleranceInMDE/src/Language.msl"
import "platform:/resource/SLROnToleranceInMDE/src/Authors.msl"
import "platform:/resource/SLROnToleranceInMDE/src/Venues.msl"
import "platform:/resource/SLROnToleranceInMDE/src/Papers/Dummy.msl"

	«FOR paper : papers»
	«IF(paper.getYear !== year) && !years.contains(paper.getYear)»	
			      «{years.add(paper.getYear); "" }»
import "platform:/resource/SLROnToleranceInMDE/src/Papers/Paper«paper.getYear.toString()».msl"
          «ENDIF»          
 	«ENDFOR»
 	
model AllPapers -> AllAuthors, AllVenues {
	«FOR paper : papers»
	      	«IF (paper.getYear()==year)»
	      	    paper«paper.getId()»:Paper {
                .title : "«getOnlyStrings(paper.getTitle())»"
            	.year : «paper.getYear()»
            	.core : «flag»
            	-venue->venue«paper.getVenue()»
            	«FOR reference : paper.getReferences()»
            		   -cites->paper«reference»
            	«ENDFOR»
            	«FOR author : paper.getAuthors()»
            	       -authors->author«author»
            	 «ENDFOR»
            }
                     «ENDIF»
                «ENDFOR»
«««    			«var s=PapersPojo.dummypaperids»
«««    				«FOR a : s»
«««    				 paper«a»:Paper {
«««    				 	.title : ""
«««    				    .year : 0000
«««    				    .core : false
«««    				    .venue : ""    				           
«««    				            }
«««    				    «ENDFOR»   
}
    '''
	}

	def String generateAuthorsMSL(List<PapersPojo> papers) {
		var i = 1;
		'''
import "platform:/resource/SLROnToleranceInMDE/src/Language.msl"

model AllAuthors {
			«var s=PapersPojo.id_to_authors_global»
		«FOR a : s.entrySet»
	        «var fname=getOnlyStrings(a.value.split("\\s").get(0))»
	        «var lname=getOnlyStrings(a.value.split("\\s").get(1))»
	             author«a.key»:Author {
            	.firstName : "«fname»"
            	.lastName : "«lname»"    	
            }
            «ENDFOR»
    
}
    '''
	}

	def String generateVenuesMSL(List<PapersPojo> papers) {
		var i = 1;
		'''
import "platform:/resource/SLROnToleranceInMDE/src/Language.msl"

model AllVenues {
	«FOR paper : papers»
	
            venue«paper.getVenue»:Venue {
            	.Name : "«paper.getVenuename»"
            }
            «ENDFOR»
}
    '''
	}

	def static String getOnlyStrings(String s) {
		var pattern = Pattern.compile("[^a-z A-Z\\s]");
		var matcher = pattern.matcher(s);
		var name = matcher.replaceAll("");
		return name;
	}

}
