package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;

public class ColorsActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mediaPlayer.pause();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN)
                mediaPlayer.start();
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS)
                releaase();

        }
    };
    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {


        @Override
        public void onCompletion(MediaPlayer mp) {
            releaase();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> Words = new ArrayList<Word>();
        Words.add(new Word("Red", "rouge", R.drawable.color_red, R.raw.red));
        Words.add(new Word("Green", "vert", R.drawable.color_green, R.raw.green));
        Words.add(new Word("Yellow", "jaune", R.drawable.color_mustard_yellow, R.raw.yellow));
        Words.add(new Word("blue", "bleu", R.drawable.color_blue, R.raw.blue));
        Words.add(new Word("Black", "noir", R.drawable.color_black, R.raw.black));
        Words.add(new Word("White", "blanc", R.drawable.color_white, R.raw.white));
        Words.add(new Word("Pink", "rose", R.drawable.color_pink, R.raw.pink));
        Words.add(new Word("Orange", "Orange", R.drawable.color_orange, R.raw.orange));
        Words.add(new Word("Grey", "gris", R.drawable.color_gray, R.raw.grey));
        Words.add(new Word("Brown", "marron", R.drawable.color_brown, R.raw.brown));

        WordAdapter adapter = new WordAdapter(this, Words, R.color.category_colors);
        //Log.i("Contcddcdcdcext : ", "getCcdccdontext: "+R.color.category_colors);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = Words.get(position);
                releaase();
                int result = audioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    int audioId = word.getAudioResourceId();

                    mediaPlayer = MediaPlayer.create(ColorsActivity.this, audioId);
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(completionListener);
                }

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaase();
    }

    public void releaase() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(afChangeListener);
        }
    }
}
