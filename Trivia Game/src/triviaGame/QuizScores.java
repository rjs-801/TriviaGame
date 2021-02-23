package triviaGame;

import java.util.Comparator;

/**
 * 
 * @author Ramon Sanchez & Joseph Bair
 *
 */
public class QuizScores {
	int score;
	String name;
	String date;

	
	public QuizScores(int score, String name, String date) {
		this.score = score;
		this.name = name;
		this.date = date;
	}

	public int getScore() {
		return score;
	}

	public String getName() {
		return name;
	}

	public String getDate() {
		return date;
	}
	
	public String toString(){
		return(String.format("%-4d %-8s %s", score, name, date));
	}

}

class SortByScore implements Comparator<QuizScores>{

	@Override
	public int compare(QuizScores a, QuizScores b) {
		
		return b.score - a.score;
	}
	
}
