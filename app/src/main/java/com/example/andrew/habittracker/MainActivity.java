package com.example.andrew.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.andrew.habittracker.data.HabitContract;
import com.example.andrew.habittracker.data.TaskDbHelper;

public class MainActivity extends AppCompatActivity {

    private EditText mTaskEditText;
    private EditText mHourEditText;
    private Spinner mDateSpinner;

    private int mDate = HabitContract.TaskEntry.DATE_PICK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find all relevant views that we will need to read user input from
        mTaskEditText = (EditText) findViewById(R.id.edit_task_name);
        mHourEditText = (EditText) findViewById(R.id.edit_hour);
        mDateSpinner = (Spinner) findViewById(R.id.spinner_date);

        setupSpinner();
    }

    private void setupSpinner() {
        ArrayAdapter dateSpinnerAddapter = ArrayAdapter.createFromResource(this, R.array
                .array_date_options, android.R.layout.simple_spinner_item);

        dateSpinnerAddapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mDateSpinner.setAdapter(dateSpinnerAddapter);

        mDateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals("Monday")) {
                        mDate = HabitContract.TaskEntry.DATE_MONDAY;
                    } else if (selection.equals("Tuesday")) {
                        mDate = HabitContract.TaskEntry.DATE_TUESDAY;
                    } else if (selection.equals("Wednesday")) {
                        mDate = HabitContract.TaskEntry.DATE_WEDNESDAY;
                    } else if (selection.equals("Thursday")) {
                        mDate = HabitContract.TaskEntry.DATE_THURSDAY;
                    } else if (selection.equals("Friday")) {
                        mDate = HabitContract.TaskEntry.DATE_FRIDAY;
                    } else if (selection.equals("Saturday")) {
                        mDate = HabitContract.TaskEntry.DATE_SATURDAY;
                    } else if (selection.equals("Sunday")) {
                        mDate = HabitContract.TaskEntry.DATE_SUNDAY;
                    } else {
                        mDate = HabitContract.TaskEntry.DATE_PICK;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mDate = HabitContract.TaskEntry.DATE_PICK;
            }
        });
    }

    private void insertTask() {
        String taskString = mTaskEditText.getText().toString().trim();
        String hourString = mHourEditText.getText().toString().trim();
        int time = Integer.parseInt(hourString);

        TaskDbHelper mDbHelper = new TaskDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(HabitContract.TaskEntry.COLUMN_TASK_NAME, taskString);
        values.put(HabitContract.TaskEntry.COLUMN_DATE, mDate);
        values.put(HabitContract.TaskEntry.COLUMN_HOUR, time);

        long newRowId = db.insert(HabitContract.TaskEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Error with adding new task", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu){
            getMenuInflater().inflate(R.menu.menu_editor, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            switch (item.getItemId()) {
                case R.id.action_save:
                    insertTask();
                    finish();
                    return true;
                case R.action_delete:
                    return true;
                case android.R.id.home:
                    NavUtils.navigateUpFromSameTask(this);
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }
}