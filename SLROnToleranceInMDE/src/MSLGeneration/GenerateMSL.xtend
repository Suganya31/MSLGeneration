package MSLGeneration;

import java.io.File
import java.util.Scanner
import java.util.ArrayList
import java.util.List
import java.io.PrintWriter

class GenerateMSL {
 public static var List<String> venues = new ArrayList

    def static void main(String[] args) {
        val instance = new GenerateMSL;
        
        
     var scanner = new Scanner(new File("venuescode.txt"));
     while (scanner.hasNext()) {
			venues.add(scanner.next());
		}
		scanner.close();
		var writer = new PrintWriter("C:\\Users\\Suganya\\Desktop\\MSL_Generation\\GeneratedVenues.msl", "UTF-8");

        
        writer.println(instance.generateVenuesMSL)
                println(instance.generateVenuesMSL)
        
    }

    def String generateVenuesMSL()
    {var i=1;
    	 '''
import "platform:/resource/SLROnToleranceInMDE/src/Language.msl"

model AllVenues {
	«FOR v : venues»
	
            venue«i++»:Venue {
            	.name : "«v»"
            }
            «ENDFOR»
}
    '''
    }
  }

