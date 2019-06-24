package com.example.itsmemory;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

public class AllScores extends AppCompatActivity {
    MediaPlayer mp;

    SQLiteDatabase mydatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_scores);


        mydatabase = openOrCreateDatabase("DB_v2",MODE_PRIVATE ,null);
        TextView t = (TextView)findViewById(R.id.hires2);
        t.setMovementMethod(new ScrollingMovementMethod());
        mp = MediaPlayer.create(this, R.raw.gamemenu);
        mp.start();
        mp.setLooping(true);


        Cursor resultSet = mydatabase.rawQuery("Select * from Vars order by result DESC",null);
        if (resultSet!= null && resultSet.moveToFirst()) {
            while (!resultSet.isAfterLast()) {
                t.append(resultSet.getColumnName(0) + " = "
                        + resultSet.getString(0) + ", " + resultSet.getColumnName(1) + " = " + resultSet.getString(1)+ "\n");
                Log.i("lol", resultSet.getColumnName(0) + "="
                        + resultSet.getString(0) + " " + resultSet.getColumnName(1) + " " + resultSet.getString(1)+ "\n");
                resultSet.moveToNext();
            }
        }

    }
    @Override
    protected void onResume() {
        if(mp != null && !mp.isPlaying())
            mp.start();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mp.pause();
    }
}
