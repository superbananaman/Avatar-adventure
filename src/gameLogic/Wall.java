package gameLogic;

import java.awt.Graphics;

import javax.swing.text.Position;

import Renderer.Tile;

public class Wall implements Object {

	private Tile tile;

	public Wall(Tile tile){
		setTile(tile);
	}

	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}

	private Tile getTile() {
		return tile;
	}

	private void setTile(Tile tile) {
		this.tile = tile;
	}

}
