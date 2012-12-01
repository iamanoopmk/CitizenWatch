package com.cognoscis.citizen.watch;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ImageDisplayActivity extends Activity {

	private LocationManager locationManager=null;  
	private LocationListener locationListener=null;
	
	private String registrationNo = null;
	private String violationType = null;
	private String violationDate = null;
	private String violationTime = null;
	private String violationPlace = null;
	private String userName = null;
	private String userContact = null;
	private String userEmail = null;
	private String remarks = null;
	
	private EditText mRegNo;
	
	private Spinner mViolation;
	
	private EditText mPlace;
	
	private EditText mUserName;
	
	private EditText mContact;
	
	private EditText mEmail;
	
	private EditText mRemarks;
	
	private TextView mTimeDisplay;
	private Button mPickTime;

	private int mHour;
	private int mMinute;
	
	static final int TIME_DIALOG_ID = 0;
	
	private int mYear;
	private int mMonth;
	private int mDay;

	private TextView mDateDisplay;
	private Button mPickDate;

	static final int DATE_DIALOG_ID = 1;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_display);
        
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.offences, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); 
        
        boolean flag = displayGpsStatus();
        
        if (flag) {
        	
        	locationListener = new MyLocationListener();  
        	  
        	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50, 10,locationListener);
        	
        	} else {
        		
        		 alertbox("Gps Status!!", "Your GPS is: OFF");
        	}
        
        mTimeDisplay = (TextView)findViewById(R.id.text_time);
        mPickTime = (Button)findViewById(R.id.time_pick);
        mPickTime.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		
        		showDialog(TIME_DIALOG_ID);
        	}
        });
        
        // get the current time
        final Calendar c = Calendar.getInstance();
        
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        
        // display the current time
        updateTimeDisplay();
        
        mDateDisplay = (TextView)findViewById(R.id.text_date);
        mPickDate = (Button)findViewById(R.id.date_pick);
        mPickDate.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		
        		showDialog(DATE_DIALOG_ID);
        	}
        });
        
        //get current date
        
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        
        //display the current date
        updateDateDisplay();
        
        Button submit = (Button)findViewById(R.id.submit_button);
        submit.setOnClickListener(new OnClickListener() {
        	
        	public void onClick(View v){
        		
        		mRegNo = (EditText)findViewById(R.id.reg_no);
        		registrationNo = mRegNo.getText().toString();
        		
        		mViolation = (Spinner)findViewById(R.id.spinner1);
        		violationType = mViolation.getSelectedItem().toString();
        		
        		mPlace =(EditText)findViewById(R.id.place);
        		violationPlace = mPlace.getText().toString();
        		
        		mUserName = (EditText)findViewById(R.id.user_name);
        		userName = mUserName.getText().toString();
        		
        		mContact = (EditText)findViewById(R.id.contact);
        		userContact = mContact.getText().toString();
        		
        		mEmail = (EditText)findViewById(R.id.email);
        		userEmail = mEmail.getText().toString();
        		
        		mRemarks = (EditText)findViewById(R.id.remarks);
        		remarks = mRemarks.getText().toString();
        		
        		if (registrationNo.length()==0){
        			
        			Toast.makeText(getBaseContext(), "Enter registration number", Toast.LENGTH_LONG).show();
        			return;
        		}
      
        		if (violationType.compareTo("Select offence") == 0) {
        			
        			Toast.makeText(getBaseContext(), "Please select an offence", Toast.LENGTH_LONG).show();
        			return;
        			
        		}
        		
        		if (violationPlace.length()==0){
        			
        			Toast.makeText(getBaseContext(), "Enter violation place", Toast.LENGTH_LONG).show();
        			return;
        		}
        		
        		if (userName.length()==0){
        			
        			Toast.makeText(getBaseContext(), "Enter your name", Toast.LENGTH_LONG).show();
        			return;
        		}
        		
        		if (userContact.length()==0){
        			
        			Toast.makeText(getBaseContext(), "Enter Contact number", Toast.LENGTH_LONG).show();
        			return;
        		}
        		
        		if (userEmail.length()==0){
        			
        			Toast.makeText(getBaseContext(), "Enter email ID", Toast.LENGTH_LONG).show();
        			return;
        		}
        		
        		else {
        			
        			Toast.makeText(getBaseContext(), registrationNo + "\n"
            				+ violationType + "\n"
            				+ violationDate + "\n"
            				+ violationTime + "\n"
            				+ violationPlace + "\n"
            				+ userName + "\n"
            				+ userContact + "\n"
            				+ userEmail + "\n"
            				+ remarks, Toast.LENGTH_LONG).show();
        			
        		}
        		
        		
        		
        	}
        });
       
      
    }
    
   // The overridden method for 'showDialog()' inside the 'onClick()' method for handling 
   // the click event of the button 'change the time'
    
    @Override
    protected Dialog onCreateDialog(int id) {
    	
    	switch (id) {
    	
    	case TIME_DIALOG_ID:
    		return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, false);
    		
    	case DATE_DIALOG_ID:
    		return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
    		
    	}
    
    	return null;
    }
    
    // updates the time we display in the TextView
    private void updateTimeDisplay() {
    	
    	mTimeDisplay.setText(new StringBuilder().append(pad(mHour)).append(":").append(pad(mMinute)));
    	violationTime = pad(mHour) + ":" + pad(mMinute);
    }
    
    // updates the date we display in the TextView
    private void updateDateDisplay(){
    	mDateDisplay.setText(new StringBuilder().append(mDay).append("/").append(mMonth + 1).append("/")
    			.append(mYear).append(" "));
    	violationDate = pad(mDay) + "/" + pad(mMonth+1) + "/" + pad(mYear); 
    }
    
    private static String pad(int c) {
    	
    	if (c >= 10)
    		return String.valueOf(c);
    	
    	else
    		return "0" + String.valueOf(c);    	
    }
    
    // the callback received when the user "sets" the time in the dialog
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
    		new TimePickerDialog.OnTimeSetListener() {
    	
    	public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
    		
    		mHour = hourOfDay;
    		mMinute = minute;
    		updateTimeDisplay();
    	}
    	
    };
    
    
    
    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
    		new DatePickerDialog.OnDateSetListener() {
				
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					
					mYear = year;
					mMonth = monthOfYear;
					mDay = dayOfMonth;
					updateDateDisplay();
					
				}
			};
    



  
    private Boolean displayGpsStatus() {
    	
    	ContentResolver contentResolver = getBaseContext().getContentResolver();  
    	boolean gpsStatus = Settings.Secure.isLocationProviderEnabled(contentResolver, LocationManager.GPS_PROVIDER);  
    	if (gpsStatus) {  
    		
    		return true;  
    	  
    	  } else {
    		  
    		  return false;  
    	  }  
    	}  
    
    protected void alertbox(String title, String mymessage) {  
    	  AlertDialog.Builder builder = new AlertDialog.Builder(this);  
    	  builder.setMessage("Your Device's GPS is Disable")  
    	  .setCancelable(false)  
    	  .setTitle("** Gps Status **")  
    	  .setPositiveButton("Gps On",  
    			  
    	   new DialogInterface.OnClickListener() {  
    	   public void onClick(DialogInterface dialog, int id) {  
    	   // finish the current activity  
    	   // AlertBoxAdvance.this.finish();  
    	   Intent myIntent = new Intent(  
    	   Settings.ACTION_SECURITY_SETTINGS);  
    	   startActivity(myIntent);  
    	      dialog.cancel();  
    	   }  
    	   })  
    	   .setNegativeButton("Cancel",  
    	   new DialogInterface.OnClickListener() {  
    	   public void onClick(DialogInterface dialog, int id) {  
    	    // cancel the dialog box  
    	    dialog.cancel();  
    	    }  
    	   });  
    	  AlertDialog alert = builder.create();  
    	  alert.show();  
    	 } 
    
    private class MyLocationListener implements LocationListener {
    	
        public void onLocationChanged(Location loc) {
        	             
            Toast.makeText(getBaseContext(),"Location changed : Lat: " + 
            loc.getLatitude()+ " Lng: " + loc.getLongitude(),
            Toast.LENGTH_SHORT).show();
            
        //    String longitude = "Longitude: " +loc.getLongitude();
        //    String latitude = "Latitude: " +loc.getLatitude();
            
        //    TextView latView = (TextView) findViewById(R.id.textLat);
        //    TextView longView = (TextView) findViewById(R.id.textLong);
            
        //    latView.setText(latitude);
        //    longView.setText(longitude);
            
            locationManager.removeUpdates(locationListener);
        }  
  
        public void onProviderDisabled(String provider) {  
            // TODO Auto-generated method stub           
        }  
  
        public void onProviderEnabled(String provider) {  
            // TODO Auto-generated method stub           
        }  
  
        public void onStatusChanged(String provider, int status, Bundle extras) {  
            // TODO Auto-generated method stub           
        }  
    }  
   
    
    @Override
    public void onDestroy()
    {   
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_image_display, menu);
        return true;
    }
}
