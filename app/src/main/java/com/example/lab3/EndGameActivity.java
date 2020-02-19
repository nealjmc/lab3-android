package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class EndGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        displayMessage();
    }

    private void displayMessage() {
        String word = getIntent().getExtras().getString("word");
        boolean didWin= getIntent().getExtras().getBoolean("didWin");

        TextView txtWinOrLose = (TextView) findViewById(R.id.txtWinOrLose);
        TextView txtWordWas = (TextView) findViewById(R.id.txtWord);
        String winOrLoseMessage = determineWinOrLostMessage(didWin);

        txtWinOrLose.setText(winOrLoseMessage);
        txtWordWas.setText("Word was: "+ word);
        showWinningAndLosingScores();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                playAgain();
            }
        }, 3000);

    }

    private void showWinningAndLosingScores() {
        TextView scores = (TextView) findViewById(R.id.txtScores);
        int gamesWon;
        int gamesLost;
        SharedPreferences preferences = this.getSharedPreferences(getString(R.string.sharedPrefsGameName), MODE_PRIVATE);
        gamesWon = preferences.getInt(getString(R.string.sharedPrefsGamesWon), 0);
        gamesLost = preferences.getInt(getString(R.string.sharedPrefsGamesLost), 0);
        String displayText = String.format("Won: %d, Lost: %d", gamesWon, gamesLost);
        scores.setText(displayText);
    }

    private String determineWinOrLostMessage(boolean didWin){
        if(didWin){
            return "You won!";
        }
        else{
            return "You Lose!";
        }
    }

    public void playAgain(){
        Intent newGameintent = new Intent(this, GameActivity.class);
        startActivity(newGameintent);
    }
}
