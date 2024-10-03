package com.javarush.pavlichenko.entities.abilities;

import com.javarush.pavlichenko.entities.abilities.parameters.AbilityParameter;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanBeCached;
import lombok.Getter;


public class Prey implements Ability {

    @Getter
    @AbilityParameter
    private Double foodAmount;

    private final CanBeCached creature;
    private final AbilityKey key;

    public Prey(CanBeCached creature) {
        this.creature = creature;
        this.key = AbilityKey.getKeyFor(this);
    }

    @Override
    public void apply() {

    }
}
