package com.javarush.pavlichenko.island.entities.abilities;

import com.javarush.pavlichenko.island.entities.abilities.sideclasses.AbilityParameter;
import com.javarush.pavlichenko.island.entities.abstr.abilitymarkers.CanBeCached;
import lombok.Getter;


public class Prey extends SomeAbility {

    @AbilityParameter
    @Getter
    private Double foodAmount;

    public Prey(CanBeCached creature) {
        super(creature, Prey.class);
    }

    @Override
    public void apply() {

    }
}
