package Renderer;

import gameLogic.Item;

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
private Point location;

/*Use this class if the tile just has a background Image*/
public Tile(BufferedImage backGroundImg,Point location){
this.backGroundImage = backGroundImg;
this.location = location;
}

public Image getBackGroundImage() {
	return backGroundImage;
}

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

public Point getLocation() {
	return location;
}

public void setLocation(Point location) {
	this.location = location;
}


}
