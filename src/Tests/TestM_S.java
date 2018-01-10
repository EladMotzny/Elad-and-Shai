package Tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import Objects.M_S;

class TestM_S {
	
	public M_S ms;
	public M_S emptyms;
	
	@Before
	public void init()
	{
		String mac="a4:2b:8c:9d:20:f7";
		int signal=60;
		ms= new M_S(mac,signal);
		emptyms = new M_S(mac);
	}
	
	@Test
	void testconstructor() {
		init();
		M_S expected=new M_S("a4:2b:8c:9d:20:f7",60);
		assertThat(expected.getMac(), is(ms.getMac()));
		assertThat(expected.getSignal(), is(ms.getSignal()));
	}
	
	
	@Test
	void testemptyconstructor() {
		init();
	    M_S expected=new M_S("a4:2b:8c:9d:20:f7");
	    assertThat(expected.getMac(), is(emptyms.getMac()));
		assertThat(-12000, is(emptyms.getSignal()));
	}

}
