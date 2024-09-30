package com.javarush.pavlichenko.entities.abstr;

import com.javarush.pavlichenko.entities.abilities.Hunt;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;
import com.javarush.pavlichenko.entities.abstr.entitiesmarkers.Predator;

public abstract class SomePredator extends SomeAnimal implements Predator {
    public SomePredator(Island island, Coordinate coordinate) {
        super(island, coordinate);
        Hunt hunt = new Hunt(this);
        this.addAbility(hunt);
    }
}
