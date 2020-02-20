# COMP 208, Assignment 1

>Create a Hangman application that meets the following requirements:

## Summary of Basic Requirements

1. Follow the examples given in Labs 1-6.
2. The application allows the user to play one or more games of hangman.
3. Each game, the application selects a random word from an SQLite database.
3. The application keeps score.  I.e. records the number of wins and losses.  The score is stored on the device so that the next time the user runs the application, the score is continued. (Week 6).
4. After each game, the score is updated, and there is a two second delay before the next game starts.   (Week 6)

## User Interface Requirements   

6. The application icon displays a hangman image.
7. The game displays a sequence of seven or eight hangman images. Each time in incorrect letter is guessed, the next image is displayed.
7. The user interface shows a list of letters that have been guessed.   The list is displayed in alphabetical order. 

## Database Requirements
9. The SQLite database is populated from a text file containing a list of words (one word per line).  
10. The text file is stored on the device’s SD card.  (Requires permission to read from external storage).

## Design Requirements
1. The application uses (at least) the following classes: 
  - MainActivity – main program logic (controller)
  - Database Contract – to provide String values for tables and columns, etc.
  - OpenHelper – to manage database creation & upgrade
  - DAO – to perform database operations
  - Entity – to represent data in database.

Due Date
Wednesday February 21st .  

  
