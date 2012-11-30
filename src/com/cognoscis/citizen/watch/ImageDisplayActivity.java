package com.cognoscis.citizen.watch;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ImageDisplayActivity extends Activity {

	private LocationManager locationManager=null;  
	private LocationListener locationListener=null;

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
        
        
      
    }
  
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
