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

	// to print the server state in that time
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
			System.out.println("\tRoom winner: " + room.winner);
			System.out.println("\tRoom player number:" + room.players.size());
			for(Player player: room.players)
			{
				System.out.println("\t      * player id: " + player.id);
				System.out.println("\t\tplayer name: " + player.name);
				
				System.out.println("\t\tplayer ship Location: ");
				if(player.shipLocation != null)
				{
					System.out.print("\t\t\t");
					for(Location position: player.shipLocation)
					{	
						System.out.print("(" + position.x + ", " + position.y + "), ");
					}
					System.out.println();
				}

				System.out.println("\t\tplayer attacked Location: ");
				if(player.attackedLocation != null)
				{
					System.out.print("\t\t\t");
					for(Location position: player.attackedLocation)
					{	
						System.out.print("(" + position.x + ", " + position.y + "), ");
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
			room.joinRoom(player);
			room.state = "playing";
			rooms.put(roomId, room);
			
			// decide the order of attack
			Random random = new Random();
			int prior = random.nextInt(2);
			room.term = String.valueOf(room.players.get(prior).id);
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

	// this term is whose
	public String whoseTerm(Player player) 
	{
		Room room = rooms.get(player.roomId);
		return room.term;
	}
	/* to set the player map, and return string
	// "success" or "fail"
	*/
	public String setPlayerMap(Player player) throws java.rmi.RemoteException
	{
		Room room = rooms.get(player.roomId);
		for(int i = 0; i < room.players.size(); i++)
		{
			Player another = room.players.get(i);
			if(player.id == another.id)
			{
				room.players.remove(i);
				room.players.add(player);
				printState();
				return "success";
			}	
		}
		printState();
		return "fail";
	}

	public String getWinner(Player player) throws java.rmi.RemoteException
	{
		Room room = rooms.get(player.roomId);
		return room.winner;
	}
	
	// player go attack his/her opponent
	public String attack(Player player, Location location) throws java.rmi.RemoteException
	{
		Room room = rooms.get(player.roomId);
		Player opponent = null;

		// not the term belong the player
		if(!room.term.equals(String.valueOf(player.id)))
		{
			return "fail";
		}

		// find another player
		for(Player another: room.players)
		{
			if(another.id != player.id)
			{
				opponent = another;
			}
		}
		// put attacked location in to "attackedLocation" list
		opponent.attackedLocation.add(location);

		for(Location l: opponent.shipLocation)
		{
			// check the ship location and attacked location
			if((l.x == location.x) && (l.y == location.y))
			{
				opponent.shipLocation.remove(l);
				printState();
				nextTerm(room, opponent);
				return "success";
			}
		}
		printState();
		nextTerm(room, opponent);
		return "fail";
	}
	private void nextTerm(Room room, Player opponent)
	{
		room.term = String.valueOf(opponent.id);
	}
	// set the winner in specific room
	private void setWinner(Player player) throws java.rmi.RemoteException
	{
		Room room = rooms.get(player.roomId);
		// find another player
		Player first = room.players.get(0);
		Player second = room.players.get(1);

		if(first.shipLocation.size() == 0)
		{
			room.winner = String.valueOf(second.id);
		}
		if(second.shipLocation.size() == 0)
		{
			room.winner = String.valueOf(first.id);
		}
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

