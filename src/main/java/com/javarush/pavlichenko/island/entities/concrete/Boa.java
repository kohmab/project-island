package com.javarush.pavlichenko.island.entities.concrete;

import com.javarush.pavlichenko.island.entities.abilities.Prey;
import com.javarush.pavlichenko.island.entities.abstr.SomePredator;
import com.javarush.pavlichenko.island.entities.abstr.abilitymarkers.CanBeCached;
import com.javarush.pavlichenko.island.entities.island.Island;

public class Boa extends SomePredator implements CanBeCached {

    protected Boa(Island island) {
        super(island);
        new Prey(this);
    }
}
