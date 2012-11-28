package com.cognoscis.citizen.watch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageDisplayActivity extends Activity {
	private static final int THUMBNAIL_SIZE = 600;
	private ImageView image;
	private Bitmap bitmap;
	private Uri selectedImage = null;
	private static File imageFile = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_display);
        
        int imageNum = 0;
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "citizenWatch");
        String fileName = "image_" + String.valueOf(imageNum) + ".jpg";
        imageFile = new File(imagesFolder, fileName);
        while (imageFile.exists()){
            imageNum++;
            fileName = "image_" + String.valueOf(imageNum) + ".jpg";
            imageFile = new File(imagesFolder, fileName);
        }
        imageNum--;
        fileName = "image_" + String.valueOf(imageNum) + ".jpg";
        imageFile = new File(imagesFolder, fileName); 
        image = (ImageView)findViewById(R.id.image01);
        
        
        
        Context context = this.getApplicationContext();
        selectedImage = Uri.fromFile(imageFile);
        
        
               
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
           Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
           Log.e("Camera", e.toString());
       }
        
    }
    
    
    public static int getRotation() {
    	
    	ExifInterface exif = null;
		try {
			exif = new ExifInterface(imageFile.getAbsolutePath());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
    public void onDestroy()
    {   
        Cleanup();
        super.onDestroy();
    }

    private void Cleanup()
    {    
        bitmap.recycle();
        System.gc();
        Runtime.getRuntime().gc();  
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_image_display, menu);
        return true;
    }
}
