/** Monster class for object-oriented dungeon exploration program for Homework 6
 *
 * @author	Evan Gary
 * @version	11/15/16
*/


import java.security.SecureRandom;

public class Monster extends GameCharacter {

	private static SecureRandom random = new SecureRandom();
	private int Xp;
	
	//No parameter constructor simply runs the one parameter constructor to make a random monster
	public Monster(){
		this(0);
	}
	
	//One parameter constructor allowing a choice of monster
	public Monster(int chooseMonster){
		if (chooseMonster == 0){ //0 means the monster can be any one of the five non-boss types
			chooseMonster = rng(1,5);
		} 
		
		if (chooseMonster == 1) {
			int tempHealth = rng(90,110);
			setMonsterValues("Goblin", tempHealth, tempHealth, rng(9,11));
			this.Xp = 1;
		}
		else if (chooseMonster == 2) {
			int tempHealth = rng(180, 220);
			setMonsterValues("Ooze", tempHealth, tempHealth, rng(5,7));
			this.Xp = 3;
		}
		else if (chooseMonster == 3) {
			int tempHealth = rng(240,260);
			setMonsterValues("Troll", tempHealth, tempHealth, rng(11,13));
			this.Xp = 5;
		}
		else if (chooseMonster == 4) {
			int tempHealth = rng(60,80);
			setMonsterValues("Giant Spider", tempHealth, tempHealth, rng(13,15));
			this.Xp = 3;
		}
		else if (chooseMonster == 5) {
			int tempHealth = rng(140,160);
			setMonsterValues("Spirit", tempHealth, tempHealth, rng(11,13));
			this.Xp = 3;
		}
		else if (chooseMonster == 6) { //The boss
			int tempHealth = rng(300,350);
			setMonsterValues("Dark Wizard", tempHealth, tempHealth, rng(15,18));
			this.Xp = 8;
		}
	
	}//End one parameter constructor

	//Constructor helper method for setting initial monster values
	private void setMonsterValues(String name, int startHealth, int currentHealth, int attack){
		this.name = name;
		this.startHealth = startHealth;
		this.currentHealth = currentHealth;
		this.attack = attack;
	}
	
	public int getXp(){
		return Xp;
	}
	
	public void attack(Hero player){
		int damage = (this.getAttack() / 2) + rng(0, this.getAttack());
		player.takeDamage(damage);
		System.out.println("The monster atttacks you for " + damage + ".");
	}
	
	public void takeTurn(Hero player){
		this.attack(player);
	}
	
	public String toString(){
		return String.format("%s HP: %d/%d%n", this.getName(), this.getCurrentHealth(), this.getStartHealth());
	}
	
	private int rng(int start, int end){
		return start + random.nextInt(end - start + 1);
	}
}