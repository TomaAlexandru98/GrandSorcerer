package pack;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* This class is the main class and simulates the application */
public class Application {

	public static void main(String[] args) 
	{
		Random random = new Random();
/* There is a maximum number of 10 witches and minim 1 */
		int numberWitches = random.nextInt(10) + 1;
/* The number of covens is between 3 and 20 */
		int numberCovens = random.nextInt(18) + 3;
		
		try
		{
/* The Grand Sorcerer is waiting for potions */
			GrandSorcerer sorcerer = new GrandSorcerer(InetAddress.getLocalHost().getHostAddress().toString(), 800 );
			sorcerer.start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		WorkshopPlan workshopPlan = new WorkshopPlan(numberCovens, numberWitches);
		
	}

}
