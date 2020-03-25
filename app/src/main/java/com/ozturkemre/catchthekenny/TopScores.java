package com.ozturkemre.catchthekenny;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

public class TopScores extends AppCompatActivity {

    TextView scores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_scores);
        scores =findViewById(R.id.scoreView);
        loadScores();

    }
    public void loadScores() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String data = sharedPreferences.getString("high", "0");
        System.out.println(data);
        scores.setText(data);
    }

    public void changeToGame(View view){
        Intent intent = new Intent(TopScores.this, MainActivity.class);
        startActivity(intent);
    }
}