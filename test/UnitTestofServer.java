
import java.io.*;
import java.rmi.*;
import java.util.*;
import java.lang.*;


public class UnitTestofServer {
    public static void main(String[] args) {
        GameFrame o = null;
		try
		{
			o = (GameFrame) Naming.lookup("rmi://127.0.0.1/game");
			System.out.println("RMI server connected");
		}
		catch(Exception e)
		{
			System.out.println("Server lookup exception: " + e.getMessage());
        }
        
        AbstractPlayer player = testLogin(o); // for login
        player = testJoin(o, player); // for join

        String state = new String(); // for get room state
        try
        {
            state = o.getRoomState(player);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("room state: " + state);

        for(int i = 0; i < 7; i++)
        {
            ArrayList<Location> l = new ArrayList<Location>();
            l.add(new Location(1,1));
            l.add(new Location(2,1));
            l.add(new Location(3,1));
            ((FighterAirplanePlayer)player).fighterAirplaneLocation.
            put(1, l);

            l = new ArrayList<Location>();
            l.add(new Location(5,5));
            l.add(new Location(5,6));
            l.add(new Location(5,7));

            ((FighterAirplanePlayer)player).fighterAirplaneLocation.
            put(2, l);
        }
        
        // set player map
        try
        {
            String str = o.setPlayerMap(player);
            System.out.println(str);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        try
        {
            String str = o.whoseTerm(player);
            System.out.println("whose term: " + str + ".....");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            Thread.sleep(6000);
            String str = o.attack(player, new Location(1, 1));
            System.out.println(str);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            String str = o.whoseTerm(player);
            System.out.println("whose term: " + str + ".....");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    // the function is to test RMI login 
    public static AbstractPlayer testLogin(GameFrame o) {
        AbstractPlayer player = new FighterAirplanePlayer();
        player.name = "Joe";
        try {
            player = o.login(player);
        }
        catch(Exception e){
            e.getStackTrace();
        }
        System.out.println("player id: " + player.id + ", name: " + player.name);
        return player;
    }

    // the function is to test RMI join
    public static AbstractPlayer testJoin(GameFrame o, AbstractPlayer player)
    {
        try {
            player = o.join(player);
            
            // print imformation of updated player
            System.out.println("player id: " + player.id + ", name: " + player.name + ", room id: " + player.roomId);
        }
        catch(Exception e)
        {
            e.getStackTrace();
        }
        return player;
    }
}