package ClientServer;

import java.io.Serializable;

/**
 * An object sent through the slave and server which contains the 
 * uid of the player sending the object and the object itself
 * @author Brendan Smith, Matt Catsburg
 *
 */
public class UIDObjectPair implements Serializable {
	
	private String uid;
	private Object ob;
	
	public UIDObjectPair(String uid, Object ob){
		this.uid = uid;
		this.ob = ob;
	}
	
	/**
	 * @return the uid of the player sending an object
	 */
	public String getUID(){
		return uid;
	}
	
	/**
	 * @return the object being sent
	 */
	public Object getObject(){
		return ob;
	}
	
}
