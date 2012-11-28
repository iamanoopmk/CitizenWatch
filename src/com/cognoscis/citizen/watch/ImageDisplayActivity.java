package com.cognoscis.citizen.watch;

import java.io.File;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.widget.ImageView;

public class ImageDisplayActivity extends Activity {
	ImageView image;
	Bitmap bitmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_display);
        
        int imageNum = 0;
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "citizenWatch");
        String fileName = "image_" + String.valueOf(imageNum) + ".jpg";
        File output = new File(imagesFolder, fileName);
        while (output.exists()){
            imageNum++;
            fileName = "image_" + String.valueOf(imageNum) + ".jpg";
            output = new File(imagesFolder, fileName);
        }
        imageNum--;
        fileName = "image_" + String.valueOf(imageNum) + ".jpg";
        output = new File(imagesFolder, fileName); 
        image = (ImageView)findViewById(R.id.image01);
        
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true; 
        options.inJustDecodeBounds = true;
        options.outHeight = 50;
        options.outWidth = 50;
        options.inSampleSize = 4;
        bitmap = BitmapFactory.decodeFile(output.getAbsolutePath());
        image.setImageBitmap(bitmap);
        bitmap = null;
        
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
