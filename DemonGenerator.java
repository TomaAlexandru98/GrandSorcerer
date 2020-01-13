package pack;

import java.util.Random;
/**This class creates multiple demons and give them a coven to work on*/
public class DemonGenerator extends Thread {
	
	private Workshop workshop;
	private int numberCovens;
	
	/**DemonGenerator needs the data from workshop*/
	public DemonGenerator(Workshop workshop, int numberCovens)
	{
		this.workshop = workshop;
		this.numberCovens = numberCovens;
	}
	
/*Demons are spawning randomly in each coven at a random place in the coven
* (random time between 500-1000 milliseconds)*/
	public void run()
	{
		Random rand = new Random();
		while(true)
		{
			try
			{
				sleep(rand.nextInt(501)+ 500);
				GenerateDemon(rand.nextInt(numberCovens));
			}
			catch(InterruptedException e)
			{
			e.printStackTrace();
			}
		}
	}
	
/* Generates a demon*/
	private void GenerateDemon(int demonId)
	{
		workshop.AddDemonToCoven(demonId);
	}
}

