package com.example.android.miwok;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;

public class PhrasesActivity extends AppCompatActivity {
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
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Hello", "Bonjour", R.raw.hello));
        words.add(new Word("What is your name?", "Comment vous appelez-vous?", R.raw.what_is_your_name));
        words.add(new Word("Thank You", "Merci", R.raw.thank_you));
        words.add(new Word("Excuse Me", "Excusez-moi", R.raw.excuse_me));
        words.add(new Word("I love you", "je t'aime", R.raw.i_love_you));
        words.add(new Word("Fuck you", "baise toi", R.raw.fuck_you));
        words.add(new Word("Do you Speak English", "Parlez vous anglais?", R.raw.do_you_speak_english));
        words.add(new Word("How are you?", "Comment allez-vous?", R.raw.how_are_you));
        words.add(new Word("I am fine", "je vais bien", R.raw.i_am_fine));
        words.add(new Word("GoodBye", "Au revoir", R.raw.goodbye));


        WordAdapter adapter = new WordAdapter(this, words, R.color.category_phrases);
        //Log.i("Contcddcdcdcext : ", "getCcdccdontext: "+R.color.category_phrases);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word = words.get(i);
                releaase();
                int result = audioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    int audioId = word.getAudioResourceId();

                    mediaPlayer = MediaPlayer.create(PhrasesActivity.this, audioId);
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
