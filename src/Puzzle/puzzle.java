package Puzzle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class puzzle {
	static JFrame frame = new JFrame("Puzzle"); // The main window.
	static JPanel panel = new JPanel(new GridBagLayout()); // Contains everything.
	static GridBagConstraints c = new GridBagConstraints(); // For organization.
	static JButton bOne, bTwo, bThree, bFour, bFive, bSix, bSeven, bEight, bNine, bTen, bEleven, bTwelve, bThirteen,
			bFourteen, bFifteen, bSixteen; //All 16 buttons (bSixteen is the "blank" square.)
	static JLabel clickCounter = new JLabel("Clicks: 0"); //Label to display clicks.
	static int emptyX, emptyY, x, y; //Used for filling in the array.
	static int clicks = 0; //Number of clicks.
	static JButton[][] buttonArray = new JButton[4][4]; //Temporary array.
	static JButton[][] buttonLayout = new JButton[4][4]; //What you will see on the screen.
	static JButton[][] solution = new JButton[4][4]; //The proper image arrangement.
	static boolean[][] taken = new boolean[4][4]; //For filling in random squares.
	static Font font = new Font("Comic Sans MS", Font.BOLD, 36); //Best font ever.
	static int countOfCorrects; //For checking a winning condition.
	static Image img; //Used for making button icons.
	static JFrame winFrame = new JFrame("Very winner.");
	static JPanel winPanel = new JPanel();
	static JButton winPic = new JButton();
	static JLabel winText = new JLabel("Wow. you did it."); //All of these make the winning screen.

	public static void main(String[] args) {
		emptyX = 3;
		emptyY = 3;
		//Makes 3,3 the blank square.
		setDisplay(); //see seeDisplay().
		frame.setFocusable(true); //Allows for the use of the key listener (sometimes.)
		frame.addKeyListener(new KeyListener() { //Allows one to cheat by pressing d (only works sometimes.)
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == 'd') {
					System.out.println("You win");
					finalMethod();
				} else {
					System.out.println("nope");
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
		System.out.println("Keylistener made");
	}

	public static void setDisplay() { //Makes and adds all the buttons. Essential sets the game up.
		makeButton(bOne, 0, 0, "01");
		makeButton(bTwo, 0, 1, "02");
		makeButton(bThree, 0, 2, "03");
		makeButton(bFour, 0, 3, "04");
		makeButton(bFive, 1, 0, "05");
		makeButton(bSix, 1, 1, "06");
		makeButton(bSeven, 1, 2, "07");
		makeButton(bEight, 1, 3, "08");
		makeButton(bNine, 2, 0, "09");
		makeButton(bTen, 2, 1, "10");
		makeButton(bEleven, 2, 2, "11");
		makeButton(bTwelve, 2, 3, "12");
		makeButton(bThirteen, 3, 0, "13");
		makeButton(bFourteen, 3, 1, "14");
		makeButton(bFifteen, 3, 2, "15");
		makeButton(bSixteen, 3, 3, "XX");
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 1;
		c.gridy = 4;
		c.ipadx = 2;
		clickCounter.setFont(font);
		panel.add(clickCounter, c); //Adds click counter with given permutations.
		taken[3][3] = true; //Button 3 by 3 cannot be filled again.
		x = 3;
		y = 3;
		for (JButton[] row : buttonArray) {
			for (JButton j : row) { //For every button in buttonArray...
				if (j == buttonArray[3][3]) { //Adds the blank square.
					addButton(j, 3, 3); 
				} else {

					System.out.println("Next button.");
					while (taken[x][y]) { //Runs until an open button is found.
						x = (int) (Math.random() * 4); //Random numbers from 0 to 4.
						y = (int) (Math.random() * 4);

					}
					taken[x][y] = true; //This button is now taken.
					addButton(j, x, y); //Adds the button to the layout.
				}

			}
		}
		buttonLayout[3][3].setBackground(Color.BLACK); //The blank button has a black background (left over from before.)
		frame.add(panel); //Adds the panel to the window.
		frame.setSize(1000, 1000);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Frame is set up.
	}

	public static void makeButton(JButton button, int localX, int localY, String text) { //First creates the button, and adds it to multiple arrays.
		button = new JButton(); //Initializes the button.
		button.setFont(font); //Comic Sans.
		button.setMinimumSize(new Dimension(100, 100)); //Gives the button a size.
		Icon localIcon = null; //The picture to be added.
		if (text.equals("XX")) { //When making the blank piece.
			try {
				img = ImageIO.read(puzzle.class.getResource("Blank.jpg")); //Black square.
			} catch (IOException ex) {
			}
			localIcon = new ImageIcon(img.getScaledInstance(200, 200, Image.SCALE_DEFAULT)); //Scales the black square.
			button.setIcon(localIcon); //Adds icon to the blank square.
		} else { //When making any other button.
			try {
				img = ImageIO.read(puzzle.class.getResource("Doge" + text + ".jpg")); //The respective image block.
				localIcon = new ImageIcon(img.getScaledInstance(200, 200, Image.SCALE_DEFAULT)); //Scales the image.
				button.setIcon(localIcon); //Sets the icon to the appropriate image.
			} catch (IOException ex) {
			}

		}
		JButton solutionButton = new JButton(); //Marks the "solution" position as a new button.
		JButton localButton = button; //local instance of the very same button.
		solutionButton.setIcon(localIcon); //Solution button receives the same icon as the button.
		buttonArray[localY][localX] = button; //Adds the button to the array to be used for later.
		solution[localY][localX] = solutionButton; //Adds the solution button to the "correct" array.
		button.addActionListener(new ActionListener() { //Runs specific code when pressed.
			public void actionPerformed(ActionEvent e) {
				int resultX = 0;
				int resultY = 0;
				for (int i = 0; i <= 3; i++) {
					for (int j = 0; j <= 3; j++) {
						if (buttonLayout[i][j] == localButton) { //Finds exactly which button is being pressed in the array.
							// System.out.println("localX = " + i);
							// System.out.println("localY = " + j);
							resultX = i;
							resultY = j;
						}
					}
				}
				shiftPiece(resultX, resultY); //Swaps the piece (if possible).
			}
		});
	}

	public static void addButton(JButton button, int localX, int localY) { //Actually places the button into the panel.
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = localX;
		c.gridy = localY;
		c.ipadx = 100;
		c.ipady = 100;
		c.insets = new Insets(5, 5, 5, 5); //All of these configure the button's physical properties.
		buttonLayout[localX][localY] = button; //Records the button's new random position.
		panel.add(buttonLayout[localX][localY], c); //Adds the button to the panel.

	}

	public static void shiftPiece(int localX, int localY) { //Swaps the pressed piece with the blank one (if possible.)
		//Note that in this method, buttonLayout[emptyX][emptyY] is the blank piece.
		//And buttonLayout[localX][localY] is the one being "shifted.:
		if (Math.abs(localX - emptyX) == 1 && Math.abs(localY - emptyY) == 0
				|| Math.abs(localX - emptyX) == 0 && Math.abs(localY - emptyY) == 1) { //When the buttons are adjacent. 
			buttonLayout[emptyX][emptyY].setText(buttonLayout[localX][localY].getText()); //Swaps text (left older from old code.)
			buttonLayout[emptyX][emptyY].setBackground(null); //Changes background (left over.)
			Icon localIcon = buttonLayout[emptyX][emptyY].getIcon(); //Temporarily stores the black square image.
			buttonLayout[emptyX][emptyY].setIcon(buttonLayout[localX][localY].getIcon());
			buttonLayout[localX][localY].setIcon(localIcon); //Icons are now swapped.
			// buttonLayout[localX][localY].setText("XX"); //Another thing left over.
			buttonLayout[localX][localY].setBackground(Color.BLACK); //Changes new blank squares baground (left over.)
			emptyX = localX;
			emptyY = localY; //Variables have now been swapped.
			clicks++; //Adds a click.
			clickCounter.setText("Clicks: " + clicks); //Displays the new number of clicks.
		}
		if (checkWin()) { //If the squares are all correct...
			for (JButton[] row : buttonLayout) {
				for (JButton j : row) {
					j.setEnabled(false); //Every button is disabled.
				}
			}
			finalMethod(); //see finalMethod()
		}
	}

	public static void finalMethod() { //Displays the victory window.
		System.out.println("WOOO");
		winPic.setPreferredSize(new Dimension(900, 900)); //Resizes winPic.

		try {
			Image winImage = ImageIO.read(puzzle.class.getResource("DogeComplete.jpg")); //The completed image.
			Icon winIcon = new ImageIcon(winImage.getScaledInstance(1000, 1000, Image.SCALE_DEFAULT)); //Scales the image.
			winPic.setIcon(winIcon); //Sets button's icon to completed image.
		} catch (IOException ex) {
		}
		winPanel.add(winPic); //Adds picture button to panel.
		winPanel.add(winText); //Adds text label to panel.
		winFrame.add(winPanel); //Adds panel to frame.
		winFrame.setSize(1000, 1000); 
		winFrame.setVisible(true); //Frame is set up.
		changeColor(); //Picks a random background color.
		winPic.addActionListener(new ActionListener() { //Whenever the picture is pressed....

			@Override
			public void actionPerformed(ActionEvent e) {
				changeColor(); //Picks a new background color.

			}
		});

	}

	public static boolean checkWin() { //Tests for a solved puzzle.
		countOfCorrects = 0; //Resets counter.
		for (int i = 0; i <= 3; i++) {
			for (int o = 0; o <= 3; o++) {
				if (solution[i][o].getIcon().equals(buttonLayout[i][o].getIcon())) { //When the layout icon matches the solutions....
					//System.out.println(i + " by " + o + "  is correct"); //For debugging purposes.
					countOfCorrects++; //Increments count for solution.
				}
			}
		}
		//System.out.println(countOfCorrects); //For debugging.
		return (countOfCorrects == 16); //When all 16 squares are in the right place, returns true.

	}

	public static void changeColor() { //Picks a random color. Not much to say.
		double randNum = Math.random(); //Depending on the number, a specific color is picked.
		if (randNum < .16) {
			winPanel.setBackground(Color.RED);
		} else if (randNum >= .16 && randNum < .32) {
			winPanel.setBackground(Color.ORANGE);
		} else if (randNum >= .32 && randNum < .48) {
			winPanel.setBackground(Color.YELLOW);
		} else if (randNum >= .48 && randNum < .56) {
			winPanel.setBackground(Color.GREEN);
		} else if (randNum >= .56 && randNum < .74) {
			winPanel.setBackground(Color.BLUE);
		} else {
			winPanel.setBackground(Color.MAGENTA);
		}
	}

}