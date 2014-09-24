package gameLogic;


import java.io.Console;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game {

	private List<Room> rooms = new ArrayList<Room>();

	public Game(String type){
		createServer();
		setUp();
	}

	public void createServer(){
		int numofplayers = askforPlayers();
		String ipaddress = askforIP();
		//int port = askforPort();



	}

	public Integer askforPlayers(){
		String players = JOptionPane
				.showInputDialog("How Many Players (1-4)?");
		if (players == null) {
			return (-1);
		}
		int number = 5;
		if (players.length() == 1) {

			try {
				number = Integer.parseInt(players);
			} catch (NumberFormatException e1) {
				number = 5;
			}
		}
		while (number > 4 || number < 1) {
			players = JOptionPane
					.showInputDialog("Not A Valid Number. How Many Players (1-4)?");
			if (players == null) {
				return (-1);
			}
			try {
				number = Integer.parseInt(players);
			} catch (NumberFormatException e1) {
				number = 5;
			}
		}
		System.out.println(number);
		return number;

	}

	public String askforIP(){
		String address = JOptionPane
				.showInputDialog
				("Open console and type in 'ifconfig'. "
						+ "On the second line of random stuff, next to the word inet, there is a 4 digit address. "
						+ "Enter the address.");
		while(address == null || address.length() < 7) {
			address = JOptionPane
					.showInputDialog
					("Invalid IP Address. Please enter again.");
		}
		return address;
	}

	public void setUp(){

	}


}
