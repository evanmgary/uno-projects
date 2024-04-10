/** Dungeon exploration program created for Homework 4
 * This program implements movement around a dungeon and some events. It is designed so that combat could be easily included.
 *
 * @author	Evan Gary
 * @version	10/6/16
*/

import java.util.Scanner;

public class ExploreDungeon {
	
	private static Scanner input = new Scanner(System.in);
	private static int inRoom;
	private static String[] roomDesc = new String[10];
	private static int[][] roomLayout = {{1,-1,-1,-1},{2,-1,-1,0},{-2,5,3,1},{-1,2,-1,4},{3,-1,-1,-1},{-1,-1,2,6},{5,-1,-1,-1},{-1,9,8,2},{-1,7,-1,-1},{-1,-1,7,-1}};
	private static boolean hasKey1;
	private static boolean hasKey2;
	private static boolean continueGame;
	private static final int NORTH = 0;
	private static final int EAST = 1;
	private static final int WEST = 2;
	private static final int SOUTH = 3;
	
	public static void main(String[] args){
		continueGame = true;
		System.out.println("You have entered the castle.");
		//Initialize game state
		inRoom = 0;
		hasKey1 = false;
		hasKey2 = false;
		//Fill room descriptions array
		makeDescriptions();
		
		//Main game loop: game continues while continueGame is true
		while (continueGame == true){
			//Series of if statements determines what room the player is in based on inRoom
			if (inRoom == 0){
				room0();
			}
			else if (inRoom == 1){
				room1();
			}
			else if (inRoom == 2){
				room2();
			}
			else if (inRoom == 3){
				room3();
			}
			else if (inRoom == 4){
				room4();
			}
			else if (inRoom == 5){
				room5();
			}
			else if (inRoom == 6){
				room6();
			}
			else if (inRoom == 7){
				room7();
			}
			else if (inRoom == 8){
				room8();
			}
			else if (inRoom == 9){
				room9();
			}
			//This should never run, but if inRoom ever gets set to some odd value, return to the entrance
			else {
				inRoom = 0;
			}
		}//End main while
		System.out.println("You have left the castle.");
		
	}//End main method
	
	public static int dungeonMove(){
		System.out.println("Where would you like to move?");
		System.out.print("Please enter n for north, s for south, e for east, or w for west: ");
		String destination = input.nextLine();
		//Call getRoom method to convert the String input into a direction that can be used in the layout array. Also check for valid input.
		int attemptMove = getRoom(destination);
		System.out.println();
		//If the move is possible, move the character by changing the inRoom variable based on the layout array
		if (attemptMove >=0){
			inRoom = attemptMove;
			return 1;
		}
		//If the move is not valid but the input is valid, indicate that the desired move cannot be made
		else if (attemptMove == -1)	{
			System.out.println("There is no exit in that direction.");
			return 0;
		}
		//If the move attempt triggers a special event, return -2 so that the room method can trigger the event
		else if (attemptMove == -2) {
			return -1;
		}
		//If the user entered invalid input, notify the user
		else {
			System.out.println("That is not a valid input.");
			return -2;
		}
		
	}

	//Method to fill the room description array to make the code easier to change if needed
	public static void makeDescriptions() {
		roomDesc[0] = "You are at the castle entrance.%n There is a door to the north.%n";
		roomDesc[1] = "You are in a small foyer.%nThere are doors to the north and south.%n";
		roomDesc[2] = "You are in a grand hall.%nYou see a door in each direction. The door to the north is large and appears to be very heavy.%n";
		roomDesc[3] = "You are in the west corridor. %nThere is a door to the east and to the south.%n";
		roomDesc[4] = "You are in the parlor.%nThere is a door to the north.%n";
		roomDesc[5] = "You are in the dining area.%nThere are doors to the west and soutn.%n";
		roomDesc[6] = "You are in the kitchen.%nThere is a door to the north.%n";
		roomDesc[7] = "You are in the upper corridor.%nThere is a door to the west and more stairs to the east. There are stairs going down to the south.%n";
		roomDesc[8] = "You are in the study.%nThere is a door to the east.%n";
		roomDesc[9] = "You are in the castle tower.%nThere is a door to the west.%n";
	}

	//getRoom method translates user input from the move method into a direction
	public static int getRoom(String inputRoom){
		
		//moveValue is the numerical value of the chosen direction
		int moveValue;
		//destRoom is the room to which the player will move
		int destRoom = 0;
		
		//Assign the appropriate directional value to moveValue, or assign 0 indicating incorrect input
		if (inputRoom.equals("n") || inputRoom.equals("N")){
			moveValue = NORTH;
		}
		else if (inputRoom.equals("e") || inputRoom.equals("E")){
			moveValue = EAST;
		}
		else if (inputRoom.equals("w") || inputRoom.equals("W")){
			moveValue = WEST;
		}
		else if (inputRoom.equals("s") || inputRoom.equals("S")){
			moveValue = SOUTH;
		}
		else {
			moveValue = -3;
		}
		//If valid input, assign the destination room based on the layout array
		if (moveValue >=0) {
			destRoom = roomLayout[inRoom][moveValue];
		}
		//If invalid input, assign -3 indicating invalid input
		else if (moveValue==-3){
			destRoom = -3; 
		}
		return destRoom;
		
	}
	
	//roomInput method translates the move, examine, or quit input into an integer for use in the individual room methods
	public static int roomInput(){
		int validInput = 0;
		String playerInput;
		int playerChoice = 0;
		//While loop ensures the player enters usable input
		while (validInput <=0){
			System.out.print("Enter m to move, x to examine, or q to quit: ");
			playerInput = input.nextLine();
			System.out.println();
			
			if (playerInput.equals("m") || playerInput.equals("M")) {
				playerChoice = 1; 
				validInput = 1;
			}
			else if (playerInput.equals("x") || playerInput.equals("X")) {
				playerChoice = 2;
				validInput = 1;
			}
			else if (playerInput.equals("q") || playerInput.equals("Q")) {
				playerChoice = 3;
				validInput = 1;
			}
			else {
				System.out.println("That is not a valid input.");
			}
		}
		return playerChoice;
	}//End roomInput
	
	//The ten room methods allow for the easy implementation of room specific events
	public static void room0() {
		int exitValue = 0;
		System.out.printf(roomDesc[0]);
		int playerChoice = 0;
		//While loop is used to keep the player in the room until he/she leaves or quits the game
		while(exitValue <=0){
			playerChoice = roomInput();
			//Player chooses move
			if (playerChoice == 1){
				//dungeonMove method attempts to move the player to another room. The loop only ends if a move is successful.
				exitValue = dungeonMove();
			}
			//Player choose examine
			if (playerChoice == 2){
				System.out.println("This is the entrance to the castle. You see nothing special but a door to your north.");
			}
			//Player chooses quit
			if (playerChoice == 3){
				exitValue = 1;
				continueGame = false;
			}
		}//End while
	}//End room0
	
	public static void room1() {
		int exitValue = 0;
		System.out.printf(roomDesc[1]);
		int playerChoice = 0;
		//While loop is used to keep the player in the room until he/she leaves or quits the game
		while(exitValue <=0){
			playerChoice = roomInput();
			//Player chooses move
			if (playerChoice == 1){
				//dungeonMove method attempts to move the player to another room. The loop only ends if a move is successful.
				exitValue = dungeonMove();
			}
			//Player choose examine
			if (playerChoice == 2){
				System.out.println("There is some old furniture nearby and doors to the north and south. Nothing else catches your eye.");
			}
			//Player chooses quit
			if (playerChoice == 3){
				exitValue = 1;
				continueGame = false;
			}
		}//End while
	}//End room1
	
	public static void room2() {
		int exitValue = 0;
		System.out.printf(roomDesc[2]);
		int playerChoice = 0;
		//While loop is used to keep the player in the room until he/she leaves or quits the game
		while(exitValue <=0){
			playerChoice = roomInput();
			//Player chooses move
			if (playerChoice == 1){
				//dungeonMove method attempts to move the player to another room. The loop only ends if a move is successful.
				exitValue = dungeonMove();
				//Locked door event: the door opens only if the player has both keys
				if (exitValue == -1){
					if (hasKey1 == true && hasKey2 == true){
						System.out.println("You unlock the large door to the north. You feel a sense of foreboding.");
						roomLayout[2][NORTH] = 7;
					}
					else {
						System.out.printf("The door to the north is locked. There are two locks. You need to find at least one more key to open it.%n");
					}
				}//End locked door if
			}
			//Player choose examine
			if (playerChoice == 2){
				System.out.println("This is the grand hall. The room is enormous and well-decorated but empty. There is a large door to the north. You feel something powerful is behind it.");
			}
			//Player chooses quit
			if (playerChoice == 3){
				exitValue = 1;
				continueGame = false;
			}
		}//End while	
	}//End room2 method
	
	public static void room3() {
		int exitValue = 0;
		System.out.printf(roomDesc[3]);
		int playerChoice = 0;
		//While loop is used to keep the player in the room until he/she leaves or quits the game
		while(exitValue <=0){
			playerChoice = roomInput();
			//Player chooses move
			if (playerChoice == 1){
				//dungeonMove method attempts to move the player to another room. The loop only ends if a move is successful.
				exitValue = dungeonMove();
			}
			//Player choose examine
			if (playerChoice == 2){
				System.out.println("This is a corridor. There is nothing special here but a number of paintings along the walls.");
			}
			//Player chooses quit
			if (playerChoice == 3){
				exitValue = 1;
				continueGame = false;
			}
		}//End while		
	}//End room3
	
	public static void room4() {
		int exitValue = 0;
		System.out.printf(roomDesc[4]);
		int playerChoice = 0;
		//While loop is used to keep the player in the room until he/she leaves or quits the game
		while(exitValue <=0){
			playerChoice = roomInput();
			//Player chooses move
			if (playerChoice == 1){
				//dungeonMove method attempts to move the player to another room. The loop only ends if a move is successful.
				exitValue = dungeonMove();
			}
			//Player choose examine
			if (playerChoice == 2){
				System.out.println("This is a parlor. You see some dusty couches and tables. The room is lined with ornate paintings.");
				if (hasKey1 == false){
					System.out.println("You look on the small table in the corner. You find a golden key!");
					hasKey1 = true;
				}
			}
			//Player chooses quit
			if (playerChoice == 3){
				exitValue = 1;
				continueGame = false;
			}
		}//End while		
	}//End room4
	
	public static void room5() {
		int exitValue = 0;
		System.out.printf(roomDesc[5]);
		int playerChoice = 0;
		//While loop is used to keep the player in the room until he/she leaves or quits the game
		while(exitValue <=0){
			playerChoice = roomInput();
			//Player chooses move
			if (playerChoice == 1){
				//dungeonMove method attempts to move the player to another room. The loop only ends if a move is successful.
				exitValue = dungeonMove();
			}
			//Player choose examine
			if (playerChoice == 2){
				System.out.println("This is a dining area. There is a large round table covered in dust surrounded by some chairs.");
			}
			//Player chooses quit
			if (playerChoice == 3){
				exitValue = 1;
				continueGame = false;
			}
		}//End while		
	}//End room5
	
	public static void room6() {
		int exitValue = 0;
		System.out.printf(roomDesc[6]);
		int playerChoice = 0;
		//While loop is used to keep the player in the room until he/she leaves or quits the game
		while(exitValue <=0){
			playerChoice = roomInput();
			//Player chooses move
			if (playerChoice == 1){
				//dungeonMove method attempts to move the player to another room. The loop only ends if a move is successful.
				exitValue = dungeonMove();
			}
			//Player choose examine
			if (playerChoice == 2){
				System.out.println("This is the castle kitchen. You see a number of dusty kitchen tools. There is a line of cabinets ahead of you.");
				if (hasKey2 == false){
					System.out.println("You look in one of the cabinets. You find a silver key!");
					hasKey2 = true;
				}
			}
			//Player chooses quit
			if (playerChoice == 3){
				exitValue = 1;
				continueGame = false;
			}
		}//End while		
	}//End room6
	
	public static void room7() {
		int exitValue = 0;
		System.out.printf(roomDesc[7]);
		int playerChoice = 0;
		//While loop is used to keep the player in the room until he/she leaves or quits the game
		while(exitValue <=0){
			playerChoice = roomInput();
			//Player chooses move
			if (playerChoice == 1){
				//dungeonMove method attempts to move the player to another room. The loop only ends if a move is successful.
				exitValue = dungeonMove();
			}
			//Player choose examine
			if (playerChoice == 2){
				System.out.println("This is the upper level of the castle. You feel a powerful presence up the stairs to your east.");
			}
			//Player chooses quit
			if (playerChoice == 3){
				exitValue = 1;
				continueGame = false;
			}
		}//End while		
	}//End room7
	
	public static void room8() {
		int exitValue = 0;
		System.out.printf(roomDesc[8]);
		int playerChoice = 0;
		//While loop is used to keep the player in the room until he/she leaves or quits the game
		while(exitValue <=0){
			playerChoice = roomInput();
			//Player chooses move
			if (playerChoice == 1){
				//dungeonMove method attempts to move the player to another room. The loop only ends if a move is successful.
				exitValue = dungeonMove();
			}
			//Player choose examine
			if (playerChoice == 2){
				System.out.println("This is the castle study. There are a number of bookshelves full of old books.");
			}
			//Player chooses quit
			if (playerChoice == 3){
				exitValue = 1;
				continueGame = false;
			}
		}//End while		
	}//End room8
	
	public static void room9() {
		int exitValue = 0;
		System.out.printf(roomDesc[9]);
		int playerChoice = 0;
		//While loop is used to keep the player in the room until he/she leaves or quits the game
		while(exitValue <=0){
			playerChoice = roomInput();
			//Player chooses move
			if (playerChoice == 1){
				//dungeonMove method attempts to move the player to another room. The loop only ends if a move is successful.
				exitValue = dungeonMove();
			}
			//Player choose examine
			if (playerChoice == 2){
				System.out.println("You see the dark wizard that is the master of the castle. He does not attack you. You know you cannot defeat him right now so you run away.");
				//If combat were implemented, the player could fight the boss. As of now, the player must run away by moving back to the upper level.
				inRoom = roomLayout[inRoom][WEST];
				exitValue = 1;
			}
			//Player chooses quit
			if (playerChoice == 3){
				exitValue = 1;
				continueGame = false;
			}
		}//End while		
	}//End room9
}//End class