package com.javarush.pavlichenko.entities.abstr;

import com.javarush.pavlichenko.entities.abilities.EatingPlants;
import com.javarush.pavlichenko.entities.abilities.Prey;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;
import com.javarush.pavlichenko.entities.abstr.entitiesmarkers.Herbivore;

public abstract class SomeHerbivore extends SomeAnimal implements Herbivore {

    private Double foodAmount;

    public SomeHerbivore(Island island, Coordinate coordinate) {
        super(island, coordinate);

        EatingPlants eatingPlants = new EatingPlants(this);
        this.addAbility(eatingPlants);

        Prey prey = new Prey(this);
        this.addAbility(prey);
    }

//    @Override
//    public Double getFoodAmount() {
//        return foodAmount;
//    }
}
