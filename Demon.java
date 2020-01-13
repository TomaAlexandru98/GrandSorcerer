package pack;

import java.util.Random;

/* This class simulates the behaviour of a demon */
public class Demon extends Thread {
	
	private int id;
	private Boolean isDead;
	private Coordinates position;
	private Coven coven;
	
	private int dx[] = {0, 0, 1, -1};
	private int dy[] = {1, -1, 0, 0};
	
/* A demon must have an unique id, a position in nest and must know which coven belongs to  */	
	public Demon(int id, Coordinates coordinates, Coven coven)
	{
		this.id = id;
		this.position = coordinates;
		this.coven = coven;
		this.isDead = false;
	}
	
	public void run()
	{
		while(true)
		{
			TryToMove();
		}
	}
	
/* This method determines if the demon can move */	
	private void TryToMove() 
	{
		boolean demonMoved = false;
		
		for(int iterator = 0; iterator < 4; iterator++)
		{
			Coordinates nextPosition = new Coordinates(this.position.getX() + dx[iterator],
					this.position.getY() + dy[iterator]);
			
			demonMoved = this.coven.TryMoveDemon(id, position, nextPosition);
			
			if(demonMoved)
			{
				break;
			}
		}
		
		if(!demonMoved)
		{
			try
			{
				sleep(new Random().nextInt(41) + 10);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			CreateIngredient();
			
			try 
			{
				sleep(30);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
		
/* Creates a new ingredient and sends it to the coven */	
	public void CreateIngredient()
	{
		 Ingredient ingredient = new Ingredient();
		 coven.PrintMessageFromCoven("Demon " + this.id + " from coven " + coven.GetCovenId() 
		 + " created the ingredient " +  ingredient.getIngredient());
		 coven.GetIngredientFromDemon(id, ingredient);
	}
	
/* In order to creates new ingredients, the demon must move */
	public void GetNewPosition(Coordinates newPosition)
	{
		coven.PrintMessageFromCoven("Demon " + this.id + " from coven " + coven.GetCovenId() +
				" moved from (" + this.position.getX() + "," + this.position.getY() + 
				") to (" + newPosition.getX() + "," + newPosition.getY() + ")");
		this.position.setX(newPosition.getX());
		this.position.setY(newPosition.getY());
	}
	
/* This method retires the demon */	
	public void Retire()
	{
		this.isDead = false;
	}
	
/* This method gets the current position of the demon */
	public Coordinates GetCurrentPosition()
	{
		return this.position;
	}
}
