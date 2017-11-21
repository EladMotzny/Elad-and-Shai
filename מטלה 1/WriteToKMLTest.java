import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;

class WriteToKMLTest {
	Timestamp ts; //for TimeConvertTest
	List<ArrayList<String>> inputCSV;//for inputheCSVfile maybe use the actual file?
	List<ArrayList<String>> FilterListTest;//for filterthelist
	File KMLOutpuTest;//for writethekmlfileTest
	List<ArrayList<String>> FilteredByTest;//for filterbyTest
	
	
	@Before
	public void init() {
		String time="2017-10-27 16:19:59.0";
		ts=Timestamp.valueOf(time);
	}

	@Test
	void inputheCSVfileTest() {
		
	}
	
	@Test
	void filterthelistTest() {
		
	}
	
	@Test
	void writethekmlfileTest() {
		
	}
	
	@Test
	void filterbyTest() {
		
	}
	
	@Test
	void TimeConvertTest() {
		
		//assertThat(, is(ts));
	}
	
	
}
