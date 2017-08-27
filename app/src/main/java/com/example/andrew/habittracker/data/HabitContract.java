package com.example.andrew.habittracker.data;


import android.provider.BaseColumns;

public final class HabitContract {

    private HabitContract() {}

    public static final class TaskEntry implements BaseColumns {

        public final static String TABLE_NAME = "tasks";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_TASK_NAME = "name";
        public final static String COLUMN_DATE = "date";
        public final static String COLUMN_HOUR = "hour";

        public static final int DATE_PICK = 0;
        public static final int DATE_MONDAY = 1;
        public static final int DATE_TUESDAY = 2;
        public static final int DATE_WEDNESDAY = 3;
        public static final int DATE_THURSDAY = 4;
        public static final int DATE_FRIDAY = 5;
        public static final int DATE_SATURDAY = 6;
        public static final int DATE_SUNDAY = 7;

    }
}