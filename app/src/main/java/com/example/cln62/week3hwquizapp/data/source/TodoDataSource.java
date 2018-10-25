package com.example.cln62.week3hwquizapp.data.source;

import com.example.cln62.week3hwquizapp.data.TodoNote;

public interface TodoDataSource {
    void dbInitializer();

    void getQuesAndAnsFromDB(TodoNoteCallBack callBack, int cursorPosition);

    interface TodoNoteCallBack{
        public void showQuesAndAns(TodoNote todonote);

        void positionDecrease();
    };
}
