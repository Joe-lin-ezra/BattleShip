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

    public String attacked(Location l)
    {
        for(Integer index: fighterAirplaneLocation.keySet())
        {
            ArrayList<Location> locations = fighterAirplaneLocation.get(index);
            if(locations.contains(l))
            {
                fighterAirplaneLocation.remove(index);
                return "success";
            }
        }
        return "fail";
    }
}
