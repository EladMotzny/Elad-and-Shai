
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

public class WriteToCSV {
	/**
	 * source of the code1: https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
	 * source of the code2: https://www.mkyong.com/java/how-to-export-data-to-csv-file-java/
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/**
		 * this section input a WigleWifi csv file and import a csv file accoring
		 * to what we need to do in the assignment (file name of the input is "test4")
		 * 
		 * please note that you need to change the directory location according to
		 * your computer for it to read and export the file successfully
		 */

		String csvfile="C:\\Users\\computer\\Desktop\\csv\\test4.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			boolean skipfirst=false;
			br = new BufferedReader(new FileReader(csvfile));
			List<ArrayList<String>> inputCSV=new ArrayList<ArrayList<String>>();
			ArrayList<String> headlines=new ArrayList<String>();
			while ((line = br.readLine()) != null)
			{
				// use comma as separator
				String[] Getline = line.split(cvsSplitBy);
				if(!skipfirst)
				{
					skipfirst=true;
					headlines.add(Getline[0]);
					headlines.add(Getline[1]);
					headlines.add(Getline[2]);
					headlines.add(Getline[3]);
					headlines.add(Getline[4]);
					headlines.add(Getline[5]);
					headlines.add(Getline[6]);
					headlines.add(Getline[7]);
					headlines.add(Getline[8]);
					headlines.add(Getline[9]);
					headlines.add(Getline[10]);
				}
				else
				{
					ArrayList<String> inner=new ArrayList<String>();
					inner.add(Getline[0]);
					inner.add(Getline[1]);
					inner.add(Getline[2]);
					inner.add(Getline[3]);
					inner.add(Getline[4]);
					inner.add(Getline[5]);
					inner.add(Getline[6]);
					inner.add(Getline[7]);
					inner.add(Getline[8]);
					inner.add(Getline[9]);
					inner.add(Getline[10]);
					inputCSV.add(inner);
				}
			}
			/**
			 * here i used bubblesort on the list and sorted is by the signal(RSSI)
			 * as requested in the assignment
			 */
			bubbleSort(inputCSV);
			//print to check what i got:
			/*System.out.println(headlines);
			for(int i=0; i<=9; i++)
			{
				System.out.println(inputCSV.get(i));
			}*/

			/**
			 * here i write the first 10 spots of my list + the headlines into 
			 * a csv file (csvfile name is "finaltest2") and output it to my computer
			 */
			FileWriter writer = new FileWriter("C:\\Users\\computer\\Desktop\\csv\\finaltest2.csv");
			List<String> TopTen = new ArrayList<>();
			for(int k=0; k<headlines.size(); k++)
			{
				TopTen.add(headlines.get(k));
			}
			String collectheadlines= TopTen.stream().collect(Collectors.joining(","));
			String collect0 = inputCSV.get(0).stream().collect(Collectors.joining(","));
			String collect1 = inputCSV.get(1).stream().collect(Collectors.joining(","));
			String collect2 = inputCSV.get(2).stream().collect(Collectors.joining(","));
			String collect3 = inputCSV.get(3).stream().collect(Collectors.joining(","));
			String collect4 = inputCSV.get(4).stream().collect(Collectors.joining(","));
			String collect5 = inputCSV.get(5).stream().collect(Collectors.joining(","));
			String collect6 = inputCSV.get(6).stream().collect(Collectors.joining(","));
			String collect7 = inputCSV.get(7).stream().collect(Collectors.joining(","));
			String collect8 = inputCSV.get(8).stream().collect(Collectors.joining(","));
			String collect9 = inputCSV.get(9).stream().collect(Collectors.joining(","));

			writer.write(collectheadlines);
			writer.write("\n");
			writer.write(collect0);
			writer.write("\n");
			writer.write(collect1);
			writer.write("\n");
			writer.write(collect2);
			writer.write("\n");
			writer.write(collect3);
			writer.write("\n");
			writer.write(collect4);
			writer.write("\n");
			writer.write(collect5);
			writer.write("\n");
			writer.write(collect6);
			writer.write("\n");
			writer.write(collect7);
			writer.write("\n");
			writer.write(collect8);
			writer.write("\n");
			writer.write(collect9);
			writer.close();





// this is to catch Exception and wrong files
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
	 * this is the bubble sort i made to sort my list by signals(RSSI)
	 * @param arr
	 */
	static void bubbleSort(List<ArrayList<String>> arr)
	{  
		int n = arr.size();  
		ArrayList<String> temp=new ArrayList<String>();  
		for(int i=0; i < n; i++){  
			for(int j=1; j < (n-i); j++){ 
				int n1=Integer.parseInt(arr.get(j-1).get(5));
				int n2=Integer.parseInt(arr.get(j).get(5));
				if(n1 < n2){  
					//swap elements  
					temp = arr.get(j-1); 
					arr.set(j-1, arr.get(j));  
					arr.set(j, temp);
				}  

			}  
		}  
	}
	
}