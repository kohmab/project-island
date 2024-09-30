package com.javarush.pavlichenko.entities.concrete;

import com.javarush.pavlichenko.entities.abstr.SomeHerbivore;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;


public class TestPrey extends SomeHerbivore {
    private static int counter = 0;
    private final int id = counter++;


    public TestPrey(Island island, Coordinate coordinate) {
        super(island, coordinate);

    }

//    @Override
//    public Double getFoodAmount() {
//        return 100.;
//    }

    @Override
    public String toString() {
        return "TestPrey" + id + "{" +
                "coordinate=" + this.getCoordinate() +
                '}';
    }


}
