import java.lang.*;
import java.util.*;
import java.io.Serializable;

public class FighterAirplanePlayer extends AbstractPlayer implements Serializable
{
    Map<Integer, ArrayList<Location>> fighterAirplaneLocation = new HashMap<Integer, ArrayList<Location>>();
    
    public void setDeployment(AbstractPlayer player)
    {
        if(player instanceof FighterAirplanePlayer)
        {
            fighterAirplaneLocation = ((FighterAirplanePlayer)player).fighterAirplaneLocation;
        }
    }
    public int getDeployLength()
    {
        return fighterAirplaneLocation.size();
    }

    public ArrayList<Location> attacked(Location l)
    {
        ArrayList<Location> result = new ArrayList<Location>();
        for(Integer index: fighterAirplaneLocation.keySet())
        {
            ArrayList<Location> locations = fighterAirplaneLocation.get(index);
            for(Location location: locations)
            if((location.x == l.x) && (location.y == l.y))
            {
                fighterAirplaneLocation.remove(index);
                attackedLocation.addAll(locations);
                return result;
            }
        }
        return result;
    }
}
