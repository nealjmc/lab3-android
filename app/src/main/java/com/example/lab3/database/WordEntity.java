package com.example.lab3.database;

public class WordEntity {
    private Long Id;
    private String word;

    public Long getId() {
        return Id;
    }

    void setId(Long id) {
        Id = id;
    }

    public String getWord() {
        return word;
    }

    void setWord(String word) {
        this.word = word;
    }
}
