package com.javarush.pavlichenko.entities.abstr;

import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanAge;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;

import java.util.UUID;
import java.util.concurrent.Callable;

public interface IslandEntity extends Callable<IslandEntity>,  Mortal, HasAbilities, Lockable {
    Island getIsland();

    UUID getId();
}
