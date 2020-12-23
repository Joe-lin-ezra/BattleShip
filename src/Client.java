import java.io.*;
import java.rmi.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Client
{
	//static Scanner scaneer = new Scanner(System.in); // because static method need use static object
	static Player player;
	//static Guishow guib = new Guishow();
	//private static JFrame f;
	static GameFrame	o = null;
	Client(){
		player = new Player();
		try
		{
			// default is localhost if connect other computer please modify here
			o = (GameFrame) Naming.lookup("rmi://127.0.0.1/game");
			//System.out.println("RMI server connected");
		}
		catch(Exception e)
		{
			System.out.println("Server lookup exception: " + e.getMessage());
		}
	}
	public static void main(String args[]) // test only
	{
	
		
		String c = null;
		int h=0;
		//System.setSecurityManager(new RMISecurityManager());
		// Connect to RMIServer
		try
		{
			// default is localhost if connect other computer please modify here
			o = (GameFrame) Naming.lookup("rmi://127.0.0.1/game");
			//System.out.println("RMI server connected");
		}
		catch(Exception e)
		{
			System.out.println("Server lookup exception: " + e.getMessage());
		}
		//System.out.println("Hello Gui!!");
		/*while(true){
			System.out.println("Hello Guest");
			System.out.print(">>");
			c = scaneer.next();
			if(c.compareTo("1") == 0){
				System.out.println("Login!!!");
				player.name = scaneer.next();
				h=1;
				
			}
			
			try
			{
				switch(h){
					case 1:
						player = o.login(player);
						break;
				}
			}catch(Exception e)
			{
				System.out.println("ArithmeticServer exception: " + e.getMessage());
				e.printStackTrace();
			}
		}*/
		
		
		/*Runnable r = new Runnable() {
			@Override
			public void run(){
				f = new JFrame("warship");
				f.add(guib.getGuia());
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// let Gui close when user use "x"
				f.setLocationByPlatform(true);//
				f.pack();
                f.setMinimumSize(f.getSize());// ensures the minimum size is enforced.
                f.setVisible(true);
				f.setResizable(false);
			}
		}; */
		//SwingUtilities.invokeLater(r);
		//player = new Player();

		// run login function in client
		//while(!login(o, player)) {}

		// run join function in client
		//while(!join(o, player)) {}
		
	}
	
	public static boolean login(String name)
	{
		
		//System.out.print("input username: ");
		//player.name = scaneer.next();
		player.name = name;
		try
		{
			player = o.login(player);
			System.out.println(player.id);
			return true;
		}
		catch(Exception e)
		{
			System.out.println("GameServer exception: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public static boolean join()
	{
		try
		{
			player = o.join(player); // Let player join game(room)
			System.out.println(player.name + " GGroomId is " + player.roomId);
			return true;
		}
		catch(Exception e)
		{
			System.out.println("GameServer exception: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	public static boolean getroomstate()
	{
		try
		{
			String check_room = null;
			check_room = o.getRoomState(player); // Let player join game(room)
			if(check_room.compareTo("free")==0){
				return false;
			}else if(check_room.compareTo("playing")==0){
				return true;
			}
			//System.out.println(player.name + " GGroomId is " + player.roomId);
		}
		catch(Exception e)
		{
			System.out.println("GameServer exception: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		return false;
	}
	public static boolean setbattleship()
	{
		try
		{
			String check_map = null;
			check_map = o.setPlayerMap(player); // Let player join game(room)
			if(check_map.compareTo("fail")==0){
				return false;
			}else if(check_map.compareTo("success")==0){
				return true;
			}
			//System.out.println(player.name + " GGroomId is " + player.roomId);
		}
		catch(Exception e)
		{
			System.out.println("GameServer exception: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		return false;
	}
	public static boolean playing()
	{
		String term = null;
		//System.out.println("yaho!!");
		//1. get order
		try
		{
			term = o.whoseTerm(player);
		}
		catch(Exception e)
		{
			term="0";
			System.out.println("GameServer exception: " + e.getMessage());
			e.printStackTrace();
		}
		
		if(term.compareTo(String.valueOf(player.id)) == 0){
			System.out.println("now is your term !!!");
			return true;
		}else{
			System.out.println("emery term !!!");
			return false;
		}
		
		//o.getSelfState();
		//2. get attack()
		//3.
	}
	public static boolean attack(Location loc)
	{
		String state = null;
		try
		{
			state = o.attack(player,loc);
			System.out.println(state);
			return true;
		}
		catch(Exception e)
		{
			System.out.println("GameServer exception: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	public static void getSelfState(){
		try
		{
			o.getSelfState(player);
			if(player.attackedLocation.isEmpty()){
				System.out.println("attackedLocation isEmpty");
			}
			for(Location i: player.attackedLocation)
			{
				System.out.println("i is " + i);
			}
		}
		catch(Exception e)
		{
			System.out.println("GameServer exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
