import java.util.*;

public class Room {
    int id;
    List<Player> players = new ArrayList<Player>(); //java List not equal to java ArrayList so you need to import both of them
    String state = new String("free");
	String term = new String(""); 

    public Room(int roomId)
    {
        this.id = roomId;
    }

    public Player joinRoom(Player player)
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
