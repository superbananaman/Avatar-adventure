package Renderer;

public class Sprite{
	private String name;
	private int currentX;
	private int currentY;
	private String facing;
	private int step;
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
		return currentX;
	}
	/**
	 * @param currentX the currentX to set
	 */
	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}
	/**
	 * @return the currentY
	 */
	public int getCurrentY() {
		return currentY;
	}
	/**
	 * @param currentY the currentY to set
	 */
	public void setCurrentY(int currentY) {
		this.currentY = currentY;
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
	public void setStep(int step) {
		this.step = step;
	}
	public String getName() {
		return name;
	}
	public Tile getTile(){
		
		return null;
	}
	
}