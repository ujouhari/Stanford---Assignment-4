/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.awt.Color;

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

	/*Constants to add random space to display the words.*/
	private static final int RANDOM_SPACE_1 = 60; 
	private static final int RANDOM_SPACE_2 = 30; 


	/** Resets the display so that only the scaffold appears */
	public void reset() {

		removeAll();

		drawScaffold();
		drawBeam();
		drawRope();
	}

	/*Defines a common method to draw a line on screen
	 * Since most the Hangman is a stick figure, this method can be reused multiple times.
	 */
	private GLine drawLine( double x1, double y1 , double x2 , double y2){

		GLine line = new GLine ( x1 , y1 , x2 , y2);
		add(line);
		return(line);
	}

	/*
	 * Defines method to draw the Scaffold.
	 */
	private void drawScaffold(){

		double scaffoldX1 = getWidth()/2 - UPPER_ARM_LENGTH;
		double scaffoldX2 = scaffoldX1;
		double scaffoldY1 = getHeight()/2 - BODY_LENGTH - HEAD_RADIUS*2 - ROPE_LENGTH;
		double scaffoldY2 = scaffoldY1 + SCAFFOLD_HEIGHT;
		drawLine ( scaffoldX1, scaffoldY1, scaffoldX2, scaffoldY2);

	}

	/*
	 * Defines the method to draw the beam.
	 */
	private void drawBeam(){

		double beamX1 = getWidth()/2 - UPPER_ARM_LENGTH;
		double beamX2 = beamX1 + BEAM_LENGTH; 
		double beamY1 = getHeight()/2 - BODY_LENGTH - HEAD_RADIUS*2 - ROPE_LENGTH;
		double beamY2 = beamY1;
		drawLine ( beamX1, beamY1, beamX2, beamY2);
	}

	/*
	 * Defines the method to draw the rope.
	 */
	private void drawRope(){

		double ropeX1 = getWidth()/2 - UPPER_ARM_LENGTH + BEAM_LENGTH;
		double ropeX2 = ropeX1;
		double ropeY1 = getHeight()/2 - BODY_LENGTH - HEAD_RADIUS*2 - ROPE_LENGTH;
		double ropeY2 = ropeY1 + ROPE_LENGTH;
		drawLine ( ropeX1, ropeY1, ropeX2, ropeY2);
	}


	/**
	 * Updates the word on the screen to correspond to the current
	 * state of the game.  The argument string shows what letters have
	 * been guessed so far; unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word) {

		double wordX = getWidth()/2;
		double wordY = getHeight() - RANDOM_SPACE_1;
		GLabel dashedWord = new GLabel (word, wordX, wordY);
		dashedWord.setFont("Helvetica-20");
		dashedWord.setColor(Color.BLUE);


		if (getElementAt(wordX, wordY) != null ){ 
			remove(getElementAt(wordX, wordY)); //Removes the dashes before the letter is made visible.
		}
		add(dashedWord);
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the
	 * user.  Calling this method causes the next body part to appear
	 * on the scaffold and adds the letter to the list of incorrect
	 * guesses that appears at the bottom of the window.
	 */
	public void noteIncorrectGuess(String letter) {

		double guessX = getWidth()/2;
		double guessY = getHeight() - RANDOM_SPACE_2;
		GLabel incorrectGuess = new GLabel (letter, guessX, guessY);
		if (getElementAt(guessX, guessY) != null ){
			remove(getElementAt(guessX, guessY));
		}
		add(incorrectGuess);

		int wrongGuesses = letter.length();

		switch (wrongGuesses){

		case 1:
			drawHead();
			break;

		case 2:
			drawBody();
			break;

		case 3:
			drawRightArm();
			break;

		case 4:
			drawLeftArm();
			break;

		case 5:
			drawRightLeg();
			break;

		case 6:
			drawLeftLeg();
			break;

		case 7:
			drawRightFoot();
			break;

		case 8:
			drawLeftFoot();
			displayGameOver();
			break;
		}		
	}

	/*
	 * Defines the method to draw the head.
	 */
	private void drawHead() {

		double headX = getWidth()/2 - UPPER_ARM_LENGTH + BEAM_LENGTH - HEAD_RADIUS;
		double headY = getHeight()/2 - BODY_LENGTH - HEAD_RADIUS*2;
		GOval head = new GOval (headX, headY, HEAD_RADIUS*2, HEAD_RADIUS*2);
		add(head);
	}

	/*
	 * Defines the method to draw the body.
	 */
	private void drawBody() {

		double bodyX1 = getWidth()/2 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS;
		double bodyX2 = bodyX1;
		double bodyY1 = getHeight()/2 - BODY_LENGTH;
		double bodyY2 = bodyY1 + BODY_LENGTH;
		drawLine(bodyX1, bodyY1, bodyX2, bodyY2 );
	}

	/*
	 * Defines the method to draw the right arm.
	 */
	private void drawRightArm() {

		double armX1 =  getWidth()/2 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS;
		double armY =  getHeight()/2 - BODY_LENGTH + ARM_OFFSET_FROM_HEAD;
		double rightArmX2 = getWidth()/2 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS + UPPER_ARM_LENGTH;
		drawLine(armX1, armY, rightArmX2, armY);
		double handY = armY + LOWER_ARM_LENGTH;
		drawLine(rightArmX2, armY, rightArmX2, handY);
	}

	/*
	 * Defines the method to draw the left arm.
	 */
	private void drawLeftArm() {

		double armX1 =  getWidth()/2 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS;
		double armY =  getHeight()/2 - BODY_LENGTH + ARM_OFFSET_FROM_HEAD;
		double leftArmX2 = getWidth()/2 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS - UPPER_ARM_LENGTH;
		drawLine(armX1, armY, leftArmX2, armY);
		double handY = armY + LOWER_ARM_LENGTH;
		drawLine(leftArmX2, armY, leftArmX2, handY);
	}

	/*
	 * Defines the method to draw the right leg.
	 */
	private void drawRightLeg() {

		double hipX1 = getWidth()/2 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS;
		double hipX2 = hipX1 + HIP_WIDTH;
		double hipY = getHeight()/2;
		drawLine(hipX1, hipY, hipX2, hipY);
		double legY = hipY + LEG_LENGTH;
		drawLine(hipX2,hipY,hipX2,legY);
	}

	/*
	 * Defines the method to draw the left leg.
	 */
	private void drawLeftLeg() {

		double hipX1 = getWidth()/2 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS;
		double hipX2 = hipX1 - HIP_WIDTH;
		double hipY = getHeight()/2;
		drawLine(hipX1, hipY, hipX2, hipY);
		double legY = hipY + LEG_LENGTH;
		drawLine(hipX2,hipY,hipX2,legY);

	}

	/*
	 * Defines the method to draw the right foot.
	 */
	private void drawRightFoot() {

		double footX1 = getWidth()/2 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS + HIP_WIDTH;
		double footX2 = footX1 + FOOT_LENGTH;
		double footY1 = getHeight()/2 + LEG_LENGTH;
		double footY2 = footY1;
		drawLine(footX1,footY1,footX2,footY2);
	}

	/*
	 * Defines the method to draw the left foot.
	 */
	private void drawLeftFoot() {

		double footX1 = getWidth()/2 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS - HIP_WIDTH;
		double footX2 = footX1 - FOOT_LENGTH;
		double footY1 = getHeight()/2 + LEG_LENGTH;
		double footY2 = footY1;
		drawLine(footX1,footY1,footX2,footY2);
	}

	/*
	 * Displays the message Game over when the Hangman is completely hung.
	 */
	private void displayGameOver(){

		GLabel gameOver= new GLabel ("GAME OVER!!!", getWidth()/2, getHeight()/2);
		gameOver.setColor(Color.RED);
		gameOver.setFont("Helvetica-30");
		double x = gameOver.getWidth()/2;
		double y = gameOver.getHeight()/2;
		gameOver.setLocation(getWidth()/2-x, getHeight()/2-y);
		add(gameOver);	
	}

	/*
	 * Displays the message You won when the user guesses the word correctly.
	 */
	public void displayGameWon(){

		GLabel gameWon= new GLabel ("YOU WON!!!", getWidth()/2, getHeight()/2);
		gameWon.setColor(Color.RED);
		gameWon.setFont("Helvetica-30");
		double x = gameWon.getWidth()/2;
		double y = gameWon.getHeight()/2;
		gameWon.setLocation(getWidth()/2-x, getHeight()/2-y);
		add(gameWon);	
	}

}
