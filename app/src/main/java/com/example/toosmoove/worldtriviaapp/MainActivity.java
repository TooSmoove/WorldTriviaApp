package com.example.toosmoove.worldtriviaapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String questionsUrl = "http://liisp.uncc.edu/~mshehab/api/trivia.xml";
    TextView title, message;
    ProgressBar progressBar1;
    Button exitButton, start;
    ImageView image1;
    public static ArrayList<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = (TextView)findViewById(R.id.textView1);
        title.setText("Welcome to Trivia App");

        message = (TextView)findViewById(R.id.textView2);
        message.setText("Loading Trivia");

        //Trivia Image
        image1 = (ImageView)findViewById(R.id.imageView1);
        image1.setImageDrawable(null);
        image1.setVisibility(View.INVISIBLE);

        //Progress Bar for loading trivia
        progressBar1 = (ProgressBar)findViewById(R.id.progressBar1);
        progressBar1.setVisibility(View.VISIBLE);
        progressBar1.setProgress(0);


        exitButton = (Button)findViewById(R.id.button1);
        exitButton.setText("Exit");
        exitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });


        start = (Button)findViewById(R.id.button2);
        start.setText("Start Trivia");
        start.setEnabled(false);

        questionList = new  ArrayList<Question>();

         new AsyncQuestionsGet().execute(questionsUrl);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public class AsyncQuestionsGet extends AsyncTask<String, Void, ArrayList<Question>> {

        @Override
        protected ArrayList<Question> doInBackground(String... params) {
            String urlString = params[0];
            try
            {
                URL url = new URL(urlString);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                int statusCode = con.getResponseCode();
                if (statusCode == HttpURLConnection.HTTP_OK)
                {
                    InputStream in = con.getInputStream();
                    ArrayList<Question> Questions = QuestionUtil.QuestionsXMLPullParser.parseQuestions(in);
                    return Questions;
                }

            }
            catch (MalformedURLException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (NumberFormatException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (XmlPullParserException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            //image1.setVisibility(View.GONE);
            super.onPreExecute();
            progressBar1.setProgress(25);
        }

        @Override
        protected void onPostExecute(ArrayList<Question> result) {
            super.onPostExecute(result);
            progressBar1.setProgress(100);
            message.setText("Trivia Ready");
            progressBar1.setVisibility(View.GONE);
            start.setEnabled(true);
            image1.setImageResource(R.drawable.trivialoaded);
            image1.setVisibility(View.VISIBLE);
            questionList.addAll(result);

            start.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(getBaseContext(), TriviaActivity.class);
                    //Bundle b = new Bundle();
                    i.putParcelableArrayListExtra("questionList", questionList);
                    startActivity(i);
                }
            });

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
