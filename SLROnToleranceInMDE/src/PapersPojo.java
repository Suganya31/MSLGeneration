import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PapersPojo {
	
	
	public List<String> getAuthors() {
		return authors;
	}
	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getReferences() {
		return references;
	}
	public void setReferences(List<String> references) {
		this.references = references;
	}
	String title;
	int year;
	String id;
	List<String> references= new ArrayList<String>();
	List<String> authors= new ArrayList<String>();
	HashMap<String, String> id_to_authors = new HashMap<String, String>();

	public HashMap<String, String> getId_to_authors() {
		return id_to_authors;
	}
	public void setId_to_authors(HashMap<String, String> id_to_authors) {
		this.id_to_authors = id_to_authors;
	}



}
