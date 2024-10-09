package com.javarush.pavlichenko.entities.abilities;

import com.javarush.pavlichenko.entities.abilities.sideclasses.AbilityParameter;
import com.javarush.pavlichenko.entities.abilities.sideclasses.AbilityKey;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanBeCached;
import lombok.Getter;
import lombok.Setter;


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
