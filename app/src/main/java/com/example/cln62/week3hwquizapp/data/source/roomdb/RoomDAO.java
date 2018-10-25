package com.example.cln62.week3hwquizapp.data.source.roomdb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.cln62.week3hwquizapp.data.TodoNote;

@Dao
public interface RoomDAO {

    @Insert
    void insert(DbSchema exampleSchema);

    @Query("DELETE FROM room_table")
    void deleteAll();

    @Query("SELECT question from room_table ")
    String[] getQuestion();

    @Query("SELECT option1 from room_table ")
    String[] getOption1();

    @Query("SELECT option2 from room_table ")
    String[] getOption2();

    @Query("SELECT option3 from room_table ")
    String[] getOption3();

    @Query("SELECT option4 from room_table ")
    String[] getOption4();


}
