package com.example.itsmemory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.os.Build.VERSION_CODES.M;
import static android.os.Build.VERSION_CODES.O;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mp;
    SQLiteDatabase mydatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydatabase = openOrCreateDatabase("DB_v2",MODE_PRIVATE ,null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS Vars(name VARCHAR,result INT);");

        mp = MediaPlayer.create(this, R.raw.bg_menu);
        mp.start();
        mp.setLooping(true);


        ImageView playButton = (ImageView) findViewById(R.id.imageView3);
        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mp.pause();
                startActivityForResult(new Intent(MainActivity.this, GameActivity.class), 10);

            }
        });
        Button storyButton = (Button) findViewById(R.id.button);
        storyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(MainActivity.this, HiScores.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10) {

            mydatabase.execSQL("INSERT INTO Vars VALUES(\'" + data.getExtras().getString("name") + "\'," +
                    data.getExtras().getInt("result")+ ");");
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
