package matala3gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.wb.swt.SWTResourceManager;

public class GUI extends Thread {
	private Text FolderPath;
	private Text FilePath;
	private Text TimeFrom;
	private Text TimeTo;
	private Text LatMin;
	private Text LatMax;
	private Text LonMin;
	private Text LonMax;
	private Text AltMin;
	private Text AltMax;
	private Text IDtext;
	private Text MAC1;
	private Text Signal1;
	private Text MAC2;
	private Text MAC3;
	private Text Signal2;
	private Text Signal3;

	public List<ArrayList<String>> PreData;
	public List<ArrayList<String>> Data;
	public List<ArrayList<String>> DataTemp;

	public GUI()
	{
		this.PreData=new ArrayList<ArrayList<String>>();
		this.Data=new ArrayList<ArrayList<String>>();
		this.DataTemp=new ArrayList<ArrayList<String>>();
	}


	static GUI Glist=new GUI();
	
	static boolean Timecheckbox=false;
	static boolean Locationcheckbox=false;
	static boolean IDcheckbox=false;
	static boolean Andbutt=false;
	static boolean Orbutt=false;
	static boolean TimeNot=false;
	static boolean LocationNot=false;
	static boolean IDNot=false;
	static boolean NoneButt=false;
	private Text MacForAlg1;
	
	static boolean TerminateThread=false;
	
	static Thread t1;
	
	static ObjectInputStream kelet;
	static SaveFilters upload;

	/**
	 * Launch the application.
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {	
	
		try {	
			GUI window = new GUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 * @throws InterruptedException 
	 */
	public void open() throws InterruptedException {
		Display display = Display.getDefault();
		Shell shell = new Shell();
		shell.setSize(800,1000);
		shell.setText("SWT Application");

		FolderPath = new Text(shell, SWT.BORDER);
		FolderPath.setBounds(10, 31, 76, 21);

		Label lblEnterFolderPath = new Label(shell, SWT.NONE);
		lblEnterFolderPath.setBounds(10, 10, 99, 15);
		lblEnterFolderPath.setText("Enter folder path");

		Button btnAdd = new Button(shell, SWT.NONE);
		btnAdd.setText("Add");
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				try {
					String FolderAddress=FolderPath.getText();
					
					t1=new Thread(new Watcher(FolderAddress));
					t1.start();
					
					WriteToCsv TakeFolder=new WriteToCsv(FolderAddress);
					Glist.PreData=TakeFolder.createlistofdata();
					HashMap<Location,ArrayList<WIFI>> m1=new HashMap();
					m1=TakeFolder.createMap(Glist.PreData);
					TakeFolder.writethecsvtable(m1);//file name will be guioutput
					WriteToKML getData=new WriteToKML();
					Glist.Data=getData.inputheCSVfile();
					Glist.DataTemp=Glist.Data;
				} catch (IOException e1) {
					e1.printStackTrace();
					return;
				}

			}
		});
		btnAdd.setBounds(92, 29, 75, 25);

		Label lblEnterCsvFile = new Label(shell, SWT.NONE);
		lblEnterCsvFile.setBounds(10, 80, 99, 15);
		lblEnterCsvFile.setText("Enter CSV file path");

		FilePath = new Text(shell, SWT.BORDER);
		FilePath.setBounds(10, 101, 76, 21);

		Button btnAdd_1 = new Button(shell, SWT.NONE);
		btnAdd_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String FileAddress=FilePath.getText();
				WriteToKML TakeFile=new WriteToKML();
				Glist.DataTemp=TakeFile.inputheCSVfile(FileAddress);
				for(int i=0; i<Glist.DataTemp.size(); i++)
				{
					if(!(Glist.Data.contains(Glist.DataTemp.get(i))))
					{
						Glist.Data.add(Glist.DataTemp.get(i));
					}
				}
				Glist.DataTemp=Glist.Data;
			}
		});
		btnAdd_1.setBounds(92, 99, 75, 25);
		btnAdd_1.setText("Add");

		Button btnDelete = new Button(shell, SWT.NONE);
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Glist.Data.clear();
				MessageDialog.openConfirm(shell, "Alert!", "Data Deleted");
			}
		});
		btnDelete.setBounds(10, 128, 157, 25);
		btnDelete.setText("DELETE");

		Button btnSave = new Button(shell, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				WriteToCsv savecsv=new WriteToCsv();
				try {
					savecsv.writethecsvtable(Glist.Data);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSave.setBounds(558, 232, 119, 25);
		btnSave.setText("Save as CSV");

		Button btnSaveAsKml = new Button(shell, SWT.NONE);
		btnSaveAsKml.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				WriteToKML makekml=new WriteToKML();
				try {
					makekml.writethekmlfile(Glist.Data);
				} catch (FileNotFoundException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSaveAsKml.setBounds(558, 263, 119, 25);
		btnSaveAsKml.setText("Save as KML");

		final Label Entries = new Label(shell, SWT.NONE);
		Entries.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		Entries.setBounds(10, 221, 176, 32);
		Entries.setText("Number of entries:");

		final Label Routers = new Label(shell, SWT.NONE);
		Routers.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		Routers.setBounds(10, 259, 176, 31);
		Routers.setText("Number of routers:");

		Button btnShowInfo = new Button(shell, SWT.NONE);
		btnShowInfo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Entries.setText("Number of entries: "+Glist.Data.size());
				WriteToKML routers=new WriteToKML();
				Routers.setText("Number of routers: "+routers.RouterCount(Glist.Data));

			}
		});
		btnShowInfo.setBounds(10, 190, 157, 25);
		btnShowInfo.setText("Show info");

		Label label = new Label(shell, SWT.SEPARATOR | SWT.VERTICAL);
		label.setBounds(192, 10, -23, 342);

		Label lblFilters = new Label(shell, SWT.NONE);
		lblFilters.setBounds(376, 10, 55, 15);
		lblFilters.setText("Filters");

		Label lblFrom = new Label(shell, SWT.NONE);
		lblFrom.setBounds(207, 58, 34, 15);
		lblFrom.setText("From:");

		TimeFrom = new Text(shell, SWT.BORDER);
		TimeFrom.setBounds(247, 55, 76, 21);

		Label lblTo = new Label(shell, SWT.NONE);
		lblTo.setBounds(207, 80, 34, 15);
		lblTo.setText("To:");

		TimeTo = new Text(shell, SWT.BORDER);
		TimeTo.setBounds(247, 77, 76, 21);

		Button TimeButton = new Button(shell, SWT.CHECK);
		TimeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(Timecheckbox==false)
				{
					Timecheckbox=true;
				}
				else
				{
					Timecheckbox=false;
				}
			}
		});
		TimeButton.setBounds(209, 33, 93, 16);
		TimeButton.setText("Filter by time");

		Button LocationButton = new Button(shell, SWT.CHECK);
		LocationButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(Locationcheckbox==false)
				{
					Locationcheckbox=true;
				}
				else
				{
					Locationcheckbox=false;
				}
			}
		});
		LocationButton.setBounds(338, 36, 109, 16);
		LocationButton.setText("Filter by location");

		Label lblLatitude = new Label(shell, SWT.NONE);
		lblLatitude.setBounds(348, 58, 55, 15);
		lblLatitude.setText("Latitude:");

		Label lblMinimum = new Label(shell, SWT.NONE);
		lblMinimum.setBounds(410, 58, 24, 15);
		lblMinimum.setText("Min:");

		LatMin = new Text(shell, SWT.BORDER);
		LatMin.setBounds(436, 52, 76, 21);

		Label lblMax = new Label(shell, SWT.NONE);
		lblMax.setBounds(410, 90, 24, 15);
		lblMax.setText("Max:");

		LatMax = new Text(shell, SWT.BORDER);
		LatMax.setBounds(436, 84, 76, 21);

		Label lblLongitude = new Label(shell, SWT.NONE);
		lblLongitude.setBounds(348, 127, 55, 15);
		lblLongitude.setText("Longitude:");

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(410, 127, 24, 15);
		lblNewLabel.setText("Min:");

		LonMin = new Text(shell, SWT.BORDER);
		LonMin.setBounds(436, 119, 76, 21);

		Label lblMax_1 = new Label(shell, SWT.NONE);
		lblMax_1.setBounds(410, 158, 24, 15);
		lblMax_1.setText("Max:");

		LonMax = new Text(shell, SWT.BORDER);
		LonMax.setBounds(436, 152, 76, 21);

		Label lblAltitude = new Label(shell, SWT.NONE);
		lblAltitude.setBounds(348, 182, 45, 15);
		lblAltitude.setText("Altitude:");

		Label lblMin = new Label(shell, SWT.NONE);
		lblMin.setBounds(410, 182, 24, 15);
		lblMin.setText("Min:");

		Label lblMax_2 = new Label(shell, SWT.NONE);
		lblMax_2.setBounds(410, 210, 24, 15);
		lblMax_2.setText("Max:");

		AltMin = new Text(shell, SWT.BORDER);
		AltMin.setBounds(436, 179, 76, 21);

		AltMax = new Text(shell, SWT.BORDER);
		AltMax.setBounds(436, 204, 76, 21);

		Button IDButton = new Button(shell, SWT.CHECK);
		IDButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(IDcheckbox==false)
				{
					IDcheckbox=true;
				}
				else
				{
					IDcheckbox=false;
				}
			}
		});
		IDButton.setBounds(541, 36, 93, 16);
		IDButton.setText("Filter By ID");

		Label lblDeviceName = new Label(shell, SWT.NONE);
		lblDeviceName.setBounds(541, 58, 76, 15);
		lblDeviceName.setText("Device name:");

		IDtext = new Text(shell, SWT.BORDER);
		IDtext.setBounds(619, 52, 76, 21);

		Button btnNotLocation = new Button(shell, SWT.CHECK);
		btnNotLocation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(LocationNot==false)
				{
					LocationNot=true;
				}
				else
				{
					LocationNot=false;
				}
			}
		});
		btnNotLocation.setBounds(410, 232, 93, 16);
		btnNotLocation.setText("Not");

		Button btnNotTime = new Button(shell, SWT.CHECK);
		btnNotTime.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(TimeNot==false)
				{
					TimeNot=true;
				}
				else
				{
					TimeNot=false;
				}
			}
		});
		btnNotTime.setBounds(246, 104, 93, 16);
		btnNotTime.setText("Not");

		Button btnNotID = new Button(shell, SWT.CHECK);
		btnNotID.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(IDNot==false)
				{
					IDNot=true;
				}
				else
				{
					IDNot=false;
				}
			}
		});
		btnNotID.setBounds(619, 82, 93, 16);
		btnNotID.setText("Not");

		Label Filteredby = new Label(shell, SWT.NONE);
		Filteredby.setBounds(174, 294, 729, 15);
		Filteredby.setText("Filtered by: None at the moment");

		Button btnSubmitFilter = new Button(shell, SWT.NONE);
		btnSubmitFilter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(NoneButt==true)
				{
					GuiFilters filt=new GuiFilters(Glist.Data);
					if(Timecheckbox==true)
					{
						String from=TimeFrom.getText();
						String to=TimeTo.getText();
						filt.FilterByMinMaxTime(from, to, TimeNot);
						Glist.Data=filt.Data0;
						if(TimeNot==false)
						{
							Filteredby.setText("Filtered by: {"+from+"<Time<"+to+"}");
						}
						else
						{
							Filteredby.setText("Filtered by: {!("+from+"<Time<"+to+")}");	
						}
					}
					if(Locationcheckbox==true)
					{
						double latmin=Double.parseDouble(LatMin.getText());
						double latmax=Double.parseDouble(LatMax.getText());
						double lonmin=Double.parseDouble(LonMin.getText());
						double lonmax=Double.parseDouble(LonMax.getText());
						double altmin=Double.parseDouble(AltMin.getText());
						double altmax=Double.parseDouble(AltMax.getText());
						filt.FilterByMinMaxLoc(latmin, latmax, lonmin, lonmax, altmin, altmax, LocationNot);
						Glist.Data=filt.Data0;
						if(LocationNot==false)
						{
							Filteredby.setText("Filtered by: {"+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+"}");
						}
						else
						{
							Filteredby.setText("Filtered by: {!("+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+")}");
						}
					}
					if(IDcheckbox==true)
					{
						String id=IDtext.getText();
						filt.FilterByID(id, IDNot);
						Glist.Data=filt.Data0;
						if(IDNot==false)
						{
							Filteredby.setText("Filtered by: {Device name="+id+"}");
						}
						else
						{
							Filteredby.setText("Filtered by: {!(Device name="+id+")}");
						}
					}
				}
				if(Andbutt==true)
				{
					GuiFilters filt=new GuiFilters(Glist.Data);
					List<ArrayList<String>> temp=new ArrayList<ArrayList<String>>();
					if(Timecheckbox==true && Locationcheckbox==true)
					{
						String from=TimeFrom.getText();
						String to=TimeTo.getText();
						filt.FilterByMinMaxTime(from, to, TimeNot);
						temp=filt.Data0;
						GuiFilters nextfilt=new GuiFilters(temp);
						double latmin=Double.parseDouble(LatMin.getText());
						double latmax=Double.parseDouble(LatMax.getText());
						double lonmin=Double.parseDouble(LonMin.getText());
						double lonmax=Double.parseDouble(LonMax.getText());
						double altmin=Double.parseDouble(AltMin.getText());
						double altmax=Double.parseDouble(AltMax.getText());
						nextfilt.FilterByMinMaxLoc(latmin, latmax, lonmin, lonmax, altmin, altmax, LocationNot);
						Glist.Data=nextfilt.Data0;
						if(TimeNot==false && LocationNot==false)//time & location
						{
							Filteredby.setText("Filtered by: {"+from+"<Time<"+to+"} && {"+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+"}");	
						}
						else if(TimeNot==true && LocationNot==false)//not time & location
						{
							Filteredby.setText("Filtered by: {!("+from+"<Time<"+to+")} && {"+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+"}");	
						}
						else if(TimeNot==false && LocationNot==true)//time & not location
						{
							Filteredby.setText("Filtered by: {"+from+"<Time<"+to+"} && {!("+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+")}");	
						}
						else // not time & not location
						{
							Filteredby.setText("Filtered by: {!("+from+"<Time<"+to+")} && {!("+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+")}"); 	
						}
					}
					if(Timecheckbox==true && IDcheckbox==true)
					{
						String from=TimeFrom.getText();
						String to=TimeTo.getText();
						filt.FilterByMinMaxTime(from, to, TimeNot);
						temp=filt.Data0;
						GuiFilters nextfilt=new GuiFilters(temp);
						String id=IDtext.getText();
						nextfilt.FilterByID(id, IDNot);
						Glist.Data=nextfilt.Data0;
						if(TimeNot==false && IDNot==false)// time & ID
						{
							Filteredby.setText("Filtered by: {"+from+"<Time<"+to+"} && {Device name="+id+"}");	
						}
						else if(TimeNot==true && IDNot==false)//not time & ID
						{
							Filteredby.setText("Filtered by: {!("+from+"<Time<"+to+")} && {Device name="+id+"}");  		
						}
						else if(TimeNot==false && IDNot==true)//time & not ID
						{
							Filteredby.setText("Filtered by: {"+from+"<Time<"+to+"} && {!(Device name="+id+")}"); 
						}
						else//not time & not ID
						{
							Filteredby.setText("Filtered by: {!("+from+"<Time<"+to+")} && {!(Device name="+id+")}"); 	
						}
					}
					if(Locationcheckbox==true && IDcheckbox==true)
					{
						String id=IDtext.getText();
						filt.FilterByID(id, IDNot);
						temp=filt.Data0;
						GuiFilters nextfilt=new GuiFilters(temp);
						double latmin=Double.parseDouble(LatMin.getText());
						double latmax=Double.parseDouble(LatMax.getText());
						double lonmin=Double.parseDouble(LonMin.getText());
						double lonmax=Double.parseDouble(LonMax.getText());
						double altmin=Double.parseDouble(AltMin.getText());
						double altmax=Double.parseDouble(AltMax.getText());
						nextfilt.FilterByMinMaxLoc(latmin, latmax, lonmin, lonmax, altmin, altmax, LocationNot);
						Glist.Data=nextfilt.Data0;
						if(IDNot==false && LocationNot==false)//ID & location
						{
							Filteredby.setText("Filtered by: {Device name="+id+"} && {"+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+"}");	 
						}
						else if(IDNot==true && LocationNot==false)// not id && location
						{
							Filteredby.setText("Filtered by: {!(Device name="+id+")} && {"+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+"}");
						}
						else if(IDNot==false && LocationNot==true)//id && not location
						{
							Filteredby.setText("Filtered by: {Device name="+id+"} && {!("+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+")}");  
						}
						else//not id && not location
						{
							Filteredby.setText("Filtered by: {!(Device name="+id+")} && {!("+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+")}");
						}
					}
				}
				if(Orbutt==true)
				{
					List<ArrayList<String>> Data1=new ArrayList<ArrayList<String>>();
					List<ArrayList<String>> Data2=new ArrayList<ArrayList<String>>();
					GuiFilters filt1=new GuiFilters(Glist.Data);
					GuiFilters filt2=new GuiFilters(Glist.Data);
					if(Timecheckbox==true && Locationcheckbox==true)
					{
						String from=TimeFrom.getText();
						String to=TimeTo.getText();
						filt1.FilterByMinMaxTime(from, to, TimeNot);
						Data1=filt1.Data0;
						double latmin=Double.parseDouble(LatMin.getText());
						double latmax=Double.parseDouble(LatMax.getText());
						double lonmin=Double.parseDouble(LonMin.getText());
						double lonmax=Double.parseDouble(LonMax.getText());
						double altmin=Double.parseDouble(AltMin.getText());
						double altmax=Double.parseDouble(AltMax.getText());
						filt2.FilterByMinMaxLoc(latmin, latmax, lonmin, lonmax, altmin, altmax, LocationNot);
						Data2=filt2.Data0;
						Glist.Data=GuiFilters.MergeData(Data1, Data2);
						if(TimeNot==false && LocationNot==false)//time & location
						{
							Filteredby.setText("Filtered by: {"+from+"<Time<"+to+"} || {"+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+"}");	
						}
						else if(TimeNot==true && LocationNot==false)//not time & location
						{
							Filteredby.setText("Filtered by: {!("+from+"<Time<"+to+")} || {"+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+"}");	
						}
						else if(TimeNot==false && LocationNot==true)//time & not location
						{
							Filteredby.setText("Filtered by: {"+from+"<Time<"+to+"} || {!("+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+")}");	
						}
						else // not time & not location
						{
							Filteredby.setText("Filtered by: {!("+from+"<Time<"+to+")} || {!("+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+")}"); 	
						}

					}
					if(Timecheckbox==true && IDcheckbox==true)
					{
						String from=TimeFrom.getText();
						String to=TimeTo.getText();
						filt1.FilterByMinMaxTime(from, to, TimeNot);
						Data1=filt1.Data0;
						String id=IDtext.getText();
						filt2.FilterByID(id, IDNot);
						Data2=filt2.Data0;
						Glist.Data=GuiFilters.MergeData(Data1, Data2);
						if(TimeNot==false && IDNot==false)// time & ID
						{
							Filteredby.setText("Filtered by: {"+from+"<Time<"+to+"} || {Device name="+id+"}");	
						}
						else if(TimeNot==true && IDNot==false)//not time & ID
						{
							Filteredby.setText("Filtered by: {!("+from+"<Time<"+to+")} || {Device name="+id+"}");  		
						}
						else if(TimeNot==false && IDNot==true)//time & not ID
						{
							Filteredby.setText("Filtered by: {"+from+"<Time<"+to+"} || {!(Device name="+id+")}"); 
						}
						else//not time & not ID
						{
							Filteredby.setText("Filtered by: {!("+from+"<Time<"+to+")} || {!(Device name="+id+")}"); 	
						}
					}
					if(Locationcheckbox==true && IDcheckbox==true)
					{
						String id=IDtext.getText();
						filt1.FilterByID(id, IDNot);
						Data1=filt1.Data0;
						double latmin=Double.parseDouble(LatMin.getText());
						double latmax=Double.parseDouble(LatMax.getText());
						double lonmin=Double.parseDouble(LonMin.getText());
						double lonmax=Double.parseDouble(LonMax.getText());
						double altmin=Double.parseDouble(AltMin.getText());
						double altmax=Double.parseDouble(AltMax.getText());
						filt2.FilterByMinMaxLoc(latmin, latmax, lonmin, lonmax, altmin, altmax, LocationNot);
						Data2=filt2.Data0;
						Glist.Data=GuiFilters.MergeData(Data1, Data2);
						if(IDNot==false && LocationNot==false)//ID & location
						{
							Filteredby.setText("Filtered by: {Device name="+id+"} || {"+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+"}");	 
						}
						else if(IDNot==true && LocationNot==false)// not id && location
						{
							Filteredby.setText("Filtered by: {!(Device name="+id+")} || {"+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+"}");
						}
						else if(IDNot==false && LocationNot==true)//id && not location
						{
							Filteredby.setText("Filtered by: {Device name="+id+"} || {!("+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+")}");  
						}
						else//not id && not location
						{
							Filteredby.setText("Filtered by: {!(Device name="+id+")} || {!("+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+")}");
						}
					}
				}
			}
		});
		btnSubmitFilter.setBounds(558, 128, 119, 56);
		btnSubmitFilter.setText("Submit Filters");

		Button btnResetFilters = new Button(shell, SWT.NONE);
		btnResetFilters.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Glist.Data=Glist.DataTemp;
				Filteredby.setText("Filtered by: None at the moment");
			}
		});
		btnResetFilters.setBounds(558, 190, 119, 39);
		btnResetFilters.setText("Reset Filters");

		Label lblAlgorithms = new Label(shell, SWT.NONE);
		lblAlgorithms.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblAlgorithms.setBounds(376, 350, 93, 25);
		lblAlgorithms.setText("Algorithms:");

		Label LatAlg1 = new Label(shell, SWT.NONE);
		LatAlg1.setBounds(32, 523, 209, 15);
		LatAlg1.setText("Latitude:");

		Label LonAlg1 = new Label(shell, SWT.NONE);
		LonAlg1.setBounds(31, 543, 210, 15);
		LonAlg1.setText("Longitude:");

		Label AltAlg1 = new Label(shell, SWT.NONE);
		AltAlg1.setBounds(31, 570, 197, 15);
		AltAlg1.setText("Altitude:");

		Button btnActivateTheFirst = new Button(shell, SWT.NONE);
		btnActivateTheFirst.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String Mac=MacForAlg1.getText();
				Algorithems alg=new Algorithems(Glist.Data);
				Point3D ans=alg.getWpoint(Mac);
				LatAlg1.setText("Latitude: "+ans.getLat());
				LonAlg1.setText("Longitude: "+ans.getLon());
				AltAlg1.setText("Altitude: "+ans.getAlt());
			}
		});
		btnActivateTheFirst.setBounds(32, 470, 99, 25);
		btnActivateTheFirst.setText("Submit");

		Label lblSecondAlgorithm = new Label(shell, SWT.NONE);
		lblSecondAlgorithm.setBounds(541, 401, 101, 15);
		lblSecondAlgorithm.setText("Second algorithm");

		Label lblMac = new Label(shell, SWT.NONE);
		lblMac.setBounds(436, 434, 38, 15);
		lblMac.setText("MAC1:");

		MAC1 = new Text(shell, SWT.BORDER);
		MAC1.setBounds(480, 431, 76, 21);

		Label lblSignal = new Label(shell, SWT.NONE);
		lblSignal.setBounds(579, 434, 55, 15);
		lblSignal.setText("Signal1:");

		Signal1 = new Text(shell, SWT.BORDER);
		Signal1.setBounds(636, 431, 76, 21);

		Label lblMac_1 = new Label(shell, SWT.NONE);
		lblMac_1.setBounds(436, 470, 38, 15);
		lblMac_1.setText("MAC2:");

		Label lblMac_2 = new Label(shell, SWT.NONE);
		lblMac_2.setBounds(436, 502, 38, 15);
		lblMac_2.setText("MAC3:");

		MAC2 = new Text(shell, SWT.BORDER);
		MAC2.setBounds(480, 467, 76, 21);

		MAC3 = new Text(shell, SWT.BORDER);
		MAC3.setBounds(480, 499, 76, 21);

		Label lblSignal_1 = new Label(shell, SWT.NONE);
		lblSignal_1.setBounds(579, 470, 45, 15);
		lblSignal_1.setText("Signal2:");

		Label lblSignal_2 = new Label(shell, SWT.NONE);
		lblSignal_2.setBounds(579, 502, 45, 15);
		lblSignal_2.setText("Signal3:");

		Signal2 = new Text(shell, SWT.BORDER);
		Signal2.setBounds(636, 467, 76, 21);

		Signal3 = new Text(shell, SWT.BORDER);
		Signal3.setBounds(636, 499, 76, 21);

		Label LatAlg2 = new Label(shell, SWT.NONE);
		LatAlg2.setBounds(527, 570, 247, 15);
		LatAlg2.setText("Latitude:");

		Label LonAlg2 = new Label(shell, SWT.NONE);
		LonAlg2.setBounds(527, 596, 247, 15);
		LonAlg2.setText("Longitude:");

		Label AltAlg2 = new Label(shell, SWT.NONE);
		AltAlg2.setBounds(527, 617, 247, 15);
		AltAlg2.setText("Altitude:");

		Button btnSubmitAlg2 = new Button(shell, SWT.NONE);
		btnSubmitAlg2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String mac1=MAC1.getText();
				String mac2=MAC2.getText();
				String mac3=MAC3.getText();
				int signal1=Integer.parseInt(Signal1.getText());
				int signal2=Integer.parseInt(Signal2.getText());
				int signal3=Integer.parseInt(Signal3.getText());
				M_S[] array=new M_S[3];
				array[0]=new M_S(mac1,signal1);
				array[1]=new M_S(mac2,signal2);
				array[2]=new M_S(mac3,signal3);
				Algorithems alg2=new Algorithems(Glist.Data);
				Point3D ans=alg2.GetWlocation(array);
				LatAlg2.setText("Latitude: "+ans.getLat());
				LonAlg2.setText("Longitude: "+ans.getLon());
				AltAlg2.setText("Altitude: "+ans.getAlt());
			}
		});
		btnSubmitAlg2.setBounds(436, 543, 75, 25);
		btnSubmitAlg2.setText("Submit");

		Label lblTheWeightedPoint = new Label(shell, SWT.NONE);
		lblTheWeightedPoint.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblTheWeightedPoint.setBounds(527, 548, 139, 15);
		lblTheWeightedPoint.setText("The weighted point is:");

		Button btnAnd = new Button(shell, SWT.RADIO);
		btnAnd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Andbutt=true;
				NoneButt=false;
				Orbutt=false;
			}
		});
		btnAnd.setBounds(558, 106, 45, 16);
		btnAnd.setText("And");

		Button btnOr = new Button(shell, SWT.RADIO);
		btnOr.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Orbutt=true;
				NoneButt=false;
				Andbutt=false;
			}
		});
		btnOr.setBounds(609, 106, 34, 16);
		btnOr.setText("Or");

		Button btnNone = new Button(shell, SWT.RADIO);
		btnNone.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				NoneButt=true;
				Andbutt=false;
				Orbutt=false;
			}
		});
		btnNone.setBounds(643, 106, 90, 16);
		btnNone.setText("None");

		Label lblAlgorithem = new Label(shell, SWT.NONE);
		lblAlgorithem.setBounds(119, 401, 109, 15);
		lblAlgorithem.setText("first algorithm");

		Label lblMacAddress = new Label(shell, SWT.NONE);
		lblMacAddress.setBounds(32, 448, 77, 15);
		lblMacAddress.setText("Mac address:");

		MacForAlg1 = new Text(shell, SWT.BORDER);
		MacForAlg1.setBounds(119, 442, 76, 21);

		Label lblThe = new Label(shell, SWT.NONE);
		lblThe.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblThe.setBounds(31, 502, 136, 15);
		lblThe.setText("The weighted point is:");

		Button btnSaveFilters = new Button(shell, SWT.NONE);
		btnSaveFilters.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SaveFilters save=new SaveFilters();
				if(IDNot==false)
				{
					save.ID=IDtext.getText();
				}
				else
				{
					save.ID="not "+IDtext.getText();	
				}
				if(TimeNot==false)
				{
					save.TimeFrom=TimeFrom.getText();
					save.TimeTo=TimeTo.getText();
				}
				else
				{
					save.TimeFrom="not "+TimeFrom.getText();
					save.TimeTo="not "+TimeTo.getText();	
				}
				if(LocationNot==false)
				{
					save.LatMin=LatMin.getText();
					save.LatMax=LatMax.getText();
					save.LonMin=LonMin.getText();
					save.LonMax=LonMax.getText();
					save.AltMin=AltMin.getText();
					save.AltMax=AltMax.getText();
				}
				else
				{
					save.LatMin="not "+LatMin.getText();
					save.LatMax="not "+LatMax.getText();
					save.LonMin="not "+LonMin.getText();
					save.LonMax="not "+LonMax.getText();
					save.AltMin="not "+AltMin.getText();
					save.AltMax="not "+AltMax.getText();
				}

				if(NoneButt==true)
				{
					save.AndOrNone="None";
				}
				if(Andbutt==true)
				{
					save.AndOrNone="And";
				}
				if(Orbutt==true)
				{
					save.AndOrNone="Or";
				}

				String filename= "C:\\Users\\computer\\Desktop\\OutPut\\filters.bin";
				try {
					ObjectOutputStream os=new ObjectOutputStream(new FileOutputStream(filename));
					os.writeObject(save);
					os.close();

					kelet=new ObjectInputStream(new FileInputStream(filename));
					try {
						upload=(SaveFilters) kelet.readObject();
						kelet.close();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSaveFilters.setBounds(683, 128, 91, 56);
		btnSaveFilters.setText("Save Filters");

		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if(upload.AndOrNone.equals("None"))
				{
					GuiFilters filt=new GuiFilters(Glist.Data);
					if(upload.TimeFrom.length()>0)//time only
					{
						if(!(upload.TimeFrom.contains("not")))//not is off
						{
							String from=upload.TimeFrom;
							String to=upload.TimeTo;
							filt.FilterByMinMaxTime(from, to, false);
							Glist.Data=filt.Data0;
							Filteredby.setText("Filtered by: {"+from+"<Time<"+to+"}");
						}
						else//not is on
						{
							String from=upload.TimeFrom.substring(4);
							String to=upload.TimeFrom.substring(4);
							filt.FilterByMinMaxTime(from, to, true);
							Glist.Data=filt.Data0;
							Filteredby.setText("Filtered by: {!("+from+"<Time<"+to+")}");	
						}
					}
					if(upload.AltMax.length()>0)//location only
					{
						if(!(upload.AltMax.contains("not")))//not is off
						{
							double latmin=Double.parseDouble(upload.LatMin);
							double latmax=Double.parseDouble(upload.LatMax);
							double lonmin=Double.parseDouble(upload.LonMin);
							double lonmax=Double.parseDouble(upload.LonMax);
							double altmin=Double.parseDouble(upload.AltMin);
							double altmax=Double.parseDouble(upload.AltMax);
							filt.FilterByMinMaxLoc(latmin, latmax, lonmin, lonmax, altmin, altmax, false);
							Glist.Data=filt.Data0;
							Filteredby.setText("Filtered by: {"+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+"}");
						}
						else//not is on
						{
							double latmin=Double.parseDouble(upload.LatMin.substring(4));
							double latmax=Double.parseDouble(upload.LatMax.substring(4));
							double lonmin=Double.parseDouble(upload.LonMin.substring(4));
							double lonmax=Double.parseDouble(upload.LonMax.substring(4));
							double altmin=Double.parseDouble(upload.AltMin.substring(4));
							double altmax=Double.parseDouble(upload.AltMax.substring(4));
							filt.FilterByMinMaxLoc(latmin, latmax, lonmin, lonmax, altmin, altmax, true);
							Glist.Data=filt.Data0;
							Filteredby.setText("Filtered by: {!("+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+")}");
						}
					}
					if(upload.ID.length()>0)//ID only
					{
						if(!(upload.ID.contains("not")))//not is off
						{
							String id=upload.ID;
							filt.FilterByID(id, false);
							Glist.Data=filt.Data0;
							Filteredby.setText("Filtered by: {Device name="+id+"}");
						}
						else//not is on
						{
							String id=upload.ID.substring(4);
							filt.FilterByID(id, true);
							Glist.Data=filt.Data0;
							Filteredby.setText("Filtered by: {!(Device name="+id+")}");
						}
					}
				}
				if(upload.AndOrNone.equals("And"))
				{
					GuiFilters filt=new GuiFilters(Glist.Data);
					List<ArrayList<String>> temp=new ArrayList<ArrayList<String>>();
					if(upload.TimeFrom.length()>0 && upload.AltMax.length()>0)//time & location
					{
						if(!(upload.TimeFrom.contains("not")) && !(upload.AltMax.contains("not")))//not is off in both
						{
							String from=upload.TimeFrom;
							String to=upload.TimeTo;
							filt.FilterByMinMaxTime(from, to, false);
							temp=filt.Data0;
							GuiFilters nextfilt=new GuiFilters(temp);
							double latmin=Double.parseDouble(upload.LatMin);
							double latmax=Double.parseDouble(upload.LatMax);
							double lonmin=Double.parseDouble(upload.LonMin);
							double lonmax=Double.parseDouble(upload.LonMax);
							double altmin=Double.parseDouble(upload.AltMin);
							double altmax=Double.parseDouble(upload.AltMax);
							nextfilt.FilterByMinMaxLoc(latmin, latmax, lonmin, lonmax, altmin, altmax, false);
							Glist.Data=nextfilt.Data0;
							Filteredby.setText("Filtered by: {"+from+"<Time<"+to+"} && {"+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+"}");
						}
						else if(upload.TimeFrom.contains("not") && !(upload.AltMax.contains("not")))//not is on on time and off on location
						{
							String from=upload.TimeFrom.substring(4);
							String to=upload.TimeTo.substring(4);
							filt.FilterByMinMaxTime(from, to, true);
							temp=filt.Data0;
							GuiFilters nextfilt=new GuiFilters(temp);
							double latmin=Double.parseDouble(upload.LatMin);
							double latmax=Double.parseDouble(upload.LatMax);
							double lonmin=Double.parseDouble(upload.LonMin);
							double lonmax=Double.parseDouble(upload.LonMax);
							double altmin=Double.parseDouble(upload.AltMin);
							double altmax=Double.parseDouble(upload.AltMax);
							nextfilt.FilterByMinMaxLoc(latmin, latmax, lonmin, lonmax, altmin, altmax, false);
							Glist.Data=nextfilt.Data0;
							Filteredby.setText("Filtered by: {!("+from+"<Time<"+to+")} && {"+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+"}");
						}
						else if(!(upload.TimeFrom.contains("not")) && upload.AltMax.contains("not"))//not is on on location and off on time
						{
							String from=upload.TimeFrom;
							String to=upload.TimeTo;
							filt.FilterByMinMaxTime(from, to, false);
							temp=filt.Data0;
							GuiFilters nextfilt=new GuiFilters(temp);
							double latmin=Double.parseDouble(upload.LatMin.substring(4));
							double latmax=Double.parseDouble(upload.LatMax.substring(4));
							double lonmin=Double.parseDouble(upload.LonMin.substring(4));
							double lonmax=Double.parseDouble(upload.LonMax.substring(4));
							double altmin=Double.parseDouble(upload.AltMin.substring(4));
							double altmax=Double.parseDouble(upload.AltMax.substring(4));
							nextfilt.FilterByMinMaxLoc(latmin, latmax, lonmin, lonmax, altmin, altmax, true);
							Glist.Data=nextfilt.Data0;
							Filteredby.setText("Filtered by: {"+from+"<Time<"+to+"} && {!("+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+")}");
						}
						else//not is on on both
						{
							String from=upload.TimeFrom.substring(4);
							String to=upload.TimeTo.substring(4);
							filt.FilterByMinMaxTime(from, to, true);
							temp=filt.Data0;
							GuiFilters nextfilt=new GuiFilters(temp);
							double latmin=Double.parseDouble(upload.LatMin.substring(4));
							double latmax=Double.parseDouble(upload.LatMax.substring(4));
							double lonmin=Double.parseDouble(upload.LonMin.substring(4));
							double lonmax=Double.parseDouble(upload.LonMax.substring(4));
							double altmin=Double.parseDouble(upload.AltMin.substring(4));
							double altmax=Double.parseDouble(upload.AltMax.substring(4));
							nextfilt.FilterByMinMaxLoc(latmin, latmax, lonmin, lonmax, altmin, altmax, true);
							Glist.Data=nextfilt.Data0;
							Filteredby.setText("Filtered by: {!("+from+"<Time<"+to+")} && {!("+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+")}");
						}				
					}
					if(upload.TimeFrom.length()>0 && upload.ID.length()>0)//time & ID
					{
						if(!(upload.TimeFrom.contains("not")) && !(upload.ID.contains("not")))//not is off on both
						{
							String from=upload.TimeFrom;
							String to=upload.TimeTo;
							filt.FilterByMinMaxTime(from, to, false);
							temp=filt.Data0;
							GuiFilters nextfilt=new GuiFilters(temp);
							String id=upload.ID;
							nextfilt.FilterByID(id, false);
							Glist.Data=nextfilt.Data0;
							Filteredby.setText("Filtered by: {"+from+"<Time<"+to+"} && {Device name="+id+"}");
						}
						else if(upload.TimeFrom.contains("not") && !(upload.AltMax.contains("not")))//not is on on time and off on ID
						{
							String from=upload.TimeFrom.substring(4);
							String to=upload.TimeTo.substring(4);
							filt.FilterByMinMaxTime(from, to, true);
							temp=filt.Data0;
							GuiFilters nextfilt=new GuiFilters(temp);
							String id=upload.ID;
							nextfilt.FilterByID(id, false);
							Glist.Data=nextfilt.Data0;
							Filteredby.setText("Filtered by: {!("+from+"<Time<"+to+")} && {Device name="+id+"}");
						}
						else if(!(upload.TimeFrom.contains("not")) && upload.AltMax.contains("not"))//not is on on ID and off on time
						{
							String from=upload.TimeFrom;
							String to=upload.TimeTo;
							filt.FilterByMinMaxTime(from, to, false);
							temp=filt.Data0;
							GuiFilters nextfilt=new GuiFilters(temp);
							String id=upload.ID.substring(4);
							nextfilt.FilterByID(id, true);
							Glist.Data=nextfilt.Data0;
							Filteredby.setText("Filtered by: {"+from+"<Time<"+to+"} && {!(Device name="+id+")}");
						}
						else//not is on on both
						{
							String from=upload.TimeFrom.substring(4);
							String to=upload.TimeTo.substring(4);
							filt.FilterByMinMaxTime(from, to, true);
							temp=filt.Data0;
							GuiFilters nextfilt=new GuiFilters(temp);
							String id=upload.ID.substring(4);
							nextfilt.FilterByID(id, true);
							Glist.Data=nextfilt.Data0;
							Filteredby.setText("Filtered by: {!("+from+"<Time<"+to+")} && {!(Device name="+id+")}");
						}
					}
					if(upload.AltMax.length()>0 && upload.ID.length()>0)//location & ID
					{
						if(!(upload.AltMax.contains("not")) && !(upload.ID.contains("not")))//not is off on both
						{
							String id=upload.ID;
							filt.FilterByID(id, false);
							temp=filt.Data0;
							GuiFilters nextfilt=new GuiFilters(temp);
							double latmin=Double.parseDouble(upload.LatMin);
							double latmax=Double.parseDouble(upload.LatMax);
							double lonmin=Double.parseDouble(upload.LonMin);
							double lonmax=Double.parseDouble(upload.LonMax);
							double altmin=Double.parseDouble(upload.AltMin);
							double altmax=Double.parseDouble(upload.AltMax);
							nextfilt.FilterByMinMaxLoc(latmin, latmax, lonmin, lonmax, altmin, altmax, false);
							Glist.Data=nextfilt.Data0;
							Filteredby.setText("Filtered by: {Device name="+id+"} && {"+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+"}");
						}
						else if(upload.AltMax.contains("not") && !(upload.ID.contains("not")))//not is on on location and off on id
						{
							String id=upload.ID;
							filt.FilterByID(id, false);
							temp=filt.Data0;
							GuiFilters nextfilt=new GuiFilters(temp);
							double latmin=Double.parseDouble(upload.LatMin.substring(4));
							double latmax=Double.parseDouble(upload.LatMax.substring(4));
							double lonmin=Double.parseDouble(upload.LonMin.substring(4));
							double lonmax=Double.parseDouble(upload.LonMax.substring(4));
							double altmin=Double.parseDouble(upload.AltMin.substring(4));
							double altmax=Double.parseDouble(upload.AltMax.substring(4));
							nextfilt.FilterByMinMaxLoc(latmin, latmax, lonmin, lonmax, altmin, altmax, true);
							Glist.Data=nextfilt.Data0;
							Filteredby.setText("Filtered by: {Device name="+id+"} && {!("+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+")}");  
						}
						else if(!(upload.AltMax.contains("not")) && upload.ID.contains("not"))//not is on on id and off on location
						{
							String id=upload.ID.substring(4);
							filt.FilterByID(id, true);
							temp=filt.Data0;
							GuiFilters nextfilt=new GuiFilters(temp);
							double latmin=Double.parseDouble(upload.LatMin);
							double latmax=Double.parseDouble(upload.LatMax);
							double lonmin=Double.parseDouble(upload.LonMin);
							double lonmax=Double.parseDouble(upload.LonMax);
							double altmin=Double.parseDouble(upload.AltMin);
							double altmax=Double.parseDouble(upload.AltMax);
							nextfilt.FilterByMinMaxLoc(latmin, latmax, lonmin, lonmax, altmin, altmax, false);
							Glist.Data=nextfilt.Data0;
							Filteredby.setText("Filtered by: {!(Device name="+id+")} && {"+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+"}");
						}
						else//not is on on both
						{
							String id=upload.ID.substring(4);
							filt.FilterByID(id, true);
							temp=filt.Data0;
							GuiFilters nextfilt=new GuiFilters(temp);
							double latmin=Double.parseDouble(upload.LatMin.substring(4));
							double latmax=Double.parseDouble(upload.LatMax.substring(4));
							double lonmin=Double.parseDouble(upload.LonMin.substring(4));
							double lonmax=Double.parseDouble(upload.LonMax.substring(4));
							double altmin=Double.parseDouble(upload.AltMin.substring(4));
							double altmax=Double.parseDouble(upload.AltMax.substring(4));
							nextfilt.FilterByMinMaxLoc(latmin, latmax, lonmin, lonmax, altmin, altmax, true);
							Glist.Data=nextfilt.Data0;
							Filteredby.setText("Filtered by: {!(Device name="+id+")} && {!("+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+")}");
						}
					}
				}
				if(upload.AndOrNone.equals("Or"))
				{
					List<ArrayList<String>> Data1=new ArrayList<ArrayList<String>>();
					List<ArrayList<String>> Data2=new ArrayList<ArrayList<String>>();
					GuiFilters filt1=new GuiFilters(Glist.Data);
					GuiFilters filt2=new GuiFilters(Glist.Data);
					if(upload.TimeFrom.length()>0 && upload.AltMax.length()>0)//time & location
					{
						if(!(upload.TimeFrom.contains("not")) && !(upload.LonMax.contains("not")))//not is off on both
						{
							String from=upload.TimeFrom;
							String to=upload.TimeTo;
							filt1.FilterByMinMaxTime(from, to, false);
							Data1=filt1.Data0;
							double latmin=Double.parseDouble(upload.LatMin);
							double latmax=Double.parseDouble(upload.LatMax);
							double lonmin=Double.parseDouble(upload.LonMin);
							double lonmax=Double.parseDouble(upload.LonMax);
							double altmin=Double.parseDouble(upload.AltMin);
							double altmax=Double.parseDouble(upload.AltMax);
							filt2.FilterByMinMaxLoc(latmin, latmax, lonmin, lonmax, altmin, altmax, false);
							Data2=filt2.Data0;
							Glist.Data=GuiFilters.MergeData(Data1, Data2);
							Filteredby.setText("Filtered by: {"+from+"<Time<"+to+"} || {"+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+"}");	
						}
						else if(upload.TimeFrom.contains("not") && !(upload.LonMax.contains("not")))//not is on on time and off on location
						{
							String from=upload.TimeFrom.substring(4);
							String to=upload.TimeTo.substring(4);
							filt1.FilterByMinMaxTime(from, to, true);
							Data1=filt1.Data0;
							double latmin=Double.parseDouble(upload.LatMin);
							double latmax=Double.parseDouble(upload.LatMax);
							double lonmin=Double.parseDouble(upload.LonMin);
							double lonmax=Double.parseDouble(upload.LonMax);
							double altmin=Double.parseDouble(upload.AltMin);
							double altmax=Double.parseDouble(upload.AltMax);
							filt2.FilterByMinMaxLoc(latmin, latmax, lonmin, lonmax, altmin, altmax, false);
							Data2=filt2.Data0;
							Glist.Data=GuiFilters.MergeData(Data1, Data2);
							Filteredby.setText("Filtered by: {!("+from+"<Time<"+to+")} || {"+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+"}");
						}
						else if(!(upload.TimeFrom.contains("not")) && upload.LonMax.contains("not"))//not is on on location and off on time
						{
							String from=upload.TimeFrom;
							String to=upload.TimeTo;
							filt1.FilterByMinMaxTime(from, to, false);
							Data1=filt1.Data0;
							double latmin=Double.parseDouble(upload.LatMin.substring(4));
							double latmax=Double.parseDouble(upload.LatMax.substring(4));
							double lonmin=Double.parseDouble(upload.LonMin.substring(4));
							double lonmax=Double.parseDouble(upload.LonMax.substring(4));
							double altmin=Double.parseDouble(upload.AltMin.substring(4));
							double altmax=Double.parseDouble(upload.AltMax.substring(4));
							filt2.FilterByMinMaxLoc(latmin, latmax, lonmin, lonmax, altmin, altmax, true);
							Data2=filt2.Data0;
							Glist.Data=GuiFilters.MergeData(Data1, Data2);
							Filteredby.setText("Filtered by: {"+from+"<Time<"+to+"} || {!("+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+")}");
						}
						else//not is on on both
						{
							String from=upload.TimeFrom.substring(4);
							String to=upload.TimeTo.substring(4);
							filt1.FilterByMinMaxTime(from, to, true);
							Data1=filt1.Data0;
							double latmin=Double.parseDouble(upload.LatMin.substring(4));
							double latmax=Double.parseDouble(upload.LatMax.substring(4));
							double lonmin=Double.parseDouble(upload.LonMin.substring(4));
							double lonmax=Double.parseDouble(upload.LonMax.substring(4));
							double altmin=Double.parseDouble(upload.AltMin.substring(4));
							double altmax=Double.parseDouble(upload.AltMax.substring(4));
							filt2.FilterByMinMaxLoc(latmin, latmax, lonmin, lonmax, altmin, altmax, true);
							Data2=filt2.Data0;
							Glist.Data=GuiFilters.MergeData(Data1, Data2);
							Filteredby.setText("Filtered by: {!("+from+"<Time<"+to+")} || {!("+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+")}");
						}
					}
					if(upload.TimeFrom.length()>0 && upload.ID.length()>0)//time & ID
					{
						if(!(upload.TimeFrom.contains("not")) && !(upload.ID.contains("not")))//not is off on both
						{
							String from=upload.TimeFrom;
							String to=upload.TimeTo;
							filt1.FilterByMinMaxTime(from, to, false);
							Data1=filt1.Data0;
							String id=upload.ID;
							filt2.FilterByID(id, false);
							Data2=filt2.Data0;
							Glist.Data=GuiFilters.MergeData(Data1, Data2);
							Filteredby.setText("Filtered by: {"+from+"<Time<"+to+"} || {Device name="+id+"}");
						}
						else if(upload.TimeFrom.contains("not") && !(upload.ID.contains("not")))//not is on on time and off on id
						{
							String from=upload.TimeFrom.substring(4);
							String to=upload.TimeTo.substring(4);
							filt1.FilterByMinMaxTime(from, to, true);
							Data1=filt1.Data0;
							String id=upload.ID;
							filt2.FilterByID(id, false);
							Data2=filt2.Data0;
							Glist.Data=GuiFilters.MergeData(Data1, Data2);
							Filteredby.setText("Filtered by: {!("+from+"<Time<"+to+")} || {Device name="+id+"}");  		
						}
						else if(!(upload.TimeFrom.contains("not")) && upload.ID.contains("not"))//not is on on id and off on time
						{
							String from=upload.TimeFrom;
							String to=upload.TimeTo;
							filt1.FilterByMinMaxTime(from, to, false);
							Data1=filt1.Data0;
							String id=upload.ID.substring(4);
							filt2.FilterByID(id, true);
							Data2=filt2.Data0;
							Glist.Data=GuiFilters.MergeData(Data1, Data2);
							Filteredby.setText("Filtered by: {"+from+"<Time<"+to+"} || {!(Device name="+id+")}"); 
						}
						else//not is on on both
						{
							String from=upload.TimeFrom.substring(4);
							String to=upload.TimeTo.substring(4);
							filt1.FilterByMinMaxTime(from, to, true);
							Data1=filt1.Data0;
							String id=upload.ID.substring(4);
							filt2.FilterByID(id, true);
							Data2=filt2.Data0;
							Glist.Data=GuiFilters.MergeData(Data1, Data2);
							Filteredby.setText("Filtered by: {!("+from+"<Time<"+to+")} || {!(Device name="+id+")}"); 	
						}
					}
					if(upload.AltMax.length()>0 && upload.ID.length()>0)//location & ID
					{
						if(!(upload.AltMax.contains("not")) && !(upload.ID.contains("not")))//not is off on both
						{
							String id=upload.ID;
							filt1.FilterByID(id, false);
							Data1=filt1.Data0;
							double latmin=Double.parseDouble(upload.LatMin);
							double latmax=Double.parseDouble(upload.LatMax);
							double lonmin=Double.parseDouble(upload.LonMin);
							double lonmax=Double.parseDouble(upload.LonMax);
							double altmin=Double.parseDouble(upload.AltMin);
							double altmax=Double.parseDouble(upload.AltMax);
							filt2.FilterByMinMaxLoc(latmin, latmax, lonmin, lonmax, altmin, altmax, false);
							Data2=filt2.Data0;
							Glist.Data=GuiFilters.MergeData(Data1, Data2);
							Filteredby.setText("Filtered by: {Device name="+id+"} || {"+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+"}");
						}
						else if(upload.AltMax.contains("not") && !(upload.ID.contains("not")))//not is on on location and off on id
						{
							String id=upload.ID;
							filt1.FilterByID(id, false);
							Data1=filt1.Data0;
							double latmin=Double.parseDouble(upload.LatMin.substring(4));
							double latmax=Double.parseDouble(upload.LatMax.substring(4));
							double lonmin=Double.parseDouble(upload.LonMin.substring(4));
							double lonmax=Double.parseDouble(upload.LonMax.substring(4));
							double altmin=Double.parseDouble(upload.AltMin.substring(4));
							double altmax=Double.parseDouble(upload.AltMax.substring(4));
							filt2.FilterByMinMaxLoc(latmin, latmax, lonmin, lonmax, altmin, altmax, true);
							Data2=filt2.Data0;
							Glist.Data=GuiFilters.MergeData(Data1, Data2);
							Filteredby.setText("Filtered by: {Device name="+id+"} || {!("+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+")}");
						}
						else if(!(upload.AltMax.contains("not")) && upload.ID.contains("not"))//not is on on id and off on location
						{
							String id=upload.ID.substring(4);
							filt1.FilterByID(id, true);
							Data1=filt1.Data0;
							double latmin=Double.parseDouble(upload.LatMin);
							double latmax=Double.parseDouble(upload.LatMax);
							double lonmin=Double.parseDouble(upload.LonMin);
							double lonmax=Double.parseDouble(upload.LonMax);
							double altmin=Double.parseDouble(upload.AltMin);
							double altmax=Double.parseDouble(upload.AltMax);
							filt2.FilterByMinMaxLoc(latmin, latmax, lonmin, lonmax, altmin, altmax, false);
							Data2=filt2.Data0;
							Glist.Data=GuiFilters.MergeData(Data1, Data2);
							Filteredby.setText("Filtered by: {!(Device name="+id+")} || {"+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+"}");
						}
						else//not is on on both
						{
							String id=upload.ID.substring(4);
							filt1.FilterByID(id, true);
							Data1=filt1.Data0;
							double latmin=Double.parseDouble(upload.LatMin.substring(4));
							double latmax=Double.parseDouble(upload.LatMax.substring(4));
							double lonmin=Double.parseDouble(upload.LonMin.substring(4));
							double lonmax=Double.parseDouble(upload.LonMax.substring(4));
							double altmin=Double.parseDouble(upload.AltMin.substring(4));
							double altmax=Double.parseDouble(upload.AltMax.substring(4));
							filt2.FilterByMinMaxLoc(latmin, latmax, lonmin, lonmax, altmin, altmax, true);
							Data2=filt2.Data0;
							Glist.Data=GuiFilters.MergeData(Data1, Data2);
							Filteredby.setText("Filtered by: {!(Device name="+id+")} || {!("+latmin+"<Latitude<"+latmax+" ,"+lonmin+"<Longitude<"+lonmax+" ,"+altmin+"<Altitude<"+altmax+")}");
						}
					}
				}


			}
		});
		btnNewButton.setBounds(683, 190, 91, 39);
		btnNewButton.setText("Use Saved Filter");

		shell.open();
		shell.layout();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {	
				display.sleep();
				TerminateThread=true;
			}
		}
		if(TerminateThread==true)
		{
			t1.interrupt();
		}
		
	}
}
