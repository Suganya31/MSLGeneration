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

class test {
	public static var List<String> venues = new ArrayList

	def static void main(String[] args) {
		val instance = new test;
		var id_to_authors = new HashMap<String, String>();

	var scanner = new Scanner(new File("venuescode.txt"));
	while (scanner.hasNext()) {
		venues.add(scanner.next());
	}
	scanner.close();
	//var writer = new PrintWriter("C:\\Users\\Suganya\\Desktop\\MSL_Generation\\GeneratedVenues.msl", "UTF-8");
//
		var writer = new PrintWriter("GeneratedAuthors.msl", "UTF-8");
	
//	println(instance.generateVenuesMSL)
		var List<PapersPojo> papers = new ArrayList
		papers = JsonParse.extractAuthors();
		
	//	writer.println(instance.generateVenuesMSL)
			//writer.println(instance.generatePapersMSL(papers))
			writer.println(instance.generateAuthorsMSL(papers))
	
	writer.close();
		//println(instance.generatePapersMSL(papers));

//println(instance.generateAuthorsMSL(id_to_authors));
	}

	def String generatePapersMSL(List<PapersPojo> papers) {

var i=0
		''' 
import "platform:/resource/SLROnToleranceInMDE/src/Language.msl"
import "platform:/resource/SLROnToleranceInMDE/src/Authors.msl"
import "platform:/resource/SLROnToleranceInMDE/src/Venues.msl"
model AllPapers -> AllAuthors, AllVenues {
	«FOR paper : papers»
	
	       
	        paper«paper.getId()»:Paper {
                .title : "«paper.getTitle()»"
            	.year : «paper.getYear()»
            	«FOR reference : paper.getReferences()»
            		   -cites->$«reference»
            	«ENDFOR»
            	«FOR author : paper.getAuthors()»
            	        -authors->$«author»
            	 «ENDFOR»
            }
    «ENDFOR»
}
    '''
	}

	def String generateAuthorsMSL(List<PapersPojo> papers) {
		var i = 1;
		'''
import "platform:/resource/SLROnToleranceInMDE/src/Language.msl"

model AllAuthors {
		«FOR paper : papers»
	
	«FOR a : paper.getId_to_authors.entrySet»
	        «var fname=a.value.split("\\s").get(0)»
	        «var lname=a.value.split("\\s").get(1)»
	        
            author«a.key»:Author {
            	.firstName : "«fname»"
            	.lastName : "«lname»"
            	
            	
            	
            }
            «ENDFOR»
        «ENDFOR»
    
}
    '''
	}

	def String generateVenuesMSL() {
		var i = 1;
		'''
import "platform:/resource/SLROnToleranceInMDE/src/Language.msl"

model AllVenues {
	«FOR v : venues»
	
            venue«i++»:Venue {
            	.name : "«v»"
            }
            «ENDFOR»
}
    '''
	}

}
