/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {

	private HangmanCanvas canvas;

	private HangmanLexicon listOfWords;

	private RandomGenerator rgen = RandomGenerator.getInstance();

	private String word = chooseWord();

	private String dashWord;

	private String wrongGuesses = "";

	private int counter = 8;

	private char guess;
	

	public void init(){

		canvas = new HangmanCanvas();
		add(canvas);
	}

	public void run() {

		initializeHangman();
		playHangman();
	}

	/*
	 * Chooses a random word from the Hangman Lexicon class.
	 */	
	private String chooseWord(){

		listOfWords= new HangmanLexicon();
		int random=rgen.nextInt(0 , (listOfWords.getWordCount()-1));
		String chosenWord=listOfWords.getWord(random);
		return chosenWord;
	}

	/*
	 * Initializes the game. Clears the canvas and displays messages for the user.
	 */
	private void initializeHangman(){

		canvas.reset();

		dashWord = addDashes();
		canvas.displayWord (dashWord) ;

		println("Welcome to Hangman!");
		println("The word now looks like this:" + dashWord);
		println("You have " +counter+ " turns left.");
	}

	/*
	 * Displays the word as dashes.
	 */
	private String addDashes(){

		String dashString = "";

		for(int i = 0; i < word.length(); i++){
			dashString += "-";
		}
		return dashString;
	}

	/*
	 * Defines the method to play the game of hangman.
	 */
	private void playHangman(){

		while(counter>0){
			String getChar = readLine("Your guess: ");

			while (true){
				if (getChar.length() > 1){ //If the user enters more than one letter at a time.
					println("Please enter only one letter at a time");//display error message.
					getChar = readLine("Your guess: ");
				}
				if (getChar.length() == 1 ) break;
			}

			guess=getChar.charAt(0);

			if(Character.isLowerCase(guess)){
				guess = Character.toUpperCase(guess);//changed the letter entered to upper case
			}

			checkGuess(); 

			if (dashWord.equals(word)){
				gameWon();
				break;
			}
			println("The word now looks like this: " + dashWord);
			println("You have " +counter+ " turns left.");
		}

		if (counter == 0){
			gameLost();
		}

	}

	/*
	 * Defines the method to check the guess with the actual word.
	 */
	private void checkGuess(){

		if(word.indexOf(guess) == -1) { //word not found
			println("There are no " + guess + "'s in the word");
			counter--;
			wrongGuesses += guess;
			canvas.noteIncorrectGuess(wrongGuesses); 
		}

		if(word.indexOf(guess) != -1) { //word found.
			println("The guess is correct.");
		}

		for(int i = 0; i < word.length(); i++) { // if guess is correct, display guess instead of dash.
			if (guess == word.charAt(i)) {
				if (i > 0) {
					dashWord = dashWord.substring(0, i) + guess + dashWord.substring(i + 1);
				}
				if(i == 0) {
					dashWord = guess + dashWord.substring(1);
				}

			}
		}
		canvas.displayWord (dashWord) ;//display guess instead of dash on the canvas.
	}

	/*
	 * Defines the method for game won.
	 */
	private void gameWon(){ 
		println("You guessed the word:" + word);
		println("You win!!!");
		canvas.displayGameWon();
	}

	/*
	 * Defines the method for game lost.
	 */
	private void gameLost(){
		println("You are completely hung.");
		println("The word was:" + word);
		println("You lose.");
		canvas.displayWord(word);
	}
}

