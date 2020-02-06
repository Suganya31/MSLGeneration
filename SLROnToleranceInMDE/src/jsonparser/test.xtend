package jsonparser

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
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.Set
import java.util.HashSet
import java.io.BufferedReader
import java.io.FileReader

/**
 * This file contains the logic to convert PapersPojo objects into MSL files. 
 *  
 * @author Suganya Kannan
 * @version 1.0
 * @since 2020-01-30
 */
class test {

	public static var Set<String> filtervenues = new HashSet
	/**
	 *  
	 * This method contains the method calls to extractData method to parse the JSON dataset and convert it into POJO class.
	 * This method also contains calls to generate the MSL files such as generateAuthorsMSL, generateVenuesMSL and generateAuthorsMSL

	 * @param  papers  a list containing all the PapersPojo objects.
	 * @param  year one of the years contained in the dataset.
	 */

	def static void main(String[] args) {
		val instance = new test;

		var br = new BufferedReader(new FileReader("C:\\Users\\Suganya\\Downloads\\dblp.v11\\venuesexpansion.txt"));
		var sCurrentLine = "";
		var filtered = "";

		while ((sCurrentLine = br.readLine()) != null) {
			filtered = getOnlyStrings(sCurrentLine);

			filtervenues.add(filtered.toLowerCase());
		}
//
		// var writer = new PrintWriter("src\\Papers.msl", "UTF-8");
		var List<PapersPojo> papers = new ArrayList
		var Set<Integer> years = new HashSet;

		var core = true;
		var datasetname = "FinalDatasetfeb.txt";
		var writer = new PrintWriter("src\\Papers\\papers.msl", "UTF-8");
		papers = JsonParse.extractData(core, datasetname, filtervenues);
		datasetname = "References_dataset_newfeb.txt";
		core = false;
//		//need to call this findReference only once to create the dataset of all the references of the CORE papers.
		// JsonData.findReference(PapersPojo.dummypaperids);
		papers = JsonParse.extractData(false, datasetname, filtervenues);
//
//		// writer.println(instance.generatePapersMSL(papers))
	//writer.println(instance.generateAuthorsMSL(papers))
//
		// writer.println(instance.generateVenuesMSL(papers))
		writer.close();

		for (paper : papers) {
			years.add(paper.getYear)

		}
		for (year : years) {
			writer = new PrintWriter("src\\Papers\\Paper" + year + ".msl", "UTF-8");
			writer.println(instance.generatePapersMSL(papers, year))
					writer.close();
			

		}
	}

	/**
	 *  
	 * This method generates multiple Paper.msl based on the year by adding an entry for every PapersPojo objects in the list of papers.

	 * @param  papers  a list containing all the PapersPojo objects.
	 * @param  year one of the years contained in the dataset.
	 */
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
«»}
    '''
	}

	/**
	 *  
	 * This method generates the Authors.msl file.

	 * @param  papers  a list containing all the PapersPojo objects.
	 * @param  year one of the years contained in the dataset.
	 */
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

	/**
	 *  
	 * This method generates the Venues.msl file.

	 * @param  papers  a list containing all the PapersPojo objects.
	 */
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

	/**
	 *  
	 * This method contains regex to filter numbers and special characters.

	 * @param  s  a word with possible occurences of numerical and special characters.
	 * @param  name  the word after the numerical and special characters are removed.
	 */
	def static String getOnlyStrings(String s) {
		var pattern = Pattern.compile("[^a-z A-Z\\s]");
		var matcher = pattern.matcher(s);
		var name = matcher.replaceAll("");
		return name;
	}

}
