package com.example.cln62.week3hwquizapp;

import com.example.cln62.week3hwquizapp.data.TodoNote;
import com.example.cln62.week3hwquizapp.main.MainActivity;
import com.example.cln62.week3hwquizapp.main.MainPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    //Junit Test
    @Test
    public void junitTest() {

        MainActivity mainActivity = new MainActivity();
        mainActivity.cursorPosition = 0;
        MainPresenter mainPresenter = new MainPresenter(mainActivity);
        mainPresenter.positionIncrease();

        assertEquals(mainActivity.cursorPosition, 1);

    }




    //Mockito Test
    @Test
    public void MainActivityTest() {
        MainActivity test = mock(MainActivity.class);

        TodoNote todoNote1 = new TodoNote("Hi", "1", "2", "3", "4");
        TodoNote todoNote2 = new TodoNote("Hello", "1", "2", "3", "4");
        test.showQuesAndAns(todoNote1);
        test.positionIncreaseComfirm();
        test.positionIncreaseComfirm();
        test.positionIncreaseComfirm();
        test.positionDecreaseComfirm();

        verify(test).showQuesAndAns(ArgumentMatchers.eq(todoNote1));
//        verify(test).showQuesAndAns(ArgumentMatchers.eq(todoNote2));

//        verify(test, atLeast(3)).positionIncreaseComfirm();

//        verify(test, never()).positionDecreaseComfirm();




        //java.lang.RuntimeException: Method getWritableDatabase in android.database.sqlite.SQLiteOpenHelper not mocked
/*        test.cursorPosition = 0;

        MainPresenter mainPresenterTest = new MainPresenter(test);

        mainPresenterTest.positionIncrease();

        assertEquals(test.cursorPosition, 1);*/

    }


}