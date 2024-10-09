package com.javarush.pavlichenko.entities.abstr;

import com.javarush.pavlichenko.entities.abilities.Hunger;
import com.javarush.pavlichenko.entities.abilities.Movement;
import com.javarush.pavlichenko.entities.abilities.Multiplication;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;
import com.javarush.pavlichenko.entities.abstr.entitiesmarkers.Animal;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public abstract class SomeAnimal extends SomeIslandEntity implements Animal {

    protected SomeAnimal(Island island) {
        super(island);
        new Movement(this);
        new Hunger(this);
        new Multiplication(this);
    }

}
