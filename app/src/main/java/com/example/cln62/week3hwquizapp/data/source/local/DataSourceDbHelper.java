package com.example.cln62.week3hwquizapp.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataSourceDbHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "QuestionAndAnswer2";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DataSourceContract.TodoEntry.TABLE_NAME + " (" +
                    DataSourceContract.TodoEntry._ID + " INTEGER PRIMARY KEY" + " AUTOINCREMENT," +
                    DataSourceContract.TodoEntry.COLUMN_NAME_QUESTION + TEXT_TYPE + COMMA_SEP +
                    DataSourceContract.TodoEntry.COLUMN_NAME_ANSWER + TEXT_TYPE + COMMA_SEP +
                    DataSourceContract.TodoEntry.COLUMN_NAME_ANSWER2 + TEXT_TYPE + COMMA_SEP +
                    DataSourceContract.TodoEntry.COLUMN_NAME_ANSWER3 + TEXT_TYPE + COMMA_SEP +
                    DataSourceContract.TodoEntry.COLUMN_NAME_ANSWER4 + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DataSourceContract.TodoEntry.TABLE_NAME;

    public DataSourceDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
