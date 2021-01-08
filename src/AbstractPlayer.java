import java.util.*;
import java.io.Serializable;

public abstract class AbstractPlayer implements Serializable
{
    int id;
    String name;
    int roomId;
    // depends on player type
    // List<Location> shipLocation = new ArrayList<Location>();
    List<Location> attackedLocation = new ArrayList<Location>();
    boolean alive = true;

    public abstract void setDeployment(AbstractPlayer player);
    public abstract int getDeployLength();
}
