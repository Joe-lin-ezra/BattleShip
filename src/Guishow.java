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
	//private static final JPanel gui = new JPanel(new BorderLayout(3, 3));
	
	private JPanel chessBoardy,chessBoarde;
	private Chessboard chessBoardz;
	
	private static JFrame f;
	
	JScrollPane jScrollPane=null;
	private JPanel title = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel content  = new JPanel(new FlowLayout(FlowLayout.LEADING));
	private JPanel ground  = new JPanel(new FlowLayout(FlowLayout.LEADING));
	private JButton conb = new JButton("Start Game");
	private JLabel namelabel = new JLabel("Nickname :");
	private JTextField nickname;
	private JTextArea area = new JTextArea();
	private static final String COLS = "ABCDEFGHIJK";
	private final JLabel message = new JLabel("Warship battle!!!!!");
	private boolean check_login = false;
	private boolean check_join = false;
	//define deploy button
	private JButton aircraft_carrier = new JButton("BBV"); //Shorthand
	private JButton battleship = new JButton("BB");
	private JButton cruiser = new JButton("CL");
	private JButton destroyer = new JButton("DD");
	private JButton submarine = new JButton("SS");
	private JButton deployyy = new JButton("Deploy");
	//FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 0, 0); 
	int i,j;
	public int ship_longths=0,ships_ver=0;
	public ArrayList<Location> location = new ArrayList<Location>(); // changing "Locate" to "Location"
	Guishow(){
		client = new Client();
		welcomepage();
		//initializeGui();
	}
	private void update(){
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
	public final void welcomepage(){
		guia.setBorder(new EmptyBorder(5, 5, 5, 5)); // make border style
		nickname = new JTextField(20);
		//gui.setLayout(layout);
		ActionListener buttonListener = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        check_login = client.login(nickname.getText()); // call function login by RMI
						//conb.setText("waiting");
						if(check_login == true){
							System.out.println("login"); // use to debug
							//check_join = client.join(); // call function join by RMI
							if(client.join()==true)
							{
								conb.setEnabled(!conb.isEnabled());
								while(true){
									System.out.println("w");
									if(client.getroomstate()!= false){
										update();
										System.out.println("done");
										break;
									}else{
										System.out.println("waiting");
									}
									
								}
							}
						}
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
	
	public final void initializeGui(){
		guic.setBorder(new EmptyBorder(5, 5, 5, 5)); // make border style
		guic.add(message,BorderLayout.NORTH); 
		chessBoardy = new JPanel(new GridLayout(0, 11));// get new board
		chessBoardy.setBorder(new LineBorder(Color.BLACK)); // set chessboard
		chessBoardz = new Chessboard('y'); // make new chessboard
		chessBoardy=chessBoardz.getChessBoard(); // change type
		guic.add(chessBoardy,BorderLayout.EAST); // add chessboard in west
		area.setLineWrap(true); 
		jScrollPane = new JScrollPane(area);
		guic.add(jScrollPane,BorderLayout.CENTER);
		//chessBoarde = new JPanel(new GridLayout(1, 11));
		//chessBoarde.setBorder(new LineBorder(Color.BLACK));
		chessBoardz = new Chessboard('z');
		//chessBoarde=chessBoardz.getChessBoard();
		guic.add(chessBoardz.getChessBoard(),BorderLayout.WEST);
		//System.out.println(chessBoardz.getship_longth());
		ground.add(aircraft_carrier); //BBV 
		ground.add(battleship);//BB
		ground.add(cruiser); //CL
		ground.add(destroyer); //DD
		ground.add(submarine); //SS
		ground.add(deployyy);// deploy 
		guic.add(ground,BorderLayout.SOUTH);
		/*ActionListener deployee = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("eee");
				System.out.println(chessBoardz.getChessButton(0,1));
			}
		};*/
		ActionListener deployListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                        if(e.getActionCommand() == "BBV"){
							System.out.println("BBV");
							chessBoardz.setship_longth(5);  //
							aircraft_carrier.setEnabled(!aircraft_carrier.isEnabled());
							//chessBoardy.ship_longth=5;
						}else if(e.getActionCommand() == "BB"){
							System.out.println("BB");
							chessBoardz.ship_longth=4;
							battleship.setEnabled(!battleship.isEnabled());
							//chessBoardy.ship_longth=4;
						}else if(e.getActionCommand() == "CL"){
							System.out.println("CL");
							chessBoardz.ship_longth=3;
							cruiser.setEnabled(!cruiser.isEnabled());
							//chessBoardy.ship_longth=3;
						}else if(e.getActionCommand() == "DD"){
							System.out.println("DD");
							chessBoardz.ship_longth=2;
							destroyer.setEnabled(!destroyer.isEnabled());
							//chessBoardy.ship_longth=2;
						}else if(e.getActionCommand() == "SS"){
							System.out.println("SS");
							chessBoardz.ship_longth=1;
							submarine.setEnabled(!submarine.isEnabled());
							//chessBoardy.ship_longth=1;
						}else if(e.getActionCommand() == "Deploy"){
							//System.out.println("eee");
							for (i = 0; i < 10; i++) {
								for (j = 0; j < 10; j++) {
									if(chessBoardz.getChessButton(i,j).getBackground()==Color.yellow)
									{
										Location ddd = new Location(i,j); // changing "Locate" to "Location"
										location.add(ddd);
										System.out.println("(" + i + "," + j + ") is yellow");
									}
								}
							}
						}
				//System.out.println("get!! x is " + x + " y is " + y + "t is " + t);
            }
		//chessBoarde = new mkchessboard();
		//---------------------------------------------------
		};
		aircraft_carrier.addActionListener(deployListener);
		battleship.addActionListener(deployListener);
		cruiser.addActionListener(deployListener);
		destroyer.addActionListener(deployListener);
		submarine.addActionListener(deployListener);
		deployyy.addActionListener(deployListener);
	}
	/*public final JComponent getGui() {
        return gui;
    }*/
	public final JComponent getGuia() {
        return guia;
    }
	public static void main(String args[]){
		System.out.println("Hello Gui!!");
		Runnable r = new Runnable() {
			@Override
			public void run(){
				Guishow guib = new Guishow();
				f = new JFrame("warship");
				f.add(guib.getGuia());
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// let Gui close when user use "x"
				f.setLocationByPlatform(true);//
				f.pack();
                f.setMinimumSize(f.getSize());// ensures the minimum size is enforced.
                f.setVisible(true);
				f.setResizable(false);
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
	public int ship_longth=1,ship_ver=0;// here need modfy when user select
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
				/*ActionListener buttonListener = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //this block is use to check is button work
						// this block can use to transfer data  
						System.out.println("get!! x is " + x + " y is " + y + "t is " + t);
                    }
				};*/
				
				this.chessBoardSquares[j][i] = b; 
				//this.chessBoardSquares[j][i].addActionListener(buttonListener);
				MouseListener deploychess = new MouseListener() {
					// mouselistener 
					
					public void mouseReleased(MouseEvent e) {
					}
					public void mousePressed(MouseEvent e) {
					}
					public void mouseExited(MouseEvent e) {
						//chessBoardSquares[x][y].setBackground(Color.BLUE);
						if(chessBoardSquares[x][y].getBackground() != Color.YELLOW){
							//System.out.println(chessBoardSquares[x][y].getBackground());
						if(ship_ver == 0){
							try{
								switch(ship_longth){
									case 1:
										if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
										{
											chessBoardSquares[x][y].setBackground(Color.BLUE);
										}
										break;
									case 2:
										if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
										{
											if(chessBoardSquares[x+1][y].getBackground() != Color.YELLOW)
											{
												chessBoardSquares[x][y].setBackground(Color.BLUE);
												chessBoardSquares[x+1][y].setBackground(Color.BLUE);
											}
										}
										break;
									case 3:
										if(chessBoardSquares[x-1][y].getBackground() != Color.YELLOW)
										{
											if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x+1][y].getBackground() != Color.YELLOW)
												{
													chessBoardSquares[x-1][y].setBackground(Color.BLUE);
													chessBoardSquares[x][y].setBackground(Color.BLUE);
													chessBoardSquares[x+1][y].setBackground(Color.BLUE);
												}
											}
										}
										break;
									case 4:
										if(chessBoardSquares[x-2][y].getBackground() != Color.YELLOW)
										{
											if(chessBoardSquares[x-1][y].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
												{
													if(chessBoardSquares[x+1][y].getBackground() != Color.YELLOW)
													{
														chessBoardSquares[x-2][y].setBackground(Color.BLUE);
														chessBoardSquares[x-1][y].setBackground(Color.BLUE);
														chessBoardSquares[x][y].setBackground(Color.BLUE);
														chessBoardSquares[x+1][y].setBackground(Color.BLUE);
													}
												}
											}
										}	
										break;
									case 5:
										if(chessBoardSquares[x-2][y].getBackground() != Color.YELLOW)
										{
											if(chessBoardSquares[x-1][y].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
												{
													if(chessBoardSquares[x+1][y].getBackground() != Color.YELLOW)
													{
														if(chessBoardSquares[x+2][y].getBackground() != Color.YELLOW)
														{
															chessBoardSquares[x-2][y].setBackground(Color.BLUE);
															chessBoardSquares[x-1][y].setBackground(Color.BLUE);
															chessBoardSquares[x][y].setBackground(Color.BLUE);
															chessBoardSquares[x+1][y].setBackground(Color.BLUE);
															chessBoardSquares[x+2][y].setBackground(Color.BLUE);
														}
													}
												}
											}
										}
										break;
								}
							} catch (Exception ex){
								System.out.println("out of range");
							}
							
						}else if(ship_ver == 1){
							try{
								switch(ship_longth){
									case 1:
										if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
										{
											chessBoardSquares[x][y].setBackground(Color.BLUE);
										}
											
										break;
									case 2:
										if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
										{
											if(chessBoardSquares[x][y+1].getBackground() != Color.YELLOW)
											{
												chessBoardSquares[x][y].setBackground(Color.BLUE);
												chessBoardSquares[x][y+1].setBackground(Color.BLUE);
											}
										}
											
										break;
									case 3:
										if(chessBoardSquares[x][y-1].getBackground() != Color.YELLOW)
										{
											if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x][y+1].getBackground() != Color.YELLOW)
												{
													chessBoardSquares[x][y-1].setBackground(Color.BLUE);
													chessBoardSquares[x][y].setBackground(Color.BLUE);
													chessBoardSquares[x][y+1].setBackground(Color.BLUE);
												}
											}
										}	
										break;
									case 4:
										if(chessBoardSquares[x][y-2].getBackground() != Color.YELLOW)
										{
											if(chessBoardSquares[x][y-1].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
												{
													if(chessBoardSquares[x][y+1].getBackground() != Color.YELLOW)
													{
														chessBoardSquares[x][y-2].setBackground(Color.BLUE);
														chessBoardSquares[x][y-1].setBackground(Color.BLUE);
														chessBoardSquares[x][y].setBackground(Color.BLUE);
														chessBoardSquares[x][y+1].setBackground(Color.BLUE);
													}
												}
											}
										}
										break;
									case 5:
										if(chessBoardSquares[x][y-2].getBackground() != Color.YELLOW)
										{
											if(chessBoardSquares[x][y-1].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
												{
													if(chessBoardSquares[x][y+1].getBackground() != Color.YELLOW)
													{
														if(chessBoardSquares[x][y+2].getBackground() != Color.YELLOW)
														{
															//System.out.println("5-5");
															chessBoardSquares[x][y-2].setBackground(Color.BLUE);
															chessBoardSquares[x][y-1].setBackground(Color.BLUE);
															chessBoardSquares[x][y].setBackground(Color.BLUE);
															chessBoardSquares[x][y+1].setBackground(Color.BLUE);
															chessBoardSquares[x][y+2].setBackground(Color.BLUE);
														}
													}
												}
											}
										}
											
										break;
							}
							}catch(Exception ex){
								System.out.println("out of range");
							}
							
						}
					}
					}
					public void mouseEntered(MouseEvent e) {
						if(chessBoardSquares[x][y].getBackground() != Color.YELLOW){
						if(ship_ver == 0){
							try{
								switch(ship_longth){
									case 1:
										if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
										{
											chessBoardSquares[x][y].setBackground(Color.BLACK);
										}
										break;
									case 2:
										if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
										{
											if(chessBoardSquares[x+1][y].getBackground() != Color.YELLOW)
											{
												chessBoardSquares[x][y].setBackground(Color.BLACK);
												chessBoardSquares[x+1][y].setBackground(Color.BLACK);
											}
										}
										break;
									case 3:
										if(chessBoardSquares[x-1][y].getBackground() != Color.YELLOW)
										{
											if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x+1][y].getBackground() != Color.YELLOW)
												{
													chessBoardSquares[x-1][y].setBackground(Color.BLACK);
													chessBoardSquares[x][y].setBackground(Color.BLACK);
													chessBoardSquares[x+1][y].setBackground(Color.BLACK);
												}
											}
										}
										break;
									case 4:
										if(chessBoardSquares[x-2][y].getBackground() != Color.YELLOW)
										{
											if(chessBoardSquares[x-1][y].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
												{
													if(chessBoardSquares[x+1][y].getBackground() != Color.YELLOW)
													{
														chessBoardSquares[x-2][y].setBackground(Color.BLACK);
														chessBoardSquares[x-1][y].setBackground(Color.BLACK);
														chessBoardSquares[x][y].setBackground(Color.BLACK);
														chessBoardSquares[x+1][y].setBackground(Color.BLACK);
													}
												}
											}
										}	
										break;
									case 5:
										if(chessBoardSquares[x-2][y].getBackground() != Color.YELLOW)
										{
											if(chessBoardSquares[x-1][y].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
												{
													if(chessBoardSquares[x+1][y].getBackground() != Color.YELLOW)
													{
														if(chessBoardSquares[x+2][y].getBackground() != Color.YELLOW)
														{
															chessBoardSquares[x-2][y].setBackground(Color.BLACK);
															chessBoardSquares[x-1][y].setBackground(Color.BLACK);
															chessBoardSquares[x][y].setBackground(Color.BLACK);
															chessBoardSquares[x+1][y].setBackground(Color.BLACK);
															chessBoardSquares[x+2][y].setBackground(Color.BLACK);
														}
													}
												}
											}
										}
										break;
								}
							} catch (Exception ex){
								System.out.println("out of range");
							}
							
						}else if(ship_ver == 1){
							try{
								switch(ship_longth){
									case 1:
										if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
										{
											chessBoardSquares[x][y].setBackground(Color.BLACK);
										}
											
										break;
									case 2:
										if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
										{
											if(chessBoardSquares[x][y+1].getBackground() != Color.YELLOW)
											{
												chessBoardSquares[x][y].setBackground(Color.BLACK);
												chessBoardSquares[x][y+1].setBackground(Color.BLACK);
											}
										}
											
										break;
									case 3:
										if(chessBoardSquares[x][y-1].getBackground() != Color.YELLOW)
										{
											if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x][y+1].getBackground() != Color.YELLOW)
												{
													chessBoardSquares[x][y-1].setBackground(Color.BLACK);
													chessBoardSquares[x][y].setBackground(Color.BLACK);
													chessBoardSquares[x][y+1].setBackground(Color.BLACK);
												}
											}
										}	
										break;
									case 4:
										if(chessBoardSquares[x][y-2].getBackground() != Color.YELLOW)
										{
											if(chessBoardSquares[x][y-1].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
												{
													if(chessBoardSquares[x][y+1].getBackground() != Color.YELLOW)
													{
														chessBoardSquares[x][y-2].setBackground(Color.BLACK);
														chessBoardSquares[x][y-1].setBackground(Color.BLACK);
														chessBoardSquares[x][y].setBackground(Color.BLACK);
														chessBoardSquares[x][y+1].setBackground(Color.BLACK);
													}
												}
											}
										}
										break;
									case 5:
										if(chessBoardSquares[x][y-2].getBackground() != Color.YELLOW)
										{
											if(chessBoardSquares[x][y-1].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
												{
													if(chessBoardSquares[x][y+1].getBackground() != Color.YELLOW)
													{
														if(chessBoardSquares[x][y+2].getBackground() != Color.YELLOW)
														{
															//System.out.println("5-5");
															chessBoardSquares[x][y-2].setBackground(Color.BLACK);
															chessBoardSquares[x][y-1].setBackground(Color.BLACK);
															chessBoardSquares[x][y].setBackground(Color.BLACK);
															chessBoardSquares[x][y+1].setBackground(Color.BLACK);
															chessBoardSquares[x][y+2].setBackground(Color.BLACK);
														}
													}
												}
											}
										}
										break;
							}
							}catch(Exception ex){
								System.out.println("out of range");
							}
							
						}
						}
						//chessBoardSquares[x][y].setBackground(Color.BLACK);
						//System.out.println("Mouse Entered!!  x is " + x + " y is " + y + "t is " + t);
					}
					public void mouseClicked(MouseEvent e) {
						int c = e.getButton();
						if (c == MouseEvent.BUTTON1){ //------deploy------
							System.out.println("deploy!!!");
							if(ship_ver == 0){
								try{
									switch(ship_longth){
										case 1:
											if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
											{
												chessBoardSquares[x][y].setBackground(Color.YELLOW);
											}
											break;
										case 2:
											if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x+1][y].getBackground() != Color.YELLOW)
												{
													chessBoardSquares[x][y].setBackground(Color.YELLOW);
													chessBoardSquares[x+1][y].setBackground(Color.YELLOW);
												}
											}
											break;
										case 3:
											if(chessBoardSquares[x-1][y].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
												{
													if(chessBoardSquares[x+1][y].getBackground() != Color.YELLOW)
													{
														chessBoardSquares[x-1][y].setBackground(Color.YELLOW);
														chessBoardSquares[x][y].setBackground(Color.YELLOW);
														chessBoardSquares[x+1][y].setBackground(Color.YELLOW);
													}
												}
											}
											break;
										case 4:
											if(chessBoardSquares[x-2][y].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x-1][y].getBackground() != Color.YELLOW)
												{
													if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
													{
														if(chessBoardSquares[x+1][y].getBackground() != Color.YELLOW)
														{
															chessBoardSquares[x-2][y].setBackground(Color.YELLOW);
															chessBoardSquares[x-1][y].setBackground(Color.YELLOW);
															chessBoardSquares[x][y].setBackground(Color.YELLOW);
															chessBoardSquares[x+1][y].setBackground(Color.YELLOW);
														}
													}
												}
											}	
											break;
										case 5:
											if(chessBoardSquares[x-2][y].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x-1][y].getBackground() != Color.YELLOW)
												{
													if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
													{
														if(chessBoardSquares[x+1][y].getBackground() != Color.YELLOW)
														{
															if(chessBoardSquares[x+2][y].getBackground() != Color.YELLOW)
															{
																chessBoardSquares[x-2][y].setBackground(Color.YELLOW);
																chessBoardSquares[x-1][y].setBackground(Color.YELLOW);
																chessBoardSquares[x][y].setBackground(Color.YELLOW);
																chessBoardSquares[x+1][y].setBackground(Color.YELLOW);
																chessBoardSquares[x+2][y].setBackground(Color.YELLOW);
															}
														}
													}
												}
											}
											break;
									}
								} catch (Exception ex){
									System.out.println("out of range");
								}
							
						}else if(ship_ver == 1){
							try{
								switch(ship_longth){
									case 1:
										if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
										{
											chessBoardSquares[x][y].setBackground(Color.YELLOW);
										}
											
										break;
									case 2:
										if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
										{
											if(chessBoardSquares[x][y+1].getBackground() != Color.YELLOW)
											{
												chessBoardSquares[x][y].setBackground(Color.YELLOW);
												chessBoardSquares[x][y+1].setBackground(Color.YELLOW);
											}
										}
											
										break;
									case 3:
										if(chessBoardSquares[x][y-1].getBackground() != Color.YELLOW)
										{
											if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x][y+1].getBackground() != Color.YELLOW)
												{
													chessBoardSquares[x][y-1].setBackground(Color.YELLOW);
													chessBoardSquares[x][y].setBackground(Color.YELLOW);
													chessBoardSquares[x][y+1].setBackground(Color.YELLOW);
												}
											}
										}	
										break;
									case 4:
										if(chessBoardSquares[x][y-2].getBackground() != Color.YELLOW)
										{
											if(chessBoardSquares[x][y-1].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
												{
													if(chessBoardSquares[x][y+1].getBackground() != Color.YELLOW)
													{
														chessBoardSquares[x][y-2].setBackground(Color.YELLOW);
														chessBoardSquares[x][y-1].setBackground(Color.YELLOW);
														chessBoardSquares[x][y].setBackground(Color.YELLOW);
														chessBoardSquares[x][y+1].setBackground(Color.YELLOW);
													}
												}
											}
										}
										break;
									case 5:
										if(chessBoardSquares[x][y-2].getBackground() != Color.YELLOW)
										{
											if(chessBoardSquares[x][y-1].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
												{
													if(chessBoardSquares[x][y+1].getBackground() != Color.YELLOW)
													{
														if(chessBoardSquares[x][y+2].getBackground() != Color.YELLOW)
														{
															//System.out.println("5-5");
															chessBoardSquares[x][y-2].setBackground(Color.YELLOW);
															chessBoardSquares[x][y-1].setBackground(Color.YELLOW);
															chessBoardSquares[x][y].setBackground(Color.YELLOW);
															chessBoardSquares[x][y+1].setBackground(Color.YELLOW);
															chessBoardSquares[x][y+2].setBackground(Color.YELLOW);
														}
													}
												}
											}
										}
										break;
							}
							}catch(Exception ex){
								System.out.println("out of range");
							}
							
						}
						ship_longth=0;
						}
						if (c == MouseEvent.BUTTON3) {
							if(ship_ver == 0){
								ship_ver=1;
								try{
									switch(ship_longth){
										case 1:
											if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
											{
												chessBoardSquares[x][y].setBackground(Color.BLUE);
											}
											break;
										case 2:
											if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x+1][y].getBackground() != Color.YELLOW)
												{
													chessBoardSquares[x][y].setBackground(Color.BLUE);
													chessBoardSquares[x+1][y].setBackground(Color.BLUE);
												}
											}
											break;
										case 3:
											if(chessBoardSquares[x-1][y].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
												{
													if(chessBoardSquares[x+1][y].getBackground() != Color.YELLOW)
													{
														chessBoardSquares[x-1][y].setBackground(Color.BLUE);
														chessBoardSquares[x][y].setBackground(Color.BLUE);
														chessBoardSquares[x+1][y].setBackground(Color.BLUE);
													}
												}
											}
											break;
										case 4:
											if(chessBoardSquares[x-2][y].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x-1][y].getBackground() != Color.YELLOW)
												{
													if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
													{
														if(chessBoardSquares[x+1][y].getBackground() != Color.YELLOW)
														{
															chessBoardSquares[x-2][y].setBackground(Color.BLUE);
															chessBoardSquares[x-1][y].setBackground(Color.BLUE);
															chessBoardSquares[x][y].setBackground(Color.BLUE);
															chessBoardSquares[x+1][y].setBackground(Color.BLUE);
														}
													}
												}
											}	
											break;
										case 5:
											if(chessBoardSquares[x-2][y].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x-1][y].getBackground() != Color.YELLOW)
												{
													if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
													{
														if(chessBoardSquares[x+1][y].getBackground() != Color.YELLOW)
														{
															if(chessBoardSquares[x+2][y].getBackground() != Color.YELLOW)
															{
																chessBoardSquares[x-2][y].setBackground(Color.BLUE);
																chessBoardSquares[x-1][y].setBackground(Color.BLUE);
																chessBoardSquares[x][y].setBackground(Color.BLUE);
																chessBoardSquares[x+1][y].setBackground(Color.BLUE);
																chessBoardSquares[x+2][y].setBackground(Color.BLUE);
															}
														}
													}
												}
											}
											break;
									}
								} catch (Exception ex){
									System.out.println("out of range");
								}
							
						}else if(ship_ver == 1){
							ship_ver = 0;
							try{
								switch(ship_longth){
									case 1:
										if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
										{
											chessBoardSquares[x][y].setBackground(Color.BLUE);
										}
											
										break;
									case 2:
										if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
										{
											if(chessBoardSquares[x][y+1].getBackground() != Color.YELLOW)
											{
												chessBoardSquares[x][y].setBackground(Color.BLUE);
												chessBoardSquares[x][y+1].setBackground(Color.BLUE);
											}
										}
											
										break;
									case 3:
										if(chessBoardSquares[x][y-1].getBackground() != Color.YELLOW)
										{
											if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x][y+1].getBackground() != Color.YELLOW)
												{
													chessBoardSquares[x][y-1].setBackground(Color.BLUE);
													chessBoardSquares[x][y].setBackground(Color.BLUE);
													chessBoardSquares[x][y+1].setBackground(Color.BLUE);
												}
											}
										}	
										break;
									case 4:
										if(chessBoardSquares[x][y-2].getBackground() != Color.YELLOW)
										{
											if(chessBoardSquares[x][y-1].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
												{
													if(chessBoardSquares[x][y+1].getBackground() != Color.YELLOW)
													{
														chessBoardSquares[x][y-2].setBackground(Color.BLUE);
														chessBoardSquares[x][y-1].setBackground(Color.BLUE);
														chessBoardSquares[x][y].setBackground(Color.BLUE);
														chessBoardSquares[x][y+1].setBackground(Color.BLUE);
													}
												}
											}
										}
										break;
									case 5:
										if(chessBoardSquares[x][y-2].getBackground() != Color.YELLOW)
										{
											if(chessBoardSquares[x][y-1].getBackground() != Color.YELLOW)
											{
												if(chessBoardSquares[x][y].getBackground() != Color.YELLOW)
												{
													if(chessBoardSquares[x][y+1].getBackground() != Color.YELLOW)
													{
														if(chessBoardSquares[x][y+2].getBackground() != Color.YELLOW)
														{
															//System.out.println("5-5");
															chessBoardSquares[x][y-2].setBackground(Color.BLUE);
															chessBoardSquares[x][y-1].setBackground(Color.BLUE);
															chessBoardSquares[x][y].setBackground(Color.BLUE);
															chessBoardSquares[x][y+1].setBackground(Color.BLUE);
															chessBoardSquares[x][y+2].setBackground(Color.BLUE);
														}
													}
												}
											}
										}
										break;
							}
							}catch(Exception ex){
								System.out.println("out of range");
							}
							
						}
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