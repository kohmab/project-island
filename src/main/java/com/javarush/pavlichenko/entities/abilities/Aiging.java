package com.javarush.pavlichenko.entities.abilities;

import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanAge;
import com.javarush.pavlichenko.entities.abstr.entitiesmarkers.Herbivore;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Aiging implements Ability {

    private final CanAge entity;
    private final AbilityKey key;

    @Getter
    private Integer age = 0;
    public Aiging(CanAge canAge) {
        this.entity = canAge;
        this.key = AbilityKey.getKeyFor(this);
    }

    @Override
    public void apply() {
        if (age++ == 0){
            log.info("{} was born.", entity);
        } else {
            log.info("{} was aged. Age: {}.", entity, age);
        }
    }
}
