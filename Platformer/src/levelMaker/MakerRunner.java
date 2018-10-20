package levelMaker;

import java.util.TimerTask;

public class MakerRunner extends TimerTask
{
	public MakerComponent comp;
	
	public MakerRunner(MakerComponent comp)
	{
		this.comp = comp;
	}
	
	public void run()
	{
		comp.repaint();
	}
}
