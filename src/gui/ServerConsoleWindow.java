package gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

public class ServerConsoleWindow extends JFrame {//put a mouseListener Here?

    private JPanel contentPane;

    JLabel textArea;
    JPanel buttonPanel;
    JLabel rightLabel;
    String currentText = "";

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ServerConsoleWindow frame = new ServerConsoleWindow();
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
    public ServerConsoleWindow() {
        setTitle("Avatar Adventure server ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 720);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        //gbl_contentPane.columnWidths = new int[]{800, 200, 0};
        //gbl_contentPane.rowHeights = new int[]{600, 120, 0};
        gbl_contentPane.columnWeights = new double[]{18.0, 5.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{17.0, 1.5, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);

        //Consoleoutput placeholder
        textArea = new JLabel();
        GridBagConstraints gbc_textArea = new GridBagConstraints();
        textArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        textArea.setBackground(Color.WHITE);
        textArea.setOpaque(true);
        textArea.setVerticalAlignment(SwingConstants.BOTTOM);
        gbc_textArea.insets = new Insets(0, 0, 5, 5);
        gbc_textArea.fill = GridBagConstraints.BOTH;
        gbc_textArea.gridx = 0;
        gbc_textArea.gridy = 0;
        contentPane.add(textArea, gbc_textArea);

        //Inventory Place Holder
        rightLabel = new JLabel();
        rightLabel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        rightLabel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        rightLabel.setBackground(Color.WHITE);
        rightLabel.setOpaque(true);
        GridBagConstraints gbc_rightLabel = new GridBagConstraints();
        gbc_rightLabel.fill = GridBagConstraints.BOTH;
        gbc_rightLabel.insets = new Insets(0, 0, 5, 0);
        gbc_rightLabel.gridx = 1;
        gbc_rightLabel.gridy = 0;
        contentPane.add(rightLabel, gbc_rightLabel);

        //for Buttons
        buttonPanel = new JPanel();
        GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
        buttonPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        buttonPanel.setOpaque(true);
        gbc_buttonPanel.insets = new Insets(0, 5, 5, 10);
        gbc_buttonPanel.fill = GridBagConstraints.BOTH;
        gbc_buttonPanel.gridx = 0;
        gbc_buttonPanel.gridy = 1;
        contentPane.add(buttonPanel, gbc_buttonPanel);

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
     * @param name    The Player saying the message
     * @param message    The Message
     */
    public void toConsole(String name, String message){
        currentText = currentText +"<br>" +name +": \""+ message+"\"";
        textArea.setText("<html>"+currentText+"</html>");
    }
}


