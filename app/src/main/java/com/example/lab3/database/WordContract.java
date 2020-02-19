package com.example.lab3.database;

import android.provider.BaseColumns;

public final class WordContract {

    /**
     * Constructor
     */
    private WordContract(){}

    public static class Words implements BaseColumns{
        public static final String TABLE_NAME = "words";
        public static final String COLUMN_NAME_WORD = "word";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Words.TABLE_NAME + " (" +
                    Words._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Words.COLUMN_NAME_WORD + " TEXT NOT NULL)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Words.TABLE_NAME;
}
