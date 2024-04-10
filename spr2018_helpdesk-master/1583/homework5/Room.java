/** Room class for object-oriented dungeon exploration program for Homework 5
 *
 * @author	Evan Gary
 * @version	10/26/16
*/


public class Room {
	
	private String description;
	private String name;
	private Room northExit;
	private Room eastExit;
	private Room westExit;
	private Room southExit;
	private int eventType;

	//Constructor
	public Room(int event, String name){
		this.name = name;
		this.eventType = event;
		//Initialize to blank string to be set later
		this.description = "";
		//Initialize rooms to null to be set later
		this.northExit = null;
		this.eastExit = null;
		this.southExit = null;
		this.westExit = null;
	}

	public void setNorth(Room north){
		this.northExit = north;
	}

	public void setEast(Room east){
		this.eastExit = east;
	}
	
	public void setWest(Room west){
		this.westExit = west;
	}
	
	public void setSouth(Room south){
		this.southExit = south;
	}
	
	public void setExits(Room north, Room east, Room west, Room south){
		this.northExit = north;
		this.eastExit = east;
		this.southExit = south;
		this.westExit = west;
	}

	public void setDescription(String description){
		this.description = description;
	}	
	
	public void setEvent(int eventType){
		this.eventType = eventType;
	}
	
	public Room getNorth(){
		return northExit;
	}
	
	public Room getEast(){
		return eastExit;
	}
	
	public Room getWest() {
		return westExit;
	}
	
	public Room getSouth(){
		return southExit;
	}
	
	public String getDescription(){
		return description;
	}
	
	//Used as part of the examine command
	public void examine() {
		System.out.println(this.getDescription() );
	}
	
	public String getName(){
		return name;
	}
	
	public int getEvent(){
		return eventType;
	}
		
	public String getExits() {
		String northName;
		String eastName;
		String westName;
		String southName;
		if (northExit != null) {
			northName = "north ";
		}
		else 
		{
			northName = " ";
		}	
		if (eastExit != null) {
			eastName =  "east ";
		}
		else 
		{
			eastName = " ";
		}	
		if (westExit != null) {
			westName = "west ";
		}
		else 
		{
			westName = " ";
		}	
		if (southExit != null) {
			southName = "south ";
		}
		else 
		{
			southName = " ";
		}	
		return "The exits are: " + northName + eastName + westName + southName;
		
	}//end getExits
	
	public String toString() {
		String combined = "This is the " + this.name + ". ";
		return combined;
	}
	
	
	
}//end room