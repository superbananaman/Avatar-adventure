package Renderer;

import gameLogic.Item;
import gameLogic.Monster;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class Tile implements Serializable {

private BufferedImage backGroundImage;
private BufferedImage pickUpImage;
private BufferedImage monsterImage;
private boolean isWalkable = true;
private Item item = null;
private Monster monster = null;
private Point location;

/**
 * Create a new Tile with background Image location and walkable
 * @param backGroundImg
 * @param location
 * @param walk
 */
public Tile(BufferedImage backGroundImg,Point location, boolean walk){
this.backGroundImage = backGroundImg;
this.location = location;
this.isWalkable = walk;
}

/**
 * @return the background Image
 */
public Image getBackGroundImage() {
	return backGroundImage;
}

/**
 * Set the background image
 * @param img
 */
public void setBackGroundImage(BufferedImage img) {
	this.backGroundImage = img;
}

/**
 * @return the pickUpImage
 */
public BufferedImage getPickUpImage() {
	return pickUpImage;
}

/**
 * @param pickUpImage the pickUpImage to set
 */
public void setPickUpImage(BufferedImage pickUpImage) {
	this.pickUpImage = pickUpImage;
}

/**
 * @return the monsterImage
 */
public BufferedImage getMonsterImage() {
	return monsterImage;
}

/**
 * @param monsterImage the monsterImage to set
 */
public void setMonsterImage(BufferedImage monsterImage) {
	this.monsterImage = monsterImage;
}

/**
 * @param sets an item to this tile
 */
public void setItem(Item i){
	item = i;
}


/**
 * @return true if there is an item here
 */
public boolean hasItem(){
	return item!=null;
}

/**
 * @return the item on this tile
 */
public Item getItem(){
	if(item!=null){
		return item;
	}
	return null;
}

/**
 * @param sets item to null
 */
public void removeItem(){
	item = null;
}

/**
 * @return the isWalkable
 */
public boolean isWalkable() {
	return isWalkable;
}

/**
 * @param isWalkable the isWalkable to set
 */
public void setWalkable(boolean isWalkeable) {
	this.isWalkable = isWalkeable;
}

/**
 * @return Location of the Tile
 */
public Point getLocation() {
	return location;
}

/**
 * Set the Location of the tile
 * @param location
 */
public void setLocation(Point location) {
	this.location = location;
}

public Monster getMonster() {
	return monster;
}

public void setMonster(Monster monster) {
	this.monster = monster;
}


}
