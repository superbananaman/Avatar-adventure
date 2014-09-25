package Renderer;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tile {

private BufferedImage img;

public Tile(Image i){
	
	try {
	    setImg( ImageIO.read(new File("grass.png")));
	} catch (IOException e) {
		System.out.println(e.toString());
	}
}

public Image getImg() {
	return img;
}

public void setImg(BufferedImage img) {
	this.img = img;
}

}
