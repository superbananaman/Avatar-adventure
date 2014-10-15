package gameLogic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.text.Position;

import Renderer.Tile;

public interface Monster extends Character {


	public void setHealth(int numofPlayers);

	public void takeDamage(int damage);
	public int attack();
	public void Die();

	public Tile getTile();

	public String getName();
}
