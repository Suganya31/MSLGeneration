import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PapersPojo {
	
	
	public Set<String> getAuthors() {
		return authors;
	}
	public void setAuthors(Set<String> authorId) {
		this.authors = authorId;
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
	Integer venue;
	public String getVenuename() {
		return venuename;
	}
	public void setVenuename(String venuename) {
		this.venuename = venuename;
	}
	String venuename;
	public Integer getVenue() {
		return venue;
	}
	public void setVenue(Integer venue) {
		this.venue = venue;
	}
	int year;
	String id;
	
	List<String> references= new ArrayList<String>();
	Set<String> authors= new HashSet<String>();
	Map<String, String> id_to_authors = new HashMap<String, String>();

	public Map<String, String> getId_to_authors() {
		return id_to_authors;
	}
	public void setId_to_authors(Map<String, String> id_to_authors2) {
		this.id_to_authors = id_to_authors2;
	}
	
	
	static Map<String, String> id_to_authors_global = new HashMap<String, String>();
	static Set<String> dummypaperids= new HashSet<String>();
	







}
