import java.util.*;
import java.io.Serializable;

public class BattleAirplanePlayer extends AbstractPlayer implements Serializable
{
    Map<Integer, ArrayList<Location>> airplaneLocation = new HashMap<Integer, ArrayList<Location>>();
}
