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
}
