import java.lang.*;
import java.lang.reflect.Array;
import java.util.*;
import java.io.Serializable;

public class BattleShipPlayer extends AbstractPlayer implements Serializable
{
    List<Location> shipLocation = new ArrayList<Location>();

    public void setDeployment(AbstractPlayer player)
    {
        if(player instanceof BattleShipPlayer)
        {
            shipLocation = ((BattleShipPlayer)player).shipLocation;
        }
    }

    public int getDeployLength()
    {
        return shipLocation.size();
    }

    public ArrayList<Location> attacked(Location l)
    {
        ArrayList<Location> result = new ArrayList<Location>();
        for(Location location: shipLocation)
        {
            if((location.x == l.x) && (location.y == l.y))
            {
                result.add(location); 
                attackedLocation.add(location);
                shipLocation.remove(location);
                return result;
            }
        }        
        return result;
    }
}
