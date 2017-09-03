package com.example.andrew.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.andrew.habittracker.data.HabitContract.TaskEntry;
import com.example.andrew.habittracker.data.TaskDbHelper;

public class EditorActivity extends AppCompatActivity {

    private EditText mTaskEditText;
    private EditText mHourEditText;
    private Spinner mDateSpinner;

    private int mDate = TaskEntry.DATE_PICK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mTaskEditText = (EditText) findViewById(R.id.edit_task_name);
        mHourEditText = (EditText) findViewById(R.id.edit_hour);
        mDateSpinner = (Spinner) findViewById(R.id.spinner_date);

        setupSpinner();
    }

    private void setupSpinner() {
        ArrayAdapter dateSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array
                .array_date_options, android.R.layout.simple_spinner_item);

        dateSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mDateSpinner.setAdapter(dateSpinnerAdapter);

        mDateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.monday))) {
                        mDate = TaskEntry.DATE_MONDAY;
                    } else if (selection.equals(getString(R.string.tuesday))) {
                        mDate = TaskEntry.DATE_TUESDAY;
                    } else if (selection.equals(getString(R.string.wednesday))) {
                        mDate = TaskEntry.DATE_WEDNESDAY;
                    } else if (selection.equals(getString(R.string.thursday))) {
                        mDate = TaskEntry.DATE_THURSDAY;
                    } else if (selection.equals(getString(R.string.friday))) {
                        mDate = TaskEntry.DATE_FRIDAY;
                    } else if (selection.equals(getString(R.string.saturday))) {
                        mDate = TaskEntry.DATE_SATURDAY;
                    } else if (selection.equals(getString(R.string.sunday))) {
                        mDate = TaskEntry.DATE_SUNDAY;
                    } else {
                        mDate = TaskEntry.DATE_PICK;
                    }
                }
            }
                @Override
                public void onNothingSelected (AdapterView < ? > parent){
                    mDate = TaskEntry.DATE_PICK;
                }
            });
        }

        private void insertTask () {
            String taskString = mTaskEditText.getText().toString().trim();
            String hourString = mHourEditText.getText().toString().trim();
            int time = Integer.parseInt(hourString);

            TaskDbHelper mDbHelper = new TaskDbHelper(this);

            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(TaskEntry.COLUMN_TASK_NAME, taskString);
            values.put(TaskEntry.COLUMN_DATE, mDate);
            values.put(TaskEntry.COLUMN_HOUR, time);

            long newRowId = db.insert(TaskEntry.TABLE_NAME, null, values);

            if (newRowId == -1) {
                Toast.makeText(this, getString(R.string.adding_error), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.task_added) + newRowId, Toast
                        .LENGTH_SHORT)
                        .show();
            }
        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
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
                case R.id.action_delete:
                    return true;
                case android.R.id.home:
                    NavUtils.navigateUpFromSameTask(this);
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }