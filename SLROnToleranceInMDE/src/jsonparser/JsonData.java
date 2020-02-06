package jsonparser;

import java.util.Set;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.util.HashSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class contains methods to search for all the references of the core
 * paper in the 11GB Citation dataset.
 * 
 * @author Suganya Kannan
 * @version 1.0
 * @since 2020-01-30
 */
public class JsonData {
	/**
	 * 
	 * This method contains regex to filter numbers and special characters.
	 * 
	 * @param s      a word with possible occurences of numerical and special
	 *               characters.
	 * @param number the word after the numerical and special characters are
	 *               removed.
	 */
	public static String getOnlyStrings(String s) {
		Pattern pattern = Pattern.compile("[^a-zA-Z]");
		Matcher matcher = pattern.matcher(s);
		String number = matcher.replaceAll("");
		return number;
	}

	/**
	 * 
	 * This method contains regex to filter numbers and special characters.
	 * 
	 * @param s      a word with possible occurences of numerical and special
	 *               characters.
	 * @param number the word after the special characters are removed.
	 */
	public static String getOnlyDigits(String s) {
		Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
		Matcher matcher = pattern.matcher(s);
		String number = matcher.replaceAll("");
		return number;
	}

	/**
	 * 
	 * This method search for all the references of the core paper in the 11GB
	 * Citation dataset.
	 * 
	 * @param dummypaperids a set containing all the paper IDs.
	 */
	public static void findReference(Set<String> dummypaperids) throws IOException {
		JsonFactory f = new MappingJsonFactory();
		Set<String> listoftitle = new HashSet<String>();

		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("C:\\Users\\Suganya\\Downloads\\dblp.v11\\References_dataset_missing.txt"), "UTF-8"));

		for (String referenceid : dummypaperids) {

			listoftitle.add(referenceid);

		}

		File filename = new File("C:\\Users\\Suganya\\Downloads\\dblp.v11\\dblp_papers_v11.txt");

		JsonParser jp = f.createParser(filename);
		JsonToken current;
		current = jp.nextToken();
		if (current != JsonToken.START_OBJECT) {
			return;
		}
		while (jp.nextToken() != null) {
			JsonNode node = jp.readValueAsTree();

			String id = node.path("id").asText();

			if (listoftitle.contains(id)) {

				out.write(node.toString());
				out.write("\n");

			}

			current = jp.nextToken();

		}
		out.close();

	}

}