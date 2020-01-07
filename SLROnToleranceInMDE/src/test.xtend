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
import JsonData
import java.io.BufferedReader
import java.io.FileReader

class test {

	public static var Set<String> filtervenues = new HashSet

	def static void main(String[] args) {
		val instance = new test;

		var br = new BufferedReader(new FileReader("C:\\Users\\Suganya\\Downloads\\dblp.v11\\venuesexpansion.txt"));
		var sCurrentLine = "";
		var filtered = "";

		while ((sCurrentLine = br.readLine()) != null) {
			filtered = getOnlyStrings(sCurrentLine);

			filtervenues.add(filtered.toLowerCase());
		}
		// var writer = new PrintWriter("C:\\Users\\Suganya\\Desktop\\MSL_Generation\\GeneratedVenues.msl", "UTF-8");
//
		// var writer = new PrintWriter("src\\Papers.msl", "UTF-8");
//	println(instance.generateVenuesMSL)
		var List<PapersPojo> papers = new ArrayList
		var Set<Integer> years = new HashSet;

		var core = true;
		var datasetname = "FinalDataset.txt";
		var writer = new PrintWriter("src\\Papers\\Papers.msl", "UTF-8");
		papers = JsonParse.extractAuthors(core, datasetname, filtervenues);
		datasetname = "References_dataset.txt";
		core = false;
		// JsonData.findReference(PapersPojo.dummypaperids);
		papers = JsonParse.extractAuthors(false, datasetname, filtervenues);

		// writer.println(instance.generatePapersMSL(papers))
		// writer.println(instance.generateAuthorsMSL(papers))
		
		//writer.println(instance.generateVenuesMSL(papers))
		// writer.println(instance.generatePapersMSL(papers, 0000))
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
		var flag = true
		var Set<Integer> years = new HashSet;

		''' 
import "platform:/resource/SLROnToleranceInMDE/src/Language.msl"
import "platform:/resource/SLROnToleranceInMDE/src/Papers/Authors.msl"
import "platform:/resource/SLROnToleranceInMDE/src/Papers/Venues.msl"
	«FOR paper : papers»
	«IF(paper.getYear !== year) && !years.contains(paper.getYear)»	
			      «{years.add(paper.getYear); "" }»
import "platform:/resource/SLROnToleranceInMDE/src/Papers/Paper«paper.getYear.toString()».msl"
          «ENDIF»          
 	«ENDFOR»
 	
model Paper«year.toString()»{
	«FOR paper : papers»
	      	«IF (paper.getYear()==year)»
	      	    paper«paper.getId()»:Paper {
                .title : "«getOnlyStrings(paper.getTitle())»"
            	.year : «paper.getYear()»
            	.core : «paper.getCore()»
            	.relevance:«paper.getRelevance()»
            	-venue->venue«paper.getVenue()»
            	 «var pool=PapersPojo.poolids»
            	«FOR reference : paper.getReferences()»
            	     «IF (pool.contains(reference))»
            	      -cites->paper«reference»
            	     «ENDIF»
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
		var fname = "";
		var lname = "";
		'''
import "platform:/resource/SLROnToleranceInMDE/src/Language.msl"

model AllAuthors {
			«var s=PapersPojo.id_to_authors_global»
		«FOR a : s.entrySet»    
	        «{fname=getOnlyStrings(a.value.split("\\s").get(0));""}»  
	        «IF (a.value.split("\\s").size>=2)»
	        «{lname=getOnlyStrings(a.value.split("\\s").get(1));""}»
	        «ELSE»	        
	        «lname=""»  
	        «ENDIF»
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
	«var v=PapersPojo.venues_global»	
	«var Set<String> venues = new HashSet»
	«FOR paper : papers»
	 «IF (!venues.contains(paper.getVenuename().toLowerCase))»
	       venue«paper.getVenue»:Venue {
	             	.Name : "«paper.getVenuename»"
	             	.SE: «paper.getSE»
	             }
	              «{venues.add(paper.getVenuename().toLowerCase); "" }»
	  				        «ENDIF»
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
