package platformer;

import java.util.TimerTask;

public class PlatformRunner extends TimerTask
{
    private PlatformComponent comp;
    public PlatformRunner(PlatformComponent comp)
    {
        this.comp = comp;
    }
    
    @Override
    public void run() 
    {
        comp.repaint();
    }
}
