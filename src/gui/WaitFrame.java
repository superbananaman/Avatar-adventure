package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import ClientServer.Slave;

/**
 * Represents the frame that will be displayed for clients while waiting
 * @author mahonedevl
 *
 */
public class WaitFrame extends JFrame implements KeyListener{

	private JTextArea textArea;
	private JPanel contentPane;
	private JScrollPane scroll;
	private JTextField inputArea;

	public WaitFrame(){
		setSize(new Dimension(600,600));
		addKeyListener(this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{600, 0, 0};
        gbl_contentPane.rowHeights = new int[]{550, 10, 0};
        contentPane.setLayout(gbl_contentPane);

        textArea = new JTextArea();
        GridBagConstraints gbc_textArea = new GridBagConstraints();
        textArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        textArea.setBackground(Color.WHITE);
        textArea.setOpaque(true);
        textArea.setFocusable(false);
        scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        gbc_textArea.insets = new Insets(0, 0, 0, 0);
        gbc_textArea.fill = GridBagConstraints.BOTH;
        gbc_textArea.gridx = 0;
        gbc_textArea.gridy = 0;
        contentPane.add(scroll, gbc_textArea);

        inputArea = new JTextField();
        inputArea.addKeyListener(this);
        GridBagConstraints gbc_inputArea = new GridBagConstraints();
        inputArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        inputArea.setBackground(Color.WHITE);
        inputArea.setOpaque(true);
        gbc_inputArea.insets = new Insets(0, 0, 0, 0);
        gbc_inputArea.fill = GridBagConstraints.BOTH;
        gbc_inputArea.gridx = 0;
        gbc_inputArea.gridy = 1;
        contentPane.add(inputArea, gbc_inputArea);

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



	public void keyTyped(KeyEvent e) {}

	public void keyReleased(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 10){
            //they presed enter
            //Slave.sendMessage(inputArea.getText());
            inputArea.setText("");
        };
    }

	public static void main(String[] args){
		WaitFrame harold = new WaitFrame();
	}
}
