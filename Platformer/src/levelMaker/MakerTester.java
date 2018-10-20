package levelMaker;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class MakerTester 
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.setSize(600, 600);
		MakerComponent comp = new MakerComponent();
		
		class MousePressedListener extends MouseAdapter
		{
			@Override
			public void mousePressed(MouseEvent event)
			{
				comp.updateGrid(event.getX(), event.getY());
			}
		}
		comp.addMouseListener(new MousePressedListener());
		frame.add(comp);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
