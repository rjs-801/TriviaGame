package triviaGame;

import java.util.InputMismatchException;

/**
 * 
 * @author Ramon Sanchez & Joseph Bair
 *
 */
public class QuizApp {

	public static Quiz getQuestion(String line) {
		Quiz quiz = null;
		try {
			String[] items = line.split(",");
			int answer = Integer.parseInt(items[5]);
			int cat = Integer.parseInt(items[6]);
			quiz = new Quiz(items[0], items[1], items[2], items[3], items[4], answer, cat);
		} catch (InputMismatchException | NumberFormatException | IndexOutOfBoundsException e) {
			System.err.println("\"" + line + "\" could not be turned into a question");
		}
		return quiz;
	}
}
