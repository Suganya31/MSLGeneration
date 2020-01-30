package dblpparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.apache.http.client.utils.URIBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * This class contains methods to fetch the papers which contains our keywords from DBLP database using the DBLP search API.
 * It also filters the papers returned from DBLP based on the venues.
 
 * @author Suganya Kannan
 * @version 1.0
 * @since 2020-01-30
 */

public class DblpFetcher {

	static ArrayList<String> venues = new ArrayList<String>();

	/**
	 * 
	 * Creating file names based on the keywords. The response from the DBLP API will be stored 
	 * in initial_fileName.  For eg: consisten_Tolera_initial.
	 * The response is filtered based on the venues and the result is stored in filtered_fileName.
	
	 *              
	 */
	
	static String initial_fileName = "";
	static String filtered_fileName = "";
	
	/**
	 * 
	 * This method fetch the papers which contains our keywords from DBLP database using the DBLP search API.
	 * 
	 * @param keyword1     the first keyword.
	 * @param keyword2     the second keyword.
	 *              
	 */

	public static void fetcher(String keyword1, String keyword2)
			throws URISyntaxException, IOException, ParserConfigurationException, SAXException, TransformerException {

		readVenueFile();
		initial_fileName = keyword1.replace('*', '_') + "_" + keyword2.replace('*', '_') + "initial" + ".xml";

		filtered_fileName = keyword1.replace('*', '_') + "_" + keyword2.replace('*', '_') + "filtered" + ".xml";
		URIBuilder b = new URIBuilder("https://dblp.org/search/publ/api");
		b.addParameter("h", "1000");
		b.addParameter("q", keyword1 + "," + "" + keyword2);

		URL url = b.build().toURL();

		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");


		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}

		in.close();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		InputSource is;
		builder = factory.newDocumentBuilder();
		is = new InputSource(new StringReader(response.toString()));
		Document doc = builder.parse(is);
		DOMSource source = new DOMSource(doc);
		FileWriter writer = new FileWriter(new File(initial_fileName));
		StreamResult result = new StreamResult(writer);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.transform(source, result);
		filterByTypeAndVenues(initial_fileName);

	}
	/**
	 * 
	 * This method filters the papers which belongs to the type "Conference and Workshop Papers" as well as
	 *  to one of the CORE Venues and stores the filtered response in filtered_fileName .
	 * 
	 * @param filelocation the location of the files which contain the response received from DBLP API.
	 *              
	 */
	private static void filterByTypeAndVenues(String filelocation)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		InputSource is;
		List<Node> results = new ArrayList<Node>();

		builder = factory.newDocumentBuilder();
		is = new InputSource(new FileReader(filelocation));
		Document doc = builder.parse(is);
		NodeList nList = doc.getElementsByTagName("hit");
		System.out.println(nList.getLength());
		Set<Element> targetElements = new HashSet<Element>();

		for (int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			String venue = "";
			if (node.getNodeType() == Element.ELEMENT_NODE) {

				Element eElement = (Element) node;

				if (eElement.getElementsByTagName("venue").getLength() > 0) {
					venue = eElement.getElementsByTagName("venue").item(0).getChildNodes().item(0).getTextContent();

				}

				String type = doc.getElementsByTagName("type").item(i).getTextContent();

				if (!type.equalsIgnoreCase("Conference and Workshop Papers") || !venues.contains(venue.toUpperCase())) {
					/*
					 * System.out.println("the title is" +
					 * doc.getElementsByTagName("title").item(i).getTextContent());
					 * 
					 * System.out.println("the venue is" + venue); System.out.println("thetype is" +
					 * type);
					 */
					Element e = (Element) nList.item(i);
					targetElements.add(e);
				}
			}
		}
		for (Element e : targetElements) {
			e.getParentNode().removeChild(e);
		}

		System.out.println("The final results are" + results.size());
		DOMSource source = new DOMSource(doc);
		FileWriter writer = new FileWriter(new File(filtered_fileName));
		StreamResult result = new StreamResult(writer);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.transform(source, result);
		System.out.println("output wriiten in" + filtered_fileName);

	}
	/**
	 * 
	 * This method reads the file containing the list of CORE Venues and stores them in an ArrayList named venues.
	 * 
	 * @param filelocation the location of the files which contain the response received from DBLP API.
	 *              
	 */
	private static void readVenueFile() throws FileNotFoundException {
		
		Scanner s = new Scanner(new File("venuescode.txt"));
		while (s.hasNext()) {
			venues.add(s.next());
		}
		s.close();

	}

}
