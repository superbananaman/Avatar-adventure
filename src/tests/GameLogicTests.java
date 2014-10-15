package tests;

import gameLogic.Armour;
import gameLogic.Boss;
import gameLogic.Player;

import org.junit.*;

public class GameLogicTests {

	@Test
	public void testArmourUnequipping(){
		Player player = new Player("Awesome");
		int max1 = player.getMaxHealth();
		Armour armour = new Armour(null, "Legs");
		armour.unequip(player);
		int max2 = player.getMaxHealth();
		assert(max1 == max2);
	}
	@Test
	public void testArmourUnequipping2(){
		Player player = new Player("Awesome");
		Armour armour = new Armour(null, "Legs");
		armour.use(player);
		int max1 = player.getMaxHealth();
		armour.unequip(player);
		int max2 = player.getMaxHealth();
		assert(max1 > max2);
	}
	@Test
	public void testBossHealth(){
		Boss boss = new Boss(null);
		boss.setHealth(3);
		assert(boss.getHealth() == 4500);
	}


	@Test
	public void testBossHealth2(){
		Boss boss = new Boss(null);
		boss.setHealth(4);
		assert(boss.getHealth() != 4500);
	}
	@Test
	public void testBossAttack(){
		Boss boss = new Boss(null);
		assert(boss.attack() == 400);
	}
	@Test
	public void testBossHealth2(){

	}
	@Test
	public void testBossHealth2(){

	}


}
