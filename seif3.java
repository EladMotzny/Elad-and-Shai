import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.plaf.synth.SynthSpinnerUI;


import java.util.ArrayList;
import java.io.FileWriter;

public class seif3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
			/*for(int i=0; i<=9; i++)
			{
				System.out.println(mycsv.get(i));
			}*/
			filter condition1=s -> s.equals("Partner");
			filter condition2=s -> s.contains("20:11");// not working
			filter condition3=s -> s.equals("32.09038727,34.87862948,56");//not working
			List<ArrayList<String>> filteredStrings=filterbyID(mycsv ,condition1);
			for(int j=0; j<filteredStrings.size(); j++)
			{
				System.out.println(filteredStrings.get(j));
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

	}
	public static List<ArrayList<String>> filterbyID(List<ArrayList<String>> strings,filter condition)
	{
		List<ArrayList<String>> output=new ArrayList<ArrayList<String>>(); //initalize empty list
		for(int i=0; i<strings.size(); i++)
		{
			String s=strings.get(i).get(1);
			if(condition.test(s)==true)
			{
				output.add(strings.get(i));
			}
		}
		return output;

	}
	public static List<ArrayList<String>> filterbyTime(List<ArrayList<String>> strings,filter condition)
	{
		List<ArrayList<String>> output=new ArrayList<ArrayList<String>>(); //initalize empty list
		for(int i=0; i<strings.size(); i++)
		{
			String s=strings.get(i).get(3);
			if(condition.test(s)==true)
			{
				output.add(strings.get(i));
			}
		}
		return output;

	}
	public static List<ArrayList<String>> filterbyLocation(List<ArrayList<String>> strings,filter condition)
	{
		List<ArrayList<String>> output=new ArrayList<ArrayList<String>>(); //initalize empty list
		for(int i=0; i<strings.size(); i++)
		{
			String s1=strings.get(i).get(6);//langtitude
			String s2=strings.get(i).get(7);//logtitude
			String s3=strings.get(i).get(8);//altitude
			String s=s1+","+s2+","+s3;
			if(condition.test(s)==true)
			{
				output.add(strings.get(i));
			}
		}
		return output;

	}




}


