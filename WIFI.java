
/**
 * this class define WIFI by SSID,MAC,Frequency and Signal
 * @author computer
 *
 */
public class WIFI {
	
	public String SSID;
	public String MAC;
	public int Frequency;
	public int Signal;
	
	public WIFI(String SSID, String MAC, int F, int Signal)
	{
		this.SSID=SSID;
		this.MAC=MAC;
		this.Frequency=F;
		this.Signal=Signal;
	}
	

	@Override
	public String toString() {
		return "" + SSID + "," + MAC + "," + Frequency + "," + Signal + "";
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
