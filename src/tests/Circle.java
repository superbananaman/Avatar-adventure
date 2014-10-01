package tests;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Circle implements Serializable {
	public int x;
	public int y;	
	public int width = 10;
	public int height = 10;
	public Color col;

	public Circle(int x, int y, Color col){
		this.x = x;
		this.y = y;
		this.col = col;
	}

	public void draw(Graphics g){
		g.setColor(col);
		g.fillOval(x, y, width, height);
	}
}
