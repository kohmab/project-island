package com.javarush.pavlichenko.entities.abstr;

import com.javarush.pavlichenko.entities.abilities.EatingPlants;
import com.javarush.pavlichenko.entities.abilities.Prey;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;
import com.javarush.pavlichenko.entities.abstr.entitiesmarkers.Herbivore;

public abstract class SomeHerbivore extends SomeAnimal implements Herbivore {

    protected SomeHerbivore(Island island) {
        super(island);
        new EatingPlants(this);
        new Prey(this);

    }
}