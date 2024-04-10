/** GameCharacter class for object-oriented dungeon exploration program for Homework 6
 *
 * @author	Evan Gary
 * @version	11/15/16
*/

import java.util.Scanner;

public class GameCharacter{
	
	protected String name;
	protected int startHealth;
	protected int currentHealth;
	protected int attack;
	
	
	public GameCharacter(){
		this.name = "Hero";
		this.startHealth = 10;
		this.currentHealth = 10;
		this.attack = 5;
		
	}
	
	public GameCharacter(String name, int startHealth, int currentHealth, int attack){
		this.name = name;
		this.startHealth = startHealth;
		this.currentHealth = currentHealth;
		this.attack = attack;
	}
	
	//Getter methods
	public String getName(){
		return this.name;
	}
	
	public int getStartHealth(){
		return this.startHealth;
	}
	
	public int getCurrentHealth(){
		return this.currentHealth;
	}
	
	public int getAttack(){
		return this.attack;
	}
	
	//Setter Methods
	public void setName(String name){
		this.name = name;
	}
	
	public void setCurrentHealth(int currentHealth){
		this.currentHealth = currentHealth;
	}
	
	public void setStartHealth(int startHealth){
		this.startHealth = startHealth;
	}
	
	public void setAttack(int attack){
		this.attack = attack;
	}

	public void takeDamage(int damage){
		this.currentHealth -= damage;
	}	
	
}