package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.provider.CalendarContract;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ashis on 14-03-2018.
 */

public class WordAdapter extends ArrayAdapter<Word> {

 private int colorResourceID;
    public WordAdapter(Activity context, ArrayList<Word> Words, int color){

        super(context,0,Words);
        colorResourceID=color;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView =convertView;
        if(listItemView==null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }


        Word currentWord=getItem(position);


        TextView textView1=listItemView.findViewById(R.id.french_text_view);
        textView1.setText(currentWord.getFrenchTranslation());

        TextView textView2=listItemView.findViewById(R.id.default_text_view);
        textView2.setText(currentWord.getDefaultTranslation());

        ImageView imageview=listItemView.findViewById(R.id.image_view);

        if (currentWord.hasImage())
            imageview.setImageResource(currentWord.getImageResourceId());

        else
            imageview.setVisibility(View.GONE);

        View textContainer=listItemView.findViewById(R.id.color);
        int color= ContextCompat.getColor(getContext(),colorResourceID);
       // Log.i("Context : ", "getContext: "+colorResourceID);
        textContainer.setBackgroundColor(color);



        return listItemView;
    }



}
