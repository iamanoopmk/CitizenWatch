package com.cognoscis.citizen.watch;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
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
	private static final int THUMBNAIL_SIZE = 500;
	private Uri uriSavedImage = null;
	private Uri selectedImage = null;
	private Bitmap bitmap=null;
	private static File imageFile = null;

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
        imageFile = new File(imagesFolder, fileName);
        while (imageFile.exists()){
            imageNum += 1;
            fileName = "image_" + String.valueOf(imageNum) + ".jpg";
            imageFile = new File(imagesFolder, fileName);
        }
        
        // Along with the location start the activity to launch camera
        uriSavedImage = Uri.fromFile(imageFile);        
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {
    		
    		Context context = this.getApplicationContext();
    		selectedImage = Uri.fromFile(imageFile);
    		ImageView image= (ImageView) findViewById(R.id.image01);
            
            try {  
            	Bitmap bitmapOriginal = getThumbnail(selectedImage, context);
            	int width = bitmapOriginal.getWidth();
                int height = bitmapOriginal.getHeight();
            	Matrix matrix = new Matrix();
            	matrix.postRotate((float) getRotation());
    			bitmap = Bitmap.createBitmap(bitmapOriginal, 0, 0, width, height, matrix, true);
                image.setImageBitmap(bitmap);
                Toast.makeText(this, selectedImage.toString(), Toast.LENGTH_LONG).show();
                
           } catch (Exception e) {
               Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
               Log.e("Camera", e.toString());
               
           }
            
      // ***************** trying to compress the image ********************
            File newFolder = new File(Environment.getExternalStorageDirectory(), "citizenWatch");
            String newFileName = "test.jpg";
            File newImage = new File(newFolder, newFileName);
            if (newImage.exists()) newImage.delete(); 
            
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
            
            try {
            	
                FileOutputStream fo = new FileOutputStream(newImage);              
                fo.write(bytes.toByteArray());
                fo.close();
                
            } catch (IOException e) {
            	
            	Log.e("Camera", "could not open file to compress");
            }
            
      // ************************* Compression done ***************************
            
            Button save = (Button)findViewById(R.id.button_save);
            Button discard = (Button)findViewById(R.id.button_discard);
            
            save.setOnClickListener(new OnClickListener() {
            	public void onClick(View v) {
            		Intent display = new Intent(CameraActivity.this, ImageDisplayActivity.class);
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
    
    
    public static int getRotation() {
    	
    	ExifInterface exif = null;
		try {
			exif = new ExifInterface(imageFile.getAbsolutePath());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int rotate = 0;
        switch(orientation) {
          case ExifInterface.ORIENTATION_ROTATE_270:
              rotate = 270;
              break;
          case ExifInterface.ORIENTATION_ROTATE_180:
              rotate = 180;
              break;
          case ExifInterface.ORIENTATION_ROTATE_90:
              rotate = 90;
              break;
        }
        return rotate;
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
