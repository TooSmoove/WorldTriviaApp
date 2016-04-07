package com.example.toosmoove.worldtriviaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TriviaStats extends AppCompatActivity {

    ProgressBar stats;
    Button tryAgain, exit;
    TextView quizResults, percent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_stats);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent mIntent = getIntent();
        int right = mIntent.getIntExtra("correct", 0);
        float percentage = (float) ((float) (right/16.0)*100.0);
        System.out.println("percentage:" + percentage);
        System.out.println("Correct:" + right);
        int progress = (int) percentage;
        System.out.println("Progress:" + progress);

        stats = (ProgressBar)findViewById(R.id.statBar);
        stats.setProgress(progress);

        percent = (TextView)findViewById(R.id.percent);
        percent.setText(percentage + "%");

        quizResults = (TextView)findViewById(R.id.statMessage);
        quizResults.setText("You scored: " + percentage + "%! \n Click the 'Try Again' button to try again. ");

        tryAgain = (Button)findViewById(R.id.tryAgain);
        tryAgain.setText("Try\nAgain?");

        tryAgain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }
        });

        exit = (Button)findViewById(R.id.exit);
        exit.setText("Exit");
        exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

    }

}
