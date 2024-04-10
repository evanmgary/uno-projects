This is the object-oriented dungeon exploration program for Homework 5, by Evan Gary.
To compile the program, use a terminal and navigate to the proper directory, then enter
	javac ExploreOO.java Room.java Dungeon.java
Then, run the program with 
	java ExploreOO

This program allows the player to explore a castle. In each room, the player can move or examine. If the player moves, there is a choice between north, south, east, and west. If there is an unlocked room in the chosen direction, the player moves. There may be a locked door, in which case the player needs to find keys by exploring the castle and using examine. 
Anytime the player can move or examine, the player can also immediately quit the game.