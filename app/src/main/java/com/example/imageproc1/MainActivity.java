package com.example.imageproc1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Uri imageUri;
    Bitmap grayBitmap,imageBitmap;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        textView= (TextView) findViewById(R.id.textView);
    }

    public void openGallery(View v){
        Intent myIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(myIntent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK && data!=null){
            imageUri = data.getData();
            try{
                imageBitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
            }catch (IOException e){
                e.printStackTrace();
            }
            imageView.setImageBitmap(imageBitmap);
            int width= imageBitmap.getWidth();
            int height= imageBitmap.getHeight();
            float lum=0;
            for(int y=0;y<height;y++){
                for(int x=0;x<width;x++){
                    int pixel = imageBitmap.getPixel(x,y);
                    int red = Color.red(pixel);
                    int blue = Color.blue(pixel);
                    int green = Color.green(pixel);
                    float luminance = (red * 0.2126f + green * 0.7152f + blue * 0.0722f) / 255;
                    lum+=luminance;
                }
            }
            lum/=(width*height);
            textView.setText(Float.toString(lum));
        }
    }
}