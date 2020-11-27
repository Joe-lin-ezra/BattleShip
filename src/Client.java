import java.io.*;
import java.rmi.*;
import java.util.*;

class Client
{
	
	public static void main(String args[])
	{
	    GameFrame	o = null;
		Scanner sca = new Scanner(System.in);
		String c = null;
			
			//System.setSecurityManager(new RMISecurityManager());
			// Connect to RMIServer
			try
			{
				o = (GameFrame) Naming.lookup("rmi://127.0.0.1/game");//just localhost if want connect other computer please modfy here
				//System.out.println("RMI server connected");
			}
			catch(Exception e)
			{
				System.out.println("Server lookup exception: " + e.getMessage());
			}
			
			try
			{
				c= o.login();
				System.out.println(c);
			}
			catch(Exception e)
			{
				System.out.println("GameServer exception: " + e.getMessage());
				e.printStackTrace();
			}
		}
	    
}
