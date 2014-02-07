/*

 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import acm.util.*;
import java.util.*;
import java.io.*;


public class HangmanLexicon {
	
	private ArrayList <String> listOfWords = new ArrayList <String> ();

	public HangmanLexicon(){

		try {
			BufferedReader rd = new BufferedReader(new FileReader("HangmanLexicon.txt"));
			while(true){
				String word = rd.readLine();//Reads every word in the file
				if (word == null){
					break;	//breaks when it reaches end of the file.
				}
				listOfWords.add(word);//adds words to the array list.
			}
		}catch (IOException ex){
			throw new ErrorException(ex);//Throws an IO exception if the file to be read is not found.
		}
	}
	
	/** Returns the word at the specified index. */
	public String getWord(int index){
		
		return listOfWords.get(index);
	}
	
	/** Returns the number of words in the lexicon. */
	public int getWordCount(){
		
		return listOfWords.size();
	}
}





	/** Returns the number of words in the lexicon. */
	/*public int getWordCount() {
		return 10;
	}*/

	/** Returns the word at the specified index. */
	/*public String getWord(int index) {
		switch (index) {
			case 0: return "BUOY";
			case 1: return "COMPUTER";
			case 2: return "CONNOISSEUR";
			case 3: return "DEHYDRATE";
			case 4: return "FUZZY";
			case 5: return "HUBBUB";
			case 6: return "KEYHOLE";
			case 7: return "QUAGMIRE";
			case 8: return "SLITHER";
			case 9: return "ZIRCON";
			default: throw new ErrorException("getWord: Illegal index");
		}
	};
}
	 */