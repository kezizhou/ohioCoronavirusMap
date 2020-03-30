package packOhioCovidCounties;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;

import java.util.List;

import javax.imageio.ImageIO;

import javax.swing.*;

public class OhioMap {

	final static String aCounties[] = { "Adams", "Allen", "Ashland", "Ashtabula", "Athens", "Auglaize", "Belmont", "Brown", "Butler", 
										"Carroll", "Champaign", "Clark", "Clermont", "Clinton", "Columbiana", "Coshocton", "Crawford",
										"Cuyahoga", "Darke", "Defiance", "Delaware", "Erie", "Fairfield", "Fayette", "Franklin", "Fulton",
										"Gallia", "Geauga", "Greene", "Guernsey", "Hamilton", "Hancock", "Hardin", "Harrison", "Henry",
										"Highland", "Hocking", "Holmes", "Huron", "Jackson", "Jefferson", "Knox", "Lake", "Lawrence",
										"Licking", "Logan", "Lorain", "Lucas", "Madison", "Mahoning", "Marion", "Medina", "Meigs",
										"Mercer", "Miami", "Monroe", "Montgomery", "Morgan", "Morrow", "Muskingum", "Noble", "Ottawa",
										"Paulding", "Perry", "Pickaway", "Pike", "Portage", "Preble", "Putnam", "Richland", "Ross",
										"Sandusky", "Scioto", "Seneca", "Shelby", "Stark", "Summit", "Trumbull", "Tuscarawas", "Union",
										"Van Wert", "Vinton", "Warren", "Washington", "Wayne", "Williams", "Wood", "Wyandot"};
	
	public static void colorStates(List<County> countiesList) {
		int width = 0;
		int height = 0;
		int index = 0;
		
		int rgb = 0;
		int red = 0;
		int green = 0;
		int blue = 0;
		
		JLabel countyLabels[] = new JLabel[88];
		
        // JFrame
        JFrame frame = new JFrame("Coronavirus in Ohio Counties");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(880, 930));
        
        // JLayeredPane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(880, 930));
		
		try {
	        // Background map of Ohio
	        JLabel ohioLabel = new JLabel( new ImageIcon( ImageIO.read(new File("OhioMap.jpg")) ) );
	        ohioLabel.setSize(ohioLabel.getPreferredSize());
	        layeredPane.add(ohioLabel, 0);
			
	        for (String strCounty : aCounties) {
	        	for (County confirmedCounty : countiesList) {
	        		// County has confirmed cases
		        	if (strCounty.equals(confirmedCounty.name) ) {
		        		index += 1;
		        		
		    			BufferedImage countyImage = ImageIO.read(new File("countyImages/" + strCounty + ".png"));

		    			width = countyImage.getWidth();
		    			height = countyImage.getHeight();
		    			
		    	        for (int y = 0; y < height; y++) { 
		    	            for (int x = 0; x < width; x++) { 
		    	            	rgb = countyImage.getRGB(x, y);
		    	            	red = (rgb & 0x00ff0000) >> 16;
		    	                green = (rgb & 0x0000ff00) >> 8;
		    	                blue  =  rgb & 0x000000ff;
		    	                
		    	                if (confirmedCounty.cases > 20) {
		    	                	if (red >= 200 || blue >= 200 || green >= 200) {
		    	                		// Change to red if cases greater than 10
		    	            			countyImage.setRGB(x, y, new Color(255, 80, 80).getRGB());
			    	            	}
		    	                } else {
			    	                // Change to blue otherwise
			    	            	if (red >= 200 || blue >= 200 || green >= 200) {
			    	            		countyImage.setRGB(x, y, new Color(0, 204, 255).getRGB());
			    	            	}
		    	                }
		    	            }
		    	        }
		    	        		    	        
		    	        // Counties with confirmed
		    	        countyLabels[index] =  new JLabel( new ImageIcon(countyImage) );
		    	        countyLabels[index].setSize(ohioLabel.getPreferredSize());
		    	        layeredPane.add(countyLabels[index], JLayeredPane.PALETTE_LAYER);
		    	        
		    	        layeredPane.setVisible(true);
		    	        frame.add(layeredPane);
		        	}
	        	}
	        }
	        
	        // Show JFrame
	        frame.setVisible(true);
	        
		} catch (Exception e) {
			frame.setVisible(true);
			e.printStackTrace();
		}
	}

}
