import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;

/**
 * compare files: https://stackoverflow.com/questions/27379059/determine-if-two-files-store-the-same-content
 * @author emotz
 *
 */
class WriteToCsvTest {
	
	List<ArrayList<String>> expected; //for the bubbleSort
	File CSVtableCompare; //for the file compare for writethecsvtableTest
	List<ArrayList<String>> ListOfDada; //for createlistofdata
	HashMap<Location,ArrayList<WIFI>> MapTest;//for createMapTest

	@Before
	public void init() {
		CSVtableCompare = new File("C:\\Users\\emotz\\Desktop\\csvexamplefiles\\CSVOutput.csv");
		expected = new ArrayList<ArrayList<String>>();
	}
	
	@Test
	void createlistofdata() {
		
	}
	
	@Test
	void createMapTest() {
		
	}
	
	@Test
	void writethecsvtableTest() {
		
	}
	
/*
	@Test
	//IDEA: BBUBBLESORT TESTARR, COMPARE THE 2 STRINGS, DO A BOOLEAN TO SEE IF ITS THE SAME (TRUE IF IT IS)
	 * ASSERTTHAT(BOOLEAN,IS(TRUE))
	void bubbleSortTest() {
		expected.add("1");
		expected.add("2");
		expected.add("3");
		expected.add("4");
		expected.add("5");
		expected.add("6");
		List<ArrayList<String>> testArr=new ArrayList<ArrayList<String>>();
		testArr.add("6");
		testArr.add("1");
		testArr.add("2");
		testArr.add("3");
		testArr.add("5");
		testArr.add("4");
		bubbleSort(testArr)
		assertThat(bubbleSort(testArr).toString(), is(expected.toString()));
		OR assertArrayEquals(testArr,is(expected)); bubbleSort before


	}
*/
}
