package com.javarush.pavlichenko.entities.creatures;

import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;
import com.javarush.pavlichenko.enums.Gender;
import com.javarush.pavlichenko.interfaces.Animal;

public class TestAnimal extends AbstractAnimal {
    private static int counter = 0;
    private final int id = counter++;

    public TestAnimal(Gender sex, Island island, Coordinate coordinate) {
        super(sex, island, coordinate);
    }

    @Override
    public boolean checkDeath() {
        return false;
    }

    @Override
    public String toString() {
        return "TestAnimal" + id + "{" +
                "coordinate=" + this.getCoordinate() +
                '}';
    }
}
