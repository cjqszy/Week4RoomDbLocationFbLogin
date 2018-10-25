package com.example.cln62.week3hwquizapp.main;

import com.example.cln62.week3hwquizapp.data.TodoNote;
import com.example.cln62.week3hwquizapp.data.source.TodoDataSource;
import com.example.cln62.week3hwquizapp.data.source.TodoRepository;

public class MainPresenter implements MainContract.Presenter {

    MainContract.View view;
    TodoDataSource todoRepository;

    public MainPresenter(MainActivity mainActivity) {
        view = mainActivity;
        todoRepository = new TodoRepository(mainActivity);
    }


/*    @Override
    public void roomDbInitializer() {
        view.roomDbInitializerConfirm();
    }*/
/*    @Override
    public void getLocation() {
//        view.getLocationConfirm();
    }*/
}
