package pack;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;

/* This class creates witches */
public class WitchGenerator extends Thread{

	private Workshop workshop;
	private List<Coven> covens;
	int id;
	
	InetAddress serverAddress;
	int serverPort = 800;
	
	public WitchGenerator(Workshop workshop, List<Coven> covens)
	{
		this.id = 0;
		this.workshop = workshop;
		this.covens = covens;
		
		try
		{
			serverAddress = InetAddress.getLocalHost();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
/* This method generates the number of witches and it spawnes them */
	public void run()
	{
		Random random = new Random();
		int numberWitches = random.nextInt(10) + 1;
		
		for(int iterator = 0; iterator < numberWitches; iterator++)
		{
			GenerateWitch();
		}
	}

/* This method generates a new witch */
	private void GenerateWitch() 
	{
		id++;
		System.out.println("Workshop : Witch " + id + " has spawned");
		workshop.AddWitch(new Witch(covens, id, serverAddress, serverPort));
	}

}
