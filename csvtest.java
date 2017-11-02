
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.io.FileWriter;

public class csvtest {
	/**
	 * source of the code: https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String csvfile="C:\\Users\\computer\\Desktop\\csv\\test4.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			boolean skipfirst=false;
			br = new BufferedReader(new FileReader(csvfile));
			List<ArrayList<String>> test2=new ArrayList<ArrayList<String>>();
			ArrayList<String> headlines=new ArrayList<String>();
			while ((line = br.readLine()) != null)
			{
				// use comma as separator
				String[] test = line.split(cvsSplitBy);
				if(!skipfirst)
				{
					skipfirst=true;
					headlines.add(test[0]);
					headlines.add(test[1]);
					headlines.add(test[2]);
					headlines.add(test[3]);
					headlines.add(test[4]);
					headlines.add(test[5]);
					headlines.add(test[6]);
					headlines.add(test[7]);
					headlines.add(test[8]);
					headlines.add(test[9]);
					headlines.add(test[10]);
				}
				else
				{
					ArrayList<String> inner=new ArrayList<String>();
					inner.add(test[0]);
					inner.add(test[1]);
					inner.add(test[2]);
					inner.add(test[3]);
					inner.add(test[4]);
					inner.add(test[5]);
					inner.add(test[6]);
					inner.add(test[7]);
					inner.add(test[8]);
					inner.add(test[9]);
					inner.add(test[10]);
					test2.add(inner);
				}
			}
			bubbleSort(test2);
			System.out.println(headlines);
			for(int i=0; i<=9; i++)
			{
				System.out.println(test2.get(i));
			}
			String csvoutput="C:\\Users\\computer\\Desktop\\csv\\finaltest.csv";
			FileWriter writer = new FileWriter(csvoutput);
			CSVutils.writeLine(writer, Arrays.asList("a", "b", "c", "d"));
			

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
	static void bubbleSort(List<ArrayList<String>> arr) {  
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