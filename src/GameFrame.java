import java.rmi.Remote;
import java.util.*;

public interface GameFrame extends Remote
{
	// login to the server, giving an ID
	public AbstractPlayer login(AbstractPlayer AbstractPlayer) throws java.rmi.RemoteException;
	// join into a room, or create a new room to join
	public AbstractPlayer join(AbstractPlayer player) throws java.rmi.RemoteException;
	// get the room state, "free" or "playing"
	// free: the player2 place is free
	// playing: the players in the room began to play
	// end: the game finished
	public String getRoomState(AbstractPlayer player) throws java.rmi.RemoteException;
	// to set player map in server
	public String setPlayerMap(AbstractPlayer player) throws java.rmi.RemoteException; 
	public String whoseTerm(AbstractPlayer player) throws java.rmi.RemoteException;
	public String getWinner(AbstractPlayer player) throws java.rmi.RemoteException;
	public String attack(AbstractPlayer player, Location location) throws java.rmi.RemoteException;
	public AbstractPlayer getSelfState(AbstractPlayer player) throws java.rmi.RemoteException;
	public void isAlive(AbstractPlayer player) throws java.rmi.RemoteException;
}

