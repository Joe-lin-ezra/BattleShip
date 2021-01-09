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
	public ArrayList<Location> attack(AbstractPlayer player, Location location) throws java.rmi.RemoteException
	{
		Room room = rooms.get(player.roomId);
		AbstractPlayer opponent = null;

		// not the term belong the player
		if(!room.term.equals(String.valueOf(player.id)))
		{
			return new ArrayList<Location>();
		}

		// find another player
		for(AbstractPlayer another: room.players)
		{
			if(another.id != player.id)
			{
				opponent = another;
			}
		}

		ArrayList<Location> result = opponent.attacked(location);
		printState();
		setWinner(player);
		nextTerm(room, opponent);
		return result;
	}
}
