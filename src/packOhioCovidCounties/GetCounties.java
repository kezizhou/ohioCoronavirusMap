package packOhioCovidCounties;

import java.util.ArrayList;

import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class GetCounties {

	public static void main(String[] args) {
		String url = "https://coronavirus.ohio.gov/wps/portal/gov/covid-19/";
		
    	try {
    		OhioMap.colorStates( convertToArray( retrieveFromWebsite(url) ) );
    	} catch(Exception e) {
    		e.printStackTrace();
        }
	}
	
    public static String retrieveFromWebsite(String url) {
    	String text = "";
    	
    	try {
    		// Get the tag from the website using JSoup
        	Document doc = Jsoup.connect("https://coronavirus.ohio.gov/wps/portal/gov/covid-19/").get();
        	Elements div = doc.select("div[class=odh-ads__super-script-item]");
        	text = div.select("> p[dir=ltr]").text();

        	// Counties are found in the footnote '*'
    		// If there is another footnote, remove it from the string
        	if ( text.indexOf("*", text.indexOf("*")) != -1 ) {
        		text = text.substring( 0, text.indexOf("*", text.indexOf("*") + 1) );
        	}
    		 
        	// Remove the intro text
        	text = text.replace("* Number of counties with cases: ", "");
        	text = text.substring(5);
        	
        	System.out.println(text);
        	
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	
		return text;

    }
    
    public static List<County> convertToArray(String countiesText) {
    	List<County> countiesList = new ArrayList<County>();
    	String currentCounty = "";
    	int index = 0;
    	
    	try {
    		while (countiesText.indexOf(",") > 0) {

    			if (index > 0) {
        			// Remove previous county info from the total string
        			countiesText = countiesText.substring(index + 2);
    			}
        			
        		// Sets end of county to ',' if there is another one
    			if (countiesText.indexOf(",") > 0) {
    				index = countiesText.indexOf( "," );
    			// If last county, then set to the end of string
    			} else {
    				index = countiesText.length() - 1;
    			}
    			
    			County c = new County();
    			
    			// Get the current 'countyName (cases)'
        		currentCounty = countiesText.substring(0, index);
    			
    			// Separate the county name
        		c.name  = currentCounty.substring( 0, currentCounty.indexOf("(") ).trim();
        		System.out.println(c.name);

    			// Separate the number of cases
    			c.cases = Integer.parseInt( currentCounty.substring( currentCounty.indexOf("(") + 1, currentCounty.indexOf(")") ) );
    			System.out.println( Integer.toString(c.cases) );
        		
        		// Add new object to list
        		countiesList.add(c);
        		
    		}
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	
		return countiesList;
    }

}
