package com.javarush.pavlichenko.entities.abilities;

import com.javarush.pavlichenko.entities.abilities.sideclasses.AbilityParameter;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanBeHungry;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Hunger extends SomeAbility {

    @AbilityParameter
    private Double maxSatiety; // TODO set according game rules

    @AbilityParameter
    private Double hungryRate;

    @AbilityParameter
    private Double satiety;

    public Hunger(CanBeHungry creature) {
        super(creature, Hunger.class);
    }

    @Override
    public void apply() {
        satiety -= hungryRate;

        if (satiety <= 0.) {
            owner.die();
            log.info(marker, "{} died of hunger.", owner);
            return;
        }
        log.info(marker, "{} got hungry (satiety is {} of {}).", owner, satiety, maxSatiety);

    }

    protected boolean isNotHungry() {
        return satiety >= maxSatiety;
    }

    protected void addSatiety(Double foodAmount) {
        satiety = Math.min(maxSatiety, satiety + foodAmount);
        log.info(marker, "{} ate (satiety is {} of {}).", owner, satiety, maxSatiety);
    }

}
