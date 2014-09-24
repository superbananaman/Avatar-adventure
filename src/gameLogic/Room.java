package gameLogic;

import java.awt.Graphics;
import java.util.*;

import Renderer.Tile;

public class Room {

	private int height;
	private int width;

	private List<Character> players = new ArrayList<Character>();
	private List<Character> monsters = new ArrayList<Character>();
	private List<Item> items = new ArrayList<Item>();
	private Tile[][] tiles;

	public Room(int Height, int Width){
	height = Height;
	width = Width;

	}


	public void draw(Graphics g){

	}
}
