import java.util.Set;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.google.common.base.CharMatcher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonData {
	 public static String getOnlyStrings(String s) {
		    Pattern pattern = Pattern.compile("[^a-zA-Z]");
		    Matcher matcher = pattern.matcher(s);
		    String number = matcher.replaceAll("");
		    return number;
		 }
	 public static String getOnlyDigits(String s) {
		    Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
		    Matcher matcher = pattern.matcher(s);
		    String number = matcher.replaceAll("");
		    return number;
		 }
    public static void findReference(Set<String> dummypaperids) throws IOException{
        JsonFactory f = new MappingJsonFactory();
		Set<String> listoftitle = new HashSet<String>();
		Set<String> listoftitleunfiltered = new HashSet<String>();

		Set<String> titlelist = new HashSet<String>();
		Set<String> foundlist = new HashSet<String>();


        HashMap<String, List<String>> id_to_references = new HashMap<String, List<String>>();
	//	FileWriter writer = new FileWriter("C:\\Users\\Suganya\\Downloads\\dblp.v11\\References_dataset.txt");
	//	BufferedWriter out = new BufferedWriter(writer);
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\Suganya\\Downloads\\dblp.v11\\References_dataset_new.txt"), "UTF-8"));


   	String sCurrentLine;
   	String filtered;
	String charsToRemove = "{}[],();:+/-_. ";
			 
	for(String referenceid:dummypaperids)
	{

		listoftitle.add(referenceid);
		
		
	}


       File filename=new File("C:\\Users\\Suganya\\Downloads\\dblp.v11\\dblp_papers_v11.txt");

		JsonParser jp = f.createParser(filename);
        JsonToken current;
        current = jp.nextToken();
        if (current != JsonToken.START_OBJECT) {
            return;
        }
        while (jp.nextToken() != null) {
            JsonNode node = jp.readValueAsTree();
 
        
            	
           
           String id=node.path("id").asText();


         
        	   if(listoftitle.contains(id))
        	   {
          
            	//System.out.print(node);
               out.write(node.toString());   
               out.write("\n");

            }
         
            current = jp.nextToken();

            
        }
        out.close();
        
    }

}