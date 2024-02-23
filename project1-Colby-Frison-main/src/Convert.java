import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Convert {
    

    public static void convertFile(String filename){
		try {
            File inputFile = new File(filename); // open gpx file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputFile); // parse file into doc obj

            doc.getDocumentElement().normalize(); // normalize doc

            NodeList trkptList = doc.getElementsByTagName("trkpt"); //get list of all elements from doc

            BufferedWriter writer = new BufferedWriter(new FileWriter("triplog.csv")); // create writer to write to csv file

            writer.write("Time,Latitude,Longitude\n"); // label title

            int timeIncrement = 5; // increment

            long currentTime = 0; // starts @ 0

            for (int i = 0; i < trkptList.getLength(); i++) { // loop through node list
                Element trkpt = (Element) trkptList.item(i); // get node and get lat and lon wwhile calling clean method to remove any non numbers
                String lat = cleanCoordinate(trkpt.getAttribute("lat"));
                String lon = cleanCoordinate(trkpt.getAttribute("lon"));

                // Writing time, latitude, longitude to CSV
                writer.write(currentTime + "," + lat + "," + lon + "\n");

                // Incrementing time
                currentTime += timeIncrement;
            }

            writer.close();

            System.out.println("Conversion complete. CSV file saved as: triplog.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
    
    // Remove any non-numeric characters except '-' for negative sign
    private static String cleanCoordinate(String coordinate) {
        return coordinate.replaceAll("[^\\d.-]", "");
    }
	

}
