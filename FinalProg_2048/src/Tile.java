import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Tile {

	int value;
	public Color color;
	static int theTileSize = Game.TILE_SIZE;
	static int theBoardStartX = Game.BOARD_START_X;
	static int theBoardStartY = Game.BOARD_START_Y;
	static int theWindowWidth = Game.WINDOW_WIDTH;
	static int theWindowHeight = Game.WINDOW_HEIGHT;
	//has two constructors so that the tile can be empty and it can have a number if the random number generator assigns it one
	public Tile()
	{
		value = 0;
	}

	public Tile(int number)
	{
		value = number;
	}
	
	public int getValue()
	{
		return value;
	}

	public void setValue(int value)
	{

		this.value = value;

	}

	public void setColor()
	{
		//for each new value, the tile gets a new color
        if ( this.getValue() == 2 )
        {
            color = new Color( 89,99,101 );
        }
        else if ( this.getValue() == 4 )
        {
            color = new Color( 237, 224, 200 );
        }
        else if ( this.getValue() == 8 )
        {
            color = new Color( 242, 177, 121 );
        }
        else if ( this.getValue() == 16 )
        {
            color = new Color( 245, 149, 99 );
        }
        else if ( this.getValue() == 32 )
        {
            color = new Color( 246, 124, 95 );
        }
        else if ( this.getValue() == 64 )
        {
            color = new Color( 246, 94, 59 );
        }
        else if ( this.getValue() == 128 )
        {
            color = new Color( 237, 207, 114 );
        }
        else if ( this.getValue() == 256 )
        {
            color = new Color( 237, 204, 97 );
        }
        else if ( this.getValue() == 512 )
        {
            color = new Color( 237, 200, 80 );
        }
        else if ( this.getValue() == 1024 )
        {
            color = new Color( 237, 197, 63 );
        }
        else
        {
            color = new Color( 237, 194, 46 );
        }
	}
	
	public Color getColor()
	{
		this.setColor();
		return color;
		
	}
	
	//for testing purposes
	
	public static void print(Tile [][] board)
    {
		//draws the board in the console for testing
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                String s = board[i][j].getValue() + " ";
                System.out.print( s );
            }
            System.out.println( "" );
        }
    }

	public static void drawTiles(Graphics g, Tile[][] board)
	{
//draws the tiles by calling each individual tile in the drawTile method
		g.setColor(Color.gray);
		g.fillRoundRect(275,100,475,475,5,5);
		for ( int i = 0; i < 4; i++ )
		{
			for ( int j = 0; j < 4; j++ )
			{
				drawTile( g, board[i][j],j * 60 + 150, i * 60 + 60 );
			}
		}
	}

	public static void drawTile(Graphics g, Tile tile,int x, int y)
	{
		//gets the value and the size of the text that it wants to be, and then draws it
		int tileValue = tile.getValue();
		g.setColor(tile.getColor());
		g.fillRoundRect( x*2, y*2, 70, 70, 5, 5 );
		if ( tileValue > 0 )
		{
			g.setColor( Color.GRAY);
			g.setFont(new Font("Serif", Font.BOLD, stringSizeCalculator(String.valueOf(tileValue).length())));	
			g.drawString( "" + tileValue, x*2+20, y*2+50);	

		}
	}
	
	public static int stringSizeCalculator(int length)
	{
		//calculates the appropriate font size for the tile based on how large the tile is
		if(length == 1)
		{
			return 50;
		}
		else if(length == 2)
		{
			return 40;
		}
		else if(length == 3)
		{
			return 30;
		}
		else
		{
			return 15;
		}
	}

}
