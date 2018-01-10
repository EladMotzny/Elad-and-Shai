package Tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import Objects.Point3D;

class Point3DTest {
	
	public Point3D P;
	public Point3D emptyP;
	
	@Before
	public void init()
	{
		P=new Point3D(32.567,34.678,50);
		emptyP=new Point3D();
	}

	@Test
	void testpoint() {
		init();
		Point3D expected=new Point3D(32.567,34.678,50);
		assertThat(expected.getLon(), is(P.Lon));
		assertThat(expected.getLat(), is(P.Lat));
		assertThat(expected.getAlt(), is(P.Alt));
	}
	
	@Test
	void testemptypoint()
	{
		init();
		assertThat(-1.0, is(emptyP.Lon));
		assertThat(-1.0, is(emptyP.Lat));
		assertThat(-1.0, is(emptyP.Alt));
	}

}
