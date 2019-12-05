import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class testing {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String venuesexpansion = "C:\\Users\\Suganya\\Downloads\\dblp.v11\\venuesexpansion.txt";
		
		String FinalOutput = "C:\\Users\\Suganya\\Downloads\\dblp.v11\\FinalOutput.txt";

	   	BufferedReader br = new BufferedReader(new FileReader(venuesexpansion));
	   	BufferedWriter writernew= new BufferedWriter( new FileWriter("C:\\Users\\Suganya\\Downloads\\dblp.v11\\outputset2.txt"));

	   	String sCurrentLine;

		 Set<String> venuesexp = new HashSet<String>();

	    while ((sCurrentLine = br.readLine()) != null) {
	    	venuesexp.add(sCurrentLine);

	    }
	    
	    BufferedReader br1 = new BufferedReader(new FileReader(FinalOutput));
	   	String sCurrentLine1;

		 Set<String> FinalOutputset = new HashSet<String>();
		 Set<String> Outputset = new HashSet<String>();
		 Set<String> Outputsetf = new HashSet<String>();

	    while ((sCurrentLine1 = br1.readLine()) != null) {
	    	FinalOutputset.add(sCurrentLine1);

	    }
	    for(String v:FinalOutputset)
	    {
	    	for(String c:venuesexp)
	    	{
	    		if(v.toLowerCase().contains(c.toLowerCase())||(c.toLowerCase().contains(v.toLowerCase())))
	    		{
	    			//System.out.println("venueexpansion"+v+"dataset"+c);
	    			System.out.println("c"+" "+c+" "+"v"+" "+v);
	    			Outputset.add(v);
	    		}
	    		
	    	
	    }
	    }
	    
	    //find difference of putputset and venuesexp
	  //  FinalOutputset.removeAll(Outputset);
	    System.out.println(Outputset.size());

	   // System.out.println(FinalOutputset.size());
	    writernew.write("begin");

	    
	    for(String s:Outputset)
		    writernew.write(s+"\n");
	    writernew.write("the end");

	    writernew.close();


	}

}
