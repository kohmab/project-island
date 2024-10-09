package com.javarush.pavlichenko.entities.abilities;

import com.javarush.pavlichenko.entities.abilities.sideclasses.AbilityKey;
import com.javarush.pavlichenko.entities.abstr.IslandEntity;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanAge;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Aiging extends SomeAbility {

    @Getter
    private Integer age = 0;

    public Aiging(CanAge canAge) {
        super(canAge, Aiging.class);
    }

    @Override
    public void apply() {
        if (age++ == 0) {
            log.info(marker, "{} was born.", owner);
        } else {
            log.info(marker, "{} was aged. Age: {}.", owner, age);
        }
    }

}
