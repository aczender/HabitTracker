package com.example.andrew.habittracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.andrew.habittracker.data.HabitContract.TaskEntry;
import com.example.andrew.habittracker.data.TaskDbHelper;

public class MainActivity extends AppCompatActivity {

    private TaskDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new TaskDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private Cursor readDataFromDB() {
        TaskDbHelper mDbHelper = new TaskDbHelper(this);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                TaskEntry._ID,
                TaskEntry.COLUMN_TASK_NAME,
                TaskEntry.COLUMN_DATE,
                TaskEntry.COLUMN_HOUR
        };

        Cursor cursor = db.query(
                TaskEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }

    private void displayDatabaseInfo() {
        Cursor cursor = readDataFromDB();
        if (cursor != null && cursor.getCount() > 0) {

            TextView displayView = (TextView) findViewById(R.id.text_view_task);
            try

            {
                displayView.setText("The chores table contains " + cursor.getCount() + " chores.\n\n");
                displayView.append(TaskEntry._ID + " - " +
                        TaskEntry.COLUMN_TASK_NAME + " - " +
                        TaskEntry.COLUMN_DATE + " - " +
                        TaskEntry.COLUMN_HOUR + "\n");

                // Figure out the index of each column
                int idColumnIndex = cursor.getColumnIndex(TaskEntry._ID);
                int taskColumnIndex = cursor.getColumnIndex(TaskEntry.COLUMN_TASK_NAME);
                int dateColumnIndex = cursor.getColumnIndex(TaskEntry.COLUMN_DATE);
                int hourColumnIndex = cursor.getColumnIndex(TaskEntry.COLUMN_HOUR);

                // Iterate through all the returned rows in the cursor
                while (cursor.moveToNext()) {
                    // Use that index to extract the String or Int value of the word
                    // at the current row the cursor is on.
                    int currentID = cursor.getInt(idColumnIndex);
                    String currentTask = cursor.getString(taskColumnIndex);
                    String currentDate = cursor.getString(dateColumnIndex);
                    int currentHour = cursor.getInt(hourColumnIndex);
                    // Display the values from each column of the current row in the cursor in the TextView
                    displayView.append(("\n" + currentID + " - " +
                            currentTask + " - " +
                            currentDate + " - " +
                            currentHour));
                }
            } finally

            {
                // Always close the cursor when you're done reading from it. This releases all its
                // resources and makes it invalid.
                cursor.close();
            }
        }

    }

    private void insertTask() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TaskEntry.COLUMN_TASK_NAME, "Workout");
        values.put(TaskEntry.COLUMN_DATE, TaskEntry.DATE_MONDAY);
        values.put(TaskEntry.COLUMN_HOUR, 2);

        long newRowId = db.insert(TaskEntry.TABLE_NAME, null, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertTask();
                displayDatabaseInfo();

                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}