package com.cognoscis.citizen.watch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;



public class CameraActivity extends Activity {
	
	private static final int CAMERA_PIC_REQUEST = 1337;
	private static final int THUMBNAIL_SIZE = 400;
	Uri uriSavedImage = null;
	Uri selectedImage = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_image);
        
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
        uriSavedImage = Uri.fromFile(output);        
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {
    		
    		Context context = this.getApplicationContext();
    		selectedImage = uriSavedImage;
    		ImageView image= (ImageView) findViewById(R.id.image01);
            Bitmap bitmap=null;
            try {
            	bitmap = getThumbnail(selectedImage, context);
                image.setImageBitmap(bitmap);
                Toast.makeText(this, selectedImage.toString(), Toast.LENGTH_LONG).show();
           } catch (Exception e) {
               Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
               Log.e("Camera", e.toString());
           }
            
            Button save = (Button)findViewById(R.id.button_save);
            Button discard = (Button)findViewById(R.id.button_discard);
            
            save.setOnClickListener(new OnClickListener() {
            	public void onClick(View v) {
            		Intent display = new Intent(CameraActivity.this, ImageDisplayActivity.class);
            		Bundle extras = new Bundle();
            		extras.putString("uri", selectedImage.toString());
            		startActivity(display);
            	}
            });
            
            discard.setOnClickListener(new OnClickListener() {
            	public void onClick(View v) {
            		Intent camera = new Intent(CameraActivity.this, CameraActivity.class);
            		startActivity(camera);
            	}
            });
            
    	}

    }
    
    public static Bitmap getThumbnail(Uri uri, Context context) throws FileNotFoundException, IOException{
        InputStream input = context.getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither=true;
        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > THUMBNAIL_SIZE) ? (originalSize / THUMBNAIL_SIZE) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither=true;//optional
        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        input = context.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }
    
    

    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_camera, menu);
        return true;
    }
	
}
