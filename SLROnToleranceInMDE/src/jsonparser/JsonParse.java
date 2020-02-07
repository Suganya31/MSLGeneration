package jsonparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.Map;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * This class contains methods to parse the citation dataset and map them to the
 * PapersPojo class.
 * 
 * @author Suganya Kannan
 * @version 1.0
 * @since 2020-01-30
 */
public class JsonParse {
	static List<PapersPojo> papers = new ArrayList<PapersPojo>();
	static Map<String, String> id_to_authors_global = new HashMap<String, String>();
	static Set<String> paperids = new HashSet<String>();
	static Set<String> newpaperids = new HashSet<String>();

	static Map<String, Integer> venues_global = new HashMap<String, Integer>();

	static int i = 1;

	public static String getOnlyStrings(String s) {
		Pattern pattern = Pattern.compile("[^a-z A-Z]");
		Matcher matcher = pattern.matcher(s);
		String name = matcher.replaceAll("");
		return name;
	}

	public static List<PapersPojo> setDetails() {
		return null;

	}

	/**
	 * 
	 * This method parses the JSON dataset and map them to the PapersPojo class.
	 * 
	 * @param core         Boolean value which is true for core papers and false for
	 *                     the references of the core papers.
	 * @param datasetname  name of the JSON dataset to parse.
	 * @param filtervenues set of venues which has only the CORE values.
	 * @return papers a list containing all the PapersPojo objects.
	 */
	public static List<PapersPojo> extractData(Boolean core, String datasetname, Set<String> filtervenues)
			throws IOException {

		JsonFactory f = new MappingJsonFactory();
		String doifilepath = "C:\\Users\\Suganya\\Downloads\\dblp.v11\\Relevant_Titles";
		BufferedReader br = new BufferedReader(new FileReader(doifilepath));
		String sCurrentLine;
		Set<String> relevanttitle = new HashSet<String>();

		while ((sCurrentLine = br.readLine()) != null) {
			relevanttitle.add(getOnlyStrings(sCurrentLine).toLowerCase());

		}
		br.close();
		File filename = new File("C:\\Users\\Suganya\\Downloads\\dblp.v11\\" + datasetname);

		JsonParser jp = f.createParser(filename);
		Set<String> dummypaperids = new HashSet<String>();
		Set<String> thefinallist = new HashSet<String>();

		int relcount = 0;

		while (jp.nextToken() != null) {
			Map<String, String> id_to_authors = new HashMap<String, String>();
		

			JsonNode node = jp.readValueAsTree();
			String id = node.path("id").asText();
			if(!newpaperids.isEmpty()&&newpaperids.contains(id))
			{
				//System.out.println("inside if");
				continue;
			}
			newpaperids.add(id);
			
			PapersPojo paper = new PapersPojo();

			
			
			JsonNode authors = node.path("authors");
			Boolean SE = false;
			String title = node.path("title").asText();

			Boolean relevance = false;
			String newttitle = getOnlyStrings(title).toLowerCase();
			if (relevanttitle.contains(newttitle)) {
				relevance = true;
				thefinallist.add(title);
				relcount++;
			}

			JsonNode venuenode = node.path("venue");
			String venue = venuenode.findPath("id").asText();
			String venuename = getOnlyStrings(venuenode.findPath("raw").asText());

			for (String c : filtervenues) {
				if (venuename.toLowerCase().contains(c.toLowerCase())
						|| (c.toLowerCase().contains(venuename.toLowerCase()))) {
					SE = true;
				}

			}

		

			Integer year = Integer.parseInt(node.path("year").asText());

			paperids.add(id);

			if (venuename != "" && !venues_global.containsKey(getOnlyStrings(venuename.toLowerCase()))) {
				venues_global.put(getOnlyStrings(venuename.toLowerCase()), i);
				i++;
			}

			paper.setId(id);
			paper.setSE(SE);
			paper.setRelevance(relevance);

			paper.setTitle(title);
			paper.setVenuename(getOnlyStrings(venuename));

			paper.setCore(core);

			paper.setYear(year);
			i++;

			ArrayNode arrayNode = (ArrayNode) authors;

			List<String> references = new ArrayList<String>();
			ObjectMapper mapper = new ObjectMapper();
			ObjectReader reader = mapper.reader(new TypeReference<List<String>>() {
			});
			if (node.get("references") != null) {
				references = reader.readValue(node.get("references"));
				paper.setReferences(references);

			}
			for (String r : paper.getReferences())
				dummypaperids.add(r);

			List<String> authorIdList = authors.findValuesAsText("id");
			List<String> authorNamesList = authors.findValuesAsText("name");
			Set<String> authorId = new HashSet<String>();
			authorId.addAll(authorIdList);

			paper.setAuthors(authorId);

			Iterator<String> itr = authorIdList.iterator();
			Iterator<String> itr1 = authorNamesList.iterator();

			while (itr.hasNext() && itr1.hasNext()) {
				{
					String key = itr.next();
					String value = itr1.next();
					if (!id_to_authors_global.containsKey(key)) {
						id_to_authors_global.put(key, value);
						id_to_authors.put(key, value);
					}

				}
			}

			paper.setId_to_authors(id_to_authors);
			papers.add(paper);

			jp.nextToken();

		}

		venues_global.put("", 0);


		PapersPojo.id_to_authors_global = id_to_authors_global;
		PapersPojo.poolids = paperids;

		dummypaperids.removeAll(paperids);

		PapersPojo.dummypaperids = dummypaperids;

		jp.close();

		for (PapersPojo paper : papers) {
			String name = paper.getVenuename();
			paper.setVenue(venues_global.get(getOnlyStrings(name).toLowerCase()));

		}

		return (papers);

	}
}
