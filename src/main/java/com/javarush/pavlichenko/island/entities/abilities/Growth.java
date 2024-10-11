package com.javarush.pavlichenko.island.entities.abilities;

import com.javarush.pavlichenko.island.entities.abilities.sideclasses.AbilityParameter;
import com.javarush.pavlichenko.island.entities.abstr.abilitymarkers.CanGrow;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Growth extends SomeAbility {

    @AbilityParameter
    private Double maxWeight;

    @AbilityParameter
    private Double growthRate;

    @AbilityParameter
    private Double weight;

    @Override
    public void apply() {
            weight = Math.min(maxWeight, weight + growthRate);
            log.info(marker, "{} grew (weight is {} of {}).", owner, weight, maxWeight);
    }

    protected void setWeight(Double weight) {
        this.weight = weight;
    }

    public Growth(CanGrow growable) {
        super(growable, Growth.class);
    }

}
