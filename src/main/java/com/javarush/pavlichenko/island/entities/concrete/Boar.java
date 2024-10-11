package com.javarush.pavlichenko.island.entities.concrete;

import com.javarush.pavlichenko.island.entities.abilities.Hunt;
import com.javarush.pavlichenko.island.entities.abstr.SomeHerbivore;
import com.javarush.pavlichenko.island.entities.abstr.abilitymarkers.CanHunt;
import com.javarush.pavlichenko.island.entities.island.Island;

public class Boar extends SomeHerbivore implements CanHunt {

    protected Boar(Island island) {
        super(island);
        new Hunt(this);

    }
}
