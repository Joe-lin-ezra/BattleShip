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

	private void printState()
	{
		System.out.println("================       status begin       ========================");
		System.out.println("client counter: " + counter);
		System.out.println();
		Set<Integer> keySet = rooms.keySet();
		for(Integer i: keySet)
		{
			Room room = rooms.get(i);
			System.out.println("\tRoom id: " + room.id);
			System.out.println("\tRoom state: " + room.state);
			System.out.println("\tRoom player number:" + room.players.size());
			for(Player player: room.players)
			{
				System.out.println("\t      * player id: " + player.id);
				System.out.println("\t\tplayer name: " + player.name);
				System.out.println("\t\tplayer ship Location: ");
				
				if(player.shipLocation != null)
				{
					System.out.print("\t\t\t");
					for(List<Integer> position: player.shipLocation)
					{	System.out.println("in");
						System.out.print("(" + position.get(0) + ", " + position.get(1) + "), ");
					}
					System.out.println();
				}
			}
		}

		System.out.println("================       status end       ========================");
	}

	public Player login(Player player) throws java.rmi.RemoteException
	{	
		player.id = counter++;
		return player;
	}

	/* 	To find whether there is a room can enter
		found, return room index
		not found, return -1 
	*/
	private int isRoomFree() throws java.rmi.RemoteException
	{
		if(rooms.isEmpty()) 
		{
			//System.out.println("rooms is empty!!!"); // debug
			return -1;
		}
		
		for(Room room : rooms.values())  // hashmap has special fpr each
		{
			if(room.isAvailableJoin())
			{
				//System.out.println("rooms Id is " + room.id); // debug
				return room.id;
			}
		}
		return -1;
	}
	public Player join(Player player) throws java.rmi.RemoteException
	{	
		int roomId;
		// if there is any room is available to join
		if((roomId = isRoomFree()) != -1)
		{
			Room room = rooms.get(roomId);
			//System.out.println("player " + player.name + " is join " + room.id);// debug
			room.joinRoom(player);
			room.state = "playing";
			//System.out.println(" room " + room.id + " state is " + room.state); // debug
			rooms.put(roomId, room);
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
			//System.out.println("make a new room id: " + room.id); // debug
			room.joinRoom(player);
			//System.out.println("player " + player.name + " is room " + room.id  +" owner "); //debug
			
			// put the new room into rooms list
			rooms.put(key, room);
		}
		printState();
		return player;
	}

	/* to get the room state, and return string
	// "free": the player2 place is free
	// "playing": the players in the room began to play
	*/
	public String getRoomState(Player player)  throws java.rmi.RemoteException
	{
		int roomIndex = player.roomId;
		Room room = rooms.get(roomIndex);
		return room.getState();
	}

	/* to set the player map, and return string
	// "success" or "fail"
	*/
	public String setPlayerMap(Player player) throws java.rmi.RemoteException
	{
		Room room = rooms.get(player.roomId);
		for(int i = 0; i < room.players.size(); i++)
		{
			Player tmpPlayer = room.players.get(i);
			if(player.id == tmpPlayer.id)
			{
				room.players.remove(i);
				room.players.add(player);
				rooms.put(player.id, room);
				return "success";
			}	
		}
		return "fail";
	}

	// check whether i win this game 
	public boolean isWin(Player player) throws java.rmi.RemoteException
	{
		Room room = rooms.get(player.roomId);
		// find another player
		for(Player another: room.players)
		{
			if(another.id != player.id)
			{
				if(another.shipLocation.size() == 0)
				{
					return true;
				}
			}
		}
		return false;
	}
	// player go attack his/her opponent
	public String attack(Player player, ArrayList<Integer> location) throws java.rmi.RemoteException
	{
		Room room = rooms.get(player.roomId);
		Player opponent = null;

		// find another player
		for(Player another: room.players)
		{
			if(another.id != player.id)
			{
				opponent = another;
				room.players.remove(another);
			}
		}

		if(opponent.shipLocation.contains(location))
		{
			opponent.shipLocation.remove(location);
		}
		room.players.add(opponent);
		rooms.put(player.roomId, room);
		return "fail";
	}

	public Player getSelfState(Player player) throws java.rmi.RemoteException
	{
		Room room = rooms.get(player.roomId);
		Player self = null;

		// find self state
		for(Player another: room.players)
		{
			if(another.id == player.id)
			{
				self = another;
			}
		}
		return self;
	}
}

