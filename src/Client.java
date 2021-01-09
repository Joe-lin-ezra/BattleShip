import java.io.*;
import java.rmi.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Client extends AbstractClient
{
	//static Scanner scaneer = new Scanner(System.in); // because static method need use static object
	AbstractPlayer player;
	//static Guishow guib = new Guishow();
	//private static JFrame f;
	GameFrame	o = null;
	Client(){
		player = new BattleShipPlayer();
		try
		{
			// default is localhost if connect other computer please modify here
			o = (GameFrame) Naming.lookup("rmi://127.0.0.1/BattleShip");
			System.out.println("RMI server connected");
		}
		catch(Exception e)
		{
			System.out.println("Server lookup exception: " + e.getMessage());
		}
	}
	
	public boolean login(String name)
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

	public boolean join()
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
	public boolean getroomstate()
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
	public void setlocation(ArrayList<Location> location){
		BattleShipPlayer b = (BattleShipPlayer)player;
		b.shipLocation=location;
	}
	public boolean setbattleship()
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
		}
		catch(Exception e)
		{
			System.out.println("GameServer exception: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		return false;
	}
	public boolean playing()
	{
		String term = null;
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
	}
	public ArrayList<Location> attack(Location loc)
	{
		ArrayList<Location> locat = new ArrayList<Location>();
		try
		{
			locat = o.attack(player,loc);
			//System.out.println(state);
			return locat;
		}
		catch(Exception e)
		{
			System.out.println("GameServer exception: " + e.getMessage());
			e.printStackTrace();
		}
		return locat;
	}
	public void getSelfState(){
		try
		{
			player=o.getSelfState(player);
		}
		catch(Exception e)
		{
			System.out.println("GameServer exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	public String isAlive(){
		String state = "";
		try
		{
			o.isAlive(player);
			state=o.getWinner(player);
			return state;
		}
		catch(Exception e)
		{
			System.out.println("GameServer exception: " + e.getMessage());
			e.printStackTrace();
		}
		return state;
	}
}
