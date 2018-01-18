package test.java;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;


import org.junit.jupiter.api.*;


import main.java.Objects.WIFI;

class WIFITest {
	
	WIFI wifi;
	@BeforeEach
	public void init() {
		wifi= new WIFI("[HOT123","c0:ac:54:f6:c5:4b",1,-87);
	}

	@Test
	void testtoString() {
		init();
		String expected = "" + "[HOT123" + "," + "c0:ac:54:f6:c5:4b" + "," + "1" + "," + "-87" + "";
		assertThat(expected, is(wifi.toString()));
	}

}
