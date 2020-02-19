package com.example.lab3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.util.Log;

import java.util.ArrayList;

public class WordsDataSource {
    private SQLiteDatabase db;
    private WordDbhelper dbHelper;
    private String TAG = WordsDataSource.class.getSimpleName();

    public WordsDataSource(Context context) {
        dbHelper = new WordDbhelper(context);
    }

    public void openDb() {
        db = dbHelper.getWritableDatabase();
        Log.d(TAG, "Db Opened");
    }

    public void closeDb() {
        db.close();
        Log.d(TAG, "Db Closed");

    }

    public void insertWord(String newWord) {
        ContentValues values = new ContentValues();
        values.put(WordContract.Words.COLUMN_NAME_WORD, newWord);
        Log.d(TAG, "Db: Insert word " + newWord);
        db.insert(WordContract.Words.TABLE_NAME, null, values);
    }

    public void removeWord(String wordToRemove) {
        // Define 'where' part of query.
        String selection = WordContract.Words.TABLE_NAME + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {wordToRemove};
        // Issue SQL statement.
        Log.d(TAG, "Db: remove word " + wordToRemove);

        int deletedRows = db.delete(WordContract.Words.TABLE_NAME, selection, selectionArgs);
    }

    public ArrayList<WordEntity> getAllWords() {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                WordContract.Words._ID,
                WordContract.Words.COLUMN_NAME_WORD
        };

        Cursor cursor = db.query(WordContract.Words.TABLE_NAME, projection, null, null, null, null, null, null);

        ArrayList<WordEntity> wordList = new ArrayList<>();
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndexOrThrow("word"));
            if(name.length() > 0){
                WordEntity newWordFromDb = new WordEntity();
                newWordFromDb.setId(cursor.getLong(cursor.getColumnIndexOrThrow(WordContract.Words._ID)));
                newWordFromDb.setWord(cursor.getString(cursor.getColumnIndexOrThrow(WordContract.Words.COLUMN_NAME_WORD)));
                wordList.add(newWordFromDb);
            }
        }

        cursor.close();
        return wordList;
    }
}
