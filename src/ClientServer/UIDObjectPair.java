package ClientServer;

import java.io.Serializable;

/**
 * An object sent through the slave and server which contains the
 * uid of the player sending the object and the object itself
 * @author Brendan Smith, Matt Catsburg
 *
 */
public class UIDObjectPair implements Serializable {


	public enum Operation{
		Message, Monster, KeyEvent, SpaceSelected, Pickup, Drop, DeadPlayer, Damage, Damage2
	};
	private Operation op;
	private String uid;
	private Object ob;
	private Object ob2;

	public UIDObjectPair(Operation op, String uid, Object ob){
		this.op = op;
		this.uid = uid;
		this.ob = ob;
	}

	public UIDObjectPair(Operation op, String uid, Object ob, Object ob2){
		this.op = op;
		this.uid = uid;
		this.ob = ob;
		this.ob2 = ob2;
	}

	/**
	 * @return the operation that this uidObjectPair is being used
	 * for.
	 */
	public Operation getOp(){
		return op;
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

	/**
	 * @return a second optional object
	 */
	public Object getObject2(){
		return ob2;
	}

}
