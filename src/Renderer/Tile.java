package Renderer;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tile {

private BufferedImage backGroundImage;
private BufferedImage pickUpImage;
private BufferedImage monsterImage;

/*Use this class if the tile just has a background Image*/
public Tile(BufferedImage backGroundImg){
this.backGroundImage = backGroundImg;
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

}
