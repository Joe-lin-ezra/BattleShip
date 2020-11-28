import java.io.*;
import java.rmi.*;
import java.util.*;

class Client
{
	public static void main(String args[])
	{
	    GameFrame	o = null;
		Scanner scaneer = new Scanner(System.in);
		String c = null;

		//System.setSecurityManager(new RMISecurityManager());
		// Connect to RMIServer
		try
		{
			// localhost if connect other computer please modify here
			o = (GameFrame) Naming.lookup("rmi://127.0.0.1/game");
			//System.out.println("RMI server connected");
		}
		catch(Exception e)
		{
			System.out.println("Server lookup exception: " + e.getMessage());
		}
		


		Player player = new Player();

		// run login function in client
		while(!login(o, player)) {}

		// run join function in client
		while(!join(o, player)) {}
		
	}
	
	public static boolean login(GameFrame	o, Player player)
	{
		player.name = scanner.nextLine();
		try
		{
			int id = o.login(player.name());
			player.id = id;
			System.out.println(id);
			return true;
		}
		catch(Exception e)
		{
			System.out.println("GameServer exception: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public static boolean join(GameFrame o, Player player)
	{
		try
		{
			int roomNumber = o.join(id);
			player.roomId = roomNumber;
			System.out.println(roomNumber);
			return true;
		}
		catch(Exception e)
		{
			System.out.println("GameServer exception: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}
