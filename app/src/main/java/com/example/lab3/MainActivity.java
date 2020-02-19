package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.lab3.database.WordContract;
import com.example.lab3.database.WordsDataSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check for first time
        if(isFirstTime()){
            addWordsToDb();
        }
    }

    public  void btnStartHandler(View view){
        Intent newIntent = new Intent(this, GameActivity.class);
        startActivity(newIntent);
    }

    /*
    * Determine if users first time playing the game based on user prefs
    * */
    public boolean isFirstTime(){
        // Get user Prefs
        SharedPreferences preferences = this.getSharedPreferences(getString(R.string.sharedPrefsGameName), MODE_PRIVATE);
        String hasPlayedKey = "hasPlayed";
        boolean isUsersFirstTime = preferences.getBoolean(hasPlayedKey, true);
        if(isUsersFirstTime == true){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(hasPlayedKey, false);
            editor.apply();
        }
        return isUsersFirstTime;
    }

    /*
    * Retrieve words from words file and add to databsae
    * */
    public void addWordsToDb(){
        ArrayList<String> wordsList = readFile(getString(R.string.wordsFileName));
        WordsDataSource database = new WordsDataSource(this);
        database.openDb();
        for(String s : wordsList){
            database.insertWord(s);
        }
        database.closeDb();
    }

    /*
    * Read file and return list of words from file
    * */
    private ArrayList<String> readFile(String filename) {
        String path = "/sdcard/Files/" + filename;
        ArrayList<String> returnWords = new ArrayList<>();

        try {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferReader = new BufferedReader(fileReader);
            String word;
            //Read all words
            while ((word = bufferReader.readLine()) != null) {
                returnWords.add(word);
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            Log.d("RIP", e.getMessage());
        }
        return returnWords;
    }
}
