import java.awt.*;
import java.awt.image.BufferStrategy;       
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import java.util.Random;
import java.awt.image.BufferStrategy; 

public class Game extends JFrame implements KeyListener{

	public static final int WINDOW_WIDTH = 1000; 
	public static final int WINDOW_HEIGHT = 770;
	public static final int BORDER_SIZE_Y = 50;
	public static final int BORDER_SIZE_X = BORDER_SIZE_Y*2;
	public static final int BOARD_START_X = BORDER_SIZE_X;
	public static final int BOARD_START_Y = BORDER_SIZE_Y;
	public static final int TILE_SIZE = WINDOW_WIDTH - BORDER_SIZE_X*2 -200;



	public static Board board;

	public static void main(String[] args)
	{

		Game game = new Game();
		//game starts with 2 new tiles
		board.newTile();
		board.newTile();
		game.repaint();

	}

	public Game(){

		board = new Board();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setTitle("2048");
		setVisible(true);
		addKeyListener(this);
		createBufferStrategy(2);
	}

	public void keyTyped(KeyEvent e)
	{
		//Empty
	}

	public void keyReleased(KeyEvent e)
	{
		//Empty
	}	

	public void keyPressed(KeyEvent e)
	{

		if((e.getKeyChar() == 'w' || e.getKeyCode() == KeyEvent.VK_UP)&&(board.getGameOver() == false))
		{
			//creates a new tile after the movement, this way if the game is over a new tile will never try to spawn
			board.up();   
			board.newTile();
			repaint();

		}
		else if((e.getKeyChar() == 's' || e.getKeyCode() == KeyEvent.VK_DOWN)&&(board.getGameOver() == false))
		{

			board.down();   
			board.newTile();
			repaint(); 

		}
		else if((e.getKeyChar() == 'a' || e.getKeyCode() == KeyEvent.VK_LEFT)&&(board.getGameOver() == false))
		{

			board.left();   
			board.newTile();
			repaint(); 

		}
		else if((e.getKeyChar() == 'd' || e.getKeyCode() == KeyEvent.VK_RIGHT)&&(board.getGameOver() == false))
		{

			board.right();   
			board.newTile();
			repaint();

		}
		else if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			//resets the board, sets score to 0 but keeps the high score, changes the game over boolean to false, 
			//and then repaints the board
			board.boardReset();
			board.setScore(0);
			board.changeGameOver(false);
			repaint();
		}
		else if (e.getKeyCode() == KeyEvent.VK_SHIFT)
		{
			board.changeEncouragement();
		}

		//		for testing:
		//		Tile.print(board.getBoard());

	}

	public void paint(Graphics g)
	{
		//sets up buffering
		BufferStrategy Bs = getBufferStrategy(); 
		if (Bs == null)
			return;
		Graphics g2 = null;
		try 
		{
			g2 = Bs.getDrawGraphics();
			myPaint(g2);
		} 
		finally 
		{  
			g2.dispose();
		}
		Bs.show();
		Toolkit.getDefaultToolkit().sync(); 


	}


	public void myPaint(Graphics g)
	{
		//if the game is over, draw the game is over board, if not, cancel the game over board/draw the regular board 
		if(board.gameOver())
		{
			gameOverBoard(g);
			board.changeGameOver(true);
			board.boardReset();
			board.setScore(0);
		}
		else
		{
			board.changeGameOver(false);
			g.setColor(Color.white);
			g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
			draw(g);
		}
	}

	public void draw(Graphics g) {
		//draws board, score and checks if encouragment is on
		board.drawBoard(g);
		board.drawScore(g);
		encouragementCheck(g);
	}
	
	public void encouragementCheck(Graphics g)
	{
		//If the highestNum on the board is different (higher) then it triggers the board encouragement
		//printing, and then turns the encouragment off
		if(board.getHighestNumChanged())
		{
			board.encouragement(g);
			board.highestNumChanged(false);
		}
	}
	
	public void gameOverBoard(Graphics g)
	{
		//draws the game over board
		g.setColor(Color.black);
		g.fillRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
		g.setColor(Color.white);
		g.setFont(new Font("SansSerif", Font.BOLD,75));
		g.drawString("Game Over",302,375);
		g.setFont(new Font("SansSerif", Font.BOLD,25));
		g.drawString("Press space to restart",380,450);
	}
}