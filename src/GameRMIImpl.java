import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class GameRMIImpl extends UnicastRemoteObject implements GameFrame
{
	public GameRMIImpl() throws java.rmi.RemoteException
	{
		super(); 	// Use constructor of parent class
		System.out.println("create new GameRMIImpl");
	}
	public String login(){
		
		return "name";
	}

}

