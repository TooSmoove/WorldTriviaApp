package com.example.toosmoove.worldtriviaapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Toosmoove on 1/23/2016.
 */
public class Question implements Parcelable
{

    private ArrayList<Question> questions;

    String questionText, questionAnswer, imageUrl;
    int questionId;
    String[] choices = { "", "", "", "", ""};

    public Question()
    {
        questionText = null;
        questionAnswer = null;
        imageUrl = null;
    }

    protected Question(Parcel in) {
        questionText = in.readString();
        questionAnswer = in.readString();
        imageUrl = in.readString();
        questionId = in.readInt();
        choices = in.createStringArray();
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }


    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public void setQuestionText (String question)
    {
        questionText = question;
    }

    public void setQuestionAnswer (String answer)
    {
        questionAnswer = answer;
    }

    public void setImageUrl (String url)
    {
        imageUrl = url;
    }

    public void setId (int id)
    {
        questionId = id;
    }

    public void setChoice (int index, String choice)
    {
        choices[index] = choice;
    }

    public void clearChoices()
    {
        for(int z = 0; z < choices.length; z++)
        {
            choices[z] = "";
        }
    }

    public String getQuestionText ()
    {
        return questionText;
    }

    public String getQuestionAnswer ()
    {
        return questionAnswer;
    }

    public String getImageUrl ()
    {
        return imageUrl;
    }

    public int getId ()
    {
        return questionId;
    }

    public String[] getChoices ()
    {
        return choices;
    }

    public int getChoiceCount()
    {
        int choiceCount = 0;

        for(int y = 0; y < choices.length; y++)
        {
            if(!choices[y].equals(""))
            {
                choiceCount++;
            }
        }
        return choiceCount;
    }

    @Override
    public String toString()
    {
        String part1 = "[Question=" + questionText + ", Answer=" + questionAnswer + ", id=" + questionId
                + ", Image URL=" + imageUrl + "\n";
        String part2 = "choices: ";

        for(int y = 0; y < choices.length; y++)
        {
            if(!choices[y].equals(""))
            {
                part2 = part2 + (y + 1) + ") " + choices[y] + " ";
            }
        }

        String result = part1 + part2;

        return  result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //Log.v(TAG, "writeToParcel..." + flags);
        dest.writeString(questionText);
        dest.writeString(questionAnswer);
        dest.writeString(imageUrl);
        dest.writeInt(questionId);
        dest.writeStringArray(choices);
    }
}

