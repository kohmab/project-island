package com.javarush.pavlichenko.island.entities.abstr;

import com.javarush.pavlichenko.island.entities.abilities.Hunger;
import com.javarush.pavlichenko.island.entities.abilities.Movement;
import com.javarush.pavlichenko.island.entities.abilities.Multiplication;
import com.javarush.pavlichenko.island.entities.island.Island;
import com.javarush.pavlichenko.island.entities.abstr.entitiesmarkers.Animal;

import lombok.Getter;

@Getter
public abstract class SomeAnimal extends SomeIslandEntity implements Animal {

    protected SomeAnimal(Island island) {
        super(island);
        new Movement(this);
        new Hunger(this);
        new Multiplication(this);
    }

}
