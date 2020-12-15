# Object-Oriented Programming Course Assignment #3

### Authors: Kfir Goldfarb and Nadav Keysar


This project is for represents a game which is based on the application of a two-way weighted graph with different methods and algorithms,
In this game we have to manage a group of agents whose goal is to collect as many pokemons as possible through one or more of your pokemon balls before time runs out.
The more Pokemon you catch, the more points you will earn. The game is played on a changing game board. The game has slightly different scenarios, based on different graphs with different time limits, at different levels. The Pokémon remain static in their places and have different values. One Pokemon can be more expensive than the other. Agents can increase speed if they catch enough Pokemon. The game is played in four stages: choosing the stage of the agents' location to their place of origin. Starting the game Continuous management of the agents until the game is over.

* This assignment have two parts:

## Part 1:

Build directed weighted graph data structure, and graph algorithms class for the directed and wighted graphs.

## Part 2:

By part 1 graph data structure and graph algorithms build a client class that know how to play the best in pokemon game on the directed weighted graph (the game is work on a server called Ex2_Server_v0.13.jar - can see in libs folder), every interaction with the server is doing by json formats, the server can get a game level [from 0 to 23] and an ID of the student, and can build random directed weighted graph with some pokemon on his edges, the Ex2 class will do some inserting manipulations on the given graph and with the DWGraph_Algo class will use some algorithms in it aka shortestPathDist, shortestPath, isConnected and more (details below).
###### to read more details on the interfaces, classes, implementation, algorithms and the pokemon game, go to the wiki of this project - https://github.com/kggold4/ObjectOriented_S2020_Ex2/wiki

## packages and classes

For the part 1 of this assignment we have the api package for all interfaces and classes for build a directed weighted graph data structure and graph algorithms class, for more information about the interfaces and the classes see at the wiki page.

For the part 2 of this assignment we have the gameClient package for all classes for making Ex2 class play as client properly on the Pokémon game.
also we have the util package with all the classes that represents a vectors points int the arena.

## For clone down this repository
$ git clone https://github.com/kggold4/ObjectOriented_S2020_Ex2.git