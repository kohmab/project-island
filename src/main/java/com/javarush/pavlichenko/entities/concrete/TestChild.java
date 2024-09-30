package com.javarush.pavlichenko.entities.concrete;

import com.javarush.pavlichenko.entities.abstr.SomeAnimal;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;

public class TestChild extends SomeAnimal {
    public TestChild(Island island, Coordinate coordinate) {
        super(island, coordinate);
    }
}
