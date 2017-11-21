import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.KmlFactory;
import de.micromata.opengis.kml.v_2_2_0.Placemark;

/**
 * https://labs.micromata.de/projects/jak/quickstart.html
 * https://stackoverflow.com/questions/12701364/how-to-mark-multiple-coordinates-in-kml-using-java
 */

public class WriteToKML {

	public String address;

	public WriteToKML()
	{
		//the address of the 46 columns csv table we made in the previous class
		this.address="C:\\Users\\emotz\\Desktop\\csvexamplefiles\\CSVOutput.csv";
	}

	/**
	 * in "inputtheCSVfile" function i input the big 46 columns CSV file
	 * that we made in WriteToCSV class
	 * and insert the data to a collection (arraylist of arraylists)
	 * 
	 * please note that you need to change the location of the file according to
	 * your computer for it to read the file successfully
	 */
	
	public static List<ArrayList<String>> inputheCSVfile(WriteToKML s)
	{
		List<ArrayList<String>> mycsv=new ArrayList<ArrayList<String>>();
		String csvFile =s.address;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(csvFile));
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
		return mycsv;
	}
	
	/**
	 * in "filterthelist" funtion i filter my list by Time, ID or location
	 * and insert the filtered list to a new list.
	 * please note the following instruction on how to filter succesfully:
	 * /**
		 * to filter by ID, enter the ID name by String and number 3!
		 * to filter by Time, enter the Time by String and number 4!
		 * to filter by Location, enter String like this: "Lat,Lon" and choose other number than 3 or 4!
		 * 
		 * 
		 * this instructions also written in the main
		 */
	
	public static List<ArrayList<String>> filterthelist(List<ArrayList<String>> list, String filterby, int choose )
	{
		Predicate<ArrayList<String>> condition1;
		if(choose==3)// filter by ID
		{
			condition1=s -> s.get(3).contains(filterby);
		}
		else if(choose==4)//filter by Time
		{
			condition1=s -> s.get(4).contains(filterby);
		}
		else // filter by Location
		{
		    condition1=s -> s.get(0).contains(filterby) & s.get(1).contains(filterby);
		}
		List<ArrayList<String>> filteredStrings=filterby(list ,condition1);
		return filteredStrings;
	}
	
	/**
	 * in "writethekmlfile" function i am writing my filtered list into a kml file 
	 * and then output it to my computer.
	 * file name is "KMLoutputAPI".
	 * we are using JAK library to create the kml file
	 */
	
	public static void writethekmlfile(List<ArrayList<String>> filteredlist) throws FileNotFoundException
	{
		final Kml kml=new Kml();
		Document doc=kml.createAndSetDocument().withName("my points");
		for(int i=0; i<filteredlist.size(); i++)
		{
			String Location=filteredlist.get(i).get(1)+","+filteredlist.get(i).get(0);
			String time=filteredlist.get(i).get(4);
			Timestamp ts=Timestamp.valueOf(time);
			Placemark p=KmlFactory.createPlacemark();
			p.createAndSetTimeStamp().addToTimeStampSimpleExtension(ts);
			doc.createAndAddPlacemark().withName("point"+i).withOpen(Boolean.TRUE).withTimePrimitive(p.getTimePrimitive())
			.createAndSetPoint().addToCoordinates(Location);
		}
		kml.marshal(new File("C:\\Users\\emotz\\Desktop\\csvexamplefiles\\KMLoutputID.kml"));
	}



	public static void main(String[] args) throws FileNotFoundException {
		
		/**
		 * some tests to see that my functions work properly
		 */
		
		WriteToKML test=new WriteToKML();
		List<ArrayList<String>> check=new ArrayList<ArrayList<String>>();
		check=inputheCSVfile(test);
		List<ArrayList<String>> afterfilter=new ArrayList<ArrayList<String>>();
		/**
		 * to filter by ID, enter the ID name String and number 3!
		 * to filter by Time, enter the Time by String and number 4!
		 * to filter by Location, enter String like this: "Lat,Lon" and choose other number than 3 or 4!
		 */
		afterfilter=filterthelist(check,"Lenovo",3);
		writethekmlfile(afterfilter);
		//System.out.println(afterfilter);
		//System.out.println(check);
		
		String time=afterfilter.get(2).get(4);
		System.out.println(time);
		
		
		

	}
	
	/**
	 * this function help me to filter the list by interface
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
	
	/**
	 * this function Convert the Time on the CSV file to Time in format
	 * that fit timestamp primitive
	 */
	
	public static String TimeConvert(String time)
	{
		String time2=time.replace('/', '-');
		String time3=time2;
		String day=time3.substring(0,2);
		String year=time3.substring(6, 10);
		String month=time3.substring(2, 6);
		String rest=time3.substring(11, time3.length());
		String finaltime=year+month+day+rest;
		return finaltime;
	}


}
