package tests;
	import gameLogic.*;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

	import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Renderer.*;

	public class RenderTests extends JFrame {

		JPanel pane = new JPanel();

		int width = 800;
		int height = 600;

		public RenderTests(){
			super("Frame 1");
			setBounds(100,100,width,height);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Container con = this.getContentPane();
			pane.setLayout(null);
			pane.setSize(height,width);
			con.add(pane);
		

			

			setVisible(true);
		}
public void paint(Graphics g){				//System.out.println("Drawing room now");
	Room r = new Room(10,10);
	Renderer renderer = new Renderer();
	renderer.drawLevel(g, r);
	
	
	
}
		public void actionPerformed(ActionEvent e){

		}

		public static void main(String args[]) {
			new RenderTests();
		}
	}


