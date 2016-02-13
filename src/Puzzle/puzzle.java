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
	static JFrame frame = new JFrame("Puzzle");
	static JPanel panel = new JPanel(new GridBagLayout());
	static GridBagConstraints c = new GridBagConstraints();
	static JButton bOne, bTwo, bThree, bFour, bFive, bSix, bSeven, bEight, bNine, bTen, bEleven, bTwelve, bThirteen,
			bFourteen, bFifteen, bSixteen;
	static int emptyX, emptyY, x, y;
	static JButton[][] buttonArray = new JButton[4][4];
	static JButton[][] buttonLayout = new JButton[4][4];
	static JButton[][] solution = new JButton[4][4];
	static boolean[][] taken = new boolean[4][4];
	static Font font = new Font("Comic Sans MS", Font.BOLD, 36);
	static int countOfCorrects;
	static Image img;
	static boolean tempColor;
	static JFrame winFrame = new JFrame();
	static JPanel winPanel = new JPanel();
	static JButton winPic = new JButton();
	static JLabel winText = new JLabel("Wow. you did it.");

	public static void main(String[] args) {
		emptyX = 3;
		emptyY = 3;
		setDisplay();
		frame.setFocusable(true);
		frame.addKeyListener(new KeyListener() {
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
		finalMethod();
	}

	public static void setDisplay() {
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
		taken[3][3] = true;
		x = 3;
		y = 3;
		for (JButton[] row : buttonArray) {
			for (JButton j : row) {
				if (j == buttonArray[3][3]) {
					addButton(j, 3, 3);
				} else {

					System.out.println("Next button.");
					while (taken[x][y]) {
						x = (int) (Math.random() * 4);
						y = (int) (Math.random() * 4);

					}
					taken[x][y] = true;
					addButton(j, x, y);
				}

			}
		}
		buttonLayout[3][3].setBackground(Color.BLACK);
		frame.add(panel);
		frame.setSize(1000, 1000);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void makeButton(JButton button, int localX, int localY, String text) {
		button = new JButton();
		button.setFont(font);
		button.setMinimumSize(new Dimension(100, 100));
		Icon localIcon = null;
		if (text.equals("XX")) {
			try {
				img = ImageIO.read(puzzle.class.getResource("Blank.jpg"));
			} catch (IOException ex) {
			}
			localIcon = new ImageIcon(img.getScaledInstance(200, 200, Image.SCALE_DEFAULT));
			button.setIcon(localIcon);
		}
		else{
			// button.setMargin(new Insets(0,0,0,0));
			try {
				img = ImageIO.read(puzzle.class.getResource("Doge" + text + ".jpg"));
				localIcon = new ImageIcon(img.getScaledInstance(200, 200, Image.SCALE_DEFAULT));
				button.setIcon(localIcon);
			} catch (IOException ex) {
			}
			

		}
		JButton solutionButton = new JButton();
		JButton localButton = button;
		solutionButton.setIcon(localIcon);
		buttonArray[localY][localX] = button;
		solution[localY][localX] = solutionButton;
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int resultX = 0;
				int resultY = 0;
				for (int i = 0; i <= 3; i++) {
					for (int j = 0; j <= 3; j++) {
						if (buttonLayout[i][j] == localButton) {
							// System.out.println("localX = " + i);
							// System.out.println("localY = " + j);
							resultX = i;
							resultY = j;
						}
					}
				}
				shiftPiece(resultX, resultY);
			}
		});
	}

	public static void addButton(JButton button, int localX, int localY) {
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = localX;
		c.gridy = localY;
		c.ipadx = 100;
		c.ipady = 100;
		c.insets = new Insets(5, 5, 5, 5);
		buttonLayout[localX][localY] = button;
		panel.add(buttonLayout[localX][localY], c);

	}

	public static void shiftPiece(int localX, int localY) {

		if (Math.abs(localX - emptyX) == 1 && Math.abs(localY - emptyY) == 0
				|| Math.abs(localX - emptyX) == 0 && Math.abs(localY - emptyY) == 1) {
			buttonLayout[emptyX][emptyY].setText(buttonLayout[localX][localY].getText());
			buttonLayout[emptyX][emptyY].setBackground(null);
			Icon localIcon = buttonLayout[emptyX][emptyY].getIcon();
			buttonLayout[emptyX][emptyY].setIcon(buttonLayout[localX][localY].getIcon());
			buttonLayout[localX][localY].setIcon(localIcon);
			//buttonLayout[localX][localY].setText("X");
			buttonLayout[localX][localY].setBackground(Color.BLACK);
			emptyX = localX;
			emptyY = localY;
			panel.revalidate();
		}
		if (checkWin()) {
			for (JButton[] row : buttonLayout) {
				for (JButton j : row) {
					j.setEnabled(false);
				}
			}
			finalMethod();
		}
	}

	public static void finalMethod() {
		System.out.println("WOOO");
		winPic.setPreferredSize(new Dimension(900, 900));

		try {
			Image winImage = ImageIO.read(puzzle.class.getResource("DogeComplete.jpg"));
			Icon winIcon = new ImageIcon(winImage.getScaledInstance(1000, 1000, Image.SCALE_DEFAULT));
			winPic.setIcon(winIcon);
		} catch (IOException ex) {
		}
		winPanel.add(winPic);
		winPanel.add(winText);
		winFrame.add(winPanel);
		winFrame.setSize(1000, 1000);
		winFrame.setVisible(true);
		changeColor();
		winPic.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				changeColor();
				
			}});

	}

	public static boolean checkWin() {
		countOfCorrects = 0;
		System.out.println(buttonArray[3][3].getIcon());
		System.out.println(buttonLayout[3][3].getIcon());
		for (int i = 0; i <= 3; i++) {
			for (int o = 0; o <= 3; o++) {
				if (solution[i][o].getIcon().equals(buttonLayout[i][o].getIcon())) {
					System.out.println(i + " by " + o + "  is correct" );
					countOfCorrects++;
				}
			}
		}
		System.out.println(countOfCorrects);
		if (countOfCorrects == 16) {
			return true;
		} else {
			return false;
		}

	}
	public static void changeColor(){
		double randNum = Math.random();
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