package pack;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Witch extends Thread {

	
	List<String[]> potions = GeneratePotions();
	List<String> ingredients = new ArrayList<String>(); 
	List<Coven> covens = new ArrayList<Coven>();
	
	int id;
	Random random = new Random();
	Socket socket;
	
/* Witches have access to all covens and send potions to the Grand Sorcerer */	
	public Witch(List<Coven> covens, int id , InetAddress serverAddress, int serverPort)
	{
		this.id = id;
		this.covens = covens;
		
		try
		{
			serverPort = 800;
			socket = new Socket(serverAddress.getHostAddress(), serverPort);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void run()
	{
		while(true)
		{
			GetIngredientsForPotion();
		}
	}
	
/* This method creates 20 potions with a number between 4-8 ingredients. The ingredients for each
 * potion are random */
	public ArrayList<String[]> GeneratePotions()
	{
		List<String[]> listPotions = new ArrayList<String[]>();
		Random random = new Random();
		
		for(int iterator = 0; iterator < 20; iterator++)
		{
			int numberIngredientsPotion = random.nextInt(5) + 4;
/* A potion is an array of strings containing the name of ingredients */
			String[] potion = new String[numberIngredientsPotion];
			
			for(int iterator2 = 0; iterator2 < numberIngredientsPotion; iterator2++)
			{
				potion[iterator2] = (new Ingredient()).getIngredient();
			}
			
			listPotions.add(potion);	
		}
		return (ArrayList<String[]>) listPotions;
	}
	
/* This method receives a ingredient from a coven */
	public void GetIngredientsForPotion()
	{
		for(Coven coven : covens)
		{
			Ingredient ingredient = coven.GetIngredientFromCoven(id);
			
			if(ingredient != null)
			{
				System.out.println("Witch " + this.id + " got the ingredient " + 
						ingredient.getName() + " from coven " + id);
				TryMakePotion(ingredient);
			}
			
			try 
			{
				sleep(random.nextInt(30) + 20);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

/* With the ingredients that a witch has, she will try to make a potion */
	public void TryMakePotion(Ingredient ingredient)
	{
		ingredients.add(ingredient.getName());
		for(String[] potion : potions)
		{
			int iterator = 0;
			
			while(iterator < potion.length && ingredients.contains(potion[iterator]) == true)
			{
				iterator++;
			}
			
			if(iterator == potion.length)
			{
				System.out.println("Potion " + potions.indexOf(potion) + " was created");
				
				for(String i : potion)
				{
					ingredients.remove(i);
				}
				
				SendPotionToGrandSorcere(potion);
			}
			
		}
	}

/* This method sends a potion to the Grand Sorcerer */
	private void SendPotionToGrandSorcere(String[] potion)
	{	
		try
		{
			System.out.println("Witch " + id + " has send potion " + potions.indexOf(potion) + 
					" to Grand Sorcerer");
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println("potion " + potions.indexOf(potion));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
