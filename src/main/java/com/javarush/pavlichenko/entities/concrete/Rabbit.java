package com.javarush.pavlichenko.entities.concrete;

import com.javarush.pavlichenko.entities.abstr.SomeHerbivore;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;

public class Rabbit extends SomeHerbivore {

    protected Rabbit(Island island, Coordinate coordinate) {
        super(island, coordinate);

    }
}
