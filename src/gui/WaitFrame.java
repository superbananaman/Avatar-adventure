package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import ClientServer.Slave;

/**
 * Represents the frame that will be displayed for clients while waiting
 * @author Devlin Mahoney
 *
 */
public class WaitFrame extends JFrame implements KeyListener, ActionListener{

	private JTextArea textArea;
	private JPanel contentPane;
	private JScrollPane scroll;

	private JTextField inputArea;
	private FloatControl volume;
	private float curVol;
	private BooleanControl mute;
	private Clip clip;

	public WaitFrame(){
		super("Avatar-adventure wait frame");
		setSize(new Dimension(650,650));
		addKeyListener(this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		loadMusic();
		clip.loop(Clip.LOOP_CONTINUOUSLY);

		volume= (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

		mute = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
		mute.setValue(false);

		contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{600, 0, 0};
        gbl_contentPane.rowHeights = new int[]{550, 10, 75};
        contentPane.setLayout(gbl_contentPane);

        //Console Output
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

        //Console Input
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

        //Sound Button Panel
        JPanel buttonPanel = new JPanel();
        GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
        gbc_buttonPanel.insets = new Insets(0, 0, 10, 10);
        gbc_buttonPanel.fill = GridBagConstraints.BOTH;
        gbc_buttonPanel.gridx = 0;
        gbc_buttonPanel.gridy = 2;
        contentPane.add(buttonPanel, gbc_buttonPanel);

        JButton b = new JButton("Mute");
        b.addActionListener(this);
        buttonPanel.add(b);

        b = new JButton("Increase V");
        b.addActionListener(this);
        buttonPanel.add(b);

        b = new JButton("Decrease V");
        b.addActionListener(this);
        buttonPanel.add(b);

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

    /**
     * Loads the music to play in the frame
     */
    private void loadMusic(){
    	try{
    		File soundFile = new File("sound/Retribution.wav");
    		clip = AudioSystem.getClip();
    		AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
    		clip.open(ais);

    		clip.loop(Clip.LOOP_CONTINUOUSLY);
    	}catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * Safety method to stop music
     */
    public void stopMusic(){
    	clip.stop();
    }

    /**
     * makes the music 2DB quieter
     */
    private void quieter(){
    	curVol -= 2;
    	volume.setValue(curVol);
    }
    /**
     * makes the music 2DB louder, to a limit of 6DB above default
     */
    private void louder(){
    	curVol += 2;
    	if(curVol > 6)curVol=6;
    	volume.setValue(curVol);
    }

    /**
     * Toggles muting of the music
     */
    private void toggleMute(){
    	if(mute.getValue())mute.setValue(false);
    	else mute.setValue(true);
    }

	public void keyTyped(KeyEvent e) {}

	public void keyReleased(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 10){
            //they presed enter
            Slave.sendMessage(inputArea.getText());
            inputArea.setText("");
        }
    }

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Mute")){
			toggleMute();
		}else if(e.getActionCommand().equals("Increase V")){
			louder();
		}else if(e.getActionCommand().equals("Decrease V")){
			quieter();
		}
	}
}
