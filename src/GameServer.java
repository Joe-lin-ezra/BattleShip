import java.rmi.*;
import java.rmi.server.*;

public class GameServer
{
	// Bind GameServer and Registry
	public static void main(String args[])
	{
		//System.setSecurityManager(new RMISecurityManager());
		try
		{
			GameRMIImpl name = new GameRMIImpl();
			System.out.println("Registering ...");
			Naming.rebind("game", name);	// game is the name of the service
			System.out.println("Register success");
		}
		catch(Exception e)
		{
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}