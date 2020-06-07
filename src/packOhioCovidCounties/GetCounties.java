package packOhioCovidCounties;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class GetCounties {

	/**
	 * Method: Main - Calls other methods to display Ohio map by counties
	 * @param args
	 * args not used
	 */
	public static void main(String[] args) {
		String url = "https://coronavirus.ohio.gov/static/COVIDSummaryData.csv";
		
    	try {
    		retrieveFromWebsite(url);
    		OhioMap.colorStates( convertToArray() );
    	} catch(Exception e) {
    		e.printStackTrace();
        }
	}
	
	/**
	 * Method: retrieveFromWebsite - Downloads the CSV file
	 * @param url
	 * URL of csv file as a string
	 */
    public static void retrieveFromWebsite(String url) {
    	
    	try {
    		String filePath = "CoronavirusOhio.csv";
    		
    		// Convert string to URL
            URL CSVurl = new URL(url);
            
            // Create input and output streams
            BufferedInputStream bis = new BufferedInputStream(CSVurl.openStream());
            FileOutputStream fis = new FileOutputStream(filePath);
            
            byte[] buffer = new byte[1024];
            int count=0;
            while( (count = bis.read(buffer, 0, 1024)) != -1 ) {
                fis.write(buffer, 0, count);
            }
            
            // Close input and output streams
            fis.close();
            bis.close();
        	
    	} catch(Exception e) {
    		e.printStackTrace();
    	}

    }
    
    /**
     * Method: convertToArray - Converts the CSV in an array of County objects
     * @return countiesList
     * List of county objects
     */
    public static List<County> convertToArray() {
    	List<County> countiesList = new ArrayList<County>();
    	
    	try {
    		// Skip first line of headings in csv file
    		CSVReader csvReader = new CSVReaderBuilder(new FileReader("CoronavirusOhio.csv")).withSkipLines(1).build(); 
		    String[] nextLine = null;
		    while ((nextLine = csvReader.readNext()) != null) {
		    	County c = new County();
		    	
		    	// Get the County column for county name
		    	c.setName( nextLine[0] );
		    	
		    	// Get the Case Count column for number of cases
		    	c.setCases( Integer.parseInt( nextLine[6].replaceAll(",", "") ) );
		    	
        		// Add new object to list
        		countiesList.add(c);
		    }
		    
		    // Close CSV reader
		    csvReader.close();
		    
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	
		return countiesList;
    }

}
