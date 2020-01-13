
package pack;

import java.util.List;
import java.util.Random;

/**This class will retire a demon */
public class DemonRetire extends Thread {
	
	private Workshop workshop;
	
	public DemonRetire(Workshop workshop)
	{
		this.workshop = workshop;
	}
	
	/**At one moment of time a random coven from workshop is picked and a demon from this
	 *  coven will be retired*/
	public void run()
	{
		Random rand = new Random();
		while(true)
		{
			try
			{
				sleep(rand.nextInt(501)+ 501);
				Coven coven = workshop.covens.get(workshop.covens.size() - 1);
				int covenId = coven.GetCovenId();
				System.out.println("One demon from coven " + covenId + " will be retired");
				if(coven.GetNumberOfDemons() > 0)
				{
					int demonId = rand.nextInt(coven.GetNumberOfDemons());
					workshop.RetireDemon(covenId, demonId);
				}
			}
			catch(InterruptedException e)
			{
			e.printStackTrace();
			}
		}
	}
}
