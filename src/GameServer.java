import java.rmi.*;
import java.rmi.server.*;

public abstract class GameServer
{
	// Bind GameServer and Registry
	public abstract void establishServer();
}