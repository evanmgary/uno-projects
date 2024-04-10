This is the enhanced combat program for Homework 3.
To compile the program, use a terminal and navigate to the proper directory, then enter
	javac EnhancedCombatFinal.java
Then, run the program with 
	java EnhancedCombatFinal

The program implements a hero creation and level up system. The player starts with 20 stat points to allocate freely among health, attack, and magic. At each level up the player can allocate two more points. It takes 8 XP to level up.
There are five monsters that can be generated. Each focuses on different stats and may require a different strategy to defeat. It may not be possible to defeat every monster at level 1.

Combat has four options. Attack does damage based on the hero's attack. Cast spell costs 3 mana and reduces the monster's health by half. Charge adds mana based on the hero's magic stat. Run away gives a 50% chance to run from the battle. 

