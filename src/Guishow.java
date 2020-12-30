import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;


public class Guishow{
	Client client = null;
	private final JPanel guia = new JPanel(new BorderLayout(3, 3));  //test
	private final JPanel guic = new JPanel(new BorderLayout(3, 3));  //test
	private final JPanel guid = new JPanel(new BorderLayout(3, 3));
	//private static final JPanel gui = new JPanel(new BorderLayout(3, 3));
	
	private JPanel chessBoardy,chessBoardg;
	private Chessboard chessBoardz,chessBoarde;
	
	private static JFrame f;
	
	JScrollPane jScrollPane=null;
	private JPanel title = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel content  = new JPanel(new FlowLayout(FlowLayout.LEADING));
	private JPanel ground  = new JPanel(new FlowLayout(FlowLayout.LEADING));
	private JPanel endedpage = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JButton conb = new JButton("Start Game");
	private JLabel namelabel = new JLabel("Nickname :");
	private JLabel ended = new JLabel("");
	private JTextField nickname;
	private JTextArea area = new JTextArea();
	private static final String COLS = "ABCDEFGHIJK";
	private final JLabel message = new JLabel("Warship battle!!!!!");
	private boolean check_login = false;
	private boolean check_join = false;
	static boolean play = false;
	//define deploy button
	private JButton aircraft_carrier = new JButton("BBV"); //Shorthand
	private JButton battleship = new JButton("BB");
	private JButton cruiser = new JButton("CL");
	private JButton destroyer = new JButton("DD");
	private JButton submarine = new JButton("SS");
	private JButton deployyy = new JButton("Deploy");
	private JButton attack = new JButton("Attack");
	//private Runnable rr;
	//FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 0, 0); 
	int i,j,s;
	String state=null;
	public ArrayList<Location> location = new ArrayList<Location>(); // changing "Locate" to "Location"
	public Location locat = null;
	Guishow(){
		client = new Client();
		welcomepage();
		//initializeGui();
	}
	private void updated(){
		f.remove(guia);
		//f.setVisible(false);
		f.setSize(800,400);
		initializeGui();
		f.add(guic);
		f.setMinimumSize(f.getSize());// ensures the minimum size is enforced.
		//f.invalidate();
		//f.repaint();
		f.setVisible(true);
	}
	private void last(String str){
		f.remove(guic);
		//Settlement(str);
		int result = JOptionPane.showConfirmDialog(f,str,"Win or Lose?",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
		if ( result == JOptionPane.YES_OPTION){
					System.exit(0);
				}
		//f.add(guid);
		//f.setMinimumSize(f.getSize());
		//f.setVisible(true);
	}
	public final void welcomepage(){
		guia.setBorder(new EmptyBorder(5, 5, 5, 5)); // make border style
		nickname = new JTextField(20);
		//gui.setLayout(layout);
		
		ActionListener buttonListener = new ActionListener() {/*--------------------------here been change make a new class to cover it---------------------------*/
                    public void actionPerformed(ActionEvent e) {
						
						check_login = client.login(nickname.getText());// do first
						client.join();
						new Thread(new Runnable(){
							@Override
							public void run(){
								
								conb.setText("waiting");
								conb.setEnabled(!conb.isEnabled());
								playing();
								while(true){
									//System.out.println("w");
									if(client.getroomstate()!= false){ // room is created
										updated();
										area.append("connect!!!\n");
										System.out.println("done");
										break;
									}else{ // waiting another player
										System.out.println("waiting");
									}
									
								}
								
							}
						}).start();
						
					}
		};
		conb.addActionListener(buttonListener);
		title.add(message);
		guia.add(title,BorderLayout.NORTH);
		content.add(namelabel);
		content.add(nickname);
		content.add(conb);
		guia.add(content,BorderLayout.CENTER);
		
	}
	/*public void Settlement(String str){
			guid.setBorder(new EmptyBorder(5, 5, 5, 5));
			ended.setText(str);
			endedpage.add(ended);
			guid.add(endedpage,BorderLayout.CENTER);
	}*/
	public final void initializeGui(){
		guic.setBorder(new EmptyBorder(5, 5, 5, 5)); // make border style
		guic.add(message,BorderLayout.NORTH); 
		chessBoardy = new JPanel(new GridLayout(0, 11));// get new board
		chessBoardy.setBorder(new LineBorder(Color.BLACK)); // set chessboard
		chessBoardg = new JPanel(new GridLayout(0, 11));// get new board
		chessBoardg.setBorder(new LineBorder(Color.BLACK)); // set chessboard
		chessBoarde = new Chessboard('e');
		chessBoardz = new Chessboard('y'); // make new chessboard
		chessBoardy=chessBoardz.getChessBoard(); // change type
		chessBoardg=chessBoarde.getChessBoard(); // change type
		guic.add(chessBoardg,BorderLayout.EAST); // add chessboard in west
		area.setLineWrap(true); 
		jScrollPane = new JScrollPane(area);
		guic.add(jScrollPane,BorderLayout.CENTER);
		guic.add(chessBoardy,BorderLayout.WEST);
		ground.add(aircraft_carrier); //BBV 
		ground.add(battleship);//BB
		ground.add(cruiser); //CL
		ground.add(destroyer); //DD
		ground.add(submarine); //SS
		ground.add(deployyy);// deploy 
		ground.add(attack);
		attack.setEnabled(!attack.isEnabled());
		guic.add(ground,BorderLayout.SOUTH);
		ActionListener deployListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                        if(e.getActionCommand() == "BBV"){
							//System.out.println("BBV");
							//System.out.println("5. is "+chessBoardz.getship_longth());
							if(chessBoardz.getship_longth()==0){
								chessBoardz.setship_longth(5);
								aircraft_carrier.setEnabled(!aircraft_carrier.isEnabled());
							}
							
							//chessBoardy.ship_longth=5;
						}else if(e.getActionCommand() == "BB"){
							//System.out.println("BB");
							if(chessBoardz.getship_longth()==0){
								chessBoardz.ship_longth=4;
								//System.out.println("4. is "+chessBoardz.getship_longth());
								battleship.setEnabled(!battleship.isEnabled());
							}
							
							//chessBoardy.ship_longth=4;
						}else if(e.getActionCommand() == "CL"){
							System.out.println("CL");
							if(chessBoardz.getship_longth()==0){
								chessBoardz.ship_longth=3;
								//System.out.println("3. is "+chessBoardz.getship_longth());
								cruiser.setEnabled(!cruiser.isEnabled());
							}
							
							//chessBoardy.ship_longth=3;
						}else if(e.getActionCommand() == "DD"){
							//System.out.println("DD");
							if(chessBoardz.getship_longth()==0){
								chessBoardz.ship_longth=2;
								//System.out.println("2. is "+chessBoardz.getship_longth());
								destroyer.setEnabled(!destroyer.isEnabled());
							}
							
							//chessBoardy.ship_longth=2;
						}else if(e.getActionCommand() == "SS"){
							//System.out.println("SS");
							if(chessBoardz.getship_longth()==0){
								chessBoardz.ship_longth=1;
								//System.out.println("1. is "+chessBoardz.getship_longth());
								submarine.setEnabled(!submarine.isEnabled());
							}
							//chessBoardy.ship_longth=1;
						}
				//System.out.println("get!! x is " + x + " y is " + y + "t is " + t);
            }
		//chessBoarde = new mkchessboard();
		//---------------------------------------------------
		};
		ActionListener playerrr = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand() == "Deploy"){
							//System.out.println("eee");
					for (i = 0; i < 10; i++) {
						for (j = 0; j < 10; j++) {
									//chessBoardz.chessBoardSquares[i][j](!chessBoardz.chessBoardSquares[i][j].isEnabled());
							if(chessBoardz.getChessButton(i,j).getBackground()==Color.yellow)
							{
								Location ddd = new Location(i,j); // changing "Locate" to "Location"
								location.add(ddd);
								//System.out.println("(" + i + "," + j + ") is yellow");
							}
						}
					}
					if(location.size() < 15){  //duplicate deploy
						area.append("your deploy is not ready!!!\n");
						//System.out.println("error");
					}else{
						client.player.shipLocation = location;
						//chessBoardz.closeChessboard();
						if(client.setbattleship() == false){ //set locate
							System.out.println("error!!");
						}
						deployyy.setEnabled(!deployyy.isEnabled());
						area.append("waiting\n");
						new Thread(new Runnable (){
						@Override
							public void run(){
									do{
										if(client.playing()==true){
											//System.out.println("find");
											area.append("your term!!\n");
											client.getSelfState();
											for (Location g : client.player.attackedLocation) {
												//System.out.println("x is " + g.x + "y is " + g.y);
												chessBoardz.setChessButtoncolor(g.x,g.y,Color.red);
											}
											attack.setEnabled(!attack.isEnabled());
											chessBoarde.ship_longth=1;
											break;
										}
										else{
											try
											{
												Thread.sleep(2000);
											}
											catch(InterruptedException ex) { }
										}
								}while(true);
							}
						}).start();
					}
					
					
				}else if(e.getActionCommand() == "Attack")
				{
					System.out.println("in attack!!");
					new Thread(new Runnable(){
					@Override
					public void run(){
						
							for (i = 0; i < 10; i++) {
								for (j = 0; j < 10; j++) {
									//chessBoardz.chessBoardSquares[i][j](!chessBoardz.chessBoardSquares[i][j].isEnabled());
									if(chessBoarde.getChessButton(i,j).getBackground()==Color.red)
									{
										locat = new Location(i,j);
										/*if(s==1){
											chessBoarde.setChessButtoncolor(i,j,Color.green);
										}else{
											chessBoarde.setChessButtoncolor(i,j,Color.yellow);
										}
										s=0;*/
										chessBoarde.setChessButtoncolor(i,j,Color.yellow);
										//System.out.println("(" + i + "," + j + ") is red");
										break;
									}
								}
							}
							if(locat!=null){
								if(client.attack(locat).equals("success")){
									area.append("attack Success!!\n");
									s=1;
									//System.out.println("sucess!!");
								}else{
									area.append("attack Fail!!\n");
									s=0;
									//System.out.println("fail");
								}
								if(s==1){
											chessBoarde.setChessButtoncolor(locat.x,locat.y,Color.green);
										}else{
											chessBoarde.setChessButtoncolor(locat.x,locat.y,Color.yellow);
										}
								//chessBoarde.setChessButtoncolor(locat.x,locat.y,Color.yellow);
								locat=null;
								attack.setEnabled(!attack.isEnabled());
								do{
								if(client.playing()==true){
									client.getSelfState();
									area.append("your Term!!\n");
									for (Location g : client.player.attackedLocation) {
										//System.out.println("x is " + g.x + "y is " + g.y);
										chessBoardz.setChessButtoncolor(g.x,g.y,Color.red);
									}
									attack.setEnabled(!attack.isEnabled());
									chessBoarde.ship_longth=1;
									break;
								}
								else{
									try
									{
										Thread.sleep(2000);
									}
									catch(InterruptedException e) { }
								}
								}while(true);
							}
					}
				}).start();		
							
							
			}
			}	
		};
		aircraft_carrier.addActionListener(deployListener);
		battleship.addActionListener(deployListener);
		cruiser.addActionListener(deployListener);
		destroyer.addActionListener(deployListener);
		submarine.addActionListener(deployListener);
		deployyy.addActionListener(playerrr);
		attack.addActionListener(playerrr);
	}
	/*public final JComponent getGui() {
        return gui;
    }*/
	public void playing(){
		
		new Thread(new Runnable(){
			@Override
			public void run(){
				while(true){
					state=client.isAlive();
					if(!state.equals("")){
						int y=Integer.parseInt(state);
						if(y==client.player.id){
							System.out.println("you Win");
							area.append("you Win!!\n");
							last("you Win!!");
							break;
						}else{
							System.out.println("you Lose");
							area.append("you Lose!!\n");
							last("you Lose!!");
							break;
						}
					}
				}
			}
		}).start();
	}
	public final JComponent getGuia() {
        return guia;
    }
	
	static WindowListener close = new WindowAdapter(){// close this window
		public void windowClosing(WindowEvent we){
			int result = JOptionPane.showConfirmDialog(f,"Are you sure you want to close this window?","close Window?",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
			if ( result == JOptionPane.YES_OPTION){
					System.exit(0);
				}
		}
	};
	
	public static void main(String args[]){
		System.out.println("Hello Gui!!");
		Guishow guib = new Guishow();
		
		//System.out.println(rr.isAlive());
		//rr.run();
		Runnable r = new Runnable() {
			@Override
			public void run(){
				
				f = new JFrame("warship");
				f.add(guib.getGuia());
				f.addWindowListener(close);
				f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);// let Gui close when user use "x"
				f.setLocationByPlatform(true);//
				f.pack();
                f.setMinimumSize(f.getSize());// ensures the minimum size is enforced.
                f.setVisible(true);
				//f.setResizable(false);
			}
		};
		SwingUtilities.invokeLater(r);
	}
	
	
}

class Chessboard extends JPanel{// make chessboard
	private JPanel chessBoard;
	private JButton[][] chessBoardSquares = new JButton[10][10];
	int i,j;
	public char a = '0';//just use to check chessboard generator if a == e mean this object is chessBoarde or this object is chessBoardy 
	private static final String COLS = "ABCDEFGHIJK";
	public int ship_longth=0,ship_ver=0;// here need modfy when user select
	Chessboard(char a){// constructor
		this.a = a;
		System.out.println("make chessboard");
		mkchessboard();
	}
	public int getship_longth(){
		return this.ship_longth;
	}
	public void setship_longth(int a){
		this.ship_longth=a;
	}
	public JPanel getChessBoard() {  // return 
		//System.out.println("return chessboard");
        return chessBoard;
    }
	public JButton getChessButton(int x,int y) {  // return 
		//System.out.println("return chessboard");
        return chessBoardSquares[x][y];
    }
	public void setChessButtoncolor(int x,int y,Color color) {  // return 
		//System.out.println("yee");
		chessBoardSquares[x][y].setBackground(color);
        //switchstate(x,y,color,1);
    }
	public void closeChessboard(){
		for(i = 0; i < chessBoardSquares.length; i++){ 
			for(j = 0; j < chessBoardSquares[i].length ; j++){
				chessBoardSquares[i][j].setEnabled(!chessBoardSquares[i][j].isEnabled());
			}
		}
	}
	
	public void switchstate(int x,int y,Color color,int mode){
		if(this.ship_ver == 0){
			/*if(a =='e'){
				System.out.println("0. ship_ver is " + this.ship_ver);
			}*/
			
			if(mode==2){
				//System.out.println("ship_ver is " + this.ship_ver);
				this.ship_ver=1;
				//System.out.println("ship_ver is " + this.ship_ver);
			}
			try{
				switch(this.ship_longth){
					case 1:
						if(chessBoardSquares[x][y].getBackground() != Color.YELLOW && chessBoardSquares[x][y].getBackground() != Color.green)
						{
							chessBoardSquares[x][y].setBackground(color);
							if(mode==1){
								System.out.println("1");
								this.ship_longth=0;
							}
						}
						break;
					case 2:
						if(chessBoardSquares[x][y].getBackground() != Color.YELLOW && chessBoardSquares[x][y].getBackground() != Color.green)
						{
							if(chessBoardSquares[x+1][y].getBackground() != Color.YELLOW && chessBoardSquares[x+1][y].getBackground() != Color.green)
							{
								chessBoardSquares[x][y].setBackground(color);
								chessBoardSquares[x+1][y].setBackground(color);
								if(mode==1){
									this.ship_longth=0;
								}
							}
						}
						break;
					case 3:
						if(chessBoardSquares[x-1][y].getBackground() != Color.YELLOW && chessBoardSquares[x-1][y].getBackground() != Color.green)
						{
							if(chessBoardSquares[x][y].getBackground() != Color.YELLOW && chessBoardSquares[x][y].getBackground() != Color.green)
							{
								if(chessBoardSquares[x+1][y].getBackground() != Color.YELLOW && chessBoardSquares[x+1][y].getBackground() != Color.green)
								{
									chessBoardSquares[x-1][y].setBackground(color);
									chessBoardSquares[x][y].setBackground(color);
									chessBoardSquares[x+1][y].setBackground(color);
									if(mode==1){
										this.ship_longth=0;
									}
								}
							}
						}
						break;
					case 4:
						if(chessBoardSquares[x-2][y].getBackground() != Color.YELLOW && chessBoardSquares[x-2][y].getBackground() != Color.green)
						{
							if(chessBoardSquares[x-1][y].getBackground() != Color.YELLOW && chessBoardSquares[x-1][y].getBackground() != Color.green)
							{
								if(chessBoardSquares[x][y].getBackground() != Color.YELLOW && chessBoardSquares[x][y].getBackground() != Color.green)
								{
									if(chessBoardSquares[x+1][y].getBackground() != Color.YELLOW && chessBoardSquares[x+1][y].getBackground() != Color.green)
									{
										chessBoardSquares[x-2][y].setBackground(color);
										chessBoardSquares[x-1][y].setBackground(color);
										chessBoardSquares[x][y].setBackground(color);
										chessBoardSquares[x+1][y].setBackground(color);
										if(mode==1){
											this.ship_longth=0;
										}
									}
								}
							}
						}
						break;
					case 5:
						if(chessBoardSquares[x-2][y].getBackground() != Color.YELLOW && chessBoardSquares[x-2][y].getBackground() != Color.green)
						{
							if(chessBoardSquares[x-1][y].getBackground() != Color.YELLOW && chessBoardSquares[x-1][y].getBackground() != Color.green)
							{
								if(chessBoardSquares[x][y].getBackground() != Color.YELLOW && chessBoardSquares[x][y].getBackground() != Color.green)
								{
									if(chessBoardSquares[x+1][y].getBackground() != Color.YELLOW && chessBoardSquares[x+1][y].getBackground() != Color.green)
									{
										if(chessBoardSquares[x+2][y].getBackground() != Color.YELLOW && chessBoardSquares[x+2][y].getBackground() != Color.green)
										{
											chessBoardSquares[x-2][y].setBackground(color);
											chessBoardSquares[x-1][y].setBackground(color);
											chessBoardSquares[x][y].setBackground(color);
											chessBoardSquares[x+1][y].setBackground(color);
											chessBoardSquares[x+2][y].setBackground(color);
											if(mode==1){
												//System.out.println("ship_length is " + this.ship_longth);
												this.ship_longth=0;
												//System.out.println("ship_length is " + this.ship_longth);
											}
										}
									}
								}
							}
						}
						break;
				}
			} catch (Exception ex){
				//System.out.println("out of range");
			}
		}else if(this.ship_ver==1)
		{
			//System.out.println(" 1. ship_ver is " + this.ship_ver);
			if(mode==2){
				
				this.ship_ver=0;
				//System.out.println("ship_ver is " + this.ship_ver);
			}
			try{
				switch(this.ship_longth){
					case 1:
						if(chessBoardSquares[x][y].getBackground() != Color.YELLOW && chessBoardSquares[x][y].getBackground() != Color.green)
						{
							chessBoardSquares[x][y].setBackground(color);
							if(mode==1){
								//System.out.println("deploy!!!");
								//System.out.println("ship_length is "ship_longth);
								this.ship_longth=0;
							}
						}
						break;
					case 2:
						if(chessBoardSquares[x][y].getBackground() != Color.YELLOW && chessBoardSquares[x][y].getBackground() != Color.green)
						{
							if(chessBoardSquares[x][y+1].getBackground() != Color.YELLOW && chessBoardSquares[x][y+1].getBackground() != Color.green)
							{
								chessBoardSquares[x][y].setBackground(color);
								chessBoardSquares[x][y+1].setBackground(color);
								if(mode==1){
									this.ship_longth=0;
								}
							}
						}
						break;
					case 3:
						if(chessBoardSquares[x][y-1].getBackground() != Color.YELLOW && chessBoardSquares[x][y-1].getBackground() != Color.green)
						{
							if(chessBoardSquares[x][y].getBackground() != Color.YELLOW && chessBoardSquares[x][y].getBackground() != Color.green)
							{
								if(chessBoardSquares[x][y+1].getBackground() != Color.YELLOW && chessBoardSquares[x][y+1].getBackground() != Color.green)
								{
									chessBoardSquares[x][y-1].setBackground(color);
									chessBoardSquares[x][y].setBackground(color);
									chessBoardSquares[x][y+1].setBackground(color);
									if(mode==1){
										this.ship_longth=0;
									}
								}
							}
						}	
						break;
					case 4:
						if(chessBoardSquares[x][y-2].getBackground() != Color.YELLOW && chessBoardSquares[x][y-2].getBackground() != Color.green)
						{
							if(chessBoardSquares[x][y-1].getBackground() != Color.YELLOW && chessBoardSquares[x][y-1].getBackground() != Color.green)
							{
								if(chessBoardSquares[x][y].getBackground() != Color.YELLOW && chessBoardSquares[x][y].getBackground() != Color.green)
								{
									if(chessBoardSquares[x][y+1].getBackground() != Color.YELLOW && chessBoardSquares[x][y+1].getBackground() != Color.green)
									{
										chessBoardSquares[x][y-2].setBackground(color);
										chessBoardSquares[x][y-1].setBackground(color);
										chessBoardSquares[x][y].setBackground(color);
										chessBoardSquares[x][y+1].setBackground(color);
										if(mode==1){
											this.ship_longth=0;
										}
									}
								}
							}
						}
						break;
					case 5:
						if(chessBoardSquares[x][y-2].getBackground() != Color.YELLOW && chessBoardSquares[x][y-2].getBackground() != Color.green)
						{
							if(chessBoardSquares[x][y-1].getBackground() != Color.YELLOW && chessBoardSquares[x][y-1].getBackground() != Color.green)
							{
								if(chessBoardSquares[x][y].getBackground() != Color.YELLOW && chessBoardSquares[x][y].getBackground() != Color.green)
								{
									if(chessBoardSquares[x][y+1].getBackground() != Color.YELLOW && chessBoardSquares[x][y+1].getBackground() != Color.green)
									{
										if(chessBoardSquares[x][y+2].getBackground() != Color.YELLOW && chessBoardSquares[x][y+2].getBackground() != Color.green)
										{
											//System.out.println("5-5");
											chessBoardSquares[x][y-2].setBackground(color);
											chessBoardSquares[x][y-1].setBackground(color);
											chessBoardSquares[x][y].setBackground(color);
											chessBoardSquares[x][y+1].setBackground(color);
											chessBoardSquares[x][y+2].setBackground(color);
											if(mode==1){
												//System.out.println("deploy!!!");
												//System.out.println("ship_length is " + this.ship_longth);
												this.ship_longth=0;
												//System.out.println("ship_length is " + this.ship_longth);
											}
										}
									}
								}
							}
						}
						break;
					}
				}catch(Exception ex){
					//System.out.println("out of range");
				}
			}
		}
		
	public void mkchessboard(){
		chessBoard = new JPanel(new GridLayout(0, 11)); // let chessBoard has 11 cols
		chessBoard.setBorder(new LineBorder(Color.BLACK));
		Insets buttonMargin = new Insets(10,10,10,10); // define button sizes 
		
		for(i = 0; i < chessBoardSquares.length; i++){ 
			for(j = 0; j < chessBoardSquares[i].length ; j++){
				char t = a; // check 
				int y=i; // button ID x
				int x=j; // button ID y
				//int sel= 0; // check this buttin is been selected
				JButton b = new JButton();  // make new button 
				b.setMargin(buttonMargin);  //**I Can't explain how it work 
				b.setBackground(Color.BLUE);
				this.chessBoardSquares[j][i] = b; 
				//this.chessBoardSquares[j][i].addActionListener(buttonListener);
				
				MouseListener deploychess = new MouseListener() {
					// mouselistener 
					
					public void mouseReleased(MouseEvent e) {
					}
					public void mousePressed(MouseEvent e) {
					}
					public void mouseExited(MouseEvent e) {
						switchstate(x,y,Color.blue,0);
						//chessBoardSquares[x][y].setBackground(Color.BLUE);
					}
					public void mouseEntered(MouseEvent e) {
						switchstate(x,y,Color.BLACK,0);
						//chessBoardSquares[x][y].setBackground(Color.BLACK);
						//System.out.println("Mouse Entered!!  x is " + x + " y is " + y + "t is " + t);
					}
					public void mouseClicked(MouseEvent e) {
						int c = e.getButton();
						if (c == MouseEvent.BUTTON1){ //------deploy------
							System.out.println("deploy!!!");
							if(a == 'e'){
								switchstate(x,y,Color.red,1);
							}else{
								switchstate(x,y,Color.YELLOW,1);
							}
							
						}
						if (c == MouseEvent.BUTTON3) {
							switchstate(x,y,Color.BLUE,2);
							System.out.println("switch!!!");
						}
					}
					
				};
				this.chessBoardSquares[j][i].addMouseListener(deploychess);
				//System.out.println("x is " + x + " y is " + y);
			}
		}
		//System.out.println("check 1");
		this.chessBoard.add(new JLabel(""));
		for (i = 0; i < 10; i++) {
            this.chessBoard.add(new JLabel(COLS.substring(i, i + 1),SwingConstants.CENTER));
        }
		//chessBoard.add(new JLabel(""));
		//System.out.println("check 2");
		//---------------------------------------------------
		for (i = 0; i < 10; i++) {
            for (j = 0; j < 10; j++) {
                switch (j) {
                    case 0:
                        this.chessBoard.add(new JLabel("" + (i + 1),SwingConstants.CENTER));
                    default:
                        this.chessBoard.add(chessBoardSquares[j][i]);
                }
            }
        }
	}
}