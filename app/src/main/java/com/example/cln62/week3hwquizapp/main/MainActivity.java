package com.example.cln62.week3hwquizapp.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cln62.week3hwquizapp.R;
import com.example.cln62.week3hwquizapp.data.TodoNote;
import com.example.cln62.week3hwquizapp.data.source.roomdb.DbSchema;
import com.example.cln62.week3hwquizapp.data.source.roomdb.MyRoomDatabase;
import com.example.cln62.week3hwquizapp.data.source.roomdb.RoomDAO;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MainContract.View{

    TextView question, textViewTest2;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    MainPresenter mainPresenter;
    public int cursorPosition = 0;
    MyRoomDatabase db;
    private RoomDAO myRoomDao;
//    private TodoNote[] todoNote = new TodoNote[4];
    DbSchema[] exampleSchema = new DbSchema[4];
    Location mLastLocation;
    FusedLocationProviderClient mFusedLocationClient;
    int REQUEST_LOCATION_PERMISSION = 1;
    int REQUEST_PERMISSIONS_REQUEST_CODE = 2;
    String longitude = "null";
    String latitude = "null";
    boolean mAddressRequested;
    Button button;
    private static isLoadDataListener loadLisneter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenter(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        question = findViewById(R.id.questionTextView);
        checkBox1 = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();
        }

        button = findViewById(R.id.buttonTest);
        textViewTest2 = findViewById(R.id.textViewTest2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewTest2.setText(longitude);
            }
        });

    }

    public boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request permission
                    startLocationPermissionRequest();
                }
            };

        } else {
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    public void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }


    public void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {

            requestPermissions();
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                            latitude = String.valueOf(mLastLocation.getLatitude());
                            longitude = String.valueOf(mLastLocation.getLongitude());
                            Log.i   ("aaa", "ss");
                            roomDbInitializer();

                        } else {
                            Log.w("aaa", "getLastLocation:exception", task.getException());
                        }
                    }
                });


    }

    public void roomDbInitializer() {
        Log.i("aaa", longitude);
        db = MyRoomDatabase.getDatabase(this);
        myRoomDao = db.wordDao();
        DbSchema exampleSchema = new DbSchema("Who is SandeepBerry?", "Sandeep", "Berry",
                "Chidie", "YiXin");
        DbSchema exampleSchema2 = new DbSchema("Who is aaa", "a", "b",
                "c", "d");
        DbSchema exampleSchema3 = new DbSchema("Who is bbb?", "b", "c",
                "d", "a");
        DbSchema exampleSchema4 = new DbSchema("Who is cureent location?", longitude, latitude,
                "d", "a");

        Log.i("aaa", exampleSchema4.getOption2());
        insert(exampleSchema, 0);
        insert(exampleSchema2, 0);
        insert(exampleSchema3, 0);
        insert(exampleSchema4, 1);

    }

    public void insert (DbSchema exampleSchema, int flag) {
        new insertAsyncTask(myRoomDao).execute(exampleSchema);
        if (flag == 0) {
            return;
        }
        setLoadDataComplete(new isLoadDataListener() {
            @Override
            public void loadComplete() {
                getAsyncTask getTask = new getAsyncTask(myRoomDao);
                getTask.execute();
            }
        });
    }

    private static class insertAsyncTask extends AsyncTask<DbSchema, Void, Void> {

        private RoomDAO mAsyncTaskDao;

        insertAsyncTask(RoomDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final DbSchema... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (loadLisneter != null) {
                loadLisneter.loadComplete();
            }
        }
    }

    private class getAsyncTask extends AsyncTask<Void, Void ,Void>
    {

        private RoomDAO mAsyncTaskDao;

        public getAsyncTask(RoomDAO myRoomDao) {
            this.mAsyncTaskDao = myRoomDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String[] question = myRoomDao.getQuestion();
            String[] option1 = myRoomDao.getOption1();
            String[] option2 = myRoomDao.getOption2();
            String[] option3 = myRoomDao.getOption3();
            String[] option4 = myRoomDao.getOption4();
            for (int i = 0; i < 4; i++) {
                exampleSchema[i] = new DbSchema(question[i], option1[i], option2[i], option3[i], option4[i]);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            question.setText(exampleSchema[0].getQuestion());
            checkBox1.setText(exampleSchema[0].getOption1());
            checkBox2.setText(exampleSchema[0].getOption2());
            checkBox3.setText(exampleSchema[0].getOption3());
            checkBox4.setText(exampleSchema[0].getOption4());
        }
    }

    public void buttonListener(View view) {
        switch (view.getId()) {
            case R.id.nextButton:
                if (cursorPosition == 3) {
                    return;
                }
                cursorPosition++;
                question.setText(exampleSchema[cursorPosition].getQuestion());
                checkBox1.setText(exampleSchema[cursorPosition].getOption1());
                checkBox2.setText(exampleSchema[cursorPosition].getOption2());
                checkBox3.setText(exampleSchema[cursorPosition].getOption3());
                checkBox4.setText(exampleSchema[cursorPosition].getOption4());
                break;
            case R.id.prevButton:
                if (cursorPosition == 0) {
                    return;
                }
                cursorPosition--;
                question.setText(exampleSchema[cursorPosition].getQuestion());
                checkBox1.setText(exampleSchema[cursorPosition].getOption1());
                checkBox2.setText(exampleSchema[cursorPosition].getOption2());
                checkBox3.setText(exampleSchema[cursorPosition].getOption3());
                checkBox4.setText(exampleSchema[cursorPosition].getOption4());
                break;
        }
    }

    private interface isLoadDataListener {
        public void loadComplete();
    }

    public void setLoadDataComplete(isLoadDataListener dataComplete) {
        loadLisneter = dataComplete;
    }
}
