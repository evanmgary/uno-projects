/** Dungeon class for object-oriented dungeon exploration program for Homework 6
 *
 * @author	Evan Gary
 * @version	11/15/16
*/


public class Dungeon {
	
	Room entrance;
	Room foyer;
	Room grandHall;
	Room westCorridor;
	Room diningArea;
	Room kitchen;
	Room parlor;
	Room upperHall;
	Room study;
	Room tower;

	//Constructor
	public Dungeon() {
		//Arguments are (event type, name, combat probability)
		entrance = new Room(0, "entrance", 0);
		foyer = new Room(0, "foyer", 5);
		grandHall = new Room(3, "grand hall", 4);
		westCorridor = new Room(0, "west corridor", 6);
		parlor = new Room(1, "parlor", 4);
		diningArea = new Room(0, "dining area", 6);
		kitchen = new Room(2, "kitchen", 4);
		upperHall = new Room(0, "upper hall", 5);
		study = new Room(4, "study", 2);
		tower = new Room(5, "castle tower", 0);
		
		entrance.setExits(foyer, null, null, null);
		foyer.setExits(grandHall, null, null, entrance);
		grandHall.setExits(null, diningArea, westCorridor, foyer);
		westCorridor.setExits(null, grandHall, null, parlor);
		parlor.setExits(westCorridor, null, null, null);
		diningArea.setExits(null, null, grandHall, kitchen);
		kitchen.setExits(diningArea, null, null, null);
		upperHall.setExits(null, tower, study, grandHall);
		study.setExits(null, upperHall, null, null);
		tower.setExits(null, null, upperHall, null);
		
		entrance.setDescription("This is the entrance to the castle. You see nothing special but a door to your north.");
		foyer.setDescription("There is some old furniture nearby and doors to the north and south. Nothing else catches your eye.");
		grandHall.setDescription("This is the grand hall. The room is enormous and well-decorated but empty. There is a large door to the north. You feel something powerful is behind it.");
		westCorridor.setDescription("This is a corridor. There is nothing special here but a number of paintings along the walls.");
		parlor.setDescription("This is a parlor. You see some dusty couches and tables. The room is lined with ornate paintings.");
		diningArea.setDescription("This is a dining area. There is a large round table covered in dust surrounded by some chairs.");
		kitchen.setDescription("This is the castle kitchen. You see a number of dusty kitchen tools. There is a line of cabinets ahead of you.");
		upperHall.setDescription("This is the upper level of the castle. You feel a powerful presence up the stairs to your east.");
		study.setDescription("This is the castle study. There are a number of bookshelves full of old books.");
		tower.setDescription("You see the dark wizard that is the master of the castle.");
	}//end constructor

	//Used to start the game by creating the entrance room
	public Room getEntrance(){
		return entrance;
	} 
	
	//Used to "unlock" the door in the Great Hall
	public Room returnUpperHall(){
		return upperHall;
	}
	
}