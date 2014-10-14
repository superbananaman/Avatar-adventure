package gui;

import java.awt.Component;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class AboutFrame extends JFrame{

	private int height = 700;
	private int width = 400;

	public AboutFrame(){
		FileReader fr = null;
		try {
			fr = new FileReader("readme.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


		setSize(700, 400);
		JTextArea textArea = new JTextArea();
		textArea.setSize(700, 400);
		textArea.setEditable(false);


		Scanner scan = new Scanner(fr);


		String templine = scan.nextLine();
		textArea.append(templine);

		textArea.setAlignmentX(LEFT_ALIGNMENT);
		while(scan.hasNextLine()){
		textArea.append(scan.nextLine());
		textArea.append("\n");
		}


		add(textArea);


		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}



}

