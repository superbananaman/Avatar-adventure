package gameLogic;

import java.util.*;
import gameLogic.Character;

/**
 *
 * @author cartyschri
 *
 */

public class Battle {

	private List<Character> players = new ArrayList<Character>();
	private Monster monster;


	public Battle(List<Character> player, Monster Monster){
		players = player;
		monster = Monster;
		setUp();
	}

	public void setUp(){

	}

}
