package platformer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
public class PlatformTester
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.setSize(1200, 1000);
		frame.setTitle("ZXX Platformer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		PlatformComponent comp = new PlatformComponent(frame);
		class KeyPressedListener extends KeyAdapter
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				//left, right, up
				int keyCode = e.getKeyCode();
				
				//37 = left
				if (keyCode == KeyEvent.VK_LEFT)
				{
					comp.setKeys(MoveDirection.LEFT, true);
				}
				//39 = right
				if (keyCode == KeyEvent.VK_RIGHT)
				{
					comp.setKeys(MoveDirection.RIGHT, true);
				}
				//38 = up
				if (keyCode == KeyEvent.VK_UP)
				{
					comp.setKeys(MoveDirection.UP, true);
				}
				
			}
			@Override
			public void keyReleased(KeyEvent e)
			{
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_LEFT)
				{
					comp.setKeys(MoveDirection.LEFT, false);
					comp.updatePlayer();
				}
				if (keyCode == KeyEvent.VK_RIGHT)
				{
					comp.setKeys(MoveDirection.RIGHT, false);
					comp.updatePlayer();
				}
				if (keyCode == KeyEvent.VK_UP)
				{
					comp.setKeys(MoveDirection.UP, false);
					comp.updatePlayer();
				}
			}
		}
		
		class MousePressedListener extends MouseAdapter
		{
			@Override
			public void mousePressed(MouseEvent event)
			{
				if (!comp.getPlayer().getIsAlive())
				{
					comp.getPlayer().setIsAlive(true);
					int levelNum;
					if (comp.getLevelNum() != 1)
					{
						levelNum = comp.getLevelNum() - 1;
						comp.goToNextLevel(levelNum);
					}
					else
					{
						comp.goToNextLevel(0);
						levelNum = 0;
					}
					comp.setLevelNum(levelNum);
				}
			}
		}
		
		KeyListener keyListener = new KeyPressedListener();
		comp.addKeyListener(keyListener);
		
		MouseListener mouseListener = new MousePressedListener();
		comp.addMouseListener(mouseListener);
		
		frame.add(comp);
		frame.setVisible(true);
	}
}

