package com.javarush.pavlichenko.entities.concrete;

import com.javarush.pavlichenko.entities.abilities.Hunt;
import com.javarush.pavlichenko.entities.abstr.SomeAnimal;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;
import com.javarush.pavlichenko.entities.abstr.entitiesmarkers.Predator;

public class TestAnimal extends SomeAnimal implements Predator {
    private static int counter = 0;
    private final int id = counter++;

    @Override
    public String toString() {
        return "TestAnimal" + id + "{" +
                "coordinate=" + this.getCoordinate() +
                '}';
    }

    public TestAnimal( Island island, Coordinate coordinate) {
        super(island, coordinate);

        Hunt hunt = new Hunt(this);
        this.addAbility(hunt);
    }


}
