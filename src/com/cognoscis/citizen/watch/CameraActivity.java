package com.cognoscis.citizen.watch;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;



public class CameraActivity extends Activity {
	
	private static final int CAMERA_PIC_REQUEST = 1337;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Get a camera Intent to call camera app
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        
        // Generate file name and location to store the image
        int imageNum = 0;
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "citizenWatch");
        imagesFolder.mkdirs();
        String fileName = "image_" + String.valueOf(imageNum) + ".jpg";
        File output = new File(imagesFolder, fileName);
        while (output.exists()){
            imageNum++;
            fileName = "image_" + String.valueOf(imageNum) + ".jpg";
            output = new File(imagesFolder, fileName);
        }
        
        // Along with the location start the activity to launch camera
        Uri uriSavedImage = Uri.fromFile(output);        
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {
    	    		
    		Intent choose = new Intent(this, ChooseImageActivity.class);
    		startActivity(choose);
    	}

    }

    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_camera, menu);
        return true;
    }
	
}
