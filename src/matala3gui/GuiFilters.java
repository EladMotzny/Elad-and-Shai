package matala3gui;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class GuiFilters {

	public List<ArrayList<String>> Data0;

	public GuiFilters(List<ArrayList<String>> data)
	{
		this.Data0=data;
	}
	public GuiFilters()
	{
		this.Data0=new ArrayList<ArrayList<String>>();
	}
	
	/**
	 * This function takes the list of data and filters it by location
	 * @param latmin the minimum latitude you want to filter by
	 * @param latmax the maximum latitude you want to filter by
	 * @param lonmin the minimum longitude you want to filter by
	 * @param lonmax the maximum longitude you want to filter by
	 * @param altmin the minimum altitude you want to filter by
	 * @param altmax the maximum altitude you want to filter by
	 * @param not checks if you want everything BUT those parameters
	 */
	public void FilterByMinMaxLoc(double latmin,double latmax,double lonmin,double lonmax,double altmin,double altmax,boolean not)
	{
		List<ArrayList<String>> ans=new ArrayList<ArrayList<String>>();
		if(!not) // if not button is not clicked in gui
		{
			for(int i=0; i<this.Data0.size(); i++)
			{
				double lat=Double.parseDouble(this.Data0.get(i).get(0));
				double lon=Double.parseDouble(this.Data0.get(i).get(1));
				double alt=Double.parseDouble(this.Data0.get(i).get(2));
				if((lat>=latmin && lat<=latmax) && 
						(lon>=lonmin && lon<=lonmax) &&
						(alt>=altmin && alt<=altmax))
				{
					ans.add(this.Data0.get(i));
				}
			}
			this.Data0=ans;
		}
		else // if not button is clicked in gui
		{
			for(int i=0; i<this.Data0.size(); i++)
			{
				double lat=Double.parseDouble(this.Data0.get(i).get(0));
				double lon=Double.parseDouble(this.Data0.get(i).get(1));
				double alt=Double.parseDouble(this.Data0.get(i).get(2));
				if((lat<=latmin || lat>=latmax) || 
						(lon<=lonmin || lon>=lonmax) ||
						(alt<=altmin || alt>=altmax))
				{
					ans.add(this.Data0.get(i));
				}
			}
			this.Data0=ans;
		}
	}
	
	/**
	 * This function takes the data and filters it by ID
	 * @param ID the ID you want to filter by
	 * @param not checks if you want to filter by everything BUT the given ID
	 */
	public void FilterByID(String ID,boolean not)
	{
		List<ArrayList<String>> ans=new ArrayList<ArrayList<String>>();
		if(!not) // if not button is not clicked in gui
		{
			for(int i=0; i<this.Data0.size(); i++)
			{
				if(this.Data0.get(i).get(3).contains(ID))
				{
					ans.add(this.Data0.get(i));
				}
			}
			this.Data0=ans;
		}
		else // if not button is clicked in gui
		{
			for(int i=0; i<Data0.size(); i++)
			{
				if(!(Data0.get(i).get(3).contains(ID)))
				{
					ans.add(this.Data0.get(i));
				}
			}
			this.Data0=ans;
		}
	}

	/**
	 * This function filters by time
	 * @param MinTime time from
	 * @param MaxTime time to
	 * @param not if you want everything BUT the given time
	 */
	public void FilterByMinMaxTime(String MinTime,String MaxTime,boolean not)
	{
		List<ArrayList<String>> ans=new ArrayList<ArrayList<String>>();
		LocalTime MinTimeR=LocalTime.of(Integer.parseInt(MinTime.substring(0,2)),Integer.parseInt(MinTime.substring(3,5)),Integer.parseInt(MinTime.substring(6)));
		LocalTime MaxTimeR=LocalTime.of(Integer.parseInt(MaxTime.substring(0,2)),Integer.parseInt(MaxTime.substring(3,5)),Integer.parseInt(MaxTime.substring(6)));
		if(!not) //if not button is not clicked in gui
		{
			for(int i=0; i<this.Data0.size(); i++)
			{
				LocalTime datatime=LocalTime.of(Integer.parseInt(this.Data0.get(i).get(4).substring(11,13)),Integer.parseInt(Data0.get(i).get(4).substring(14,16)),Integer.parseInt(Data0.get(i).get(4).substring(17,19)));
				if(datatime.isAfter(MinTimeR) && datatime.isBefore(MaxTimeR))
				{
					ans.add(this.Data0.get(i));
				}
			}
			this.Data0=ans;
		}
		else // if not button is clicked in gui
		{
			for(int i=0; i<this.Data0.size(); i++)
			{
				LocalTime datatime=LocalTime.of(Integer.parseInt(this.Data0.get(i).get(4).substring(11,13)),Integer.parseInt(Data0.get(i).get(4).substring(14,16)),Integer.parseInt(Data0.get(i).get(4).substring(17,19)));
				if(datatime.isAfter(MaxTimeR) || datatime.isBefore(MinTimeR))
				{
					ans.add(this.Data0.get(i));
				}
			}
			this.Data0=ans;
		}
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WriteToKML A1=new WriteToKML();
		List<ArrayList<String>> test=A1.inputheCSVfile();
		GuiFilters B1=new GuiFilters(test);
		//String mintime="16:20:00";
		//String maxtime="16:30:00";
		//B1.FilterByMinMaxTime(mintime, maxtime, false);
		String id="SHIELD";
		B1.FilterByID(id, false);
		//System.out.println(B1.Data0);
		System.out.println(test);
	}
	
	/**
	 * takes 2 lists and merges the data
	 * @param Data1
	 * @param Data2
	 * @return merged List
	 */
	public static List<ArrayList<String>> MergeData(List<ArrayList<String>> Data1, List<ArrayList<String>> Data2)
	{
		List<ArrayList<String>> Merged=new ArrayList<ArrayList<String>>();
		for(int i=0; i<Data1.size(); i++)
		{
			Merged.add(Data1.get(i));
		}
		for(int j=0; j<Data2.size(); j++)
		{
			if(!(Merged.contains(Data2.get(j))))
			{
				Merged.add(Data2.get(j));
			}
		}
		return Merged;
	}

}
