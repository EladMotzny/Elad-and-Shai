import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.security.Timestamp;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.swing.plaf.synth.SynthSpinnerUI;

import com.sun.xml.txw2.Document;

import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.TimeStamp;

import java.util.ArrayList;
import java.io.FileWriter;

public class WriteToKML {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/**
		 * https://labs.micromata.de/projects/jak/quickstart.html
         * https://stackoverflow.com/questions/12701364/how-to-mark-multiple-coordinates-in-kml-using-java
		 */
		
		
		/**
		 * here i input the big 46 columns CSV file
		 * and insert the data to a collection (arraylist of arraylists)
		 * 
		 * please note that you need to change the directory location according to
		 * your computer for it to read and export the file successfully
		 */
		String csvFile = "C:\\Users\\computer\\Desktop\\csv\\finaltest46.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			List<ArrayList<String>> mycsv=new ArrayList<ArrayList<String>>();
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] input = line.split(cvsSplitBy);
				ArrayList<String> inner=new ArrayList<String>();
				for(int i=0; i<input.length; i++)
				{
					inner.add(input[i]);
				}
				mycsv.add(inner);
			}
			//print to check what i got
			/*for(int i=0; i<mycsv.size(); i++)
			{
				System.out.println(mycsv.get(i));
			}*/

			/**
			 * here i filter my list by Time, ID or location
			 * and insert the filtered list to a new list.
			 * in this example i chose to filter by Time 20:10.
			 * when you check our assignment you can change it and filter whatever you like :)
			 * note that in our table Location is in indexes 0,1,2 ID index is 3 and time index is 4
			 */
			Predicate<ArrayList<String>> condition1=s -> s.get(3).contains("Lenovo");
			List<ArrayList<String>> filteredStrings=filterby(mycsv ,condition1);
			//print to check what i got
			/*for(int j=0; j<filteredStrings.size(); j++)
			{
				System.out.println(filteredStrings.get(j));
			}*/


			
		/*	FileWriter writer2 = new FileWriter("C:\\Users\\computer\\Desktop\\csv\\KMLoutput46.kml");
			writer2.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>   ");
			writer2.write("<kml xmlns=\"http://earth.google.com/kml/2.0\">   ");
			writer2.write("<Document>   ");
			int c=1;
			for(int k=0; k<filteredStrings.size(); k++)
			{
				writer2.write("<Placemark>");
				writer2.write("<name>"+"point"+c+"</name>");
				writer2.write("<description>"+filteredStrings.get(k).get(3)+"</description>");
				writer2.write("<Point><coordinates>"+filteredStrings.get(k).get(1)+","+filteredStrings.get(k).get(0)+","+filteredStrings.get(k).get(2)+"</coordinates></Point>");
				writer2.write("</Placemark>");
				c++;
			}
			writer2.write("</Document>");
			writer2.write("</kml>");
			writer2.close();  */
			
			/**
			 * here i am writing my filtered list into a kml file 
			 * and then output it to my computer.
			 * file name is "KMLoutputAPI".
			 * we are using JAK library to create the kml file
			 */
			
			final Kml kml=new Kml();
			de.micromata.opengis.kml.v_2_2_0.Document doc=kml.createAndSetDocument().withName("kmlfile");
			for(int i=0; i<filteredStrings.size(); i++)
			{
				//String time=filteredStrings.get(i).get(4);
				//String timestamp=TimeConvert(time);
				String Location=filteredStrings.get(i).get(1)+","+filteredStrings.get(i).get(0);
				
				doc.createAndAddPlacemark().withName("point"+i).withOpen(Boolean.TRUE).
				createAndSetPoint().addToCoordinates(Location);
				//doc.createAndSetTimeStamp().addToObjectSimpleExtension(timestamp);
			}
			kml.marshal(new File("C:\\Users\\computer\\Desktop\\csv\\KMLoutputAPI.kml"));

			

			/**
			 * again to catch Exception and wrong files
			 */
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	/**
	 * this function help me to filter the list by time,location or ID
	 * @param strings
	 * @param condition
	 * @return
	 */
	public static List<ArrayList<String>> filterby(List<ArrayList<String>> strings, Predicate<ArrayList<String>> condition)
	{
		List<ArrayList<String>> output=new ArrayList<ArrayList<String>>(); //initalize empty list
		for(int i=0; i<strings.size(); i++)
		{
			if(condition.test(strings.get(i)))
			{
				output.add(strings.get(i));
			}
		}
		return output;

	}
	
	public static String TimeConvert(String time)
	{
		String time2=time.replace('/', '-');
		String time3=time2+":00";
		String day=time3.substring(0,2);
		String year=time3.substring(6, 10);
		String month=time3.substring(2, 6);
		String rest=" "+time3.substring(11, time3.length());
		String finaltime=year+month+day+rest;
		return finaltime;
	}




}


