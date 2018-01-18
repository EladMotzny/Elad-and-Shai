package test.java;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;


import org.junit.jupiter.api.*;


import main.java.MainClasses.Algorithems;
import main.java.Objects.M_S;
import main.java.Objects.Point3D;

class AlgorithemsTest {
	
	Algorithems A1;
	
	@BeforeEach
	public void init()
	{
		A1=new Algorithems();
	}

	@Test
	void testalgo1() {
		init();
		Point3D expected=A1.getWpoint("4c:60:de:d1:da:80");
		Point3D expected2=A1.getWpoint("4c:60:dw:d1:da:80");//fake mac
		double differencelat=32.17192733-expected.getLat();
		double differencelon=34.80130289-expected.getLon();
		double differencealt=30-expected.getAlt();
		boolean flaglat=differencelat<0.1;
		boolean flaglon=differencelon<0.1;
		boolean flagalt=differencealt<0.1;
		assertThat(flaglat, is(true));
		assertThat(flaglon, is(true));
		assertThat(flagalt, is(true));
		assertThat(expected2.getLat(), is(-1.0));
		assertThat(expected2.getLon(), is(-1.0));
		assertThat(expected2.getAlt(), is(-1.0));
	}
	
	@Test
	void testcenterW()
	{
		init();
		Point3D p1=new Point3D(1.0,2.0,3.0);
		Point3D p2=new Point3D(2.0,3.0,4.0);
		Point3D p3=new Point3D(3.0,4.0,5.0);
		Point3D[] points= {p1,p2,p3};
		double w1=0.5;
		double w2=1.0;
		double w3=0.8;
		double[] arr= {w1,w2,w3};
		Point3D ans=A1.CenterW(points, arr);
		assertThat(ans.getLat(), is(2.130434782608696));
		assertThat(ans.getLon(), is(3.130434782608696));
		assertThat(ans.getAlt(), is(4.130434782608696));
	}
	
	@Test
	void testalgo2()
	{
		init();
		M_S[] arr1=new M_S[3];
		arr1[0]=new M_S("cc:b2:55:68:0b:2a",-86);
		arr1[1]=new M_S("14:ae:db:32:cf:5a",-73);
		arr1[2]=new M_S("14:ae:db:32:cf:42",-92);
		M_S[] arr2=new M_S[3];
		arr2[0]=new M_S("cd:b2:55:68:0b:2a",-86); // fake mac
		arr2[1]=new M_S("15:ae:db:32:cf:5a",-73); //fake mac
		arr2[2]=new M_S("10:ae:db:32:cf:42",-92); // fake mac
		Point3D expected=A1.GetWlocation(arr1);
		Point3D expected2=A1.GetWlocation(arr2);
		assertThat(expected.getLat(), is(32.16641567059639));
		assertThat(expected.getLon(), is(34.80755912418933));
		assertThat(expected.getAlt(), is(35.43500340314969));
		assertThat(expected2.getLat(), is(-1.0));
		assertThat(expected2.getLon(), is(-1.0));
		assertThat(expected2.getAlt(), is(-1.0));
	}

}
