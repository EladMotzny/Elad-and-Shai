package Filters;

import java.io.Serializable;

public class SaveFilters implements Serializable{
	
	public String ID;
	public String TimeFrom;
	public String TimeTo;
	public String LatMin;
	public String LatMax;
	public String LonMin;
	public String LonMax;
	public String AltMin;
	public String AltMax;
	
	public String AndOrNone;

}
