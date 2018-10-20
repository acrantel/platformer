package platformer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
public class PlatformComponent extends JComponent
{
	
	private static final long serialVersionUID = 2718336315532527134L;
	
	private LevelStructure levelStructure;
	private Player player;
	private final Timer timer = new Timer();
	private TimerTask runner;
	private boolean[] keys = {false, false, false};
	private int levelNum = 0;
	private JFrame frame;
	
	public PlatformComponent(JFrame frame)
	{
		this.frame = frame;
		this.setFocusable(true);
		runner = new PlatformRunner(this);
		levelStructure = new LevelStructure(levelNum, frame);
		player = levelStructure.getPlayer();
		timer.schedule(runner, 1, 20);
		
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		if (player.getIsAlive())
		{
			player = levelStructure.getPlayer();
			player.update(keys);
			if (player.touchPortal(player, levelStructure.getPortal()))
			{
				levelNum++;
				goToNextLevel(levelNum);
				//reset player health
				player.resetHealth();
				this.drawLevel(g2);
			}
			player.draw(g2);
			this.drawLevel(g2);
			this.displayHealthBar(g2);
		} else //player is dead
		{
			this.displayEndScreen(g2);
		}
	}
	
	public void updatePlayer()
	{
		this.player.update(keys);
	}
	
	public void displayHealthBar(Graphics2D g2)
	{
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, frame.getWidth(), 50);
		player.drawHealthBar(g2);
	}
	
	public void goToNextLevel(int nextLevel)
	{
		this.levelStructure = new LevelStructure(nextLevel, frame);
	}
	
	public void drawLevel(Graphics2D g2)
	{
		//drawing the blocks
		ArrayList<Block> blocks = levelStructure.blocksLevel;
		for (int i = 0; i < levelStructure.blocksLevel.size(); i++)
		{
			/*****/
			blocks.get(i).drawBlock(g2);
		}
		String[] level = levelStructure.getLevel(levelNum);
		
		//drawing the portal
		for (int row = 0; row < level.length; row++)
		{
			for (int col = 0; col < level[row].length(); col++)
			{
				if (level[row].substring(col, col+1).equals("P"))
				{
					/******/
					levelStructure.getPortal().drawPortal(g2);
				}
			}
		}
		//drawing the spikes
		for (int i = 0; i < levelStructure.spikes.size(); i++)
		{
			/*******/
			levelStructure.spikes.get(i).drawSpike(g2);
		}
		
	}
	
	public void displayEndScreen(Graphics2D g2)
	{
		this.repaint();
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2.setColor(Color.WHITE);
		Font font = new Font("Chiller", Font.BOLD, 70);
		g2.setFont(font);
		g2.drawString("GAME OVER", 50, 100);
		Font font2 = new Font("Chiller", Font.ITALIC, 30);
		g2.setFont(font2);
		g2.drawString("Click anywhere to try again", 50, 140);
	}
	
	public Player getPlayer()
	{
		return this.player;
	}
	
	public int getLevelNum()
	{
		return levelNum;
	}
	
	public void setLevelNum(int levelNum)
	{
		this.levelNum = levelNum;
	}
	
	public void setKeys(int index, boolean setTo)
	{
		this.keys[index] = setTo;
	}
	
	public boolean getKeys(int index)
	{
		return keys[index];
	}
	
	public void setAction(KeyStroke keyStroke,
			String actionMapKey,
			Action action)
	{
		this.getInputMap().put(keyStroke, actionMapKey);
		this.getActionMap().put(actionMapKey, action);
	}
}

