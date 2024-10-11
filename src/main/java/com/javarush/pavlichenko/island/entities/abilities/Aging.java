package com.javarush.pavlichenko.island.entities.abilities;

import com.javarush.pavlichenko.island.entities.abilities.sideclasses.AbilityParameter;
import com.javarush.pavlichenko.island.entities.abstr.abilitymarkers.CanAge;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Getter
public class Aging extends SomeAbility {

    @Getter
    private Integer age = 0;

    @AbilityParameter
    private Integer oldAge;

    private boolean isOldAge = false;

    public Aging(CanAge canAge) {
        super(canAge, Aging.class);
    }

    @Override
    public void apply() {
        if (age++ == 0) {
            log.info(marker, "{} was born.", owner);
        } else {
            log.info(marker, "{} was aged. Age: {}.", owner, age);
        }

        if (age == oldAge ){
            log.info(marker, "{} reached old age.", owner);
            isOldAge = true;
        }

        if (isOldAge){
            deathFromOldAge();
        }
    }

    private void deathFromOldAge(){
        if (ThreadLocalRandom.current().nextDouble() > 0.6){
            log.info(marker,"{} died of natural causes.",owner);
            owner.die();
        }
    }


}
