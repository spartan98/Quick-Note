package com.example.lucifer.freehand;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    SingleTouchEventView customView;
    LinearLayout lay;
    Spinner sp;
    File myExternalFile;
    Button saveBtn,clear;
    private String filename = "SampleFile.png";
    private String filepath = "MyFileStorage";
    private final static String TAG = "Main";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.colors, android.R.layout.simple_spinner_item);
        sp.setAdapter(adapter);
        lay=findViewById(R.id.lay);
        customView=new SingleTouchEventView(this,null);
        lay.addView(customView);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (sp.getSelectedItem().toString()){
                    case "Black":
                        customView.setPathColor(Color.BLACK);
                        break;
                    case "Blue":
                        customView.setPathColor(Color.BLUE);
                        break;
                    case "Red":
                        customView.setPathColor(Color.RED);
                        break;
                    case "Green":
                        customView.setPathColor(Color.GREEN);
                        break;
                    case "Gray":
                        customView.setPathColor(Color.GRAY);
                        break;
                    case "Cyan":
                        customView.setPathColor(Color.CYAN);
                        break;
                    case "Magenta":
                        customView.setPathColor(Color.MAGENTA);
                        break;
                    case "Yellow":
                        customView.setPathColor(Color.YELLOW);
                        break;
                }
                if(sp.getSelectedItemPosition()!=0)
                Toast.makeText(getBaseContext(),sp.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                sp.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        saveBtn=findViewById(R.id.saveBtn);
        clear=findViewById(R.id.clearBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savingFile();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customView.clear();
                Toast.makeText(getApplicationContext(),"Cleared",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void savingFile() {

            Bitmap well = customView.getBitmap();
            Bitmap save = Bitmap.createBitmap(320, 480, Bitmap.Config.ARGB_8888);
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            Canvas now = new Canvas(save);
            now.drawRect(new Rect(0, 0, 320, 480), paint);
            now.drawBitmap(well,
                    new Rect(0, 0, well.getWidth(), well.getHeight()),
                    new Rect(0, 0, 320, 480), null);

            if (save == null) {
            }

            storeImage(save);
    }




    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="image_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        Toast.makeText(getApplicationContext(),"File Saved in : "+mediaFile.getPath().toString(),Toast.LENGTH_LONG).show();
        return mediaFile;
    }

    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d(TAG,
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }

}
