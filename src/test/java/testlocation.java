package test.java;
import static org.hamcrest.MatcherAssert.*;


import org.junit.jupiter.api.*;


import static org.hamcrest.CoreMatchers.*;


import main.java.Objects.Location;

class testlocation {
	
	Location m;
	@BeforeEach
	public void init() {
		m=new Location(34.0,32.05,50,"shlomo","20:10");
	}
	
	@Test
	void testequallocation() {
		init();
	assertThat(m.getLat(), is(34.0));
	}
	
	@Test
	void testtoString() {
		init();
		String expected = "" + "34.0" + "," + "32.05" + "," + "50.0" + "," + "shlomo" + "," + "20:10" + "";
		assertThat(expected, is(m.toString()));
	}

}
