package com.javarush.pavlichenko.entities.concrete;

import com.javarush.pavlichenko.entities.abilities.Hunger;
import com.javarush.pavlichenko.entities.abilities.Hunt;
import com.javarush.pavlichenko.entities.abstr.SomeHerbivore;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanHunt;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;

public class Duck extends SomeHerbivore implements CanHunt {

    protected Duck(Island island) {
        super(island);

        new Hunt(this);
    }
}
