package com.example.itsmemory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tv2 = (TextView)findViewById(R.id.textView2);
        final SharedPreferences dataobj;
        dataobj = getPreferences(MODE_PRIVATE);
        String name = dataobj.getString("name", "nessuno");

        tv2.setText(name);

        ImageView playButton = (ImageView) findViewById(R.id.imageView3);

        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivityForResult(new Intent(MainActivity.this, GameActivity.class), 10);
                dataobj.edit().putString("name", (String)tv2.getText()).commit();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10) {
            String s = data.getExtras().getString("result");
            TextView tv = (TextView)findViewById(R.id.textView2);
            Log.i("msg", s);
            tv.setText(s);
        }
    }
}
