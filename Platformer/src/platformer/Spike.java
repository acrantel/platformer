package platformer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
public class Spike
{
	private int x;
	private int y;
	private double unit;
	private LevelStructure levelStructure;
	
	public Spike(double x, double y, double unit,
			LevelStructure levelStructure)
	{
		this.x = (int) x;
		this.y = (int) y;
		this.unit = unit;
		this.levelStructure = levelStructure;
	}
	
	public Polygon getTriangle()
	{
		int updatedX = (int)(this.x + this.levelStructure.getCorner().getX());
		int updatedY = (int)(this.y + this.levelStructure.getCorner().getY());
		int[] xPoints = {
				(int)((updatedX + (unit/2))),//top
				(int)((updatedX)),//left-bottom
				(int)(updatedX + unit),//right-bottom
		};
		int[] yPoints = {
				(int)(updatedY),//top
				(int)(updatedY + unit),//left-bottom
				(int)(updatedY + unit),//right-bottom
		};
		Polygon triangle = new Polygon(xPoints, yPoints, 3);
		return triangle;
	}
	
	public void drawSpike(Graphics2D g2)
	{
		g2.setColor(Color.RED);
		g2.fill(getTriangle());
	}
	
}

