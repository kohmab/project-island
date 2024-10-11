package com.javarush.pavlichenko.island.entities.abstr;

import com.javarush.pavlichenko.island.entities.island.Island;

import java.util.UUID;
import java.util.concurrent.Callable;

public interface IslandEntity extends Callable<IslandEntity>,  Mortal, HasAbilities, Lockable {
    Island getIsland();

    UUID getId();
}
