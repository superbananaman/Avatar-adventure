package gui;
/**
 * Represents the Window displaying the game
 * @author Devlin Mahoney
 */
import gameLogic.Room;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import ClientServer.Slave;
import Renderer.RenderWindow;
import Renderer.Renderer;
import Renderer.Sprite;
import Renderer.Tile;


public class ClientFrame extends JFrame implements MouseListener, KeyListener{

    private JPanel contentPane;
    private JPanel renderWindow;

    JTextArea textArea;
    JTextField inputArea;
    String currentText = "";

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ClientFrame frame = new ClientFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public ClientFrame() {
    	addMouseListener(this);
        setTitle("Avatar Adventure! ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 720);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{800, 200, 0};
        gbl_contentPane.rowHeights = new int[]{600, 114, 6};
        gbl_contentPane.columnWeights = new double[]{5.0, 1.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{50.0, 10.0, 0.5, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);

        //Render Window Placeholder
        //renderWindow = new JPanel();
        renderWindow = new RenderWindow("room1");
        renderWindow.setBackground(Color.BLACK);
        renderWindow.addMouseListener(this);
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.insets = new Insets(0, 0, 5, 5);
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 0;
        contentPane.add(renderWindow, gbc_panel);
        renderWindow.setFocusable(true);

        //Inventory Place Holder
        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        GridBagConstraints gbc_panel_2 = new GridBagConstraints();
        gbc_panel_2.fill = GridBagConstraints.BOTH;
        gbc_panel_2.insets = new Insets(0, 0, 5, 0);
        gbc_panel_2.gridx = 1;
        gbc_panel_2.gridy = 0;
        contentPane.add(panel_2, gbc_panel_2);

        //Consoleoutput placeholder
        textArea = new JTextArea();
        GridBagConstraints gbc_textArea = new GridBagConstraints();
        textArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        textArea.setBackground(Color.WHITE);
        textArea.setOpaque(true);
        textArea.setFocusable(false);
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        gbc_textArea.insets = new Insets(0, 0, 0, 5);
        gbc_textArea.fill = GridBagConstraints.BOTH;
        gbc_textArea.gridx = 0;
        gbc_textArea.gridy = 1;
        contentPane.add(scroll, gbc_textArea);

        //ConsoleInput
        inputArea = new JTextField();
        inputArea.addKeyListener(this);
        GridBagConstraints gbc_inputArea = new GridBagConstraints();
        inputArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        inputArea.setBackground(Color.WHITE);
        inputArea.setOpaque(true);
        gbc_inputArea.insets = new Insets(0, 0, 0, 5);
        gbc_inputArea.fill = GridBagConstraints.BOTH;
        gbc_inputArea.gridx = 0;
        gbc_inputArea.gridy = 2;
        contentPane.add(inputArea, gbc_inputArea);

        setVisible(true);

        currentText += "I'm a console window!";
        textArea.setText("<html>"+currentText+"</html>");

    }

    /**
     * Displays a message on the console
     * @param message The message to display
     */
    public void toConsole(String message){
    	currentText = currentText +"<br>" +message;
    	textArea.setText("<html>"+currentText+"</html>");
    }

    /**
     * Displays chat from a player to the console
     * @param name	The Player saying the message
     * @param message	The Message
     */
    public void toConsole(String name, String message){
    	currentText = currentText +"<br>" +name +": \""+ message+"\"";
    	textArea.setText("<html>"+currentText+"</html>");
    }

	public void mouseClicked(MouseEvent e) {
		textArea.append("\n"+e.getSource().getClass().toString());
		if(e.getSource().getClass().toString().equals("class Renderer.RenderWindow")){
			((RenderWindow) e.getSource()).requestFocus();
		}

	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}


	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 10){
			//they presed enter
			Slave.sendMessage(inputArea.getText());
			inputArea.setText("");
		};
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

}
