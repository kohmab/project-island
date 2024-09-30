package com.javarush.pavlichenko.entities.abilities;

import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanBeEaten;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Edible implements Ability {
    private final CanBeEaten canBeEaten;
    private final AbilityKey key;

    public Edible(CanBeEaten canBeEaten) {
        this.canBeEaten = canBeEaten;
        this.key = AbilityKey.getKeyFor(this);
    }

    public Double getBite(Double biteSize) {
        Growth growth = (Growth) canBeEaten.getAbility(AbilityKey.getKeyForClass(Growth.class));
        Double weight = growth.getWeight();
        double amount = Math.min(biteSize, weight);
        weight -= amount;
        if (weight <= 0.) {
            log.debug("{} was completely eaten.", canBeEaten);
            canBeEaten.die();
        }
        growth.setWeight(weight);

        return amount;
    }

    @Override
    public void apply() {

    }
}
