package gui;
/**
 * Represents the Window displaying the game
 * @author Devlin Mahoney
 */
import gameLogic.Game;
import gameLogic.Inventory;
import gameLogic.Item;
import gameLogic.Player;
import gameLogic.Room;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import ClientServer.Server;
import ClientServer.Slave;
import Database.XMLWriter;
import Renderer.RenderWindow;
import Renderer.Renderer;
import Renderer.Sprite;
import Renderer.Tile;


public class ClientFrame extends JFrame implements MouseListener, KeyListener, ActionListener{

    private JPanel contentPane;
    private RenderWindow renderWindow;
    private Player currentPlayer;
    private HpPanel hpPanel;
    private int selected = 0;
    private JTextArea textArea;
    private JTextField inputArea;
    private JScrollPane scroll;
    private int selectedItem;

    private ArrayList<JLabel> inventory;
    private java.util.List<Player> players;
    private Game game;

    /**
     * Create the frame.
     */
    public ClientFrame(String UID, java.util.List<Player> players,Game game) {
        //addMouseListener(this);

    	this.game = game;
    	this.players = players;
        setTitle("Avatar Adventure! ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1010, 730);
        for(Player p: players){if(p.getUID().equals(UID)){currentPlayer = p;break;}}
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{800, 210, 0};
        gbl_contentPane.rowHeights = new int[]{600, 120, 10};
        gbl_contentPane.columnWeights = new double[]{5.0, 1.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{50.0, 10.0, 0.5, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);

        //Render Window Placeholder
        renderWindow = new RenderWindow("startroom", UID, (ArrayList<Player>) players, game);
        renderWindow.setBackground(Color.BLACK);
        renderWindow.addMouseListener(this);
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.insets = new Insets(0, 0, 5, 5);
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 0;
        contentPane.add(renderWindow, gbc_panel);
        renderWindow.setFocusable(true);

        //Console output placeholder
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

        GridLayout grid = new GridLayout(9,3,1,1);
        panel_2.setLayout(grid);
        inventory = new ArrayList<JLabel>();

        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 3; j++){
                JLabel toAdd = new JLabel();
                toAdd.setBackground(Color.BLACK);
                toAdd.addMouseListener(this);
                int n = 3*i+j;
                toAdd.setName(n+"");
                toAdd.setFocusable(false);
                toAdd.setOpaque(true);
                inventory.add(toAdd);
                panel_2.add(toAdd);
            }
        }

        //bottom right
        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        GridBagConstraints gbc_panel_3 = new GridBagConstraints();
        gbc_panel_3.fill = GridBagConstraints.BOTH;
        gbc_panel_3.insets = new Insets(0, 0, 5, 0);
        gbc_panel_3.gridx = 1;
        gbc_panel_3.gridy = 1;
        panel_3.setLayout(new BorderLayout());
        contentPane.add(panel_3, gbc_panel_3);

        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(150,40));
        saveButton.addActionListener(this);

        panel_3.add(saveButton, BorderLayout.PAGE_END);

        hpPanel = new HpPanel(200);
        panel_3.add(hpPanel, BorderLayout.PAGE_START);

        updateInventory();
        updateHealth();

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
     * @param name    The Player saying the message
     * @param message    The Message
     */
    public void toConsole(String name, String message){
        if(!textArea.getText().equals(""))textArea.append("\n");
        textArea.append(name +": \""+ message+"\"");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {

        if(e.getSource() instanceof RenderWindow){
            ((RenderWindow) e.getSource()).requestFocus();
            //Tile t = ((RenderWindow) e.getSource()).getTile(e.getX(), e.getY());
            //toConsole(t.getLocation().toString());
            //if(t.hasItem()){
            //    toConsole(t.getItem().getClass().toString());
            //}else{
            //    toConsole("null");
            //}
        }
        else if(e.getSource() instanceof JLabel){
            inventory.get(selected).setBorder(null);
            selected = Integer.parseInt(((JLabel) e.getSource()).getName());
            currentPlayer.getInventory().setSelectedSpace(selected);
            Slave.sendSelectedSpace(selected);
            inventory.get(selected).setBorder(BorderFactory.createLineBorder(Color.YELLOW));
            updateInventory();
        }
    }
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

    public RenderWindow getRenderWindow(){
        return renderWindow;
    }

    /**
     * refreshes the inventory panel
     */
    public void updateInventory(){
        ArrayList<Item> curInventory = new ArrayList<Item>();
        curInventory.addAll(currentPlayer.getInventory().getItems());
        for(int i = 0; i < inventory.size(); i++){
            if(curInventory.size() > i){
                JLabel temp = inventory.get(i);
                String name = curInventory.get(i).getName();
                if(name.equals("Apple")){
                    temp.setIcon(new ImageIcon(renderWindow.renderer.Apple));
                }else if(name.equals("Banana")){
                    temp.setIcon(new ImageIcon(renderWindow.renderer.Banana));
                }else if(name.equals("Mango")){
                    temp.setIcon(new ImageIcon(renderWindow.renderer.Mango));
                }else if(name.equals("RedPot")){
                    temp.setIcon(new ImageIcon(renderWindow.renderer.RedPot));
                }else if(name.equals("ArmorHead")){
                    temp.setIcon(new ImageIcon(renderWindow.renderer.ArmorHead));
                }else if(name.equals("ArmorChest")){
                    temp.setIcon(new ImageIcon(renderWindow.renderer.ArmorChest));
                }else if(name.equals("ArmorLegs")){
                    temp.setIcon(new ImageIcon(renderWindow.renderer.ArmorLegs));
                }else {
                    temp.setIcon(new ImageIcon(renderWindow.renderer.KeyRoom2));
                }
            }
            else {
                JLabel toAdd = inventory.get(i);
                toAdd.setIcon(null);
            }
        }
        updateHealth();
    }

    /**
     * Refreshes the HealthBar
     */
    public void updateHealth(){
    	int max = currentPlayer.getMaxHealth();
    	int cur = currentPlayer.getCurrentHealth();
    	hpPanel.updateHealth(cur, max);;
    }

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Save")){
			JOptionPane.showMessageDialog(this, "Please choose a save Location");
			JFileChooser thingy = new JFileChooser();
			thingy.showSaveDialog(this);
			XMLWriter george = new XMLWriter(thingy.getSelectedFile().toString(), players);

		}
	}
}