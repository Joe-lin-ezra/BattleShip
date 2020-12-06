import java.io.*;
import java.rmi.*;
import java.util.*;

public class UnitTestofServer {
    public static void main(String[] args) {
        
        GameFrame o = null;
		try
		{
			o = (GameFrame) Naming.lookup("rmi://127.0.0.1/game");
			//System.out.println("RMI server connected");
		}
		catch(Exception e)
		{
			System.out.println("Server lookup exception: " + e.getMessage());
        }
        
        Player player = testLogin(o); // for login
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

        
    
    }

    // the function is to test RMI login 
    public static Player testLogin(GameFrame o) {
        Player player = new Player();
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
    public static Player testJoin(GameFrame o, Player player)
    {
        try {
            player = o.join(player);
            
            // print imformation of updated player
            System.out.println("player id: " + player.id + ", name: " + player.name + "room id: " + player.roomId);
        }
        catch(Exception e)
        {
            e.getStackTrace();
        }
        return player;
    }
}