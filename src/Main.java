import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ClientServer.NetworkSetup;

public class Main {

//	public Main(){
//		public int askforPort(){
//		JTextField tf = new JTextField("40700");
//		String port = JOptionPane.showInputDialog(tf,"Enter a Port Number (default: 40700)");
//		while(port == null || port.length() < 5 || port.length() > 5) {
//			port = JOptionPane
//					.showInputDialog
//					("Invalid Port number. Please enter again.");
//		}
//		return Integer.parseInt(port);
//		}
//	}

	public static void main(String[] args) {
		NetworkSetup ns = new NetworkSetup(true);
	}
}
