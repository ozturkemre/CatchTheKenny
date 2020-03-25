package com.ozturkemre.catchthekenny;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int score;
    TextView scoreText;
    TextView timeText;

    //Declare image array
    ImageView[] imgs = new ImageView[9];

    Handler handler;
    Runnable runnable;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeText = findViewById(R.id.timeText);
        scoreText = findViewById(R.id.scoreText);

        //append image to ImageView array
        imgs[0] = findViewById(R.id.imageView);
        imgs[1] = findViewById(R.id.imageView2);
        imgs[2] = findViewById(R.id.imageView3);
        imgs[3] = findViewById(R.id.imageView4);
        imgs[4] = findViewById(R.id.imageView5);
        imgs[5] = findViewById(R.id.imageView6);
        imgs[6] = findViewById(R.id.imageView7);
        imgs[7] = findViewById(R.id.imageView8);
        imgs[8] = findViewById(R.id.imageView9);

        score = 0;
        disableClick();


    }

    public void start(View view) {
        hideImages();
        enableClick();
        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("Remaining time: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                timeText.setText("Time is over");
                handler.removeCallbacks(runnable);
                for (ImageView image : imgs) {
                    image.setVisibility(View.INVISIBLE);
                }
//                SavePreferences("",""+score);

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Play again");
                alert.setMessage("Wanna play again?");


                if (score > getHigh()) {
                    SavePreferences("high", Integer.toString(score));

                }

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);

                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Game over", Toast.LENGTH_LONG).show();
                    }
                });
                alert.show();

            }
        }.start();
    }

    ;

    public void increaseScore(View view) {
        score++;
        scoreText.setText("Score: " + score);

    }

    public void hideImages() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for (ImageView image : imgs) {
                    image.setVisibility(View.INVISIBLE);
                }
                Random random = new Random();
                int i = random.nextInt(9);
                imgs[i].setVisibility(View.VISIBLE);
                handler.postDelayed(this, 500);
            }
        };
        handler.post(runnable);

    }

    public void SavePreferences(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void changeToScores(View view) {
        Intent intent = new Intent(MainActivity.this, TopScores.class);
        startActivity(intent);
    }

    public Integer getHigh() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String data = sharedPreferences.getString("high", "0");
        int hi = Integer.parseInt(data);
        return hi;
    }

    public void disableClick() {
        for (ImageView img : imgs) {
            img.setEnabled(false);
        }
    }

    public void enableClick() {
        for (ImageView img : imgs) {
            img.setEnabled(true);
        }
    }
}
