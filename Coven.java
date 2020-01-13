package pack;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/* This class simulates a coven */
public class Coven extends Thread {

	private int id;
	private int numberDemons;
	private int sizeCoven;
	private int[][] matrix;
	private int uniqueDemonId = 0;
	
	Random random = new Random();
	
	ConcurrentLinkedQueue<Ingredient> listIngredients;
/* This lock is used to assure the corectness of the demon move method */
	ReentrantLock oneDemonTriesToMove = new ReentrantLock();
/* This lock is used to determine which operation has priority in this class at a given time 
 * The operations are : a demon tries to move, a witch tries to get the ingredients from a 
 * coven and a coven tries to ask all the demons where they are */	
	ReentrantLock setPriorityOperation = new ReentrantLock();
/* A maximum of 10 witches can read from a coven at a time */
	Semaphore semaphoreWitches;	
	ConcurrentHashMap<Integer, Demon> demons;
/* This variable helps the coven to ask all the demons about their position */	
	ObserverDemons observerDemons;
	
	public Coven(int sizeNest, int numberDemons, int id)
	{
		this.id = id;
		this.sizeCoven = sizeNest;
		this.matrix = new int[this.sizeCoven][this.sizeCoven];
		this.numberDemons = numberDemons;
		
		if(this.numberDemons == 0)
			this.numberDemons = 1;
		
		this.listIngredients = new ConcurrentLinkedQueue<Ingredient>();
		this.semaphoreWitches = new Semaphore(10);
		this.demons = new ConcurrentHashMap<Integer, Demon>();
		this.observerDemons = new ObserverDemons(this);
		
		System.out.println("The coven " + this.id + " with the size of " + this.sizeCoven + 
				" and " + this.numberDemons + " demons has been created");
	}
	
/* When coven starts to run, the observer also need to run */
	public void run()
	{
		this.observerDemons.run();
	}
	
/* A demon will be added if the maximum number of demons was not reached */	
	public void addDemon()
	{
		if(demons.size() < sizeCoven / 2)
		{
			int x = 0;
			int y = 0;
			Demon demon = new Demon(uniqueDemonId, GetFirstPosition(x, y, uniqueDemonId), this);
			demons.put(uniqueDemonId, demon);
			demon.start();
			
			System.out.println("A new demon " + uniqueDemonId + " has spawed in coven "
					+ this.id);
			uniqueDemonId++;
		}
	}
	
	public Ingredient GetIngredientFromCoven(int idWitch)
	{
		setPriorityOperation.lock();
		Ingredient ingredient = null;
		
		try 
		{
			semaphoreWitches.acquire();
			
			if(!oneDemonTriesToMove.isLocked())
			{
				ingredient = listIngredients.poll();
			}	
		}
		finally
		{
			if(ingredient != null)
			{
				PrintMessageFromCoven("Witch " + id + " got the ingredient " + 
						ingredient.getName() + " from coven " + this.id);
			}
			
			semaphoreWitches.release();
			setPriorityOperation.unlock();
			
			return ingredient;
		}
	}
	
/* This method finds a position in coven for a new demon and returns it */	
	private Coordinates GetFirstPosition(int x, int y, int idDemon) 
	{
		x = random.nextInt(sizeCoven);
		y = random.nextInt(sizeCoven);
		
		while(matrix[x][y] != 0)
		{
			x = random.nextInt(sizeCoven);
			y = random.nextInt(sizeCoven);
		}
		
		matrix[x][y] = idDemon;
		
		return new Coordinates(x, y);
	}

/* This method checks if the position of a demon is out of bounds */
	private boolean PositionOutOfBounds(Coordinates position)
	{
		
		if(position.getX() < 0 || position.getY() < 0 || position.getX() >= sizeCoven || 
				position.getY() >= sizeCoven)
			return true;
		
		return false;
	}
	
/* This method checks if the parameter position is a valid one */
	public boolean DemonCanMove(int idDemon, Coordinates position)
	{
		boolean var = !PositionOutOfBounds(position) && (matrix[position.getX()][position.getY()] == 0 ||
					matrix[position.getX()][position.getY()] == idDemon);
		return var;
	}
	
	public boolean MoveDemon(int idDemon, Coordinates position, Coordinates nextPosition)
	{
		if(oneDemonTriesToMove.tryLock())
		{
			try
			{
				
				if(DemonCanMove(idDemon, nextPosition))
				{
					matrix[nextPosition.getX()][nextPosition.getY()] = idDemon;
					demons.get(idDemon).GetNewPosition(nextPosition);

					return true;
				}
				

				return false;
			}
			finally
			{
				oneDemonTriesToMove.unlock();
			}
		}
		return false;
	}
	
/* A demon tries to move in another position : returns true if he can and false otherwise */	
	public boolean TryMoveDemon(int idDemon, Coordinates position, Coordinates nextPosition)
	{
		setPriorityOperation.lock();
		
		if(DemonCanMove(idDemon, nextPosition))
		{
			setPriorityOperation.unlock();
			return MoveDemon(idDemon, position, nextPosition);

		}
		
		setPriorityOperation.unlock();	
		
		return false;	
	}
	
/* This function adds a new ingredient to the coven */
	public void GetIngredientFromDemon(int id, Ingredient ingredient)
	{	
		listIngredients.add(ingredient);
	}

/* This method checks if the demons are in the positions known by the coven */	
	public void helpObserver()
	{
		setPriorityOperation.lock();
		Iterator<Integer> iterator = demons.keySet().iterator();
		
		while(iterator.hasNext())
		{
			Integer key = iterator.next();
			Coordinates position = demons.get(key).GetCurrentPosition();
			
			if(matrix[position.getX()][position.getY()] == key)
			{
				continue;
			}
		}
		
		setPriorityOperation.unlock();
	}
	
/* Prints a message coven */
	public void PrintMessageFromCoven(String message) 
	{
		System.out.println(message);
	}
	
/* This method removes a demon from a coven */	
	public void RemoveDemon(int id)
	{
		Demon demon = demons.get(id);
		demons.get(id).Retire();
		PrintMessageFromCoven("Demon " + id + " from coven " + this.id + " was retired");
		demons.remove(demon);
	}
	
/* Returns the id of this coven */
	public int GetCovenId()
	{
		return id;
	}
	
/* returns the number of demons from the coven */
	public int GetNumberOfDemons()
	{
		return demons.size();
	}

/* An undead visits the coven, the next operation will have place :
 * 		- a random number between 5 and 10 will retire
 * 		- all ingredients from coven will be lost */
	public void VisitedByUndead(int idUndead) 
	{
		int numberRetiredDemons = random.nextInt(6) + 5;
		listIngredients.clear();
		PrintMessageFromCoven("The undead " + idUndead + " visited the coven " + this.id +
				" and " + numberRetiredDemons + " demons retired");
	}
}
