import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.lang.*;

public class FighterAirplaneRMIImpl extends AbstractGameRMIImpl implements GameFrame
{
    public FighterAirplaneRMIImpl() throws java.rmi.RemoteException
	{
		super(); 	// Use constructor of parent class
		thread = new Daemon(rooms, this);
		thread.start();
		System.out.println("create new GameRMIImpl");
	}

	
	public void printLocation(AbstractPlayer player)
	{
		if(player instanceof FighterAirplanePlayer)
		{
			if (((FighterAirplanePlayer)player).fighterAirplaneLocation != null) {
                System.out.print("\t\t\t");
                for(Integer index: ((FighterAirplanePlayer)player).fighterAirplaneLocation.keySet())
                {
                    System.out.print(index + ": {");
                    ArrayList<Location> locations = ((FighterAirplanePlayer)player).fighterAirplaneLocation.get(index);
                    for(Location l: locations)
                    {
                        System.out.print("(" + l.x + ", " + l.y + "), ");
                    }
                    System.out.print("}, ");
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
	public String attack(AbstractPlayer player, Location location) throws java.rmi.RemoteException
	{
		Room room = rooms.get(player.roomId);
		AbstractPlayer opponent = null;

		// not the term belong the player
		if(!room.term.equals(String.valueOf(player.id)))
		{
			return "fail";
		}

		// find another player
		for(AbstractPlayer another: room.players)
		{
			if(another.id != player.id)
			{
				opponent = another;
			}
		}
		// put attacked location in to "attackedLocation" list
		opponent.attackedLocation.add(location);

		String result = opponent.attacked(location);
		printState();
		setWinner(player);
		nextTerm(room, opponent);
		return result;
	}
}
