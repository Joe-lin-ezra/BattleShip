import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;


public class Guishow{
	
	private final JPanel gui = new JPanel(new BorderLayout(3, 3));
	private JButton[][] chessBoardSquares = new JButton[10][10];
	private JPanel chessBoardy,chessBoarde,chessBoard;
	private Chessboard chessBoardz;
	JScrollPane jScrollPane=null;
	private JTextArea area = new JTextArea();
	private static final String COLS = "ABCDEFGHIJK";
	int i,j;
	Guishow(){
		initializeGui();
	}
	public final void initializeGui(){
		gui.setBorder(new EmptyBorder(5, 5, 5, 5)); // make border style
		chessBoardy = new JPanel(new GridLayout(0, 11));
		chessBoardy.setBorder(new LineBorder(Color.BLACK));
		chessBoardz = new Chessboard('y');
		chessBoardy=chessBoardz.getChessBoard();
		gui.add(chessBoardy,BorderLayout.WEST);
		area.setLineWrap(true);
		jScrollPane = new JScrollPane(area);
		gui.add(jScrollPane,BorderLayout.CENTER);
		chessBoarde = new JPanel(new GridLayout(1, 11));
		chessBoarde.setBorder(new LineBorder(Color.BLACK));
		chessBoardz = new Chessboard('e');
		chessBoarde=chessBoardz.getChessBoard();
		gui.add(chessBoarde,BorderLayout.EAST);
		
		//chessBoarde = new mkchessboard();
		//---------------------------------------------------
		}
	
	public final JComponent getChessBoard() {
        return chessBoard;
    }
	public final JComponent getGui() {
        return gui;
    }
	public static void main(String args[]){
		System.out.println("Hello Gui!!");
		Runnable r = new Runnable() {
			@Override
			public void run(){
				Guishow gui = new Guishow();
				JFrame f = new JFrame("warship");
				f.add(gui.getGui());
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// let Gui close when user use "x"
				f.setLocationByPlatform(true);//
				f.pack();
                f.setMinimumSize(f.getSize());// ensures the minimum size is enforced.
                f.setVisible(true);
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
		System.out.println("return chessboard");
        return chessBoard;
    }
	public void mkchessboard(){
		chessBoard = new JPanel(new GridLayout(0, 11)); // let chessBoard has 11 cols
		chessBoard.setBorder(new LineBorder(Color.BLACK));
		Insets buttonMargin = new Insets(5,5,5,5); // define button sizes **I Can't explain how it work
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