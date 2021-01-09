import java.lang.*;
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

    public String attacked(Location l)
    {
        for(Location location: shipLocation)
        {
            if((location.x == l.x) && (location.y == l.y))
            {
                shipLocation.remove(location);
                return "success";
            }
        }        
        return "fail";
    }
}
