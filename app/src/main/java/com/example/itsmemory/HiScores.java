package com.example.itsmemory;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HiScores extends AppCompatActivity {
    MediaPlayer mp;
    SQLiteDatabase mydatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hi_scores);

        mydatabase = openOrCreateDatabase("DB_v2",MODE_PRIVATE ,null);
        TextView t = (TextView)findViewById(R.id.hires);
        t.setMovementMethod(new ScrollingMovementMethod());

        mp = MediaPlayer.create(this, R.raw.bg_game2);
        mp.start();
        mp.setLooping(true);

        Cursor resultSet = mydatabase.rawQuery("Select * from Vars order by result DESC limit 10",null);
        if (resultSet!= null && resultSet.moveToFirst()) {
            int i = 1;
            while (!resultSet.isAfterLast()) {
                t.append(i + ". " + resultSet.getColumnName(0) + " = "
                        + resultSet.getString(0) + ", " + resultSet.getColumnName(1) + " = " + resultSet.getString(1) + "\n");
                resultSet.moveToNext();
                i++;
            }
        }

        Button storyButton = (Button) findViewById(R.id.button2);
        storyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(HiScores.this, AllScores.class));
                mp.pause();
            }
        });

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
