package com.example.lab3.game;

import android.content.Context;

import com.example.lab3.database.WordsDataSource;

import java.util.ArrayList;
import java.util.Random;

public class Hangman {
    private final WordsDataSource database;

    public Hangman(Context context){
        this.database = new WordsDataSource(context);
        this.database.openDb();
    }

    public String getRandomWord(){
        Random rnadomGenerator = new Random();
        ArrayList<String> wordList = this.database.getAllWords();
        int randomIndex = rnadomGenerator.nextInt(wordList.size());
        return wordList.get(randomIndex);
    }
}
