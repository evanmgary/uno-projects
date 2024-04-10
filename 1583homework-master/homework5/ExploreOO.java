/** Runner class for object-oriented dungeon exploration program for Homework 5
 *
 * @author	Evan Gary
 * @version	10/26/16
*/

import java.util.Scanner;

public class ExploreOO{

	static Scanner input = new Scanner(System.in);
	static boolean continueGame;
	static Room currentRoom;
	static Dungeon castle = new Dungeon();
	static boolean hasKey1 = false;
	static boolean hasKey2 = false;
	
	public static void main(String[] args){
	
		continueGame = true; //Game loop variable
		
		currentRoom = castle.getEntrance(); //set current room as entrance
	
		//continue the game unless the game loop variable is set to false
		while (continueGame == true) {
			System.out.println();
			System.out.println(currentRoom + currentRoom.getExits());
			playerCommand();
		} 
	
	}//end main

	//get input for the movement choice and return corresponding integer, set 0 for no room in a direction
	public static int getMoveCommand(){
		System.out.print("Enter n for north, e for east, w for west, or s for south: ");
		int returnCommand = -1; //-1 indicates incorrect input
		String command = input.nextLine();
		if (command.equalsIgnoreCase("n")){
			if (currentRoom.getNorth() != null){
				returnCommand = 1;
			}
			else{
				returnCommand = 0; //0 indicates correct input but no room in that direction
			}
		}
		
		else if (command.equalsIgnoreCase("e")){
			if (currentRoom.getEast() != null){
				returnCommand = 2;
			}
			else{
				returnCommand = 0;
			}
		}
		
		else if (command.equalsIgnoreCase("w")){
			if (currentRoom.getWest() != null){
				returnCommand = 3;
			}
			else{
				returnCommand = 0;
			}
		}
		
		else if (command.equalsIgnoreCase("s")){
			if (currentRoom.getSouth() != null){
				returnCommand = 4;
			}
			else{
				returnCommand = 0;
			}
		}
		
		else {
			System.out.println("That is an invalid command.");
		}
			
		return returnCommand;
	}//end getMoveCommand
	
	//Get the move, examine, or quit player command input
	public static int getCommand(){
		System.out.print("What do you want to do? Enter m to move, x to examine, or q to quit: ");
		int returnCommand = 0;
		String command = input.nextLine();
		if (command.equalsIgnoreCase("m")){
			returnCommand = 1;
		}
		else if (command.equalsIgnoreCase("x")){
			returnCommand = 2;
		}
		else if (command.equalsIgnoreCase("q")){
			returnCommand = 3;
		}
		else {
			System.out.println("That is not a valid command.");
		}
			
		return returnCommand;
	}//end getCommand
	
	public static void move(){
		int move = getMoveCommand();
		if (move == 1) {
			currentRoom = currentRoom.getNorth(); //move the current room
		}
		else if (move == 2) {
			currentRoom = currentRoom.getEast();
		}
		else if (move == 3) {
			currentRoom = currentRoom.getWest();
		}
		else if (move == 4) {
			currentRoom = currentRoom.getSouth();
		}
		else if (move == 0) {
			System.out.println("There is no exit in that direction.");
		}
		
	}//end Move
	
	//Perform the chosen action
	public static void playerCommand(){
		int action = getCommand();
		if (action == 1){
			move();
		}
		else if (action == 2){
			currentRoom.examine();
			events(currentRoom.getEvent());
		}
		else if (action == 3) {
			continueGame = false;
		}
		else {
			System.out.println();
		}
		
	}//end playerCommand
	
	//Manage the locked door and keys
	public static void events(int eventType){
		if (eventType == 1){
			System.out.println("You see something shiny on a table in a corner. You found a golden key!");
			hasKey1 = true;
			currentRoom.setEvent(0);
		}
		if (eventType == 2) {
			System.out.println("You see something shiny in a cabinet. You found a silver key!");
			hasKey2 = true;
			currentRoom.setEvent(0);
		}
		if (eventType == 3){
			System.out.println("The door to the north is locked. You need two keys to open it.");
			if (hasKey1 == true && hasKey2 == true){
				System.out.println("You unlock the door. You feel a dark power ahead.");
				currentRoom.setNorth(castle.returnUpperHall());
				currentRoom.setEvent(0);
			}
		}
		
	}//end events
	
}//end class

