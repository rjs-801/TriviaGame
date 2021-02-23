package triviaGame;

/**
 * 
 * @author Ramon Sanchez & Joseph Bair
 *
 */
public class Quiz {
	
	private static String question, multChoice1, multChoice2, multChoice3, multChoice4;
	private static int answer, category;
	
	//Question, answer, and category constructor
	public Quiz(String q, String a, String b, String c, String d, int winner, int cat) {
		question = q;
		multChoice1 = a; multChoice2 = b; multChoice3 = c; multChoice4 = d;
		answer = winner;
		category = cat;
	}	
	
	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @return the multChoice1
	 */
	public String getMultChoice1() {
		return multChoice1;
	}

	/**
	 * @return the multChoice2
	 */
	public String getMultChoice2() {
		return multChoice2;
	}

	/**
	 * @return the multChoice3
	 */
	public String getMultChoice3() {
		return multChoice3;
	}

	/**
	 * @return the multChoice4
	 */
	public String getMultChoice4() {
		return multChoice4;
	}

	/**
	 * @return the answer
	 */
	public int getAnswer() {
		return answer;
	}

	/**
	 * @return the category
	 */
	public static int getCategory() {
		return category;
	}
}