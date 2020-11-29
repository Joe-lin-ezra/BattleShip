import java.io.*;
import java.rmi.*;
import java.util.*;

class Client
{
	static Scanner scaneer = new Scanner(System.in); // because static method need use static object
	static Player player = null;
	public static void main(String args[])
	{
	    GameFrame	o = null;
		
		String c = null;

		//System.setSecurityManager(new RMISecurityManager());
		// Connect to RMIServer
		try
		{
			// default is localhost if connect other computer please modify here
			o = (GameFrame) Naming.lookup("rmi://127.0.0.1/game");
			//System.out.println("RMI server connected");
		}
		catch(Exception e)
		{
			System.out.println("Server lookup exception: " + e.getMessage());
		}
		


		player = new Player();

		// run login function in client
		while(!login(o, player)) {}

		// run join function in client
		while(!join(o, player)) {}
		
	}
	
	public static boolean login(GameFrame	o, Player player)
	{
		
		System.out.print("input username: ");
		player.name = scaneer.nextLine();
		try
		{
			player = o.login(player);
			System.out.println(player.id);
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
			player = o.join(player); // Let player join game(room)
			System.out.println(player.roomId);
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
