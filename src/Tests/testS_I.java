package Tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import Objects.S_I;

class testS_I {
	
	public S_I si;

	@Before
	public void init()
	{
		si=new S_I(-60,2);
	}
	
	@Test
	void testconstructor() {
		init();
		S_I expected=new S_I(-60,2);
		assertThat(expected.getSignal(), is(si.Signal));
		assertThat(expected.getIndex(), is(si.Index));
	}
	
	@Test
	void testS_Ibubblesort()
	{
		List<S_I> expected=new ArrayList<S_I>();
		expected.add(new S_I(-60,1));
		expected.add(new S_I(-50,2));
		expected.add(new S_I(-70,3));
		expected.add(new S_I(-45,4));
		expected.add(new S_I(-80,5));
		S_I.S_IbubbleSort(expected);
		assertThat(expected.get(0).getSignal(), is(-45));
		assertThat(expected.get(4).getSignal(), is(-80));
	}

}
