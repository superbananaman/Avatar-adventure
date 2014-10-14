package gameLogic;

import java.awt.Graphics;
import java.io.Serializable;

import Renderer.Tile;

public interface Item extends Serializable{

	public void use(Player player);

	public String getName();
	public Tile getTile();


}
