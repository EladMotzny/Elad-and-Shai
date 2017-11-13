import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.swing.plaf.synth.SynthSpinnerUI;


import java.util.ArrayList;
import java.io.FileWriter;

public class WriteToKML {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/**
		 * here i again input a csv file (the one with the top 10 signals)
		 * and insert the data into a collection (arraylist of arraylists)
		 * 
		 * please note that you need to change the directory location according to
		 * your computer for it to read and export the file successfully
		 */
		String csvFile = "C:\\Users\\computer\\Desktop\\csv\\finaltest2.csv";
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
				inner.add(input[0]);
				inner.add(input[1]);
				inner.add(input[2]);
				inner.add(input[3]);
				inner.add(input[4]);
				inner.add(input[5]);
				inner.add(input[6]);
				inner.add(input[7]);
				inner.add(input[8]);
				inner.add(input[9]);
				inner.add(input[10]);
				mycsv.add(inner);
			}
			//print to check what i got
			/*for(int i=0; i<=9; i++)
			{
				System.out.println(mycsv.get(i));
			}*/
			
			/**
			 * here i filter my list by Time, ID or location
			 * and insert the filtered list into a new list.
			 * in this example i chose to filter by ID and Time.
			 * when you check our assignment you can change it and filter whatever you like :)
			 */
			Predicate<ArrayList<String>> condition1=s -> s.get(1).contains("Partner") & s.get(3).contains("20:11");
			//this is example of filtering by ID and time;
			List<ArrayList<String>> filteredStrings=filterby(mycsv ,condition1);
			//print to check what i got
			/*for(int j=0; j<filteredStrings.size(); j++)
			{
				System.out.println(filteredStrings.get(j));
			}*/

			
			/**
			 * here i am writing my filtered list into a kml file 
			 * and then output it to my computer 
			 */
			FileWriter writer2 = new FileWriter("C:\\Users\\computer\\Desktop\\csv\\KMLoutput.kml");
			writer2.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>   ");
			writer2.write("<kml xmlns=\"http://earth.google.com/kml/2.0\">   ");
			writer2.write("<Document>   ");
			for(int k=0; k<filteredStrings.size(); k++)
			{
				writer2.write("<Placemark>");
				writer2.write("<name>"+filteredStrings.get(k).get(1)+"</name>");
				writer2.write("<description>"+filteredStrings.get(k).get(10)+"</description>");
				writer2.write("<Point><coordinates>"+filteredStrings.get(k).get(7)+","+filteredStrings.get(k).get(6)+","+filteredStrings.get(k).get(8)+"</coordinates></Point>");
				writer2.write("</Placemark>");
			}
			writer2.write("</Document>");
			writer2.write("</kml>");
			writer2.close();




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
			if(condition.test(strings.get(i))==true)
			{
				output.add(strings.get(i));
			}
		}
		return output;

	}




}


