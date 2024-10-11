package com.javarush.pavlichenko.island.entities.abstr;

import com.javarush.pavlichenko.island.entities.abilities.Hunt;
import com.javarush.pavlichenko.island.entities.island.Island;
import com.javarush.pavlichenko.island.entities.abstr.entitiesmarkers.Predator;

public abstract class SomePredator extends SomeAnimal implements Predator {
    public SomePredator(Island island) {
        super(island);

        new Hunt(this);
    }
}
