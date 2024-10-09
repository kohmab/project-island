package com.javarush.pavlichenko.entities.concrete;

import com.javarush.pavlichenko.entities.abilities.Prey;
import com.javarush.pavlichenko.entities.abstr.SomePredator;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanBeCached;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;

public class Fox extends SomePredator implements CanBeCached {

    protected Fox(Island island) {
        super(island);
        new Prey(this);
    }
}
