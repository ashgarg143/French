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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class NumbersActivity extends AppCompatActivity {

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
        words.add(new Word("one", "un", R.drawable.number_one, R.raw.one));
        words.add(new Word("two", "deux", R.drawable.number_two, R.raw.two));
        words.add(new Word("three", "Trois", R.drawable.number_three, R.raw.three));
        words.add(new Word("four", "quatre", R.drawable.number_four, R.raw.four));
        words.add(new Word("five", "cinq", R.drawable.number_five, R.raw.five));
        words.add(new Word("six", "six", R.drawable.number_six, R.raw.six));
        words.add(new Word("seven", "Sept", R.drawable.number_seven, R.raw.seven));
        words.add(new Word("eight", "huit", R.drawable.number_eight, R.raw.eight));
        words.add(new Word("nine", "neuf", R.drawable.number_nine, R.raw.nine));
        words.add(new Word("ten", "Dix", R.drawable.number_ten, R.raw.ten));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);
        // Log.i("Contcddcdcdcext : ", "getCcdccdontext: "+R.color.category_numbers);

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
                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, audioId);
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



