# BattleShip / BattleAirplane  

### Introdution  
There is a game played on papers named Battle-Ship. We digitize the paper game for reducing the wasted paper.  
Besides Battle-Ship, we still develope another game called Battle-Airplane.  Battle-Airplane has similar logic to battle-ship.  

In battle-ship, a ship will be destroyed when all units of the ship are attacked.  
In battle-airplane, an airplane will be destroyed when one of all units of the airplane is attacked.  

### Technique
Client: mainly use java-swing, java-awt to draw the user interface, and use java-rmi to connect server end.  
Server: mainly use java-rmi package to build a server.  

### How to play
1. compile all the code in src directory
``` cmd
> javac *.java
```

2. generate rmi impl stub file
``` cmd
> rmic BattleShipGameRMIImpl
or 
> rmic FighterAirplaneRMIImpl
```

3. start rmi registry
``` cmd
> start rmiregistry
```

4. starting server and client-end application
``` cmd
> java BattleShipServer
> java Guishow
or
> java FighterAirplaneServer
> java GuishowFP
```

5. play and enjoy