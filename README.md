This is a simple game simulating an island inhabited by various animals. Each creature is processed by a separate thread.

The game settings can be changed in the file src/main/resources/conf/game.yaml. The file src/main/resources/conf/cell/capacity.yaml contains the settings for the capacity of the island cells (the maximum number of creatures that can be in each of the island cells). The parameters of the island inhabitants can be changed by editing the files in the folder src/main/resources/conf/entities.

It is recommended to launch the game via
```sh
mvn compile exec:java
```
