import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

import java.util.Random;
import java.util.List;

public class GameRMIImpl extends UnicastRemoteObject implements GameFrame
{
	List<Room> rooms = new ArrayList<Room>();
	Random random = new Random();

	public GameRMIImpl() throws java.rmi.RemoteException
	{
		super(); 	// Use constructor of parent class
		System.out.println("create new GameRMIImpl");
	}

	public int login(String name)
	{	
		return random.nextInt(100000);
	}
}

