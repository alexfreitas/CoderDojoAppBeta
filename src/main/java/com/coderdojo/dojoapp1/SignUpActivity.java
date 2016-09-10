package com.coderdojo.dojoapp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;

public class SignUpActivity extends AppCompatActivity {

    private String userMode = "0";
    EditText nickname = (EditText)findViewById(R.id.nickNameBox);
    EditText newName = (EditText)findViewById(R.id.newName);
    Spinner nameDrop = (Spinner)findViewById(R.id.nameDrop);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        nameDrop.setVisibility(View.GONE);
        nickname.setVisibility(View.GONE);
        newName.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }

    private void returnUser(){
        userMode = "Returning";
        newName.setVisibility(View.GONE);
        nameDrop.setVisibility(View.VISIBLE);

    }

    private void newUser(){
        userMode = "New";
        nameDrop.setVisibility(View.GONE);
        nickname.setVisibility(View.VISIBLE);


    }

    private void submit(String mode){
        if (mode.equalsIgnoreCase("dropdown")) {



        }

        if (mode.equalsIgnoreCase("newuser")){

        }

        if (mode.equalsIgnoreCase("username")){

        }

        if (mode.equalsIgnoreCase("complete")){

        }
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
}
