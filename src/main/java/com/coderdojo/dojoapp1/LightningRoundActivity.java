package com.coderdojo.dojoapp1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.coderdojo.dojoapp1.dataSources.DojoDBAdapter;

public class LightningRoundActivity extends Activity{
	private final String TAG = "LightningRoundActivity";






	//****************************************************************************************
	// mCheckBoxListener
	//	Handles checkbox clicks.  Marks the Kid as having completed his talk
	private View.OnClickListener mCheckBoxListener = new View.OnClickListener() {
		public void onClick(View v) {
			CheckBox checkbox = (CheckBox)v;

			//update the Kid's status
			TableRow clickedRow = (TableRow)v.getParent();
			int curRow = mKidsTable.indexOfChild(clickedRow);
			Kid currKid = mPresenters.get(curRow);
			String name = currKid.getName();
			currKid.setChecked(checkbox.isChecked());

			//checked -> move Kid to bottom of list
			if (checkbox.isChecked()) {
				mKidsTable.removeViewAt(curRow);
				mPresenters.remove(curRow);

				mPresenters.add(currKid);

				//display kid in new row at bottom of table
				appendKidToTable(currKid);

				TableRow newCheckbox = (TableRow) mKidsTable.getChildAt(mPresenters.size() - 1);

				checkbox = (CheckBox)newCheckbox.getChildAt(0);
				checkbox.setChecked(true);
			}

			//refresh alternating row colors
			refreshRowColors();
		}
	};

	//****************************************************************************************
	// Remove presenters
    private View.OnClickListener mRemovePresenterListener = new View.OnClickListener() {
        public void onClick(View v) {
            Log.d(TAG, "ClearButton Clicked");
            TableRow clickedRow = (TableRow)v.getParent();
			int curRow = mKidsTable.indexOfChild(clickedRow);
			Kid kid = mPresenters.get(curRow);
			mPresenters.remove(curRow);
			mKidsTable.removeViewAt(curRow);
			try{
				mPresenters.remove(kid);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//refresh alternating row colors
			refreshRowColors();

        }

    } ;






	//List of Kids who have signed up to present
	protected ArrayList<Kid> mPresenters;

	private TableLayout mKidsTable;
	private EditText mNewNameTextBox;

	private Spinner mNameDropdown;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lightning_round);

	}

	@Override
	protected void onStart(){

		/**List<String> testArray = new ArrayList<String>();
		testArray.add("test1");
		testArray.add("test2");

		super.onStart();





**/

		String url="https://dl.dropbox.com/s/qj45pmggw7mbe05/data.csv?dl=0";
		String filename="data.csv";

		try{
			URL download=new URL(url);
			ReadableByteChannel rbc= Channels.newChannel(download.openStream());
			FileOutputStream fileOut = new FileOutputStream(filename);
			fileOut.getChannel().transferFrom(rbc, 0, 1 << 24);
			fileOut.flush();
			fileOut.close();
			rbc.close();
		}catch(Exception e){ e.printStackTrace(); }


		String sdcard = LightningRoundActivity.this.getFilesDir().getAbsolutePath();

//Get the text file

		//File file = new File(sdcard, "data.csv");
		//FileInputStream inputStream = null;


		InputStream is= getClass().getResourceAsStream(sdcard + "/data.csv");
		InputStreamReader isr = new InputStreamReader(is);
		CSVFile csvFile = new CSVFile(isr);
		final ArrayList<String> dataList = (ArrayList<String>) csvFile.read();


		final List<String> list=new ArrayList<String>();
		list.add("Item 1");
		list.add("Item 2");
		list.add("Item 3");
		list.add("Item 4");
		list.add("Item 5");

		final String[] str={"Report 1","Report 2","Report 3","Report 4","Report 5"};

		final Spinner sp1= (Spinner) findViewById(R.id.lightningNameSelect);



		ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,str);
		adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp1.setAdapter(adp1);


		ArrayAdapter<String> adp2=new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,str);
		adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getBaseContext(), dataList.get(position), Toast.LENGTH_SHORT).show();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});




		//get data for table
		mPresenters = MainActivity.getKidsData();



		//populate view
		mNewNameTextBox = (EditText)findViewById(R.id.lightningRoundAddNameText);
		mNameDropdown = (Spinner)findViewById(R.id.lightningNameSelect);

		mKidsTable = (TableLayout)findViewById(R.id.lightningRoundTable);
		mKidsTable.removeAllViews();
		populateKidsTable();

	}
	
	private void populateKidsTable(){
		for (int i=0; i<mPresenters.size(); i++){

			Kid reconKid = mPresenters.get(i);
			addKidToTable(i, reconKid);
		}


	}

	private void addKidToTable(int i, Kid kid) {
		//add row to table
		TableRow nextRow = new TableRow(this);

		TableRow.LayoutParams rowLayout = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
		nextRow.setLayoutParams(rowLayout);
		setRowColor(i, nextRow);
		RectShape rectangle = new RectShape();
		ShapeDrawable background = new ShapeDrawable(rectangle);
		background.setPadding(4, 2, 16, 2);
		Paint painter = background.getPaint();
		painter.setColor(Color.argb(100,100,0,0));
		//nextRow.setBackground(background); --- cannot use unless restrict to higher API levels



		//show Kid name in text view in new row
		TextView nextText = new TextView(this);
		nextText.setText(kid.getName());
		nextText.setPadding(2, 2, 16, 2);

		//provide checkbox to indicate has already presented
		CheckBox nextCheckBox = new CheckBox(this);
		nextCheckBox.setPadding(2, 2, 2, 2);
		nextCheckBox.setOnClickListener(this.mCheckBoxListener);
		nextCheckBox.setChecked(kid.isChecked());

		//add up/down buttons

        Button nextRemoveButton = new Button(this);
        nextRemoveButton.setText("X");
        //nextRemoveButton.setEms(4);
        nextRemoveButton.setOnClickListener(this.mRemovePresenterListener);

		//setup row with checkbox, name, up/down buttons

		nextRow.addView(nextCheckBox);
		nextRow.addView(nextText);
        nextRow.addView(nextRemoveButton);
		
		mKidsTable.addView(nextRow, i);



	}

	private void refreshRowColors(){
		for (int i=0; i<mKidsTable.getChildCount(); i++){
			TableRow nextRow = (TableRow)mKidsTable.getChildAt(i);

			setRowColor(i, nextRow);
		}
	}

	private void setRowColor(int index, TableRow nextRow) {
		if (index %2==0) {
			nextRow.setBackgroundColor(Color.LTGRAY);
		}
		else{
			nextRow.setBackgroundColor(Color.argb(50,255,0,0));
		}
	}


	private void onEditListener(View view){

	}

	private void appendKidToTable(Kid kid) {
		int count = mKidsTable.getChildCount();
		addKidToTable(count, kid);
	}

	private boolean edit(String[] command) {
		if (command[0].equalsIgnoreCase("edit")) {
			if (mKidsTable.getChildCount() == 0) {
				mNewNameTextBox.setText("");
				return false;
			}
			try {
				String test = command[1] + command[2];
			} catch (Exception e) {
				mNewNameTextBox.setText("");
				return false;
			}
			for (Kid k : mPresenters) {
				String name = k.getName();


				for (Kid c : mPresenters) {
					if (c.getName().equalsIgnoreCase(command[2])) {
						mNewNameTextBox.setText("");
						return false;
					}
				}

				if (k.getName().equalsIgnoreCase(command[1])) {


					Kid found = null;
					for (int i = 0; i < mPresenters.size(); i++) {
						if (mPresenters.get(i).getName().equalsIgnoreCase(name)) {

							found = mPresenters.get(i);
							mPresenters.remove(i);
							Kid editKid = new Kid(command[2]);
							mPresenters.add(i, editKid);

							mKidsTable.removeAllViews();
							populateKidsTable();
							mNewNameTextBox.setText("");


							return true;

						}

					}


				}


			}


		}
	return false;

	}

	private boolean del(String[] command) {

		if (command[0].equalsIgnoreCase("rm")) {
			if (mKidsTable.getChildCount() == 0) {
				mNewNameTextBox.setText("");
				return false;
			}
			try {
				String test = command[1];
			} catch (Exception e) {
				mNewNameTextBox.setText("");
				return false;
			}

			for (Kid k:mPresenters){
				if (k.getName().equalsIgnoreCase(command[1])) {
					int index = mPresenters.indexOf(k);
					mPresenters.remove(index);
				}
				mKidsTable.removeAllViews();
				populateKidsTable();
				mNewNameTextBox.setText("");
				return true;
			}

		}
		mNewNameTextBox.setText("");
		return false;
	}

	private boolean move(String[] command){

		if (command[0].equalsIgnoreCase("mv")){

			if (mKidsTable.getChildCount() == 0) {
				mNewNameTextBox.setText("");
				return false;
			}
			try {
				String test = command[1] + command[2] + command[3];
			} catch (Exception e) {
				mNewNameTextBox.setText("");
				return false;
			}

			if (command[2].equalsIgnoreCase("d")){
				for (Kid k:mPresenters){
					if (k.getName().equalsIgnoreCase(command[1])){
						if(mPresenters.indexOf(k) + Integer.parseInt(command[3]) < mPresenters.size()){
							return true;
						}
						else {
							return false;
						}
					}
				}
			}
			if (command[2].equalsIgnoreCase("u")){
				for (Kid k:mPresenters){
					if (k.getName().equalsIgnoreCase(command[1])){
						if(mPresenters.indexOf(k) - Integer.parseInt(command[3]) < 0){
							return true;
						}
						else {
							return false;
						}
					}
				}
			}


			for (Kid k: mPresenters){

				if (k.getName().equalsIgnoreCase(command[1])){
					for (int i = 0; i <= Integer.parseInt(command[3]); i++ ){
						if (command[2].equalsIgnoreCase("u")){
							Collections.swap(mPresenters, mPresenters.indexOf(k), mPresenters.indexOf(k) - 1);

						}
						if (command[2].equalsIgnoreCase("d")){
							Collections.swap(mPresenters, mPresenters.indexOf(k), mPresenters.indexOf(k) + 1);

						}
					}
				}

			}
			mKidsTable.removeAllViews();
			populateKidsTable();
			return true;






		}
		return false;



	}
	public void onClickAdd(View view){
		if  (!mNewNameTextBox.getText().toString().equalsIgnoreCase("")) {
			try {
				String newName = mNewNameTextBox.getText().toString();
				//remove leading/trailing spaces
				newName = newName.trim();

				//check name is not empty
				if (newName.isEmpty()) {
					//ignore click
					//clear the edit box
					mNewNameTextBox.setText("");
					return;
				}

				//check for edit command
				String[] command = newName.split(" ");
				if (edit(command)) {
					return;

				}
				if (command[0].equalsIgnoreCase("edit")) {
					showMessage("Edit failed");
					//clear the edit box
					mNewNameTextBox.setText("");
					return;

				}

				if (del(command)) {
					return;

				}
				if (command[0].equalsIgnoreCase("rm")) {
					showMessage("Cannot delete");
					//clear the edit box
					mNewNameTextBox.setText("");
					return;
				}
				if (move(command)) {
					return;

				}

				if (command[0].equalsIgnoreCase("mv")) {
					showMessage("Cannot move");
					mNewNameTextBox.setText("");
					return;

				}


				//check name is not already in presentation list
				Kid found = null;
				for (int i = 0; i < mPresenters.size(); i++) {
					if (mPresenters.get(i).getName().equalsIgnoreCase(newName)) {
						found = mPresenters.get(i);
						break;
					}
				}

				if (found != null) {
					showMessage("This Kid is already in the Lightning Round");
					//clear the edit box
					mNewNameTextBox.setText("");
					return;
				}

				//add kid to the presenters list
				Kid newKid = new Kid(newName);
				mPresenters.add(newKid);

				//display kid as new row in table
				appendKidToTable(newKid);

				//clear the edit box
				mNewNameTextBox.setText("");
			} catch (Exception ex) {
				showMessage("Unable to add:  " + ex.getMessage());
			}


		}
		else{
			String name = mNameDropdown.getSelectedItem().toString();
			Kid newKid = new Kid(name);
			mPresenters.add(newKid);
			appendKidToTable(newKid);


		}

	}



    private void showMessage(String message){
		ApplicationUtilities.showMessage(this, message);
    }

}
