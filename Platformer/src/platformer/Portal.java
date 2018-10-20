package platformer;
import java.awt.Color;
import java.awt.Graphics2D;
public class Portal
{
	private double cornerX;
	private double cornerY;
	private double size;
	private LevelStructure levelStructure;
	private String[] level;
	public Portal(double cornerX, double cornerY,
			double size, LevelStructure levelStructure)
	{
		this.cornerX = cornerX;
		this.cornerY = cornerY;
		this.levelStructure = levelStructure;
		this.size = size;
	}
	
	public Portal(String[] level, double size,
			LevelStructure levelStructure)
	{
		this.size = size;
		this.level = level;
		this.levelStructure = levelStructure;
		this.setPosition();
	}
	
	public void setPosition()
	{
		for (int row = 0; row < level.length; row++)
		{
			for (int col = 0; col < level[row].length(); col++)
			{
				if (level[row].substring(col, col+1).equals("P"))
				{
					this.cornerX = col*this.size;
					this.cornerY = row*this.size;
				}
			}
		}
	}
	
	public void drawPortal(Graphics2D g2)
	{
		this.setPosition();
		g2.setColor(Color.ORANGE);
		g2.fillRect(
				(int)(cornerX
						+ levelStructure.getCorner().getX()),
				(int)(cornerY
						+ levelStructure.getCorner().getY()),
				(int)size, (int)size);
	}
	
	public double x()
	{
		return cornerX;
	}
	
	public double y()
	{
		return cornerY;
	}
	
	public double size()
	{
		return size;
	}
}

