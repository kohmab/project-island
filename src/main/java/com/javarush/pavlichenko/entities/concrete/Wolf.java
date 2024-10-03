package com.javarush.pavlichenko.entities.concrete;

import com.javarush.pavlichenko.entities.abstr.SomePredator;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;

public class Wolf extends SomePredator {

    protected Wolf(Island island, Coordinate coordinate) {
        super(island, coordinate);
    }
}
