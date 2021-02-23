package triviaGame;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JLabel;

import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

/**
 * 
 * @author Ramon Sanchez & Joseph Bair
 *
 */
public class QuizGui extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6555732236766450915L;
	private static CardLayout cardLayout = new CardLayout();
	private static JPanel contentPane;
	static List<QuizScores> list = new ArrayList<QuizScores>();
	Quiz q = null;
	Scanner reader = null;
	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm");
	Date date;
	JMenuItem gameMenu = menuItems();
	String name, quizDate;
	String line = "";
	JTextArea scoresList;
	JPanel gamePanel, displayScoresPanel;
	JButton btnNextQuestion;
	JButton btn1, btn2, btn3, btn4;
	JTextField nameEntered;
	JTextArea questionArea;
	JLabel lblHighScores = new JLabel();
	JLabel gameLabel = new JLabel("");
	JLabel lblResult, lblScore, lblTriviaGame, lblMovieTrivia;
	int questionCounter, tempScore, questionNumber;
	private int counter = 0;
	File file = new File(System.getProperty("user.dir"), "scores.csv");
	List<String> randomized = new ArrayList<String>();
	int rand;
	Quiz a = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuizGui frame = new QuizGui();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					cardLayout.show(contentPane, "mainPanel");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public QuizGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 575, 585);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(cardLayout);
		final JPanel scoresPanel = scoresPanel();
		scoresButtons(scoresPanel);
		triviaPanel();
		gameButtons();
		mainPanel();

	}

	/**
	 * Main GUI panel, labels, and text field
	 */
	private void mainPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBorder(null);
		contentPane.add(mainPanel, "mainPanel");
		mainPanel.setLayout(null);

		JLabel mainLabel = new JLabel("");
		mainLabel.setBounds(196, 11, 160, 241);
		mainLabel.setOpaque(true);
		mainLabel.setBackground(Color.WHITE);
		mainLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/images/trivia.png"))
				.getImage().getScaledInstance(146, 220, Image.SCALE_SMOOTH)));
		mainPanel.add(mainLabel);

		nameEntered = new JTextField();
		nameEntered.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startGame();
			}
		});
		nameEntered.setHorizontalAlignment(SwingConstants.CENTER);
		nameEntered.setFont(new Font("Nirmala UI Semilight", Font.PLAIN, 27));
		nameEntered.setBounds(118, 348, 331, 51);
		mainPanel.add(nameEntered);
		nameEntered.setColumns(10);

		JLabel lblEnterYourName = new JLabel("Enter Your Name:");
		lblEnterYourName.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterYourName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEnterYourName.setBounds(163, 308, 233, 29);
		mainPanel.add(lblEnterYourName);

		JButton playButton = new JButton("Let's Play!");
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startGame();
			}
		});
		playButton.setFont(new Font("Calibri", Font.PLAIN, 49));
		playButton.setBounds(118, 410, 331, 72);
		mainPanel.add(playButton);

		lblTriviaGame = new JLabel("Trivia Game!");
		lblTriviaGame.setHorizontalAlignment(SwingConstants.CENTER);
		lblTriviaGame.setFont(new Font("Calibri Light", Font.BOLD, 40));
		lblTriviaGame.setBounds(117, 247, 332, 51);
		mainPanel.add(lblTriviaGame);
	}

	/**
	 * Save Score panel, buttons, and Action listener settings
	 * 
	 * @param scoresPanel
	 */
	private void scoresButtons(final JPanel scoresPanel) {
		
		JButton btnReturnGame = new JButton("Return to Game");
		btnReturnGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (randomized.size() > 0) {
					cardLayout.show(contentPane, "gamePanel");
				} else if (randomized.size() == 0) {
					JFrame f = new JFrame();
					f.setLocationRelativeTo(null);
					JLabel startGame = new JLabel("You haven't started the game!");
					startGame.setFont(new Font("Arial", Font.BOLD, 18));
					JOptionPane.showMessageDialog(f, startGame);
					cardLayout.show(contentPane, "mainPanel");
				} else {
					JFrame f = new JFrame();
					f.setLocationRelativeTo(null);
					JLabel endGame = new JLabel("You have already finished the game!");
					endGame.setFont(new Font("Arial", Font.BOLD, 18));
					JOptionPane.showMessageDialog(f, endGame);
				}
			}
		});
		btnReturnGame.setBounds(138, 434, 136, 38);
		scoresPanel.add(btnReturnGame);

		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				questionCounter = 0;
				counter = 0;
				name = null;
				nameEntered.setText("");
				lblScore.setText("Score: " + scoreCalc());
				cardLayout.show(contentPane, "mainPanel");
				nameEntered.requestFocus();
			}
		});
		btnNewGame.setBounds(286, 434, 136, 38);
		scoresPanel.add(btnNewGame);
	}

	/**
	 * High Scores Panel, labels, and High Score Display panel
	 * 
	 * @return
	 */
	private JPanel scoresPanel() {
		final JPanel scoresPanel = new JPanel();
		scoresPanel.setBackground(Color.WHITE);
		contentPane.add(scoresPanel, "scoresPanel");
		scoresPanel.setLayout(null);

		lblHighScores.setHorizontalAlignment(SwingConstants.CENTER);
		lblHighScores.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblHighScores.setBounds(138, 53, 273, 62);
		scoresPanel.add(lblHighScores);

		displayScoresPanel = new JPanel();
		displayScoresPanel.setOpaque(false);
		displayScoresPanel.setBounds(59, 105, 428, 300);
		scoresPanel.add(displayScoresPanel);

		scoresList = new JTextArea();
		displayScoresPanel.add(scoresList);
		scoresList.setFont(new Font("Monospaced", Font.BOLD, 20));
		scoresList.setEditable(false);
		scoresList.setColumns(10);
		return scoresPanel;
	}

	/**
	 * Game Panel, labels, and score counter panel
	 */
	private void triviaPanel() {
		gamePanel = new JPanel();
		gamePanel.setBackground(Color.WHITE);
		contentPane.add(gamePanel, "gamePanel");
		gamePanel.setLayout(null);

		lblMovieTrivia = new JLabel("Movie Trivia!");
		lblMovieTrivia.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMovieTrivia.setHorizontalAlignment(SwingConstants.CENTER);
		lblMovieTrivia.setBounds(106, 170, 138, 26);
		gamePanel.add(lblMovieTrivia);

		gameLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/images/popcorn.png"))
				.getImage().getScaledInstance(100, 149, Image.SCALE_SMOOTH)));
		gameLabel.setBounds(123, -29, 100, 242);
		gamePanel.add(gameLabel);

		questionArea = new JTextArea();
		questionArea.setWrapStyleWord(true);
		questionArea.setLineWrap(true);
		questionArea.setEditable(false);
		questionArea.setFont(new Font("Calibri", Font.PLAIN, 20));
		questionArea.setBounds(67, 230, 410, 71);
		gamePanel.add(questionArea);

		final JPanel scorePanel = new JPanel();
		scorePanel.setBorder(new LineBorder(new Color(119, 136, 153), 3, true));
		scorePanel.setBackground(Color.WHITE);
		scorePanel.setBounds(299, 35, 148, 151);
		gamePanel.add(scorePanel);
		scorePanel.setLayout(new GridLayout(2, 1, 0, 0));

		lblScore = new JLabel("Score: 000");
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scorePanel.add(lblScore);

		lblResult = new JLabel("");
		lblResult.setForeground(new Color(255, 255, 255));
		lblResult.setBackground(new Color(255, 255, 255));
		lblResult.setOpaque(true);
		lblResult.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblResult.setBorder(new EmptyBorder(0, 5, 0, 0));
		scorePanel.add(lblResult);
	}

	/**
	 * Game panel buttons and Action Listener settings
	 */
	private void gameButtons() {
		btn1 = new JButton("");
		btn1.setBorder(new LineBorder(new Color(119, 136, 153), 3, true));
		btn1.setBackground(Color.LIGHT_GRAY);
		btn1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn1.setBounds(67, 311, 200, 51);
		gamePanel.add(btn1);

		btn2 = new JButton("");
		btn2.setBorder(new LineBorder(new Color(119, 136, 153), 3, true));
		btn2.setBackground(Color.LIGHT_GRAY);
		btn2.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn2.setBounds(277, 311, 200, 51);
		gamePanel.add(btn2);

		btn3 = new JButton("");
		btn3.setBorder(new LineBorder(new Color(119, 136, 153), 3, true));
		btn3.setBackground(Color.LIGHT_GRAY);
		btn3.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn3.setBounds(67, 373, 200, 51);
		gamePanel.add(btn3);

		btn4 = new JButton("");
		btn4.setBorder(new LineBorder(new Color(119, 136, 153), 3, true));
		btn4.setBackground(Color.LIGHT_GRAY);
		btn4.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn4.setBounds(277, 373, 200, 51);
		gamePanel.add(btn4);

		btnNextQuestion = new JButton("Next!");
		btnNextQuestion.setBorderPainted(false);
		btnNextQuestion.setFocusPainted(false);
		btnNextQuestion.setVisible(true);
		btnNextQuestion.setOpaque(false);
		btnNextQuestion.setContentAreaFilled(false);
		btnNextQuestion
				.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/images/right arrow.png"))
						.getImage().getScaledInstance(65, 52, Image.SCALE_SMOOTH)));
		btnNextQuestion.setBounds(196, 437, 181, 62);
		btnNextQuestion.setCursor(new Cursor(Cursor.HAND_CURSOR));
		gamePanel.add(btnNextQuestion);

		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				unenableButtons();
				if (q.getAnswer() == 1) {
					questionCorrect();
					btn1.setBackground(Color.GREEN);

				} else {
					questionWrong();
					btn1.setBackground(Color.RED);
				}
			}
		});

		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				unenableButtons();
				if (q.getAnswer() == 2) {
					questionCorrect();
					btn2.setBackground(Color.GREEN);
				} else {
					questionWrong();
					btn2.setBackground(Color.RED);
				}
			}
		});

		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				unenableButtons();
				if (q.getAnswer() == 3) {
					questionCorrect();
					btn3.setBackground(Color.GREEN);
				} else {
					questionWrong();
					btn3.setBackground(Color.RED);
				}
			}

		});

		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				unenableButtons();
				if (q.getAnswer() == 4) {
					questionCorrect();
					btn4.setBackground(Color.GREEN);

				} else {
					questionWrong();
					btn4.setBackground(Color.RED);
				}
			}

		});

		btnNextQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (randomized.size() > 0) {
					resetPanel();
					getCategory();

				} else {
					JFrame f = new JFrame();
					f.setLocationRelativeTo(null);
					JLabel endGame = new JLabel("You finished all the questions! See the high scores");
					endGame.setFont(new Font("Arial", Font.BOLD, 18));
					JOptionPane.showMessageDialog(f, endGame);
					saveScores();
					lblHighScores.setText("High Scores");
					cardLayout.show(contentPane, "scoresPanel");
					displayScores();
				}
			}
		});

		final JButton saveGameButton = new JButton("Save Game!");
		saveGameButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		saveGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblHighScores.setText("Score Saved!");
				saveScores();
				cardLayout.show(contentPane, "scoresPanel");
				displayScores();
			}
		});
		saveGameButton.setBorder(new LineBorder(new Color(119, 136, 153), 3, true));
		saveGameButton.setBackground(Color.LIGHT_GRAY);
		saveGameButton.setBounds(300, 189, 148, 26);
		gamePanel.add(saveGameButton);
	}

	/**
	 * GUI main menu buttons and Action Listener settings
	 * 
	 * @return
	 */
	private JMenuItem menuItems() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		final JMenuItem gameMenu = new JMenuItem("Trivia Game");
		final JMenuItem scoreMenu = new JMenuItem("High Scores");
		final JMenuItem saveScore = new JMenuItem("Save Score");
		final JMenuItem exitMenu = new JMenuItem("Exit");

		gameMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (questionCounter == 0 && name == null) {
					cardLayout.show(contentPane, "mainPanel");
					nameEntered.requestFocus();
				} else if (name != null && questionCounter == 0) {
					cardLayout.show(contentPane, "gamePanel");
				} else if (questionCounter > 0) {

					cardLayout.show(contentPane, "gamePanel");
				} else {
					startGame();
					cardLayout.show(contentPane, "gamePanel");
				}
			}
		});

		menuBar.add(gameMenu);

		scoreMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblHighScores.setText("High Scores");
				cardLayout.show(contentPane, "scoresPanel");
				displayScores();
			}
		});
		menuBar.add(scoreMenu);

		saveScore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblHighScores.setText("Score Saved!");
				saveScores();
				cardLayout.show(contentPane, "scoresPanel");
				displayScores();
			}
		});
		menuBar.add(saveScore);

		exitMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", null,
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		menuBar.add(exitMenu);
		return gameMenu;
	}

	/**
	 * Saves and stores players score to scores.csv
	 */
	private void saveScores() {
		if (counter > 0 && name != null && questionNumber != counter) {
			questionNumber = counter;
			try {
				BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
				output.newLine();
				output.append(scoreCalc() + ", " + name + ", " + dateFormat.format(date));
				output.close();
			} catch (IOException ex1) {
				System.out.printf("ERROR writing score to file: %s\n", ex1);
			}
		}
	}

	/**
	 * Reads scores.csv file, sorts scores from highest to lowest points, and
	 * displays for user
	 */
	private void displayScores() {
		scoresList.setText("");
		scoresList.repaint();
		QuizScores savedScores = null;
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String line;
			try {
				while ((line = in.readLine()) != null) {
					if (line.length() > 0) {
						String[] scores = line.split(",");
						int score = Integer.parseInt(scores[0]);
						String name = scores[1];
						String date = scores[2];
						savedScores = new QuizScores(score, name, date);
						list.add(savedScores);
					}
				}
				for (int i = 0; i < list.size(); i++) {
					QuizScores savedScore = list.get(i);
					for (int j = 1; j < list.size(); j++) {
						QuizScores compareScore = list.get(j);
						String ssDate = savedScore.getDate();
						String csDate = compareScore.getDate();
						if (ssDate.equals(csDate)) {
							list.remove(j);
							break;
						}
					}

				}
				Collections.sort(list, new SortByScore());
				for (int i = 0; i < 10 && i < list.size(); i++) {
					scoresList.append(list.get(i).toString() + "\n");
				}
				list.clear();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Changes Score Label during game to green color and displays 'Correct', adds
	 * score to counter
	 */
	private void questionCorrect() {
		questionCounter++;
		lblResult.setText("Correct!");
		lblResult.setBackground(Color.GREEN);
		counter++;
		lblScore.setText("Score: " + scoreCalc());
		gamePanel.repaint();
		btnNextQuestion.setVisible(true);
	}

	/**
	 * Changes Score Label during game to red color and displays 'Incorrect'
	 */
	private void questionWrong() {
		questionCounter++;
		lblResult.setText("Incorrect!");
		lblResult.setBackground(Color.RED);
		gamePanel.repaint();
		btnNextQuestion.setVisible(true);
	}

	/**
	 * Disables buttons after user has answered question
	 */
	private void unenableButtons() {
		btn1.setEnabled(false);
		btn2.setEnabled(false);
		btn3.setEnabled(false);
		btn4.setEnabled(false);
	}

	/**
	 * Gathers questions from Quiz.java
	 */
	private void setButtonQuestion() {
		btn1.setText(q.getMultChoice1());
		btn2.setText(q.getMultChoice2());
		btn3.setText(q.getMultChoice3());
		btn4.setText(q.getMultChoice4());
	}

	/**
	 * Reads Quiz.csv and starts at Question 1
	 */
	private void startGame() {
		reader = new Scanner(QuizApp.class.getResourceAsStream("Quiz.csv"));
		popList();
		resetPanel();
		getCategory();
		name = nameEntered.getText();
		date = new Date();

		if (name.equals("")) {
			Component frame = null;
			JOptionPane.showMessageDialog(frame, "Please Enter Your Name");
		} else {
			cardLayout.show(contentPane, "gamePanel");
			counter = 0;
		}
	}

	/**
	 * Reads next line in Quiz.csv and resets all trivia question buttons for next
	 * question
	 */

	private void resetPanel() {
		btn1.setEnabled(true);
		btn2.setEnabled(true);
		btn3.setEnabled(true);
		btn4.setEnabled(true);
		btn1.setBackground(Color.LIGHT_GRAY);
		btn2.setBackground(Color.LIGHT_GRAY);
		btn3.setBackground(Color.LIGHT_GRAY);
		btn4.setBackground(Color.LIGHT_GRAY);
		btnNextQuestion.setVisible(false);
		lblResult.setText("");
		lblResult.setBackground(Color.WHITE);
		rand = randomIndex();
		q = QuizApp.getQuestion(randomized.get(rand));
		randomized.remove(rand);
		questionArea.setText(q.getQuestion());
		setButtonQuestion();
	}

	/**
	 * Changes trivia games icon and text based on the category
	 */
	private void getCategory() {
		if (Quiz.getCategory() == 1) {
			gameLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/images/popcorn.png"))
					.getImage().getScaledInstance(100, 149, Image.SCALE_SMOOTH)));
			lblMovieTrivia.setText("Movie Trivia!");
		} else if (Quiz.getCategory() == 2) {
			gameLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/images/science.png"))
					.getImage().getScaledInstance(100, 149, Image.SCALE_SMOOTH)));
			lblMovieTrivia.setText("Science Trivia!");
		} else if (Quiz.getCategory() == 3) {
			gameLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/images/history.png"))
					.getImage().getScaledInstance(100, 149, Image.SCALE_SMOOTH)));
			lblMovieTrivia.setText("History Trivia!");
		}
	}

	/**
	 * This populates the randomized list
	 */
	private void popList() {
		randomized.clear();
		while (reader.hasNextLine()) {
			line = reader.nextLine();
			randomized.add(line);
		}
	}

	/**
	 * This provides a random index
	 * 
	 * @return
	 */
	private int randomIndex() {
		int i = (int) Math.round(Math.random() * (randomized.size() - 1));
		return i;
	}

	/**
	 * Calculates players score
	 */
	private int scoreCalc() {
		tempScore = counter * 100;
		return tempScore;
	}

}
