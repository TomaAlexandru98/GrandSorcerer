package pack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* This class represents the undead */
public class Undead extends Thread{
	
	private int id;
	private List<Coven> covens = new ArrayList<Coven>();
	
/* The undead have access to all covens */	
	public Undead(int id, List<Coven> covens)
	{
		this.id = id;
		this.covens = covens;
	}

/* The undead visit covens */ 
	public void run()
	{
		while(true)
		{
			VisitCovens();
		}
	}

/* The undead visit a random coven at a random time */
	private void VisitCovens() 
	{
		Random random = new Random();
		int idCoven = random.nextInt(covens.size());
		
		try
		{
			covens.get(idCoven).VisitedByUndead(id);
			sleep(random.nextInt(501) + 500);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
