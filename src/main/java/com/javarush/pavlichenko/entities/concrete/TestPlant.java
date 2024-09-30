package com.javarush.pavlichenko.entities.concrete;

import com.javarush.pavlichenko.entities.abstr.SomePlant;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;


public class TestPlant extends SomePlant {
    private static int counter = 0;
    private final int id = counter++;

    public TestPlant(Island island, Coordinate coordinate) {
        super(island, coordinate);
    }

    @Override
    public String toString() {
        return "TestPlant" + id + "{" +
                "coordinate=" + this.getCoordinate() +
                '}';
    }
}
