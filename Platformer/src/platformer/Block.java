package platformer;

import java.awt.Color;
import java.awt.Graphics2D;

public class Block
{
	private final double x;
	private final double y;
	private final double unit;
	private LevelStructure levelStructure;
	
	public Block(double x, double y, double unit, LevelStructure levelStructure)
	{
		this.x = x;
		this.y = y;
		this.unit = unit;
		this.levelStructure = levelStructure;
	}
	
	public void drawBlock(Graphics2D g2)
	{
		g2.setColor(Color.BLACK);
		g2.fillRect((int)(x + levelStructure.getCorner().getX()), (int)(y + levelStructure.getCorner().getY()), (int)unit, (int)unit);
	}
	
	public double getUnit()
	{
		return unit;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
}
