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

public class FamilyActivity extends AppCompatActivity {

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

        final ArrayList<Word> Words = new ArrayList<Word>();
        Words.add(new Word("Father", "père", R.drawable.family_father, R.raw.father));
        Words.add(new Word("Mother", "Mère", R.drawable.family_mother, R.raw.mother));
        Words.add(new Word("Son", "fils", R.drawable.family_son, R.raw.son));
        Words.add(new Word("Daughter", "fille", R.drawable.family_daughter, R.raw.daughter));
        Words.add(new Word("Brother", "frère", R.drawable.family_older_brother, R.raw.brother));
        Words.add(new Word("Sister", "sœur", R.drawable.family_older_sister, R.raw.sister));
        Words.add(new Word("Uncle", "oncle", R.drawable.family_younger_brother, R.raw.uncle));
        Words.add(new Word("Aunt", "tante", R.drawable.family_younger_sister, R.raw.aunt));
        Words.add(new Word("Grandmother", "grand-mère", R.drawable.family_grandmother, R.raw.grandmother));
        Words.add(new Word("Grandfather", "grand-père", R.drawable.family_grandfather, R.raw.grandfather));

        WordAdapter adapter = new WordAdapter(this, Words, R.color.category_family);
        //Log.i("Contcddcdcdcext : ", "getCcdccdontext: "+R.color.category_family);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word = Words.get(i);
                releaase();
                int result = audioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    int audioId = word.getAudioResourceId();

                    mediaPlayer = MediaPlayer.create(FamilyActivity.this, audioId);
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
