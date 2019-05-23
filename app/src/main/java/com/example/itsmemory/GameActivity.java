package com.example.itsmemory;

import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;


public class GameActivity extends AppCompatActivity {
    int[] cardsData = new int[16];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // init cards
    for(int k=0; k<8; k++)
    {
        cardsData[k]=k;
        cardsData[k+8]=k;
    }



    //SHUFFLE CARDS
        Random rnd = new Random();

    for(int i=0; i<16; i++){
            int r = rnd.nextInt(16);
            int tmp=cardsData[i];
            cardsData[i] = cardsData[r];
            cardsData[r]= tmp;
        }

    Log.i("Cards rnd", " "+printIntArray(cardsData));


        for(int i=0; i<16; i++){
            int id = getResources().getIdentifier("imageView"+i,"id", getPackageName());
            ImageView card = (ImageView) findViewById(id);

            card.setImageResource(R.mipmap.ic_memo1);
            card.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){

                    String name = view.getResources().getResourceEntryName(view.getId());
                    int curId = Integer.parseInt(name.substring(9));
                    final ImageView curCard = (ImageView) view;

                    curCard.animate().scaleX(0f).setDuration(100);

                    Handler curTimer = new Handler();

                    curTimer.postDelayed(new Runnable(){
                        @Override
                        public void run(){
                            curCard.animate().scaleX(1f).setDuration(100);
                        }

                    }, 200);
                    curCard.setImageResource(R.mipmap.ic_memo2);

                }

            });
        }


    }
    String printIntArray (int[] a){
        String s = "";
        for(int i=0; i<a.length; i++){

            s+=a[i]+", ";

        }
        return s;
    }
}
