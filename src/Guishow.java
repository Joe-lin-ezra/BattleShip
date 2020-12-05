import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;


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
	private JButton conb = new JButton("Start Game");
	private JLabel namelabel = new JLabel("Nickname :");
	private JTextField nickname;
	private JTextArea area = new JTextArea();
	private static final String COLS = "ABCDEFGHIJK";
	private final JLabel message = new JLabel("Warship battle!!!!!");
	private boolean check_login = false;
	private boolean check_join = false;
	//FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 0, 0); 
	int i,j;
	
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
						if(check_login == true){
							System.out.println("login"); // use to debug
							check_join = client.join(); // call function join by RMI
									if(client.join()==true)
									{
										while(true){
											System.out.println("w");
											if(client.player.roomId != -1){
												update();
												System.out.println("done");
												break;
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
		guic.add(chessBoardy,BorderLayout.WEST); // add chessboard in west
		area.setLineWrap(true); 
		jScrollPane = new JScrollPane(area);
		guic.add(jScrollPane,BorderLayout.CENTER);
		chessBoarde = new JPanel(new GridLayout(1, 11));
		chessBoarde.setBorder(new LineBorder(Color.BLACK));
		chessBoardz = new Chessboard('e');
		chessBoarde=chessBoardz.getChessBoard();
		guic.add(chessBoarde,BorderLayout.EAST);
		//chessBoarde = new mkchessboard();
		//---------------------------------------------------
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
	Chessboard(char a){// constructor
		this.a = a;
		System.out.println("make chessboard");
		mkchessboard();
	}
	
	public JPanel getChessBoard() {  // return 
		//System.out.println("return chessboard");
        return chessBoard;
    }
	public void mkchessboard(){
		chessBoard = new JPanel(new GridLayout(0, 11)); // let chessBoard has 11 cols
		chessBoard.setBorder(new LineBorder(Color.BLACK));
		Insets buttonMargin = new Insets(10,10,10,10); // define button sizes 
		for(i = 0; i < chessBoardSquares.length; i++){ 
			for(j = 0; j < chessBoardSquares[i].length ; j++){
				char t = a; // check 
				int x=i+1; // button ID x
				int y=j+1; // button ID y
				JButton b = new JButton();  // make new button 
				b.setMargin(buttonMargin);  //**I Can't explain how it work 
				b.setBackground(Color.BLUE);
				ActionListener buttonListener = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //this block is use to check is button work
						// this block can use to transfer data  
						System.out.println("get!! x is " + x + " y is " + y + "t is " + t);
                    }
				};
				this.chessBoardSquares[j][i] = b; 
				this.chessBoardSquares[j][i].addActionListener(buttonListener);
				System.out.println("x is " + x + " y is " + y);
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