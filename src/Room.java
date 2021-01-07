import java.util.*;

public class Room {
    int id;
    List<AbstractPlayer> players = new ArrayList<AbstractPlayer>(); //java List not equal to java ArrayList so you need to import both of them
    String state = new String("free");
    String term = new String(""); 
    String winner = new String("");

    public Room(int roomId)
    {
        this.id = roomId;
    }

    public AbstractPlayer joinRoom(AbstractPlayer player)
    {
        player.roomId = this.id;
        players.add(player);
        return player;
    }

    public boolean isAvailableJoin()
    {
        if(players.size() < 2)
        {
            return true;
        }
        return false;
    }

    public String getState()
    {
        return state;
    }
}
