import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.lang.*;

// to monitor the player connection, preventing them from disconnecting or error state
public class Daemon extends Thread 
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
				// cancelAliveState();
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