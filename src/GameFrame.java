import java.rmi.Remote;
import java.util.*;

public interface GameFrame extends Remote
{
	public int login(String name) throws java.rmi.RemoteException;
}

