package com.javarush.pavlichenko.entities.concrete;

import com.javarush.pavlichenko.entities.abilities.Hunger;
import com.javarush.pavlichenko.entities.abilities.Hunt;
import com.javarush.pavlichenko.entities.abstr.SomeHerbivore;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanHunt;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;

public class Duck extends SomeHerbivore implements CanHunt {
    
    public Duck(Island island, Coordinate coordinate) {
        super(island, coordinate);
        Hunt hunt = new Hunt(this);
        this.addAbility(hunt);
    }
}
