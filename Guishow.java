import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;

public class Guishow{
	private final JPanel gui = new JPanel(new BorderLayout(3, 3));
	Guishow(){
		initializeGui();
	}
	public final void initializeGui(){
		gui.setBorder(new EmptyBorder(5, 5, 5, 5));
		
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