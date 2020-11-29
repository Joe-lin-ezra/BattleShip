import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.lang.*;


public class GameRMIImpl extends UnicastRemoteObject implements GameFrame
{
	int counter = 0; // for user index
	Map<Integer, Room> rooms = new HashMap<Integer, Room>();
	
	Random random = new Random();

	public GameRMIImpl() throws java.rmi.RemoteException
	{
		super(); 	// Use constructor of parent class
		System.out.println("create new GameRMIImpl");
	}

	public Player login(Player player)
	{	
		player.id = counter++;
		return player;
	}

	/* 	To find whether there is a room can be entered
		found, return room index
		not found, return -1 
	*/
	private int isRoomFree()
	{
		if(rooms.isEmpty()) 
		{
			return -1;
		}
		
		for(Room room : rooms.values())  // hashmap has special fpr each
		{
			if(room.isAvailableJoin())
			{
				return room.id;
			}
		}
		return -1;
	}
	public Player join(Player player)
	{	
		int roomId;
		// if there is any room is available to join
		if((roomId = isRoomFree()) != -1)
		{
			rooms.get(roomId).joinRoom(player);
		}
		else
		{
			// to find an available room id
			int key = random.nextInt(10000000);
			while(rooms.containsKey(key))
			{
				key = random.nextInt(10000000);
			}

			// put the player into the new room
			Room room = new Room(key);
			room.joinRoom(player);

			// put the new room into rooms list
			rooms.put(key, new Room(key));
		}
		return player;
	}
}

