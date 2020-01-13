package pack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/* Grand Sorcerer is waiting for potions */
public class GrandSorcerer extends Thread{

	private ServerSocket serverSocket;
	private Scanner scanner;
	
	public GrandSorcerer(String ipAddress, int port) throws IOException
	{
		this.serverSocket = new ServerSocket(port); //port, 1, InetAddress.getByName(ipAddress)
	}
	
	public void run()
	{
		System.out.println("Grand Sorcerer is waiting for potions");
		
		while(true)
		{
			try
			{
				listen();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private void listen() throws IOException
	{
		String data = null;
		Socket client = serverSocket.accept();
		String clientAddress = client.getInetAddress().getHostAddress();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		
		while((data = in.readLine()) != null)	
		{
			System.out.println("Potion from " + clientAddress + " : " + data + 
					" was received by GrandSorcerer");
		}
	}
	
	public InetAddress GetSocketAddress()
	{
		return this.serverSocket.getInetAddress();
	}
	
	public int GetPort()
	{
		return this.serverSocket.getLocalPort();
	}
}
