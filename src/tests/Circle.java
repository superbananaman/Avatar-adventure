package tests;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Circle implements Serializable {
	public int x;
	public int y;
	public final String uid;
	public int width = 10;
	public int height = 10;
	public Color col;

	public Circle(int x, int y, String uid, Color col){
		this.x = x;
		this.y = y;
		this.uid = uid;
		this.col = col;
	}

	public void moveHorizontal(int amt){
		x = x + amt;
	}

	public void moveVertical(int amt){
		y = y + amt;
	}

	public void draw(Graphics g){
		g.setColor(col);
		g.fillOval(x, y, width, height);
	}
}