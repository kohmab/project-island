package com.javarush.pavlichenko.entities.abstr;

import com.javarush.pavlichenko.entities.abilities.Hunger;
import com.javarush.pavlichenko.entities.abilities.Movement;
import com.javarush.pavlichenko.entities.abilities.Multiplication;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;
import com.javarush.pavlichenko.entities.abstr.entitiesmarkers.Animal;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public abstract class SomeAnimal extends SomeIslandEntity implements Animal {

    protected SomeAnimal(Island island, Coordinate coordinate) {
        super(island, coordinate);

        Movement movement = new Movement(this);
        this.addAbility(movement);

        Hunger hunger = new Hunger(this);
        this.addAbility(hunger);

        Multiplication multiplication = new Multiplication(this);
        this.addAbility(multiplication);

    }

}
