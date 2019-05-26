package com.example.itsmemory;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements Cloneable {

    String[] cardsData2 = new String[16];
    String c1 = null;
    String c2 = null;
    ImageView tmpImg;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        // init cards
        for(int k=0; k<8; k++)
        {
            //cardsData[k]=k;
            //cardsData[k+8]=k;
            cardsData2[k] = "ic_memo" + (k+2);
            cardsData2[k+8] = "ic_memo" + (k+2);
        }
        //SHUFFLE CARDS
        for(int i=0; i<16; i++)
        {
            Random rnd = new Random();
            int r = rnd.nextInt(16);
            String tmp = cardsData2[i];
            cardsData2[i] = cardsData2[r];
            cardsData2[r] = tmp;
        }
        for(int i=0; i<16; i++)
        {
            int id = getResources().getIdentifier("imageView"+i,"id", getPackageName());
            final ImageView card = findViewById(id);
            final int j = i;

            card.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    String name = view.getResources().getResourceEntryName(view.getId());
                    int curId = Integer.parseInt(name.substring(9));
                    final ImageView curCard = (ImageView) view;

                    curCard.animate().scaleX(0f).setDuration(100);

                    Handler curTimer = new Handler();

                    curTimer.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            curCard.animate().scaleX(1f).setDuration(100);
                        }

                    }, 200);
                    int imageId = getResources().getIdentifier(cardsData2[j], "mipmap", getPackageName());
                    curCard.setImageResource(imageId);
                    curCard.setClickable(false);
                    new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            checkCards(curCard, cardsData2[j]);
                                        }
                                    });
                                }
                            }, 500);
                }
            });
        }
    }

    private void checkCards(ImageView c, String s) {
        TextView points = findViewById(R.id.textScore);
        int t = Integer.parseInt(points.getText().toString());

        if (c1==null)
        {
            c1 = s;
            tmpImg = c;
        } else {
            c2 = s;
            if (c1.equals(c2)) {
                c1 = null;
                c2 = null;
                tmpImg = null;
                counter++;
                points.setText("" + (t += 10));
                if (counter == 8) {
                    Intent res = new Intent();
                    res.putExtra("result", "Il tuo record è: " + points);
                    setResult(10, res);
                    AlertDialog.Builder b = new AlertDialog.Builder(GameActivity.this);
                    b.setMessage("you win");
                    b.setPositiveButton("Torna indietro",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    startActivity(new Intent(GameActivity.this, MainActivity.class));
                                }
                            });
                    AlertDialog alertDialog = b.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                }

            } else {
                points.setText("" + (t -= 1));
                tmpImg.setImageResource(R.mipmap.ic_memo1);
                c.setImageResource(R.mipmap.ic_memo1);
                tmpImg.setClickable(true);
                c.setClickable(true);
                c1 = null;
                c2 = null;
            }
        }
    }
}


    /*  Array Print
    String printIntArray (int[] a){
        String s = "";
        for(int i=0; i<a.length; i++){

            s+=a[i]+", ";

        }
        return s;
    }
    String printStringArray (String[] a){
        String s = "";
        for(int i=0; i<a.length; i++){

            s+=a[i]+", ";

        }
        return s;
    }*/
