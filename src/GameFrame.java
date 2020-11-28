import java.rmi.Remote;
import java.util.*;

public interface GameFrame extends Remote
{
	public Player login(Player player) throws java.rmi.RemoteException;
	public int join(Player player) throws java.rmi.RemoteException;
}

