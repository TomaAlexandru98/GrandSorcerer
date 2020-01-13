package pack;

import java.util.Random;

/* This class is used by every coven in order to ask the demons where they are */
public class ObserverDemons extends Thread {
	
	private Coven coven;
	
	public ObserverDemons(Coven coven)
	{
		this.coven = coven;
	}
	
	public void run()
	{
		while(true)
		{
			coven.helpObserver();
			
			try
			{
				this.sleep(new Random().nextInt(401) + 100);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
