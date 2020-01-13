package pack;

import java.util.List;
import java.util.Random;

/* This class generates the undead */
public class UndeadGenerator extends Thread{

	private int id;
	private List<Coven> covens;
	private Workshop workshop;
	
	public UndeadGenerator(Workshop workshop, List<Coven> covens)
	{
		this.id = 0;
		this.covens = covens;
		this.workshop = workshop;
	}
	
	public void run()
	{
		Random random = new Random();
		int numberUndead = random.nextInt(21) + 30;
		
		for(int iterator = 0; iterator < numberUndead; iterator++)
		{
			GenerateUndead();
		}
	}

	private void GenerateUndead()
	{
		this.id++;
		System.out.println("The undead " + this.id + " was spawned");
		this.workshop.addUndead(new Undead(id, covens));
		
	}
}
