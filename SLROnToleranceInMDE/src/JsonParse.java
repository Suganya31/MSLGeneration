import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.util.HashMap;

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
		HashMap<String, String> id_to_authors = new HashMap<String, String>();

		File filename = new File("C:\\Users\\Suganya\\Downloads\\dblp.v11\\FinalDataset.txt");

		JsonParser jp = f.createParser(filename);
		List<PapersPojo> papers = new ArrayList<PapersPojo>();

		while (jp.nextToken() != null) {
			PapersPojo paper = new PapersPojo();

			JsonNode node = jp.readValueAsTree();
			JsonNode authors = node.path("authors");

			String title = node.path("title").asText();
			String id = node.path("id").asText();
			Integer year = Integer.parseInt(node.path("year").asText());

			paper.setId(id);
			paper.setTitle(title);
			paper.setYear(year);

			ArrayNode arrayNode = (ArrayNode) authors;
			List<String> authorNames = new ArrayList<String>();


			List<String> authorId = new ArrayList<String>();
			List<String> references=new ArrayList<String>();
			ObjectMapper mapper = new ObjectMapper();
            ObjectReader reader = mapper.reader(new TypeReference<List<String>>() {});
            if(node.get("references")!=null)
            {
                 references = reader.readValue(node.get("references"));
     			paper.setReferences(references);

              
            }
			/*
			 * System.out.println("the refreces inside java"+references);
			 * System.out.println("checking set refernces"+paper.getReferences());
			 */

			/*
			 * else { references =new ArrayList<String>();
			 * 
			 * }
			 */

			authorId = authors.findValuesAsText("id");
			authorNames = authors.findValuesAsText("name");
			paper.setAuthors(authorId);



			for (int i = 0; i < arrayNode.size(); i++) {
				id_to_authors.put(authorId.get(i), authorNames.get(i));

			}
			paper.setId_to_authors(id_to_authors);
			papers.add(paper);
			jp.nextToken();


		}
		jp.close();
//System.out.println(id_to_authors);
	//	return (id_to_authors);
		return(papers);

	}
}
