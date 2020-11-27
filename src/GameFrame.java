import java.rmi.Remote;
import java.util.*;

public interface GameFrame extends Remote
{
	public String login() throws java.rmi.RemoteException;
}

