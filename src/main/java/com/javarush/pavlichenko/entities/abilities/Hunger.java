package com.javarush.pavlichenko.entities.abilities;

import com.javarush.pavlichenko.entities.abilities.parameters.AbilityParameter;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanBeHungry;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Hunger implements Ability {
    private final CanBeHungry creature;
    private final AbilityKey key;

    @AbilityParameter
    private Double maxSatiety; // TODO set according game rules

    @AbilityParameter
    private Double hungryRate;

    @AbilityParameter
    private Double satiety;

    public Hunger(CanBeHungry creature) {
        this.creature = creature;
        this.key = AbilityKey.getKeyFor(this);
    }

    @Override
    public void apply() {
        satiety -= hungryRate;

        if (satiety <= 0.) {
            creature.die();
            log.info("{} died of hunger", creature);
            return;
        }
        log.debug("{} get hungry (satiety is {} of {}).", creature, satiety, maxSatiety);

    }

    public boolean isHungry() {
        return satiety < maxSatiety;
    }

    public void addSatiety(Double foodAmount) {
        satiety = Math.min(maxSatiety, satiety + foodAmount);
        log.debug("{} ate (satiety is {} of {}).", creature, satiety, maxSatiety);
    }

}
