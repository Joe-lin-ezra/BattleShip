import java.rmi.*;
import java.rmi.server.*;

public class FighterAirplaneServer extends GameServer
{
	// Bind GameServer and Registry
	public static void main(String args[])
	{
		//System.setSecurityManager(new RMISecurityManager());
        GameServer s = new FighterAirplaneServer();
        s.establishServer();
    }
    
    public void establishServer()
    {
        try
		{
			AbstractGameRMIImpl name = new FighterAirplaneRMIImpl();
			System.out.println("Registering ...");
			Naming.rebind("FighterAirplane", name);	// game is the name of the service
			System.out.println("Register success");
		}
		catch(Exception e)
		{
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
    }
}