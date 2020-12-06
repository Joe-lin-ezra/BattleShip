import java.rmi.Remote;
import java.util.*;

public interface GameFrame extends Remote
{
	// login to the server, giving an ID
	public Player login(Player player) throws java.rmi.RemoteException;
	// join into a room, or create a new room to join
	public Player join(Player player) throws java.rmi.RemoteException;
	// get the room state, "free" or "playing"
	// free: the player2 place is free
	// playing: the players in the room began to play
	public String getRoomState(Player player) throws java.rmi.RemoteException;
	// to set player map in server
	public String setPlayerMap(Player player) throws java.rmi.RemoteException; 
	public boolean isWin(Player player) throws java.rmi.RemoteException;
	public String attack(Player player, Location location) throws java.rmi.RemoteException;
	public Player getSelfState(Player player) throws java.rmi.RemoteException;
}

