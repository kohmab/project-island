package com.javarush.pavlichenko.entities.concrete;

import com.javarush.pavlichenko.entities.abstr.SomePredator;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;

public class Bear extends SomePredator {

    protected Bear(Island island, Coordinate coordinate) {
        super(island, coordinate);
    }
}
