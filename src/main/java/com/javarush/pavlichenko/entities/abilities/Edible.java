package com.javarush.pavlichenko.entities.abilities;

import com.javarush.pavlichenko.entities.abilities.sideclasses.AbilityKey;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanBeEaten;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Edible extends SomeAbility {

    private final Growth growth;

    public Edible(CanBeEaten canBeEaten) {
        super(canBeEaten, Edible.class);
        this.growth = getAnotherAbility(Growth.class);
    }

    protected Double getBite(Double biteSize) {
        Double weight = growth.getWeight();
        double amount = Math.min(biteSize, weight);
        weight -= amount;
        if (weight <= 0.) {
            log.info(marker, "{} was completely eaten.", owner);
            owner.die();
        }
        growth.setWeight(weight);

        return amount;
    }

    @Override
    public void apply() {

    }
}
