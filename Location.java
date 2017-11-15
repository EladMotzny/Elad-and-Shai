

/**
 * this class define Location by Lat,Lon,Alt,ID and time
 * @author computer
 *
 */
public class Location {
	
	public double Lat;
	public double Lon;
	public double Alt;
	public String ID; 
	public String Time;
	
	public Location(double Lat, double Lon, double Alt,String ID, String time)
	{
		this.Lat=Lat;
		this.Lon=Lon;
		this.Alt=Alt;
		this.ID=ID;
		this.Time=time;
	}
	public boolean equalocation(Location L)
	{
		if(this.Lat==L.Lat & this.Lon==L.Lon & this.Alt==L.Alt)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	

	@Override
	public String toString() {
		return "" + Lat + "," + Lon + "," + Alt + "," + ID + "," + Time + "";
	}




	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
