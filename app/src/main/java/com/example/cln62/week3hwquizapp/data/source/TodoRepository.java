package com.example.cln62.week3hwquizapp.data.source;

import android.content.Context;

import com.example.cln62.week3hwquizapp.data.source.local.DataSourceDAO;

public class TodoRepository implements TodoDataSource {

    TodoDataSource dataSourceDao;

    public TodoRepository(Context context) {
        dataSourceDao = new DataSourceDAO(context);
    }

    @Override
    public void dbInitializer() {
        dataSourceDao.dbInitializer();
    }

    @Override
    public void getQuesAndAnsFromDB(TodoNoteCallBack callBack, int cursorPosition) {
        dataSourceDao.getQuesAndAnsFromDB(callBack, cursorPosition);
    }

}
