
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.plaf.synth.SynthSpinnerUI;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.FilenameFilter;

public class WriteToCSV {
	/**
	 * source of the code1: https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
	 * source of the code2: https://www.mkyong.com/java/how-to-export-data-to-csv-file-java/
	 * source of the code3: http://www.avajava.com/tutorials/lessons/how-do-i-use-a-filenamefilter-to-display-a-subset-of-files-in-a-directory.html;jsessionid=C8740187DF79764CE4DAD895FD5078F0
	 * @param args
	 */

	public static void main(String[] args) throws IOException  {
		// TODO Auto-generated method stub

		/**
		 * this section input a Directory with csv files and make them one big csv file accoring
		 * to what we need to do in the assignment (file name of the input is "test4")
		 * 
		 * please note that you need to change the directory location according to
		 * your computer for it to read the directory and export the file successfully
		 */
		List<ArrayList<String>> inputCSV=new ArrayList<ArrayList<String>>();
		ArrayList<String> headlines=new ArrayList<String>();
		int count=0;

		File f = new File("C:\\Users\\computer\\Desktop\\inputD"); // current directory

		FilenameFilter textFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				String lowercaseName = name.toLowerCase();
				if (lowercaseName.endsWith(".csv")) {
					return true;
				} else {
					return false;
				}
			}
		};

		File[] files = f.listFiles(textFilter);
		for (File file : files) {

			if (file.isDirectory()) {
				//System.out.print("directory:");
			} else {
				//System.out.print("file:");
			}
			//System.out.println(file.getCanonicalPath());



			String csvfile=file.getCanonicalPath();
			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = ",";

			try {


				br = new BufferedReader(new FileReader(csvfile));
				while ((line = br.readLine()) != null)
				{
					// use comma as separator
					String[] Getline = line.split(cvsSplitBy);
					if(count==0)
					{
						count++;
						for(int i=0; i<=10; i++)
						{
							headlines.add(Getline[i]);
						}
					}
					else if(Getline[5].equals("RSSI"))
					{
						continue;
					}
					else
					{
						ArrayList<String> inner=new ArrayList<String>();
						for(int i=0; i<=10; i++)
						{
							inner.add(Getline[i]);
						}
						inputCSV.add(inner);
					}
				}
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
		 * here i used bubblesort on the list and sorted is by signal(SSID)
		 * as requested in the matala
		 */
		bubbleSort(inputCSV);

		/**
		 * optional sort instead of bubble sort (not ready)
		 */
		/*inputCSV.sort(new Comparator<ArrayList<String>>() {

		@Override
		public int compare(ArrayList<String> arg0, ArrayList<String> arg1) {
			// TODO Auto-generated method stub
			return 0;
		}

	});*/

		//print to check what i got:
		/*System.out.println(headlines);
		for(int i=0; i<=9; i++)
		{
			System.out.println(inputCSV.get(i));
		}*/

		/**
		 * here i write the first 10 spots of my list + headlines line to 
		 * a csv file (csvfile name is "finaltest2") and output it to my computer
		 */
		FileWriter writer = new FileWriter("C:\\Users\\computer\\Desktop\\csv\\finaltest2.csv");
		List<String> TopTen = new ArrayList<>();
		for(int k=0; k<headlines.size(); k++)
		{
			TopTen.add(headlines.get(k));
		}
		String collectheadlines= TopTen.stream().collect(Collectors.joining(","));
		writer.write(collectheadlines);
		writer.write("\n");
		for(int i=0; i<=9; i++)
		{
			String collect0 = inputCSV.get(i).stream().collect(Collectors.joining(","));
			writer.write(collect0);
			writer.write("\n");
		}
		writer.close();
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