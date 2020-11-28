import java.util.ArrayList;

public class Room {
    int id;
    List<Player> players = new ArrayList<Player>();

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

}
