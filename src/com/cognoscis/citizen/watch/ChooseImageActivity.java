package com.cognoscis.citizen.watch;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class ChooseImageActivity extends Activity {
	
	
    

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_image);
        
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
        ImageView image = (ImageView)findViewById(R.id.image01);
        Bitmap bitmap = BitmapFactory.decodeFile(output.getAbsolutePath());
        image.setImageBitmap(bitmap);
        
        Button save = (Button)findViewById(R.id.button_save);
        Button discard = (Button)findViewById(R.id.button_discard);
        
        
        
        save.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent display = new Intent(ChooseImageActivity.this, ImageDisplayActivity.class);
        		startActivity(display);
        	}
        });
        
        discard.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent camera = new Intent(ChooseImageActivity.this, CameraActivity.class);
        		startActivity(camera);
        	}
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.choose_image, menu);
        return true;
    }
}
