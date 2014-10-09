package gui;
/**
 * Represents the Window displaying the game
 * @author Devlin Mahoney
 */
import gameLogic.Item;
import gameLogic.Player;
import gameLogic.Room;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import ClientServer.Slave;
import Renderer.RenderWindow;
import Renderer.Renderer;
import Renderer.Sprite;
import Renderer.Tile;


public class ClientFrame extends JFrame implements MouseListener, KeyListener{

    private JPanel contentPane;
    private RenderWindow renderWindow;

    private JTextArea textArea;
    private JTextField inputArea;
    private JScrollPane scroll;
    private ArrayList<JPanel> inventory;

    /**
     * Create the frame.
     */
    public ClientFrame(Player currentPlayer, java.util.List<Player> players) {
    	addMouseListener(this);
        setTitle("Avatar Adventure! ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 720);
        setResizable(false);

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
        renderWindow = new RenderWindow("room1", (ArrayList<Player>) players);
        //renderWindow = new RenderWindow("room1");
        renderWindow.setBackground(Color.BLACK);
        renderWindow.addMouseListener(this);
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.insets = new Insets(0, 0, 5, 5);
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 0;
        contentPane.add(renderWindow, gbc_panel);
        renderWindow.setFocusable(true);

        //Consoleoutput placeholder
        textArea = new JTextArea();
        GridBagConstraints gbc_textArea = new GridBagConstraints();
        textArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        textArea.setBackground(Color.WHITE);
        textArea.setOpaque(true);
        textArea.setFocusable(false);
        scroll = new JScrollPane(textArea);
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

      //Inventory Place Holder
        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        GridBagConstraints gbc_panel_2 = new GridBagConstraints();
        gbc_panel_2.fill = GridBagConstraints.BOTH;
        gbc_panel_2.insets = new Insets(0, 0, 5, 0);
        gbc_panel_2.gridx = 1;
        gbc_panel_2.gridy = 0;
        contentPane.add(panel_2, gbc_panel_2);

        GridLayout grid = new GridLayout(3,9);
        panel_2.setLayout(grid);
        inventory = new ArrayList<JPanel>();

        for(int i = 0; i < 9; i++){
        	for(int j = 0; j < 3; j++){
        		JPanel toAdd = new JPanel();
        		toAdd.setBackground(Color.BLACK);
        		inventory.add(toAdd);
        		panel_2.add(toAdd);
        	}
        }

        setVisible(true);
    }

    /**
     * Displays a message on the console
     * @param message The message to display
     */
    public void toConsole(String message){
    	if(!textArea.getText().equals(""))textArea.append("\n");
    	textArea.append(message);
    	textArea.setCaretPosition(textArea.getDocument().getLength());


    }

    /**
     * Displays chat from a player to the console
     * @param name	The Player saying the message
     * @param message	The Message
     */
    public void toConsole(String name, String message){
    	if(!textArea.getText().equals(""))textArea.append("\n");
    	textArea.append(name +": \""+ message+"\"");
    	textArea.setCaretPosition(textArea.getDocument().getLength());
    }

	public void mouseClicked(MouseEvent e) {
		//textArea.append("\n"+e.getSource().getClass().toString());
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

	//public List<Player> getPlayers(){
	//	return renderWindow.getPlayers();
	//}
}
