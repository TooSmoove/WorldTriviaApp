package com.example.toosmoove.worldtriviaapp;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Toosmoove on 1/23/2016.
 */
public class QuestionUtil {

    static int x;
    //static Question question;

    static public class QuestionsXMLPullParser {
        static ArrayList<Question> parseQuestions(InputStream xmlIn) throws XmlPullParserException, NumberFormatException, IOException
        {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(xmlIn, "UTF-8");
            ArrayList<Question> Questions = null;
            //Question question = new Question();
            Question question = null;
            int event = parser.getEventType();

            while(event != XmlPullParser.END_DOCUMENT)
            {
                switch (event)
                {
                    case XmlPullParser.START_DOCUMENT:
                        Questions = new ArrayList<Question>();
                        break;
                    case XmlPullParser.START_TAG:
                        if(parser.getName().equals("question"))
                        {
                            question = new Question();
                            question.clearChoices();
                            x = 0;
                            question.setId(Integer.parseInt(parser.getAttributeValue(null, "id").trim()));
                        }
                        else if(parser.getName().equals("text"))
                        {
                            question.setQuestionText(parser.nextText().trim());
                        }
                        else if(parser.getName().equals("image"))
                        {
                            question.setImageUrl(parser.nextText().trim());
                        }
                        else if(parser.getName().equals("choice"))
                        {

                            if(String.valueOf((parser.getAttributeValue(null, "answer"))).equals("true"))
                            {
                                String text = parser.nextText().trim();
                                question.setQuestionAnswer(text);
                                question.setChoice(x, text);
                            }
                            else
                            {
                                String text1 = parser.nextText().trim();
                                question.setChoice(x, text1);
                            }
                            x++;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("question"))
                        {
                            Questions.add(question);
                        }
                    default:
                        break;
                }
                event = parser.next();
            }

            return Questions;
        }
    }

}

