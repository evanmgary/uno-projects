/** Combat calculator program which performs text-based RPG-style monster combat
 * This version implements the monster attacking the hero each turn.
 *
 * @author	Evan Gary
 * @version	9/7/16
*/

import java.util.Scanner;

public class CombatCalculator{

	public static void main(String[] args){
		//Declare variable for user input and intialize with a new scanner object
		Scanner input = new Scanner(System.in);
		
		/*Monster data variables*/
		//Declare variable for monster's name and initialize it go "goblin"
		String monsterName = "Goblin";
		//Declare variable for monster's health and initialize it to 100
		int monsterHealth = 100;
		//Declare variable for monster's attack power and initialize it to 15
		int monsterAttack = 15;
		
		/*Hero data variables*/
		//Declare variable for Hero's health and initialize it to 100
		int heroHealth = 100;
		//Declare variable for Hero's attack power and initialize it to 12
		int heroAttack = 12;
		//Declare variable for Hero's magic power and initialize it to 0
		int heroMagic = 0;
		
		//Declare loop control variable and initialize it to true
		boolean inCombat = true;
		
		//While the loop control variable is true
		while (inCombat == true){
			//Make sure a blank line is printed with each iteration
			System.out.println();
			/*Report Combat Stats*/
			//Print the Monster's name
			System.out.printf("You are fighting a %s!%n", monsterName);
			//Print the Monster's health
			System.out.printf("The monster HP: %d %n", monsterHealth);
			//Print the Player's health
			System.out.printf("Your HP: %d%n", heroHealth);
			//Print the Player's magic points
			System.out.printf("Your MP: %d%n%n", heroMagic);
			
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
			
			/*Implement selection for user's choice*/
			//If player chose option 1, (check with equality operator)
			if (heroChoice == 1){
				//Calculate damage & update monster health using subtraction
				//Calculation: new monster health is old monster health minus hero attack power
				monsterHealth = monsterHealth - heroAttack;
				//print attack text
				// "You strike the <monster name> with your sword for <hero attack> damage"
				System.out.printf("You strike the %s with your sword for %d damage. %n", monsterName, heroAttack);
				
				//If the monster hasn't been defeated, it attacks the hero
				if (monsterHealth > 0 ){
					//Print message describing the damage taken from the monster attack
					System.out.printf("The %s attacks you for %d damage!%n", monsterName, monsterAttack);
					//Reduce the player's health by the monster's attack
					heroHealth = heroHealth - monsterAttack;
				}
			}
				
			//Else if player chose option 2, (check with equality operator)
			else if (heroChoice == 2) {
				
				//If player has 3 or more magic points
				if (heroMagic >= 3){
					//Calculate damage & update monster health using division
					//Calculation: new monster health is old monster health divided by two
					monsterHealth = monsterHealth / 2;
					//Reduce player's mana points by the spell cost using subtraction
					//Calculation: new magic power is old magic power minus 3
					heroMagic = heroMagic - 3;
					//print spell message:
					// "You cast the weaken spell on the monster."
					System.out.println("You cast the weaken spell on the monster.");
					
					//If the monster hasn't been defeated, it attacks the hero
					if (monsterHealth > 0 ){
						//Print message describing the damage taken from the monster attack
						System.out.printf("The %s attacks you for %d damage!%n", monsterName, monsterAttack);
						//Reduce the player's health by the monster's attack
						heroHealth = heroHealth - monsterAttack;
					}	
				}
				
				//Else
				else{
					//print the message "You don't have enough mana."
					System.out.println("You don't have enough mana.");
				}
			}
			
			//Else if player chose option 3, (check with equality operator)
			else if (heroChoice == 3) {
				//Increment magic points and update player's magic using addition
				//Calculation: new hero magic is old hero magic plus one
				heroMagic = heroMagic + 1;
				//print charging message:
				// "You focus and charge your magic power."
				System.out.println("You focus and charge your magic power.");
				
				//Print message describing the damage taken from the monster attack
				System.out.printf("The %s attacks you for %d damage!%n", monsterName, monsterAttack);
				//Reduce the player's health by the monster's attack
				heroHealth = heroHealth - monsterAttack;				
			}
			
			//Else if player chose option 4, (check with equality operator)
			else if (heroChoice == 4) {
				//Stop combat loop by setting control variable to false
				inCombat = false;
				//print retreat message:
				// "You run away!"
				System.out.println("You run away!");
			}
			
			//Else the player chose incorrectly
			else {
				//print an error message:
				// "I don't understand that command."
				System.out.println("I don't understand that command.");
			}
			
			//If monsterhealth is 0 or below
			if (monsterHealth <= 0){
				//Stop combat loop by setting control variable to false
				inCombat = false;
				//Print victory message: "You defeated the <monster name>!"
				System.out.printf("You defeated the %s!%n", monsterName);
			}
			//Else if the hero health is 0 or below
			
			else if (heroHealth <= 0){
				//Stop combat loop by setting control variable to false
				inCombat = false;
				//Print defeat message "The monster defeated you. You lost."
				System.out.println ("The monster defeated you. You lost.");
			}
		}	
	}
}	