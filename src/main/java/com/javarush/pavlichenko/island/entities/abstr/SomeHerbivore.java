package com.javarush.pavlichenko.island.entities.abstr;

import com.javarush.pavlichenko.island.entities.abilities.EatingPlants;
import com.javarush.pavlichenko.island.entities.abilities.Prey;
import com.javarush.pavlichenko.island.entities.island.Island;
import com.javarush.pavlichenko.island.entities.abstr.entitiesmarkers.Herbivore;

public abstract class SomeHerbivore extends SomeAnimal implements Herbivore {

    protected SomeHerbivore(Island island) {
        super(island);
        new EatingPlants(this);
        new Prey(this);

    }
}