package com.coderdojo.dojoapp1;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class DataUpload extends AppCompatActivity {


    private View uploadButton;
    private EditText uploadBlank;
    public List dataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_upload);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data_upload, menu);
        uploadButton = (Button) findViewById(R.id.uploadButton);
        uploadBlank = (EditText) findViewById(R.id.uploadBlank);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void readFromFile() {

        //Find the directory for the SD Card using the API
//*Don't* hardcode "/sdcard"
        File sdcard = Environment.getExternalStorageDirectory();

//Get the text file
        String newFile = uploadBlank.getText().toString();
        File file = new File(sdcard, newFile);

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }





//Read text from file
/**        StringBuilder text = new StringBuilder();

 try {
 BufferedReader br = new BufferedReader(new FileReader(file));
 String line;

 while ((line = br.readLine()) != null) {
 text.append(line);
 text.append('\n');
 }
 br.close();
 }
 catch (IOException e) {
 //You'll need to add proper error handling here
 return;




 //Find the view by its id
 TextView tv = (TextView)findViewById(R.id.devDataBox);

 //Set the text
 tv.setText(text);
 }**/
    }
}
