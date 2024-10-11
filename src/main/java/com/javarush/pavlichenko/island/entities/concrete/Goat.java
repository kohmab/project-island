package com.javarush.pavlichenko.island.entities.concrete;

import com.javarush.pavlichenko.island.entities.abstr.SomeHerbivore;
import com.javarush.pavlichenko.island.entities.island.Island;

public class Goat extends SomeHerbivore {
    protected Goat(Island island) {
        super(island);
    }
}
