package Renderer;

import java.io.Serializable;

public class Sprite implements Serializable{
	private String name;
	private double currentX;
	private double currentY;
	private String facing;
	private int step;
	private int offsetX;
	private int offsetY;
	public Sprite(String name, int x, int y ){
		currentX = x; 	currentY = y;
		facing = "Up";
		step = 0;
		this.name = name;
	}
	/**
	 * @return the currentX
	 */
	public int getCurrentX() {
		return (int)currentX;
	}
	/**
	 * @param d the currentX to set
	 */
	public void addCurrentX(double d) {
		this.currentX += d;
	}
	public void setCurrentX(double d) {
		this.currentX = d;
	}
	/**
	 * @return the currentY
	 */
	public int getCurrentY() {
		return (int)currentY;
	}
	/**
	 * @param d the currentY to set
	 */
	public void addCurrentY(double d) {
		this.currentY += d;
	}
	public void setCurrentY(double d) {
		this.currentY = d;
	}
	/**
	 * @return the facing
	 */
	public String getFacing() {
		return facing;
	}
	/**
	 * @param facing the facing to set
	 */
	public void setFacing(String facing) {
		this.facing = facing;
	}
	/**
	 * @return the step
	 */
	public int getStep() {
		return step;
	}
	/**
	 * @param step the step to set
	 */
	public void step() {
		this.step++;
	}
	public String getName() {
		return name;
	}
	public Tile getTile(){

		return null;
	}
	public int getOffsetX() {
		return offsetX;
	}
	public void addOffsetX(int offsetX) {
		this.offsetX += offsetX;
	}
	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}
	public int getOffsetY() {
		return offsetY;
	}
	public void addOffsetY(int offsetY) {
		this.offsetY += offsetY;
	}
	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}

}