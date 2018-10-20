package levelMaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Timer;

import javax.swing.JComponent;

public class MakerComponent extends JComponent
{
	private static final long serialVersionUID = 1L;

	private String[][] grid = new String[WIDTH][HEIGHT];
	
	private final Timer timer = new Timer();
	
	public MakerComponent()
	{
		// setting up the grid
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[i].length; j++)
			{
				if (i == 0 || i == grid.length-1 || 
						j == 0 || j == grid.length-1)
				{
					grid[i][j] = BLOCK;
				} else
				{
					grid[i][j] = EMPTY;
				}
			}
		}
		// Starting the timer to call paintComponent
		timer.schedule(new MakerRunner(this), 300, 300);
	}
	
	
	public void paintComponent(Graphics g)
	{
		this.setVisible(true);
		Graphics2D g2 = (Graphics2D) g;
		int squareWidth = this.getWidth()/WIDTH;
		int squareHeight = this.getHeight()/HEIGHT;
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[i].length; j++)
			{
				// drawing the blocks/spikes/players/portals
				if (grid[i][j].equals(BLOCK))
				{
					g2.setColor(Color.BLACK);
					g2.fill(new Rectangle(i*squareWidth, j*squareHeight, 
							squareWidth, squareHeight));
				}
				else if (grid[i][j].equals(PLAYER))
				{
					g2.setColor(Color.PINK);
					g2.fill(new Rectangle(i*squareWidth, j*squareHeight, 
							squareWidth, squareHeight));
				}
				else if (grid[i][j].equals(PORTAL))
				{
					g2.setColor(Color.CYAN);
					g2.fill(new Rectangle(i*squareWidth, j*squareHeight,
							squareWidth, squareHeight));
				}
				else if (grid[i][j].equals(SPIKE))
				{
					g2.setColor(Color.RED);
					g2.fill(new Rectangle(i*squareWidth, j*squareHeight + squareHeight/2, 
							squareWidth, squareHeight/2));
				}
			}
		}

	}
	
	/**
	 * Called by MousePressedListener in MakerTester. 
	 * Updates a value for the grid
	 */
	public void updateGrid(double x, double y)
	{
		this.setVisible(true);
		int squareWidth = this.getWidth()/WIDTH;
		int squareHeight = this.getHeight()/HEIGHT;

		int indexX = -1;
		int indexY = -1;
		
		for (int i = 0; i < WIDTH; i++)
		{
			if (i*squareWidth <= x && (i+1)*squareWidth >= x)
			{
				indexX = i;
				continue;
			}
		}
		for (int j = 0; j < HEIGHT; j++)
		{
			if (j*squareHeight <= y && (j+1)*squareHeight >= y)
			{
				indexY = j;
				continue;
			}
		}
		// if the user clicks convert button
		if (indexY == -1 || indexX == -1)
		{
			this.convert();
		} else
			this.setNextPiece(indexX, indexY);
	}
	
	public void setNextPiece(int x, int y)
	{
		String currentPiece = grid[x][y];
		if (currentPiece.equals(BLOCK))
			grid[x][y] = PLAYER;
		else if (currentPiece.equals(PLAYER))
			grid[x][y] = PORTAL;
		else if (currentPiece.equals(PORTAL))
			grid[x][y] = EMPTY;
		else if (currentPiece.equals(EMPTY))
			grid[x][y] = SPIKE;
		else if (currentPiece.equals(SPIKE))
			grid[x][y] = BLOCK;
	}
	
	public void convert()
	{
		System.out.println("THE START -------------------");
		
		System.out.println();
		for (int i = 0; i < grid.length; i++)
		{
			System.out.print("\"");
			for (int j = 0; j < grid[i].length; j++)
			{
				System.out.print(grid[j][i]);
			}
			System.out.println("\",");
		}
		
		System.out.println("THE END --------------");
	}
	public static final String BLOCK = "1";
	public static final String PLAYER = "2";
	public static final String PORTAL = "P";
	public static final String EMPTY = " ";
	public static final String SPIKE = "^";

	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;

}
