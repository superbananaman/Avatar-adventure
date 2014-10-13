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
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/** 
	 * @return the offset x value
	 */
	public int getOffsetX() {
		return offsetX;
	}
	/** adds offsetX to the current  offset
	 * @param offsetX
	 */
	public void addOffsetX(int offsetX) {
		this.offsetX += offsetX;
	}
	/**Replaces the offset X
	 * @param offsetX
	 */
	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}
	/**
	 * @return the offset Y
	 */
	public int getOffsetY() {
		return offsetY;
	}
	/**Adds to the offset Y
	 * @param offsetY
	 */
	public void addOffsetY(int offsetY) {
		this.offsetY += offsetY;
	}
	/** Replaces the offsetY
	 * @param offsetY
	 */
	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}
	
	/**
	 * @return null dont use, use renderer.getTile()
	 */
	public Tile getTile() {
		return null;
	}

}