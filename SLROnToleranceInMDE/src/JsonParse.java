import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
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

public class JsonParse {

	public static List<PapersPojo> setDetails() {
		return null;

	}

	public static List<PapersPojo> extractAuthors() throws IOException {

		JsonFactory f = new MappingJsonFactory();

		File filename = new File("C:\\Users\\Suganya\\Downloads\\dblp.v11\\FinalDataset.txt");

		JsonParser jp = f.createParser(filename);
		List<PapersPojo> papers = new ArrayList<PapersPojo>();
		Map<String, String> id_to_authors_global = new HashMap<String, String>();
		Set<String> dummypaperids = new HashSet<String>();
		Set<String> paperids = new HashSet<String>();



		while (jp.nextToken() != null) {
			PapersPojo paper = new PapersPojo();
			Map<String, String> id_to_authors = new HashMap<String, String>();

			JsonNode node = jp.readValueAsTree();
			JsonNode authors = node.path("authors");

			String title = node.path("title").asText();
			JsonNode venuenode = node.path("venue");
			String venue=venuenode.findPath("raw").asText();


			String id = node.path("id").asText();
			Integer year = Integer.parseInt(node.path("year").asText());
			paperids.add(id);

			paper.setId(id);
			paper.setTitle(title);
			paper.setVenue(venue);

			paper.setYear(year);

			ArrayNode arrayNode = (ArrayNode) authors;

			List<String> references = new ArrayList<String>();
			ObjectMapper mapper = new ObjectMapper();
			ObjectReader reader = mapper.reader(new TypeReference<List<String>>() {
			});
			if (node.get("references") != null) {
				references = reader.readValue(node.get("references"));
				paper.setReferences(references);

			}
			for(String r:paper.getReferences())
				dummypaperids.add(r);
			/*
			 * System.out.println("the refreces inside java"+references);
			 * System.out.println("checking set refernces"+paper.getReferences());
			 */

			/*
			 * else { references =new ArrayList<String>();
			 * 
			 * }
			 */

			List<String> authorIdList = authors.findValuesAsText("id");
			List<String> authorNamesList = authors.findValuesAsText("name");

			Set<String> authorId = new HashSet<String>();
			authorId.addAll(authorIdList);
			Set<String> authorNames = new HashSet<String>();
			authorNames.addAll(authorNamesList);
			/*
			 * System.out.println("te size of authoridlis"+authorIdList.size());
			 * System.out.println("the size of authoridset"+authorId.size());
			 */
			paper.setAuthors(authorId);

			Iterator<String> itr = authorId.iterator();
			Iterator<String> itr1 = authorNames.iterator();

			while (itr.hasNext() && itr1.hasNext()) {
				{
					String key = itr.next();
					String value = itr1.next();
					id_to_authors_global.put(key, value);
					id_to_authors.put(key, value);

				}
			}

			paper.setId_to_authors(id_to_authors);
			papers.add(paper);
			jp.nextToken();

		}
		Set<String> existing = new HashSet<>();
		id_to_authors_global = id_to_authors_global.entrySet().stream().filter(entry -> existing.add(entry.getValue()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		PapersPojo.id_to_authors_global = id_to_authors_global;
		dummypaperids.removeAll(paperids);
		//System.out.println("size of ummy paper ids"+dummypaperids.size());
		PapersPojo.dummypaperids=dummypaperids;
		jp.close();

		return (papers);

	}
}
