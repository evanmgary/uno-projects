/** Hero class for object-oriented dungeon exploration program for Homework 6
 *
 * @author	Evan Gary
 * @version	11/15/16
*/

import java.security.SecureRandom;
import java.util.Scanner;

public class Hero extends GameCharacter {
	
	private int magic;
	private int currentMagic;
	private int level;
	private static SecureRandom random = new SecureRandom();
	private static Scanner input = new Scanner(System.in);
	private boolean fleeFlag;
	private int hasXp;
	
	public Hero(){
		
		super("Hero", 30, 30, 3);
		this.magic = 0;
		this.currentMagic = 0;
		this.level = 1;
		this.fleeFlag = false;
		this.hasXp = 0;
	}
	
	public int getMagic(){
		return magic;
	}
	
	public int getCurrentMagic(){
		return currentMagic;
	}
	
	public int getLevel(){
		return level;
	}
	
	//Stat Methods
	
	//Stat method used for character creation and leveling up, parameter statsRem is how many times allocation can be performed
	public void assignStats(int statsRem){
		int userChoice;
		while (statsRem>0){
			System.out.println();
			System.out.printf("Current Stats:%nLevel: %d, Health: %d, Attack: %d, Magic: %d%n", this.getLevel(), this.getStartHealth()
			, this.getAttack(), this.getMagic());
			System.out.println("Enter the number corresponding to your choice:");
			System.out.println("1) +10 Health");
			System.out.println("2) +1 Attack");
			System.out.println("3) +1 Magic Point");
			if (this.level == 1){
				System.out.println("4) Create default hero");
			}
			System.out.printf("You have %d points to spend: ", statsRem);
			userChoice = input.nextInt();
			
			//Implement selection statement for stat addition
			if (userChoice == 1){
				//Add 10 to hero health and decrement counter
				this.setStartHealth(this.getStartHealth() + 10);
				statsRem--;
			}
			else if (userChoice == 2){
				//Add 1 to hero attack and decrement counter
				this.setAttack(this.getAttack() + 1);
				statsRem--;
			}
			else if (userChoice == 3){
				//Add 1 to hero magic and decrement counter
				this.magic +=1;
				statsRem--;
			}
			//Deault hero has starting stats shown below
			else if (userChoice ==4 && this.level == 1){
				this.setStartHealth(130);
				this.setAttack(10);
				this.magic = 6;
				statsRem = 0;
			}
			//Easy mode for testing, is hidden to the player
			else if (userChoice == 32 && this.level == 1){
				this.setStartHealth(300);
				this.setAttack(20);
				this.magic = 10;
				statsRem = 0;
				System.out.println("Testing Mode");
			}
			else {
				System.out.println("That is not a valid choice.");
				System.out.println();
			}
		}//end while
	}//end assignStats method
	
	//Gain XP after successful combat
	public void gainXp(int gainedXp){
		this.hasXp += gainedXp;
		if (hasXp >= 8){ 
			this.levelUp();
		}
	}
	
	//When the hero has 8 or more XP, level up, then remove 8 XP
	public void levelUp(){
		if (hasXp >= 8){
			System.out.println("You leveled up!");
			this.level += 1;
			this.assignStats(2);
			this.hasXp -= 8;
		}
	}
	
	public void takeTurn(Monster enemy){
		boolean validChoice = false;
		while (validChoice == false){
			//Print the Monster's name
			System.out.printf("%nYou are fighting a %s!%n", enemy.getName());
			//Print the Player's health
			System.out.println(this.toString());
			//Print the Monster's health
			System.out.printf(enemy.toString());
			//Print the Player's magic points
			System.out.printf("Your MP: %d%n%n", this.currentMagic);
			
			/*Combat menu prompt*/
			System.out.println("Combat Options:");
			//Print option 1: Sword Attack
			System.out.println("  1. Sword Attack");
			//Print option 2: Cast Spell
			System.out.println("  2. Cast Spell");
			//Print option 3: Charge Mana
			System.out.println("  3. Charge Mana");
			//Print option 4: Run Away
			System.out.println("  4. Run Away");
			
			//Declare variable for user input {as number} and acquire value from Scanner object
			int heroChoice;
			System.out.print("What action do you want to perform? ");
			heroChoice = input.nextInt();
			System.out.println();
			
			//Use selection statement to determine which method runs
			if (heroChoice == 1) {
				//Run attack method
				this.attack(enemy);
				validChoice = true;
			}
			else if (heroChoice == 2) {
				//Run cast spell method
				validChoice = this.castSpell(enemy);
			}
			else if (heroChoice == 3) {
				//Run charge mana method
				this.chargeMana();
				validChoice = true;
			}
			else if (heroChoice == 4) {
				//Run flee method to set inCombat to true or false
				this.flee();
				validChoice = true;
			}
			else {
				//Print error message
				System.out.println("I don't understand that command.");
				System.out.println();
			}
			
		}//end while
		
	}//end takeTurn method

	//Reset combat variables for a new combat
	public void newCombat(){
		this.setCurrentHealth(this.getStartHealth());
		this.fleeFlag = false;
		this.currentMagic = magic / 3;
	}
	
	//Combat Action Methods
	
	public void attack(Monster enemy){
		int damage = (this.getAttack() / 2) + rng(0, this.getAttack());
		enemy.takeDamage(damage);
		System.out.printf("You attack the %s for %d damage.%n", enemy.getName(), damage);
	}
	
	public void chargeMana(){
		this.currentMagic = this.currentMagic + 1 + (magic / 4);
		System.out.println("You charge your magic.");
	}
	
	public boolean castSpell(Monster enemy){
		if (this.getCurrentMagic() >= 3){
			enemy.setCurrentHealth(enemy.getCurrentHealth() / 2);
			this.currentMagic -= 3;
			System.out.println("You cast the weaken spell on " + enemy.getName() + ".");
			return true;
		}
		
		else{
			System.out.println ("You don't have enough mana.");
			return false;
		}
		
	}//end castSpell method

	public void flee(){
		int fleeSuccess = rng(0,1);
		if (fleeSuccess == 1){
			this.fleeFlag = true;
		}
		else {
			System.out.println("You fail to run away!");
		}
		
	}//end flee method
	
	//See if the hero has fled
	public boolean hasFled(){
		return fleeFlag;
	}
	
	//Simplified random number generator
	private int rng(int start, int end){
		return start + random.nextInt(end - start + 1);
	}
	
	public String toString(){
		return String.format("%s Stats: %d/%d HP, %d Attack, %d Magic, Level %d, %d XP", this.getName(), this.getCurrentHealth(), this.getStartHealth(), this.getAttack(), this.magic, this.getLevel(), this.hasXp);
	}

}//end class Hero