package com.javarush.pavlichenko.entities.concrete;

import com.javarush.pavlichenko.entities.abilities.AbilityKey;
import com.javarush.pavlichenko.entities.abilities.Hunger;
import com.javarush.pavlichenko.entities.abilities.Hunt;
import com.javarush.pavlichenko.entities.abilities.Movement;
import com.javarush.pavlichenko.entities.abstr.SomeHerbivore;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanHunt;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;

public class Caterpillar extends SomeHerbivore {

    public Caterpillar(Island island, Coordinate coordinate) {
        super(island, coordinate);

    }
}
