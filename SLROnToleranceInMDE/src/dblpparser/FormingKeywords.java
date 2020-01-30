package dblpparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;
/**
 * This class contains methods to form the combination of keywords and to prepare the required set of venues.
 
 * @author Suganya Kannan
 * @version 1.0
 * @since 2020-01-30
 */
public class FormingKeywords {
	static ArrayList<String> venues = new ArrayList<String>();


	public static void main(String[] args) throws URISyntaxException, IOException, ParserConfigurationException, SAXException, TransformerException {
		ArrayList<String> keywords = new ArrayList<String>();

		keywords.add("tolera*");

		keywords.add("inconsisten*");
		keywords.add("uncertain*");

		keywords.add("model*");

		keywords.add("consisten*");

		keywords.add("flexib*");

		for(int i=0;i<keywords.size();i++)
		{
			for(int j=i+1;j<keywords.size();j++)
			{
				System.out.println(i+" "+keywords.get(i)+" "+j+" "+keywords.get(j));
				DblpFetcher.fetcher(keywords.get(i), keywords.get(j));
				
			}
		}
		
	


	}
	
	

}
