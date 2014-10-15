package gui;

/**
 * Represents the HealthBar on the ClientFrame
 * @author Devlin Mahoney
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class HpPanel extends JPanel {

	private int width;
	private JPanel barBack;
	private JPanel barFront;
	private JLabel hpText;

	public HpPanel(int width) {

		this.width = width;
		setLayout(new GridLayout(2,1,0,0));

		barBack = new JPanel();

		barBack.setLayout(null);
		barBack.setBackground(Color.RED);
		barBack.setBorder(BorderFactory.createLineBorder(Color.RED));
		add(barBack);

		barFront = new JPanel();
		barFront.setBackground(Color.WHITE);
		barFront.setBorder(BorderFactory.createLineBorder(Color.RED));
		barFront.setSize(new Dimension(width,18));
		barFront.setLocation(0, 0);
		barFront.setOpaque(true);
		barBack.add(barFront);

		hpText = new JLabel();
		hpText.setText("HP: ");
		hpText.setLocation(0, 20);
		hpText.setFocusable(false);
		add(hpText);

		setVisible(true);
	}

	public void updateHealth(int cur, int max){
    	double percent = (cur*1.0)/(max*1.0);
    	barFront.setSize(new Dimension((int)((1 - percent)*width),16));
    	barFront.setLocation(barBack.getWidth() - barFront.getWidth(), 0);
    	hpText.setText("HP: "+cur+" / "+max);
	}
}
