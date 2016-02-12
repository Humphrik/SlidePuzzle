package Puzzle;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class puzzle {
	static JFrame frame = new JFrame("Puzzle");
	static JPanel panel = new JPanel(new GridBagLayout());
	static GridBagConstraints c = new GridBagConstraints();
	static JButton bOne, bTwo, bThree, bFour, bFive, bSix, bSeven, bEight, bNine, bTen, bEleven, bTwelve, bThirteen, bFourteen, bFifteen, bSixteen;
	static int emptyX, emptyY, x, y;
	static JButton[][] buttonArray = new JButton[4][4];
	static JButton[][] buttonLayout = new JButton[4][4];
	static boolean[][] taken = new boolean[4][4];
	static Font font = new Font("Comic Sans MS", Font.BOLD, 36);
	public boolean won;
	public int countOfCorrects;
	public static void main(String[] args) {
		emptyX = 3;
		emptyY = 3;
		setDisplay();
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
		makeButton(bNine, 2, 0,"09");
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
					addButton(j,3,3);
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

	}

	public static void makeButton(JButton button, int localX, int localY, String text) {
		button = new JButton(text);
		button.setFont(font);
		JButton localButton = button;
		buttonArray[localX][localY] = button;
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int resultX = 0;
				int resultY = 0;
				for(int i = 0; i <= 3; i++){
					for(int j = 0; j <= 3; j++){
						if(buttonLayout[i][j] == localButton){
							System.out.println("localX = " + i);
							System.out.println("localY = " + j);
							resultX = i;
							resultY = j;
						}
					}
				}
				shiftPiece(resultX,resultY);
			}
		});
	}

	public static void addButton(JButton button, int localX, int localY) {
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = localX;
		c.gridy = localY;
		c.ipadx = 150;
		c.ipady = 150;
		c.insets = new Insets(10, 10, 10, 10);
		buttonLayout[localX][localY] = button;
		panel.add(buttonLayout[localX][localY], c);

	}

	public static void shiftPiece(int localX, int localY) {
		System.out.println("emptyX = " + emptyX);
		System.out.println("emptyY = " + emptyY);
		if (Math.abs(localX - emptyX) == 1 && Math.abs(localY - emptyY) == 0 || Math.abs(localX - emptyX) == 0 && Math.abs(localY - emptyY) == 1 ) {
			System.out.println("Swapping");
			buttonLayout[emptyX][emptyY].setText(buttonLayout[localX][localY].getText());
			buttonLayout[emptyX][emptyY].setBackground(null);
			buttonLayout[localX][localY].setText("X");
			buttonLayout[localX][localY].setBackground(Color.BLACK);
			emptyX = localX;
			emptyY = localY;
		}
	}
	public static boolean checkWin(){
		won = false;
		countOfCorrects = 0;
		for (int i = 0; i<=3; i++){
			for (int o = 0; o<=3; o++){
				if (buttonArray[i][o].getText() == buttonLayout[i][o].getText()) {
					countOfCorrects++;
				} 				
			}
		}
		if (countOfCorrects == 16) {
			won == true;	
		} else {
			won == false;
		}
		return won;
	
	}
}
