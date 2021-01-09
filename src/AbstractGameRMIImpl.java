import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.io.Serializable;
import java.lang.*;

public abstract class AbstractGameRMIImpl extends UnicastRemoteObject implements Serializable
{
    int counter = 0; // for user index
    Map<Integer, Room> rooms = new HashMap<Integer, Room>();
    Random random = new Random();

	Daemon thread = null;
    public AbstractGameRMIImpl() throws java.rmi.RemoteException
    {
        super();
    }

    // to print the server state in that time
    public void printState()
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
			for(AbstractPlayer player: room.players)
			{
				System.out.println("\t      * player id: " + player.id);
				System.out.println("\t\tplayer name: " + player.name);
				System.out.println("\t\tplayer is alive?: " + player.alive);
				System.out.println("\t\tplayer ship Location: ");
				printLocation(player);
			}
		}
		System.out.println("================       status end       ========================");
    }
    
    public abstract void printLocation(AbstractPlayer player);


    public AbstractPlayer login(AbstractPlayer player) throws java.rmi.RemoteException
	{	
		player.id = counter++;
		return player;
	}
        
    /* 	To find whether there is a room can enter
		found, return room index
		not found, return -1 
	*/
	protected int isRoomFree() throws java.rmi.RemoteException
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
    
    public AbstractPlayer join(AbstractPlayer player) throws java.rmi.RemoteException
	{	
		int roomId;
		// if there is any room is available to join
		if((roomId = isRoomFree()) != -1)
		{
			Room room = rooms.get(roomId);
			room.joinRoom(player);
			room.state = "playing";
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
	public String getRoomState(AbstractPlayer player)  throws java.rmi.RemoteException
	{
		int roomIndex = player.roomId;
		Room room = rooms.get(roomIndex);
		return room.getState();
	}

    // this term is whose
	public String whoseTerm(AbstractPlayer player) 
	{
		Room room = rooms.get(player.roomId);
		return room.term;
    }

    /* to set the player map, and return string
	// "success" or "fail"
	*/
	public String setPlayerMap(AbstractPlayer player) throws java.rmi.RemoteException
	{
		Room room = rooms.get(player.roomId);
		for(AbstractPlayer one: room.players)
		{
			if(player.id == one.id)
			{
                one.setDeployment(player);
				setAttackOrder(player);
				printState();
				return "success";
			}
		}
		printState();
		return "fail";
	}
    // decide the order of attack
	private void setAttackOrder(AbstractPlayer player)
	{
        Room room = rooms.get(player.roomId);
        for(AbstractPlayer one: room.players)
        {
            if(one.getDeployLength() == 0)
            {
                return ;
            }
        }
		Random random = new Random();
		int prior = random.nextInt(2);
		room.term = String.valueOf(room.players.get(prior).id);
    }
    
    public String getWinner(AbstractPlayer player) throws java.rmi.RemoteException
	{
		Room room = rooms.get(player.roomId);
		return room.winner;
    }
    
    protected void nextTerm(Room room, AbstractPlayer opponent)
	{
		room.term = String.valueOf(opponent.id);
    }
       
	// set the winner in specific room
	protected void setWinner(AbstractPlayer player) throws java.rmi.RemoteException
	{
		Room room = rooms.get(player.roomId);
		// find another player
		AbstractPlayer first = room.players.get(0);
		AbstractPlayer second = room.players.get(1);

		if(first.getDeployLength() == 0)
		{
			room.state = "end";
			room.winner = String.valueOf(second.id);
		}
		if(second.getDeployLength() == 0)
		{
			room.state = "end";
			room.winner = String.valueOf(first.id);
		}
    }

    public void isAlive(AbstractPlayer player) throws java.rmi.RemoteException
	{
		Room room = rooms.get(player.roomId);

		for(AbstractPlayer one: room.players)
		{
			if(one.id == player.id)
			{
				one.alive = true;
			}
		}
    }
    
    // get self object to update client-end state
	public AbstractPlayer getSelfState(AbstractPlayer player) throws java.rmi.RemoteException
	{
		Room room = rooms.get(player.roomId);
		AbstractPlayer self = null;

		// find self state
		for(AbstractPlayer another: room.players)
		{
			if(another.id == player.id)
			{
				self = another;
			}
		}
		return self;
	}
}