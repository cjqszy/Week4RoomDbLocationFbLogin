package com.example.cln62.week3hwquizapp.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.cln62.week3hwquizapp.data.TodoNote;
import com.example.cln62.week3hwquizapp.data.source.TodoDataSource;
import com.example.cln62.week3hwquizapp.data.source.local.QuestionAnswerContract.QueAndAnswer;

import static com.example.cln62.week3hwquizapp.data.source.local.DataSourceContract.TodoEntry.TABLE_NAME;

public class DataSourceDAO implements TodoDataSource{

    SQLiteDatabase sqLiteDatabase;
    SQLiteOpenHelper dataSourceDbHelper;
    int rowCnt = 0;

    public DataSourceDAO(Context context) {
        dataSourceDbHelper = new DataSourceDbHelper(context);
        openDB();
    }

    public void openDB(){
        sqLiteDatabase = dataSourceDbHelper.getWritableDatabase();
    }

    public void closeDB(){
        sqLiteDatabase.close();
    }

    @Override
    public void dbInitializer() {
        ContentValues values = new ContentValues();
        TodoNote todoNote = new TodoNote(QueAndAnswer.QUESTION, QueAndAnswer.ANSWER_1, QueAndAnswer.ANSWER_2,
                QueAndAnswer.ANSWER_3, QueAndAnswer.ANSWER_4);
        values.put(DataSourceContract.TodoEntry.COLUMN_NAME_QUESTION, todoNote.getQuestion());
        values.put(DataSourceContract.TodoEntry.COLUMN_NAME_ANSWER, todoNote.getAnswer1());
        values.put(DataSourceContract.TodoEntry.COLUMN_NAME_ANSWER2, todoNote.getAnswer2());
        values.put(DataSourceContract.TodoEntry.COLUMN_NAME_ANSWER3, todoNote.getAnswer3());
        values.put(DataSourceContract.TodoEntry.COLUMN_NAME_ANSWER4, todoNote.getAnswer4());
        sqLiteDatabase.insert(TABLE_NAME, null, values);

        values.clear();
        TodoNote todoNote1 = new TodoNote(QueAndAnswer.QUESTION2, QueAndAnswer.ANSWER2_1, QueAndAnswer.ANSWER2_2,
                QueAndAnswer.ANSWER2_3, QueAndAnswer.ANSWER2_4);
        values.put(DataSourceContract.TodoEntry.COLUMN_NAME_QUESTION, todoNote1.getQuestion());
        values.put(DataSourceContract.TodoEntry.COLUMN_NAME_ANSWER, todoNote1.getAnswer1());
        values.put(DataSourceContract.TodoEntry.COLUMN_NAME_ANSWER2, todoNote1.getAnswer2());
        values.put(DataSourceContract.TodoEntry.COLUMN_NAME_ANSWER3, todoNote1.getAnswer3());
        values.put(DataSourceContract.TodoEntry.COLUMN_NAME_ANSWER4, todoNote1.getAnswer4());
        sqLiteDatabase.insert(TABLE_NAME, null, values);
        rowCnt++;

        values.clear();
        TodoNote todoNote2 = new TodoNote(QueAndAnswer.QUESTION3, QueAndAnswer.ANSWER3_1, QueAndAnswer.ANSWER3_2,
                QueAndAnswer.ANSWER3_3, QueAndAnswer.ANSWER3_4);
        values.put(DataSourceContract.TodoEntry.COLUMN_NAME_QUESTION, todoNote2.getQuestion());
        values.put(DataSourceContract.TodoEntry.COLUMN_NAME_ANSWER, todoNote2.getAnswer1());
        values.put(DataSourceContract.TodoEntry.COLUMN_NAME_ANSWER2, todoNote2.getAnswer2());
        values.put(DataSourceContract.TodoEntry.COLUMN_NAME_ANSWER3, todoNote2.getAnswer3());
        values.put(DataSourceContract.TodoEntry.COLUMN_NAME_ANSWER4, todoNote2.getAnswer4());
        sqLiteDatabase.insert(TABLE_NAME, null, values);
        rowCnt++;


    }

    @Override
    public void getQuesAndAnsFromDB(TodoNoteCallBack callBack, int cursorPosition) {
        Cursor cursor = sqLiteDatabase.query(DataSourceContract.TodoEntry.TABLE_NAME, null, null, null, null,
            null, null);

        if (cursorPosition < 0) {
            return;
        }
        if (cursorPosition > rowCnt) {
            callBack.positionDecrease();
            return;
        }

        cursor.moveToPosition(cursorPosition);
        int questionIndex = cursor.getColumnIndexOrThrow(DataSourceContract.TodoEntry.COLUMN_NAME_QUESTION);
        int answerIndex = cursor.getColumnIndexOrThrow(DataSourceContract.TodoEntry.COLUMN_NAME_ANSWER);
        int answer2Index = cursor.getColumnIndexOrThrow(DataSourceContract.TodoEntry.COLUMN_NAME_ANSWER2);
        int answer3Index = cursor.getColumnIndexOrThrow(DataSourceContract.TodoEntry.COLUMN_NAME_ANSWER3);
        int answer4Index = cursor.getColumnIndexOrThrow(DataSourceContract.TodoEntry.COLUMN_NAME_ANSWER4);

        String question = cursor.getString(questionIndex);
        String answer = cursor.getString(answerIndex);
        String answer2 = cursor.getString(answer2Index);
        String answer3 = cursor.getString(answer3Index);
        String answer4 = cursor.getString(answer4Index);

        TodoNote todoNote = new TodoNote(question, answer, answer2, answer3, answer4);

        callBack.showQuesAndAns(todoNote);

    }
}
