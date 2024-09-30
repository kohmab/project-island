package com.javarush.pavlichenko.entities.abilities;

import com.javarush.pavlichenko.entities.abilities.parameters.AbilityParameter;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanGrow;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Growth implements Ability {

    private final CanGrow growable;
    private final AbilityKey key;

    @AbilityParameter
    private Double maxWeight;

    @AbilityParameter
    private Double growthRate;

    @AbilityParameter
    @Setter
    private Double weight;

    @Override
    public void apply() {
            weight = Math.min(maxWeight, weight + growthRate);
            log.debug("{} grow (weight is {} of {}).", growable, weight, maxWeight);

    }



    public Growth(CanGrow growable) {
        this.growable = growable;
        this.key = AbilityKey.getKeyFor(this);
    }

}
