package test.java;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;


import main.java.Filters.GuiFilters;
import main.java.MainClasses.WriteToKML;

class GuiFiltersTest {
	
	WriteToKML input;
	GuiFilters filtertest;
	
	@BeforeEach
	public void init()
	{
		input=new WriteToKML();
		filtertest=new GuiFilters(input.inputheCSVfile());
	}

	@Test
	void testlocationfilter() {
		init();
		List<ArrayList<String>> expected=new ArrayList<ArrayList<String>>();
		expected.add(filtertest.Data0.get(0));
		filtertest.FilterByMinMaxLoc(32.1, 32.2, 34.5, 34.9, 20, 40, false);
		boolean flag=filtertest.Data0.contains(expected.get(0));
		assertThat(flag, is(true));
	}
	
	@Test
	void testNOTlocationfilter() 
	{
		init();
		List<ArrayList<String>> expected=new ArrayList<ArrayList<String>>();
		expected.add(filtertest.Data0.get(0));
		filtertest.FilterByMinMaxLoc(32.1, 32.2, 34.5, 34.9, 20, 40, true);
		boolean flag=filtertest.Data0.contains(expected.get(0));
		assertThat(flag, is(false));
	}
	
	@Test
	void testFilterID()
	{
		init();
		List<ArrayList<String>> expected=new ArrayList<ArrayList<String>>();
		expected.add(filtertest.Data0.get(4));
		filtertest.FilterByID("SHIELD", false);
		boolean flag=filtertest.Data0.contains(expected.get(0));
		assertThat(flag, is(true));
	}
	
	@Test
	void testNOTFilterID()
	{
		init();
		List<ArrayList<String>> expected=new ArrayList<ArrayList<String>>();
		expected.add(filtertest.Data0.get(4));
		filtertest.FilterByID("SHIELD", true);
		boolean flag=filtertest.Data0.contains(expected.get(0));
		assertThat(flag, is(false));
	}
	
	@Test
	void testFilterTime()
	{
		init();
		List<ArrayList<String>> expected=new ArrayList<ArrayList<String>>();
		expected.add(filtertest.Data0.get(0));
		filtertest.FilterByMinMaxTime("16:20:00","16:30:00", false);
		boolean flag=filtertest.Data0.contains(expected.get(0));
		assertThat(flag, is(true));
	}
	
	@Test
	void testNOTFilterTime()
	{
		init();
		List<ArrayList<String>> expected=new ArrayList<ArrayList<String>>();
		expected.add(filtertest.Data0.get(0));
		filtertest.FilterByMinMaxTime("16:20:00","16:30:00", true);
		boolean flag=filtertest.Data0.contains(expected.get(0));
		assertThat(flag, is(false));
	}
	
	@Test
	void testmergedata()
	{
		init();
		List<ArrayList<String>> temp1=new ArrayList<ArrayList<String>>();
		List<ArrayList<String>> temp2=new ArrayList<ArrayList<String>>();
		temp1.add(filtertest.Data0.get(0));
		temp2.add(filtertest.Data0.get(1));
		List<ArrayList<String>> merged=filtertest.MergeData(temp1, temp2);
		assertThat(merged.size(), is(2));
		assertThat(merged.contains(temp1.get(0)), is(true));
		assertThat(merged.contains(temp2.get(0)), is(true));
		
	}
	

}
