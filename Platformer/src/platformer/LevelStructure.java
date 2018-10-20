package platformer;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.JFrame;
public class LevelStructure
{
	/** the array of blocks in the level */
	public final ArrayList<Block> blocksLevel;
	/** the array of the spikes in the level */
	public final ArrayList<Spike> spikes;
	
	/** the 1s are blocks, 2s are players
	 * ^'s are spikes
	 * P is portal
	 * level one */
	private final String[][] levels = {
			{
				"12P1",
				"1111"
			},
			{
				"111111111111111111111111111111",
				"1                           P1",
				"1                            1",
				"1         1 1 1  1  1111111111",
				"1        1                   1",
				"1       1                    1",
				"1      1                     1",
				"1  1111^^^^^^^^^^^^^^^^^^^^^^1",
				"1                            1",
				"11                           1",
				"1                            1",
				"1                            1",
				"1 1                          1",
				"1    1       1               1",
				"1                            1",
				"1                1  1^1^111  1",
				"1        1                   1",
				"1                            1",
				"1                            1",
				"1^^^^^^^^^^^^^^^^^^^^^    1  1",
				"1                            1",
				"1                            1",
				"1                           ^1",
				"1                         1111",
				"1                            1",
				"1              1   1         1",
				"1          1            1    1",
				"1     1                      1",
				"1211^^^^^^^^^^^^^^^^^^^^^^^^^1",
				"111111111111111111111111111111",
			}
	};
	/** the current level number */
	private int levelNum;
	private int unit = 40;
	private Player player;
	private Portal portal;
	
	/** the corner for moving camera? */
	private Point2D.Double corner;
	private JFrame frame;
	
	public LevelStructure(int levelNum, JFrame frame)
	{
		blocksLevel = levelStructure(levelNum);
		String[] currentLevel = this.getLevel(levelNum);
		Point2D.Double playerBegin = this.getPlayerBeginPoint(currentLevel);
		
		this.spikes = this.getSpikes(currentLevel);
		this.player = new Player(playerBegin.getX(), playerBegin.getY(),
				this.unit, this);
		this.portal = new Portal(currentLevel, this.unit, this);
		
		this.frame = frame;
		this.corner = getBeginCorner(currentLevel);
		this.levelNum = levelNum;
	}
	
	/**
	 * gets the beginning corner of the whole level
	 */
	public Point2D.Double getBeginCorner(String[] level)
	{
		int canvasCenterX = this.frame.getWidth()/2;
		int canvasCenterY = this.frame.getHeight()/2 - 100;
		Point2D.Double corner = new Point2D.Double();
		int currentX;
		int currentY;
		for (int row = 0; row < level.length; row++)
		{
			for (int col = 0; col < level[row].length(); col++)
			{
				currentX = (int)(col*unit);
				currentY = (int)(row*unit);
				if (level[row].substring(col, col+1).equals("2"))
				{
					corner = new Point2D.Double(canvasCenterX - currentX,
							canvasCenterY - currentY);
				}
			}
		}
		return corner;
	}
	
	/** Gets and returns the ArrayList of spikes*/
	public ArrayList<Spike> getSpikes(String[] level)
	{
		ArrayList<Spike> spikes = new ArrayList<Spike>();
		for (int row = 0; row < level.length; row++)
		{
			for (int col = 0; col < level[row].length(); col++)
			{
				if (level[row].substring(col, col+1).equals("^"))
				{
					spikes.add(new Spike(col*unit, row*unit, unit, this));
				}
			}
		}
		return spikes;
	}
	
	public Point2D.Double getPlayerBeginPoint(String[] level)
	{
		Point2D.Double playerPoint = new Point2D.Double(0, 0);
		for (int row = 0; row < level.length; row++)
		{
			for (int col = 0; col < level[row].length(); col++)
			{
				if (level[row].substring(col, col+1).equals("2"))
					playerPoint = new Point2D.Double(col*unit, row*unit);
			}
		}
		return playerPoint;
	}
	
	public ArrayList<Block> levelStructure(int levelNum)
	{
		ArrayList<Block> blocks = new ArrayList<Block>();
		//creating blocks
		String[] level = this.getLevel(levelNum);
		for (int row = 0; row < level.length; row++)
		{
			for (int col = 0; col < level[row].length(); col++)
			{
				if (level[row].substring(col, col+1).equals("1"))
					blocks.add(new Block(
							(int)(col*unit), (int)(row*unit),
							(int)unit, this));
			}
		}
		
		return blocks;
	}
	
	public String[] getLevel(int levelNum)
	{
		return levels[levelNum % levels.length];
		
	}
	
	public int getLevelNum()
	{
		return levelNum;
	}
	
	public int getUnit()
	{
		return this.unit;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	public Portal getPortal()
	{
		return portal;
	}
	
	public Point2D.Double getCorner()
	{
		return corner;
	}
	
}