/** Enhanced combat calculator program which performs text-based RPG-style monster combat
 * This version allows for hero creation, generates multiple monsters, and allows the hero to level up
 * A main goal of this program is to put code into methods
 *
 * @author	Evan Gary
 * @version	9/18/16
*/

//Import scanner and random number generator
import java.util.Scanner;
import java.security.SecureRandom;
	

public class EnhancedCombatFinal{
	
	//Make Scanner and SecureRandom objects
	private static Scanner input = new Scanner(System.in);
	private static SecureRandom rng = new SecureRandom();
	
	// Declare class variables for hero health, attack, magic, level
	private static int heroHealth;
	private static int heroAttack;
	private static int heroMagic;
	private static int heroLevel;
	private static boolean newHero;
	
	//Declare class variables for changing stats
	private static int currentXP;
	private static int currentHealth;
	private static int currentMagic;
	
	//Declare class variables for monster name, health, attack, and XP value
	private static String monsterName;
	private static int monsterHealth;
	private static int monsterAttack;
	private static int monsterXP;
	
	//Main method
	public static void main(String[] args) {
		//Print welcome message
		System.out.println("Welcome to the game. Please create your hero.");
		System.out.println();
		//Indicate that the hero is new
		newHero = true;
		//Run hero creator and level up method
		heroStats();
		//Set newHero to false to change the method's behavior when used to level up
		newHero = false;
		//Set continue game variable to true
		boolean continueGame = true;
		//Begin loop for combat encounters
		while (continueGame == true){
			//Generate a monster
			createMonster();
			//Enter combat
			combatBase();
			//Run method to check if the program ends
			continueGame = endGame();
		}//End while loop
	}//End main method
		
	//Method for hero creation and leveling
	public static void heroStats() {
		//Declare variable for remaining stat points
		int statsRem = 0;
		//If this is a new hero, give the player 20 stat points and set hero level to 1
		if (newHero == true){
			statsRem += 20;
			heroLevel = 1;
			//The hero starts with 10 health and 3 attack at level 1
			heroHealth = 10;
			heroAttack = 3;
			heroMagic = 0;
		}
		//Else give the player two stat points and level up
		else {
			statsRem += 2;
			heroLevel++;
			//Print level up message
			System.out.printf("Level Up! You are now level %d!%nYou may now assign two stat points.%n%n", heroLevel);
		} 
			
		int userChoice = 0;
		//While the remaining number of stat points is greater than zero, keep asking the player for input and increasing stats
		while (statsRem>0) {
			//Print current stats and choices
			System.out.printf("Current Stats:%nLevel: %d, Health: %d, Attack: %d, Magic: %d%n", heroLevel, heroHealth, heroAttack, heroMagic);
			System.out.println("Enter the number corresponding to your choice:");
			System.out.println("1) +10 Health");
			System.out.println("2) +1 Attack");
			System.out.println("3) +1 Magic Point");
			System.out.printf("You have %d points to spend: ", statsRem);
			userChoice = input.nextInt();
			
			//Implement selection statement for stat addition
			if (userChoice == 1){
				//Add 10 to hero health and decrement counter
				heroHealth += 10;
				statsRem--;
			}
			else if (userChoice == 2){
				//Add 1 to hero attack and decrement counter
				heroAttack +=1;
				statsRem--;
			}
			else if (userChoice == 3){
				//Add 1 to hero magic and decrement counter
				heroMagic +=1;
				statsRem--;
			}
			//If the user did not pick a valid choice, do not decrement counter and print message
			else {
				System.out.println("That is not a valid choice.");
				System.out.println();
			}
			
		} //End while loop
		return;
	} //End create hero method
	
	//Method for monster creation
	public static void createMonster(){
		//Declare variable to use for random choice
		int randMonster;
		randMonster = 1 + rng.nextInt(5);
		
		//Implement selection statement for five monsters
		//If the random variable is 1, the monster is a Goblin
		if (randMonster == 1){
			monsterName = "Goblin";
			//Monster health is between 90 and 110 inclusive
			monsterHealth = 100 + (-10 + rng.nextInt(21));
			//Monster attack is between 9 and 11 inclusive
			monsterAttack = 10 + (-1 + rng.nextInt(2));
			//Monster XP is 1
			monsterXP = 1;
		}
		
		//If the random variable is 2, the monster is an Ooze
		else if (randMonster == 2){
			monsterName = "Ooze";
			//Monster health is between 180 and 220 inclusive
			monsterHealth = 200 + (-10 + rng.nextInt(41));
			//Monster attack is between 5 and 7 inclusive
			monsterAttack = 6 + (-1 + rng.nextInt(2));
			//Monster XP is 3
			monsterXP = 3;
		}
		
		//If the random variable is 3, the monster is a Troll
		else if (randMonster == 3){
			monsterName = "Troll";
			//Monster health is between 240 and 260 inclusive
			monsterHealth = 250 + (-10 + rng.nextInt(21));
			//Monster attack is between 11 and 13 inclusive
			monsterAttack = 12 + (-1 + rng.nextInt(2));
			//Monster XP is 5
			monsterXP = 5;
		}
		
		//If the random variable is 4, the monster is a Wolf
		else if (randMonster == 4){
			monsterName = "Wolf";
			//Monster health is between 60 and 80 inclusive
			monsterHealth = 70 + (-10 + rng.nextInt(21));
			//Monster attack is between 13 and 15 inclusive
			monsterAttack = 14 + (-1 + rng.nextInt(2));
			//Monster XP is 3
			monsterXP = 3;
		}
		
		//If the random variable is 5, the monster is a Spirit
		else if (randMonster == 5){
			monsterName = "Spirit";
			//Monster health is between 140 and 160 inclusive
			monsterHealth = 150 + (-10 + rng.nextInt(21));
			//Monster attack is between 11 and 13 inclusive
			monsterAttack = 12 + (-1 + rng.nextInt(2));
			//Monster XP is 3
			monsterXP = 3;
		}
		return;
	} //End monster creation method
	
	//Combat manager method
	public static void combatBase(){
		boolean inCombat = true;
		//Start combat with max health
		currentHealth = heroHealth;
		//Start combat with MP equal to 1/3 magic stat rounded down
		currentMagic = heroMagic / 3;
		while (inCombat == true){
			//Make sure a blank line is printed with each iteration
			System.out.println();
			/*Report Combat Stats*/
			//Print the Monster's name
			System.out.printf("You are fighting a %s!%n", monsterName);
			//Print the Monster's health
			System.out.printf("The monster HP: %d %n", monsterHealth);
			//Print the Player's health
			System.out.printf("Your HP: %d%n", currentHealth);
			//Print the Player's magic points
			System.out.printf("Your MP: %d%n%n", currentMagic);
			
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
				combatAttack();
			}
			else if (heroChoice == 2) {
				//Run cast spell method
				combatMagic();
			}
			else if (heroChoice == 3) {
				//Run charge mana method
				combatCharge();
			}
			else if (heroChoice == 4) {
				//Run flee method to set inCombat to true or false
				inCombat = combatFlee();
			}
			else {
				//Print error message
				System.out.println("I don't understand that command.");
				System.out.println();
			}
			
			//Check to see if combat ended, if it hasn't already
			if (inCombat == true) {
				//If the monster was defeated
				if (monsterHealth <=0) {
					//Print message notifiying the player and tell how much XP was gained
					System.out.printf("You defeated the %s! You gained %d XP.%n", monsterName, monsterXP);
					//Add XP to player total and see if the player leveled up (It costs 8 XP to level up)
					currentXP = currentXP + monsterXP;
					if (currentXP < 8) {
						//Print remaining XP to level up if the player did not level up this round
						System.out.printf("You need %d XP to level up.%n", 8 - currentXP);
					}		
					if (currentXP >=8){
						//Subtract 8 from current XP
						currentXP -= 8;
						//Run hero stats method to level up
						heroStats();
					}
					//Set inCombat to false
					inCombat = false;
				}
				//If the player was defeated
				else if (currentHealth <=0){
					//Set hero health to 0 for program control reasons
					heroHealth = 0;
					//Print loss message
					System.out.println("You were defeated. You may run the game again to create a new hero.");
					//Set inCombat to false
					inCombat = false;
				}
				//If there is no reason to end combat
				else {
					//Do nothing and continue combat
					System.out.println();
				}
			}
		}//End while loop	
			
	}//End combat manager method
	
	//Attack method
	public static void combatAttack() {
		//Declare hero damage variable and determine damage based on a random number
		int heroDamage = (heroAttack / 2) + rng.nextInt(heroAttack);
		//Calculation: new monster health is old monster health minus hero damage
		monsterHealth = monsterHealth - heroDamage;
		//print attack text
		System.out.printf("You strike the %s with your sword for %d damage. %n", monsterName, heroDamage);
	
		//If the monster hasn't been defeated, it attacks the hero
		if (monsterHealth > 0 ){
			combatMonsterAttack();
		}		
	}//End combat attack method
	
	//Cast spell method
	public static void combatMagic() {
		//Check to see if the hero has enough mana
		if (currentMagic >= 3){
			//Cast spell reducing monster health by half
			monsterHealth = monsterHealth / 2;
			//Print message describing the spell
			System.out.printf("You cast the weaken spell on the %s.%n", monsterName);
			//Reduce hero mana by 3
			currentMagic -= 3;
			//If the monster hasn't been defeated, it attacks the hero
			if (monsterHealth > 0 ){
				combatMonsterAttack();
			}	
		}
		//Else print not enough mana message
		else {
			System.out.println ("You don't have enough mana.");
		}
		return;
	}//End spell method
	
	//Charge mana method
	public static void combatCharge() {
		//Add 1 mana to your current total plus 1 for each 4 magic points
		currentMagic = currentMagic + 1 + (heroMagic / 4);
		//The monster attacks the hero
		combatMonsterAttack();
		return;
	}//End charge method
	
	//Run away method
	public static boolean combatFlee() {
		//Run away succeeds 50% of the time
		int runAway = rng.nextInt(2);
		if (runAway == 1) {
			//Print success message
			System.out.println("You ran away!");
			//Return false to set inCombat to false in the manager method
			return false;
		}
		else {
			//Print failure message
			System.out.println("You failed to run away.");
			//The monster attacks the hero
			combatMonsterAttack();
			//Return true to set inCombat to true in the manager method
			return true;
		}
	}

	//Monster attack method
	public static void combatMonsterAttack() {
		//Use random number to generate monster damage
		int monsterDamage = (monsterAttack / 2) + rng.nextInt(monsterAttack);
		//Print message describing the damage taken from the monster attack
		System.out.printf("The %s attacks you for %d damage!%n", monsterName, monsterDamage);
		//Reduce the player's health by the monster's attack
		currentHealth = currentHealth - monsterDamage;
		return;
	}//End monster attack method
	
	public static boolean endGame() {
	
		//Declare variables used to control whether or not the player continues the game
		boolean continueGame = true;
		int playerContinue = 1;
		//If the hero died, end the game
		if (heroHealth <=0) {
			continueGame = false;
			System.out.println("Thank you for playing.");
		}
		
		//If the hero survived the combat, see if he or she wants to enter another encounter
		if (heroHealth >0) {
			boolean continueCheck = true;
			
			while (continueCheck == true) {
				System.out.print("Continue playing? Enter 1 for yes or 0 for no: ");
				playerContinue = input.nextInt();
				
				//If the player wants to continue
				if (playerContinue == 1) {
					//Do nothing and continue the loop
					System.out.println();
					//The user correctly entered input:
					continueCheck = false;
				}
				//If the player wants to quit
				else if (playerContinue == 0) {
					//End the loop and print thank you message
					continueGame = false;
					//The user correctly entered input:
					continueCheck = false;
					System.out.println();
					System.out.println("Thank you for playing.");
				}
				//If the player entered incorrectly
				else {
					System.out.println("I don't understand that command.");
				}
			}//End while loop		
		}//End if
		return continueGame;	
	}// End end game method
	
	
} //End class