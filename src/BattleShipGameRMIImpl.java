import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.lang.*;

public class BattleShipGameRMIImpl extends AbstractGameRMIImpl implements GameFrame
{
	public BattleShipGameRMIImpl() throws java.rmi.RemoteException
	{
		super(); 	// Use constructor of parent class
		thread = new Daemon(rooms, this);
		thread.start();
		System.out.println("create new GameRMIImpl");
	}

	
	public void printLocation(AbstractPlayer player)
	{
		if(player instanceof BattleShipPlayer)
		{
			if (((BattleShipPlayer)player).shipLocation != null) {
				System.out.print("\t\t\t");
				for (Location position : ((BattleShipPlayer)player).shipLocation) {
					System.out.print("(" + position.x + ", " + position.y + "), ");
				}
				System.out.println();
			}
	
			System.out.println("\t\tplayer attacked Location: ");
			if (player.attackedLocation != null) {
				System.out.print("\t\t\t");
				for (Location position : player.attackedLocation)
				{	
					System.out.print("(" + position.x + ", " + position.y + "), ");
				}
				System.out.println();
			}
		}
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
				setWinner(player);
				printState();
				nextTerm(room, opponent);
				return "success";
			}
		}
		printState();
		setWinner(player);
		nextTerm(room, opponent);
		return "fail";
	}
}


// ---------------------------------------------------------------------------------------

// to monitor the player connection, preventing them from disconnecting or error state
class Daemon extends Thread 
{
	Map<Integer, Room> rooms = null;
	AbstractGameRMIImpl mainThread = null;

	public Daemon(Map<Integer, Room> r, AbstractGameRMIImpl k) 
	{
		this.rooms = r;
		this.mainThread = k;
		System.out.println("second thread start");
	}

	public void run() 
	{
		while(true)
		{
			try
			{
				// just wait
				Thread.sleep(5000);
				this.mainThread.printState();
				
				// remove the end-game room
				removeEndRoom();
				this.mainThread.printState();
				// dealing the player in error
				dealErrorPlayer();
				this.mainThread.printState();
				// renew the alive state of all players
				cancelAliveState();
				this.mainThread.printState();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	// remove the end-game room
	public void removeEndRoom()
	{
		for(Room room: rooms.values())
		{
			if(room.state.equals("end"))
			{
				if( (room.players.get(0).alive == false) &&
					(room.players.get(1).alive == false))
				{
					rooms.remove(room.id);
				}
			}
		}
	}

	// dealing the player in error
	public void dealErrorPlayer()
	{
		for(Room room: rooms.values())
		{
			if(room.state.equals("playing"))
			{
				// per 5 second, if one online, another disconnected, and, then, online-player win 
				if((room.players.get(0).alive == true) && (room.players.get(1).alive == false))
				{
					setExceptWinner(room.players.get(0));
				}
				if((room.players.get(0).alive == false) && (room.players.get(1).alive == true))
				{
					setExceptWinner(room.players.get(1));
				}
			}
			if(room.state.equals("free"))
			{
				if(room.players.get(0).alive == false)
				{
					rooms.remove(room.id);
				}
			}
		}
	}
	private void setExceptWinner(AbstractPlayer player)
	{
		Room room = rooms.get(player.roomId);
		room.winner = String.valueOf(player.id);
		room.state = "end";
	}

	// cancel all the players alive state
	public void cancelAliveState()
	{
		for(Room room: rooms.values())
		{
			for(AbstractPlayer one: room.players)
			{
				one.alive = false;
			}
		}
	}
}