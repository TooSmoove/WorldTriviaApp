package com.example.toosmoove.worldtriviaapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity {

    ProgressBar loading;
    TextView question;
    RadioGroup answerChoices;
    RadioButton[] choices = new RadioButton[5];
    RadioButton choice, choice2, choice3, choice4, choice5, choosen;
    ImageView triviaImage;
    Button back, next, exit;
    ArrayList<Question> questions;
    int currentQuestion = 0, choiceIndex = 0, choiceCount, correct = 0, selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        questions = this.getIntent().getParcelableArrayListExtra("questionList");

        answerChoices = (RadioGroup) findViewById(R.id.answerGroup);

        back = (Button)findViewById(R.id.back);
        back.setText("Back");
        back.setEnabled(false);

        exit = (Button)findViewById(R.id.exit);
        exit.setText("Exit");

        next = (Button)findViewById(R.id.next);
        next.setText("Next");

        question = (TextView) findViewById(R.id.questionText);
        loading = (ProgressBar)findViewById(R.id.imageLoad);

        choice = new RadioButton(this);
        choice2 = new RadioButton(this);
        choice3 = new RadioButton(this);
        choice4 = new RadioButton(this);
        choice5 = new RadioButton(this);

        choices[0] = choice;
        choices[1] = choice2;
        choices[2] = choice3;
        choices[3] = choice4;
        choices[4] = choice5;

        triviaImage = (ImageView)findViewById(R.id.triviaImage);
        triviaImage.setVisibility(View.INVISIBLE);

            new ImageDownloader().execute(questions.get(currentQuestion).getImageUrl());

            choiceCount = questions.get(currentQuestion).getChoiceCount();

            question.setText(questions.get(0).getQuestionText());

            while(choiceIndex < choiceCount)
            {
                choices[choiceIndex].setText(questions.get(currentQuestion).choices[choiceIndex]);
                answerChoices.addView(choices[choiceIndex], choiceIndex);
                choiceIndex++;
            }

        next.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                selected = answerChoices.getCheckedRadioButtonId();
                choosen = (RadioButton)findViewById(selected);
                String chosenText = (String) choosen.getText();
                //System.out.println("Chosen Answer:" + chosenText);

                if(chosenText.equals(questions.get(currentQuestion).getQuestionAnswer()))
                {
                    correct+=1;
                }

                //System.out.println("Correct:" + correct);

                //if()

                if(currentQuestion == 15)
                  {
                    Intent i = new Intent(getBaseContext(), TriviaStats.class);
					i.putExtra("correct", correct);
					startActivity(i);
                  }
                  else
                  {
                      currentQuestion+=1;
                      choiceIndex = 0;
                      triviaImage.setVisibility(View.INVISIBLE);
                      loading.setVisibility(View.VISIBLE);
                      answerChoices.removeAllViews();

                      new ImageDownloader().execute(questions.get(currentQuestion).getImageUrl());

                      choiceCount = questions.get(currentQuestion).getChoiceCount();

                      question.setText(questions.get(currentQuestion).getQuestionText());

                      while(choiceIndex < choiceCount)
                      {
                          choices[choiceIndex].setText(questions.get(currentQuestion).choices[choiceIndex]);
                          answerChoices.addView(choices[choiceIndex], choiceIndex);
                          choiceIndex++;
                      }
                  }

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }
        });



    }


    private class ImageDownloader extends AsyncTask<String, Integer, Bitmap>
    {

        @Override
        protected Bitmap doInBackground(String... params)
        {
            String imageUrl = params[0];

            if(imageUrl == null)
            {
                imageUrl = "http://www.clipartbest.com/cliparts/9T4/nAe/9T4nAebTE.png";
            }

            InputStream input = null;
            Bitmap bitmap = null;

            try
            {
                URL url = new URL(imageUrl);

                // We open the connection
                URLConnection conection = url.openConnection();
                conection.connect();
                input = new BufferedInputStream(url.openStream(), 8192);

                // we convert the inputStream into bitmap
                bitmap = BitmapFactory.decodeStream(input);
                Bitmap.createScaledBitmap(bitmap, 100, 100, false);
                input.close();

            }

            catch (Exception e)
            {
                //Log.e("Error: ", e.getMessage());
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            loading.setProgress(100);
            loading.setVisibility(View.INVISIBLE);
            triviaImage.setImageBitmap(result);
            triviaImage.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();
            loading.setProgress(25);

        }

    }


}
