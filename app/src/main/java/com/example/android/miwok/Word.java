package com.example.android.miwok;

/**
 * Created by Ashish Garg on 02-02-2018.
 */

public class Word {
    private String defaultTranslation;
    private String frenchTranslation;
    private int imageResourceId = -1;
    private int audioResourceId;


    public Word(String defaultWord, String frenchWord, int imageId, int audioId) {
        defaultTranslation = defaultWord;
        frenchTranslation = frenchWord;
        imageResourceId = imageId;
        audioResourceId = audioId;
    }

    public Word(String defaultWord, String frenchWord, int audioId) {
        defaultTranslation = defaultWord;
        frenchTranslation = frenchWord;
        audioResourceId = audioId;
    }

    public String getDefaultTranslation() {
        return defaultTranslation;
    }

    public String getFrenchTranslation() {
        return frenchTranslation;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public int getAudioResourceId() {
        return audioResourceId;
    }

    public boolean hasImage() {
        return imageResourceId != -1;
    }

    @Override
    public String toString() {
        return "Word{" +
                "defaultTranslation='" + defaultTranslation + '\'' +
                ", frenchTranslation='" + frenchTranslation + '\'' +
                ", imageResourceId=" + imageResourceId +
                ", audioResourceId=" + audioResourceId +
                '}';
    }
}