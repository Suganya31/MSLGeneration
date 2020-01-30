package dblpparser;

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
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class contains the logic to create our own JSON dataset by searching the
 * titles of the CORE papers in our 11GB citation dataset
 * 
 * @author Suganya Kannan
 * @version 1.0
 * @since 2020-01-30
 */
public class ParseXmlToJsonDataset {
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

	public static void main(String[] args) throws Exception {
		JsonFactory f = new MappingJsonFactory();
		Set<String> listoftitle = new HashSet<String>();


		String titlefilepath = "C:\\Users\\Suganya\\Downloads\\dblp.v11\\listofallthetilesindataset.txt";
		BufferedReader br = new BufferedReader(new FileReader(titlefilepath));
		String sCurrentLine;

		while ((sCurrentLine = br.readLine()) != null) {

			listoftitle.add(sCurrentLine);
		}
		br.close();

		File filename = new File("C:\\Users\\Suganya\\Downloads\\dblp.v11\\dblp_papers_v11.txt");

		JsonParser jp = f.createParser(filename);
		JsonToken current;
		current = jp.nextToken();
		if (current != JsonToken.START_OBJECT) {
			return;
		}
		while (jp.nextToken() != JsonToken.END_OBJECT) {
			JsonNode node = jp.readValueAsTree();

			String title = node.path("title").asText();

			if (listoftitle.contains(title)) {

				System.out.println(node);

			}

			current = jp.nextToken();

		}

	}
}