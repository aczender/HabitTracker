package com.example.andrew.habittracker.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = TaskDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "chores.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the chores table
        String SQL_CREATE_CHORES_TABLE =  "CREATE TABLE " + HabitContract.TaskEntry.TABLE_NAME + " ("
                + HabitContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitContract.TaskEntry.COLUMN_TASK_NAME + " TEXT NOT NULL, "
                + HabitContract.TaskEntry.COLUMN_DATE + " INTEGER NOT NULL, "
                + HabitContract.TaskEntry.COLUMN_HOUR + " INTEGER NOT NULL DEFAULT 0);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_CHORES_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}