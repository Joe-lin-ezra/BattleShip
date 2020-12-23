import java.util.*;
import java.io.Serializable;

public class Player implements Serializable
{
    int id;
    String name;
    int roomId;
    List<Location> shipLocation = new ArrayList<Location>();
    List<Location> attackedLocation = new ArrayList<Location>();
    boolean alive = true;
}
