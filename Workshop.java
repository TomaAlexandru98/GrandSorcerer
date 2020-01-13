package pack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* This class creates the following entites : demon spawner, witch spawner, covens and retired demons */
public class Workshop extends Thread{

	private int numberCovens;
	private int numberWitches;
	
	private DemonGenerator demonGenerator;
	private WitchGenerator witchGenerator;
	private UndeadGenerator undeadGenerator;
	
	public List<Coven> covens;
	private List<Witch> witches;
	private List<Undead> theUndead;
	
/* Using the number of covens and the number of witches, the workshop will create : demon generator,
 * witches generator, covens and retired demons */
	public Workshop(int numberCovens,int numberWitches)
	{
		this.numberCovens = numberCovens;
		this.numberWitches = numberWitches;
		
		this.demonGenerator = new DemonGenerator(this, numberCovens);
		
		this.covens = new ArrayList<Coven>();
		this.witches = new ArrayList<Witch>();
		this.theUndead = new ArrayList<Undead>();
	}
	
	
	public void run()
	{
		CreateCovens();
		StartCovens();
		
		witchGenerator = new WitchGenerator(this, covens);
		undeadGenerator = new UndeadGenerator(this, covens);
		
		witchGenerator.start();
		undeadGenerator.start();
		demonGenerator.start();
	}
	
/* This method creates the covens. The number of covens is random, between 3-20
 * Each coven has a dimension between 100-500, a number of demons between smaller than half of
 * dimension(random) and an unique id given by iterator */
	private void CreateCovens()
	{
		Random random = new Random();
		
		for(int iterator = 0; iterator < numberCovens; iterator++)
		{
			int dimension = random.nextInt(401) + 100;
			int numberDemons = random.nextInt(dimension / 2);
			covens.add(new Coven(dimension, numberDemons, iterator));
		}
	}
	
/* Starting the covens */
	private void StartCovens()
	{
		for(Coven coven : covens)
		{
			coven.start();
		}
	}

/* This method adds a demon to a given coven */
	public void AddDemonToCoven(int covenId)
	{
		covens.get(covenId).addDemon();
	}
	
/* A witch is added to the list */
	public void AddWitch(Witch witch)
	{
		witches.add(witch);
		witch.start();
	}
	
/* A specific demon will be removed from a specific coven */
	public void RetireDemon(int covenId, int demonId)
	{
		covens.get(covenId).RemoveDemon(demonId);
	}

/* Adds an undead to the list */
	public void addUndead(Undead undead)
	{
		theUndead.add(undead);
		undead.start();
	}
}
