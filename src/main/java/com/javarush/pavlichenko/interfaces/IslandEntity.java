package com.javarush.pavlichenko.interfaces;

import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;

import java.util.concurrent.Callable;

public interface IslandEntity extends Callable<Boolean>, CanDie {
    Island getIsland();
    Coordinate getCoordinate();
}
