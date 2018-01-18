package test.java;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;


import org.junit.jupiter.api.*;


import main.java.Objects.Point3D;

class Point3DTest {
	
	public Point3D P;
	public Point3D emptyP;
	
	@BeforeEach
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
