import java.awt.Color;
import java.awt.*;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.awt.geom.AffineTransform;
import javax.swing.SwingUtilities;

public class Board {

	public Tile[][] board;

	private final int rows = 4;
	private final int cols = 4;
	public static int score = 0;
	public static int highScore = 0;
	public static boolean gameOver = false;
	public static int highestNum =4;
	public static boolean highestNumChanged = false;
	public static boolean encouragementOn = false;

	public Board()
	{
		//default board
		board = new Tile[rows][cols];
		for(int i = 0; i < board.length; i++)
		{
			for(int k = 0; k < board[i].length;k++)
			{
				board[i][k] = new Tile();
				board[i][k].setValue(0);
			}
		}

	}

	public void boardReset()
	{
		//does what the method says, recreates an empty board full of 0's
		for(int i = 0; i < board.length; i++)
		{
			for(int k = 0; k < board[i].length;k++)
			{
				board[i][k].setValue(0);
			}
		}
		newTile();
		newTile();
	}

	public Tile[][] getBoard()
	{
		return board;
	}


	public Tile getTile(int row, int col)
	{
		return board[row][col];
	}

	public int getRows()
	{
		return this.rows;
	}

	public int getCols()
	{
		return this.cols;
	}

	public void changeGameOver(boolean over)
	{
		gameOver = over;
	}

	public boolean getGameOver()
	{
		return gameOver;
	}

	public boolean getEncouragement()
	{
		return encouragementOn;
	}

	public void changeEncouragement()
	{
		if(encouragementOn)
		{
			encouragementOn = false;
		}
		else
		{
			encouragementOn = true;
		}
	}

	public void increaseScore(int num)
	{
		score += num;
		if(score>highScore)
		{
			highScore = score;
		}
		if(num > highestNum)
		{
			highestNum = num;
			highestNumChanged = true;
		}
	}

	public void highestNumChanged(boolean result)
	{
		highestNumChanged = result;
	}

	public boolean getHighestNumChanged()
	{
		return highestNumChanged;
	}

	public void setHighestNum(int num)
	{
		highestNum = num;
	}

	public int getHighestNum()
	{
		return highestNum;
	}

	public void setScore(int num)
	{
		score = num;
	}

	public int getScore()
	{
		return score;
	}

	public void up()
	{
		int myValue;
		int aboveValue;
		int twoAboveValue;
		int threeAboveValue;

		//checks to see if there is a zero above the tile

		//add an forever if that if there is no value above keep repeating moving it up until there either isn't anything above of that there is a number above
		//looks over every tile, excluding the top row since that cannot move farther upward
		for(int k = 1; k < rows; k++) {
			for(int i = 0; i < cols; i++) {

				//reset
				myValue = 0;
				aboveValue = 0;
				twoAboveValue = 0;
				threeAboveValue = 0;



				//Get's the value of the tile and the tile above it

				Tile myTile = getTile(k, i);
				myValue = myTile.getValue();


				//The tile above it
				Tile aboveTile = getTile(k-1,i);
				aboveValue = aboveTile.getValue();


				//to prevent error, if there is tiles above the tile, we want those values as well




				//If the tile isn't zero, because if it is zero we don't want to do anything
				if(myValue != 0)
				{
					//If the tile above is zero, we want to move the tile up until it either hits the top or a tile above it
					if(aboveValue==0)
					{

						aboveTile.setValue(myValue);
						myTile.setValue(0);
						aboveValue = myValue;
						myValue = 0;

						if(k >= 2)
						{
							Tile twoAboveTile = getTile(k - 2,i);
							twoAboveValue = twoAboveTile.getValue();

							if(twoAboveValue==0)
							{
								//if both tiles above the tile are a 0, and it is further down then the 2nd
								//row, then it moves up twice
								twoAboveTile.setValue(aboveValue);
								aboveTile.setValue(0);
								twoAboveValue = aboveValue;
								aboveValue = 0;

								if(k >= 3)
								{
									Tile threeAboveTile = getTile(k - 3,i);
									threeAboveValue = threeAboveTile.getValue();

									if(threeAboveValue==0)
									{
										//if the tile is on the bottom row, and every other if statement
										//is true (meaning that there is not a number above this number),
										//then we move up again and again until we reach the top
										threeAboveTile.setValue(twoAboveValue);
										twoAboveTile.setValue(0);
										threeAboveValue = twoAboveValue;
										twoAboveValue = 0;
									}
									else if(threeAboveValue == twoAboveValue)
									{
										//if the tile moves up twice, but then there is a number of identical size
										//then we merge
										threeAboveTile.setValue(twoAboveValue*2);
										threeAboveValue = threeAboveTile.getValue();
										twoAboveTile.setValue(0);
										twoAboveValue = 0;
										increaseScore(threeAboveValue);
									}
								}	
							}
							else if(twoAboveValue == aboveValue)
							{
								//If the tile moves up one, but then there is a tile of identical size, then merge
								twoAboveTile.setValue(aboveValue*2);
								twoAboveValue = twoAboveTile.getValue();
								aboveTile.setValue(0);
								aboveValue = 0;
								increaseScore(twoAboveValue);
							}

						}

					}

					//just an if statement, because if the tile moves up, we want it to merge if it moves into another tile
					else if(aboveValue == myValue)
					{
						aboveTile.setValue(myValue*2);
						aboveValue = aboveTile.getValue();
						myTile.setValue(0);
						myValue = 0;
						increaseScore(aboveValue);
					}
					//here is the error, it only merges when it's ABOVE value, not just every value able to merge, like 2 above
					//value or something
				}

			}
			//ignore
		}
	}






	public void down()
	{
		//For a full description, check the up() method
		int myValue;
		int belowValue;
		int twoBelowValue;
		int threeBelowValue;

		for(int k = rows -2; k >= 0; k--) {
			for(int i = 0; i < cols; i++) {

				myValue = 0;
				belowValue = 0;
				twoBelowValue = 0;
				threeBelowValue = 0;

				Tile myTile = getTile(k, i);
				myValue = myTile.getValue();

				Tile belowTile = getTile(k+1,i);
				belowValue = belowTile.getValue();

				if(myValue != 0)
				{
					//On row 3
					if(belowValue==0)
					{

						belowTile.setValue(myValue);
						myTile.setValue(0);
						belowValue = myValue;
						myValue = 0;

						//row 2
						if(k < 2)
						{
							Tile twoBelowTile = getTile(k + 2,i);
							twoBelowValue = twoBelowTile.getValue();

							if(twoBelowValue==0)
							{
								twoBelowTile.setValue(belowValue);
								belowTile.setValue(0);
								twoBelowValue = belowValue;
								belowValue = 0;
								//row 1
								if(k < 1)
								{
									Tile threeBelowTile = getTile(k + 3,i);
									threeBelowValue = threeBelowTile.getValue();

									if(threeBelowValue==0)
									{
										threeBelowTile.setValue(twoBelowValue);
										twoBelowTile.setValue(0);
										threeBelowValue = twoBelowValue;
										twoBelowValue = 0;
									}

									else if(threeBelowValue == twoBelowValue)
									{
										threeBelowTile.setValue(twoBelowValue*2);
										threeBelowValue = threeBelowTile.getValue();
										twoBelowTile.setValue(0);
										twoBelowValue = 0;
										increaseScore(threeBelowValue);
									}
								}	
							}
							else if(twoBelowValue == belowValue)
							{
								twoBelowTile.setValue(belowValue*2);
								twoBelowValue = twoBelowTile.getValue();
								belowTile.setValue(0);
								belowValue = 0;
								increaseScore(twoBelowValue);
							}

						}

					}

					else if(belowValue == myValue)
					{
						belowTile.setValue(myValue*2);
						belowValue = belowTile.getValue();
						myTile.setValue(0);
						myValue = 0;
						increaseScore(belowValue);
					}

				}

			}
		}		

	}

	public void right()
	{
		//For a full description, check the up() method
		int myValue;
		int rightValue;
		int twoRightValue;
		int threeRightValue;

		for(int i = cols -2; i >= 0; i--) {
			for(int k = 0; k < rows; k++) {

				myValue = 0;
				rightValue = 0;
				twoRightValue = 0;
				threeRightValue = 0;

				Tile myTile = getTile(k, i);
				myValue = myTile.getValue();

				Tile rightTile = getTile(k,i+1);
				rightValue = rightTile.getValue();

				if(myValue != 0)
				{

					if(rightValue==0)
					{

						rightTile.setValue(myValue);
						myTile.setValue(0);
						rightValue = myValue;
						myValue = 0;

						if(i < 2)
						{
							Tile twoRightTile = getTile(k,i+2);
							twoRightValue = twoRightTile.getValue();

							if(twoRightValue==0)
							{
								twoRightTile.setValue(rightValue);
								rightTile.setValue(0);
								twoRightValue = rightValue;
								rightValue = 0;

								if(k < 1)
								{
									Tile threeRightTile = getTile(k + 3,i);
									threeRightValue = threeRightTile.getValue();

									if(threeRightValue==0)
									{
										threeRightTile.setValue(twoRightValue);
										twoRightTile.setValue(0);
										threeRightValue = twoRightValue;
										twoRightValue = 0;
									}

									else if(threeRightValue == twoRightValue)
									{
										threeRightTile.setValue(twoRightValue*2);
										threeRightValue = threeRightTile.getValue();
										twoRightTile.setValue(0);
										twoRightValue = 0;
										increaseScore(threeRightValue);
									}
								}	
							}
							else if(twoRightValue == rightValue)
							{
								twoRightTile.setValue(rightValue*2);
								twoRightValue = twoRightTile.getValue();
								rightTile.setValue(0);
								rightValue = 0;
								increaseScore(twoRightValue);
							}

						}

					}

					else if(rightValue == myValue)
					{
						rightTile.setValue(myValue*2);
						rightValue = rightTile.getValue();
						myTile.setValue(0);
						myValue = 0;
						increaseScore(rightValue);
					}

				}

			}
		}
	}

	public void left()
	{
		//For a full description, check the up() method
		int myValue;
		int leftValue;
		int twoLeftValue;
		int threeLeftValue;

		for(int i = 1; i < cols; i++) {
			for(int k = 0; k < rows; k++) {

				myValue = 0;
				leftValue = 0;
				twoLeftValue = 0;
				threeLeftValue = 0;

				Tile myTile = getTile(k, i);
				myValue = myTile.getValue();

				Tile leftTile = getTile(k,i-1);
				leftValue = leftTile.getValue();

				if(myValue != 0)
				{

					if(leftValue==0)
					{

						leftTile.setValue(myValue);
						myTile.setValue(0);
						leftValue = myValue;
						myValue = 0;

						if(i > 1)
						{
							Tile twoLeftTile = getTile(k,i-2);
							twoLeftValue = twoLeftTile.getValue();

							if(twoLeftValue==0)
							{
								twoLeftTile.setValue(leftValue);
								leftTile.setValue(0);
								twoLeftValue = leftValue;
								leftValue = 0;

								if(k > 2)
								{
									Tile threeLeftTile = getTile(k - 3,i);
									threeLeftValue = threeLeftTile.getValue();

									if(threeLeftValue==0)
									{
										threeLeftTile.setValue(twoLeftValue);
										twoLeftTile.setValue(0);
										threeLeftValue = twoLeftValue;
										twoLeftValue = 0;
									}

									else if(threeLeftValue == twoLeftValue)
									{
										threeLeftTile.setValue(twoLeftValue*2);
										threeLeftValue = threeLeftTile.getValue();
										twoLeftTile.setValue(0);
										twoLeftValue = 0;
										increaseScore(threeLeftValue);
									}
								}	
							}

							else if(twoLeftValue == leftValue)
							{
								twoLeftTile.setValue(leftValue*2);
								twoLeftValue = twoLeftTile.getValue();
								leftTile.setValue(0);
								leftValue = 0;
								increaseScore(twoLeftValue);
							}

						}

					}

					else if(leftValue == myValue)
					{
						leftTile.setValue(myValue*2);
						leftValue = leftTile.getValue();
						myTile.setValue(0);
						myValue = 0;
						increaseScore(leftValue);
					}

				}

			}
		}
	}

	public void drawScore(Graphics g)
	{

		g.setColor(Color.GRAY);
		//score box
		g.fillRoundRect(600, 50, 150, 40, 5, 5);
		//highScore box
		g.fillRoundRect(440, 50, 150, 40, 5, 5);
		//2048 string
		g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 75));	
		g.drawString("2048", 275, 90);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 20));
		//Score String
		g.drawString( "Score: " + score, 610, 75);
		//Highscore String
		g.drawString( "Best: " + highScore, 450, 75);


	}

	public boolean gameOver()
	{
		//to see if the game is over, it checks whether there are any 0's on the board and 
		//whether any combinations can be made. If there is, boolean gameOver is positive.
		//Otherwise the game continues
		return cannotCombineBoard() && boardFillCheck();
	}

	public boolean boardFillCheck()
	{
		//checks if any elements of board still contain zeros
		int count = 0;
		for(int j = 0; j<rows;j++)
		{
			for(int f = 0; f < cols; f++)
			{
				if(board[j][f].getValue()>0)
				{
					count++;
				}
			}
		}

		if(count == rows*cols)
		{
			return true;
		}

		return false;
	}

	public void drawBoard(Graphics g)
	{
		//draws the 
		Tile.drawTiles(g,board);
		g.setColor(Color.gray);
		g.fillRoundRect(200,590,620,150,5,5);
		g.setColor(Color.white);
		g.setFont(new Font("SansSerif",Font.BOLD,20));
		g.drawString("HOW TO PLAY: Use your arrow keys to move the tiles. ",225,615);
		g.drawString("Tiles with the same number merge into one when they",225,645);
		g.drawString("touch. Add them up to reach 2048!",225,675);
		g.drawString(("Press space to restart | Press shift for encouragement"), 225, 725);
	}

	public void encouragement(Graphics g)
	{
		//If the shift key turns encouragement on, and a new highest number is created (condition
		//in the game class) a random compliment is chosen from an array and then printed while rotated
		if(encouragementOn)
		{
			String[] arr = {"Great job!","Impressive!","Keep going!","Nice merge!"};
			int rand = (int)(Math.random()*arr.length);
			g.setColor(Color.red);
			g.setFont(new Font("SansSerif",Font.BOLD,100));
			Graphics2D g2d = (Graphics2D) g;
			AffineTransform defaultAt = g2d.getTransform();

			// rotates the coordinate by counterclockwise

			//Appropriated from https://www.codejava.net/java-se/graphics/how-to-draw-text-vertically-with-graphics2d
			AffineTransform at = new AffineTransform();
			at.rotate(- Math.PI / 4);
			g2d.setTransform(at);
			g.drawString(arr[rand], 0,1800);
		}
	}
	/***************************************************************************************
	 *    I didn't understand how 2048's random number generation worked so I looked at this
	 *    article that explained it:
	 *
	 *    "When we start, the board will have two tiles in random locations, each of which
	 *    either has a “2” or a “4” on it – each has an independent 10% chance 
	 *    of being a “4”, or otherwise a is a “2”
	 *    
	 *    Cox, Graham. “What Is the Optimal Algorithm for the Game 2048?” Baeldung on Computer
	 *    Science, 20 May 2020, www.baeldung.com/cs/2048-algorithm#:~:text=A%20game%20of%202048
	 *    %20is%20played%20on%20a%204%C3%974%20board.&amp;text=When%20we%20start%2C%20the%20board
	 *    ,a%20is%20a%20%E2%80%9C2%E2%80%9D. 
	 *    
	 *    Code is 100% original
	 ***************************************************************************************/
	
	
	public void newTile()
	{
		
		//Searches through all the available spots on the board and then creates a 1:4 probablility
		//for generating a 4 or a 2
		boolean empty = true;
		while(empty)
		{

			int row = (int)(Math.random()*4);
			int col = (int)(Math.random()*4);

			if ( board[row][col].getValue() == 0 )
			{
				if(1==(int)(Math.random()*10))
				{

					board[row][col] = new Tile(4);
					empty = false;

				}

				else 
				{

					board[row][col] = new Tile(2);
					empty = false;

				}

			}
		}
	}


	public boolean cannotCombineBoard()
	{
		//moves through all the elements of board to see a combination is possible by sending
		//individual tiles through the canCombineTile method
		for(int i = 0; i < rows-1; i++)
		{ 
			for(int k = 0; k < cols-1; k++)
			{
				if(canCombineTile(i,k) == true)
				{
					return true;
				}
			}
		}

		return false;

	}

	public boolean canCombineTile(int row, int col)
	{

		//moves through the individual tile. If it is against the walls of the board it doesn't count whether it can
		//combine with a tile outside of the walls to prevent out of bounds error
		if(col != cols)
		{
			if(board[row][col].getValue() == board[row][col + 1].getValue())
			{

				return true;

			}
		}

		if(col != 0)
		{
			if(board[row][col].getValue() == board[row][col - 1].getValue())
			{

				return true;

			}
		}

		if(row != rows)
		{
			if(board[row][col].getValue() == board[row + 1][col].getValue())
			{

				return true;

			}
		}

		if(row != 0)
		{
			if(board[row][col].getValue() == board[row - 1][col].getValue())
			{
				return true;
			}
		}

		return false;

	}
}