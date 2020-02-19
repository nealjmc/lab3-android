package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab3.game.Hangman;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class GameActivity extends AppCompatActivity {
    Hangman hangmanGame;
    String word;
    String hiddenWord = "__________";
    ImageView imageView;
    TextView txtHiddenWord;
    EditText txtInput;
    int step = 0;
    int[] handmanImages = {
            R.drawable.base,
            R.drawable.head,
            R.drawable.body,
            R.drawable.left_leg,
            R.drawable.right_leg,
            R.drawable.left_arm,
            R.drawable.right_arm
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.hangmanGame = new Hangman(this);

        // Get word and create hidden word
        word = this.hangmanGame.getRandomWord().toLowerCase();
        hiddenWord = createHiddenWordFromWord(word);

        // Get inputs
        txtInput = findViewById(R.id.inputGuess);
        txtHiddenWord = findViewById(R.id.txtHiddenWord);

        txtHiddenWord.setText(addSpaceBetweenLetters(createHiddenWordFromWord(word)));

        // Key listener
        txtInput.setKeyListener(new KeyListener() {
            @Override
            public int getInputType() {
                return 1;
            }

            @Override
            public boolean onKeyDown(View view, Editable text, int keyCode, KeyEvent event) {
                return false;
            }

            @Override
            public boolean onKeyUp(View view, Editable text, int keyCode, KeyEvent event) {
                int inputKeyCode = event.getUnicodeChar();
                boolean didFindLetter = false;
                String foundLetterString = String.valueOf((char) inputKeyCode).toLowerCase();
                String organizedList = alphabetizeGuessedLetters(txtInput.getText().toString(), foundLetterString);
                txtInput.setText(organizedList);


                for (int index = 0; index < word.length(); index++) {
                    if (word.charAt(index) == foundLetterString.charAt(0)) {
                        didFindLetter = true;
                    }
                }

                if (didFindLetter) {
                    handleFoundLetter(foundLetterString);
                } else {
                    handleLetterNotFound();
                }
                return false;
            }

            @Override
            public boolean onKeyOther(View view, Editable text, KeyEvent event) {
                return false;
            }

            @Override
            public void clearMetaKeyState(View view, Editable content, int states) {

            }
        });

        // find image view
        imageView = findViewById(R.id.hangmanImageView);
        imageView.setImageDrawable(ContextCompat.getDrawable(GameActivity.this, handmanImages[step]));

    }

    private String alphabetizeGuessedLetters(String guessedLetters, String newLetter) {
        String stringToSort = guessedLetters + newLetter;
        char[] arrayToSort = stringToSort.toCharArray();
        Arrays.sort(arrayToSort);

        String stringToDisplay = "";
        for (char c : arrayToSort) {
            stringToDisplay += c;
        }
        return stringToDisplay;
    }

    /**
     * Check if game is lost of won
     */
    public void handleLetterNotFound() {
        if(checkForLoss()){
            addToLosingScores();
            sendToEndGameScreen(false);
        }
        incrementHangmanPicture();
    }

    /**
     * Send to end game screen
     * @param didWin
     */
    public void sendToEndGameScreen(boolean didWin) {
        Intent endGameIntent = new Intent(this, EndGameActivity.class);
        endGameIntent.putExtra("word", word);
        endGameIntent.putExtra("didWin", didWin);
        startActivity(endGameIntent);
    }

    /**
     * Check if letter is in word and replace hidden letters with actual letters
     * @param foundLetter
     */
    public void handleFoundLetter(String foundLetter) {
        char[] hiddenWordCharArray = hiddenWord.toCharArray();
        for (int index = 0; index < word.length(); index++) {
            if (foundLetter.charAt(0) == word.charAt(index)) {
                hiddenWordCharArray[index] = foundLetter.charAt(0);
            }
        }
        hiddenWord = String.valueOf(hiddenWordCharArray);
        String stringValueOfHiddenCharArray = String.valueOf(hiddenWordCharArray);
        String stringToDisplay = addSpaceBetweenLetters(stringValueOfHiddenCharArray);
        txtHiddenWord.setText(stringToDisplay);
        if(checkForWin()){
            addToWiningScores();
            sendToEndGameScreen(true);
        }
    }

    /**
     * Add to winning scores
     * */
    private void addToWiningScores() {
        SharedPreferences preferences = this.getSharedPreferences(getString(R.string.sharedPrefsGameName), MODE_PRIVATE);
        int gamesWon = preferences.getInt(getString(R.string.sharedPrefsGamesWon), 0);
        gamesWon+=1;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(getString(R.string.sharedPrefsGamesWon), gamesWon);
        editor.apply();
    }

    /**
     * Add to losing scores
     * */
    private void addToLosingScores() {
        SharedPreferences preferences = this.getSharedPreferences(getString(R.string.sharedPrefsGameName), MODE_PRIVATE);
        int gamesLost = preferences.getInt(getString(R.string.sharedPrefsGamesLost), 0);
        gamesLost+=1;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(getString(R.string.sharedPrefsGamesLost), gamesLost);
        editor.apply();
    }


    /**
     * Check if Step is equal to set of hangmanImages
     * @return
     */
    public boolean checkForLoss() {
        return step == handmanImages.length - 1;
    }

    public boolean checkForWin(){
        String hiddenWordToCompare = hiddenWord.toLowerCase().trim().replaceAll(" ", "");
        String wordToCompare = word.toLowerCase().trim();

        return hiddenWordToCompare.equals(wordToCompare);

    }

    /**
     * Create underscores based on length of wor
     * @param newWord word to transform
     * @return hidden ord
     */
    public String createHiddenWordFromWord(String newWord) {
        String newHiddenWord = "";
        for (int index = 0; index < newWord.length(); index++) {
            newHiddenWord += "_";
        }
        return newHiddenWord;
    }

    /**
     * Add space between string passed to it
     * @param wordToChange word to change into spaced letters
     * @return changed string
     */
    public String addSpaceBetweenLetters(String wordToChange){
        return wordToChange.replace("", " ").trim();
    }

    /**
     * Increment images shown for hangman
     */
    public void incrementHangmanPicture() {
        if (step < handmanImages.length - 1) {
            step++;
            imageView.setImageDrawable(ContextCompat.getDrawable(GameActivity.this, handmanImages[step]));
        }
    }



}
