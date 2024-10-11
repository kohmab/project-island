package com.javarush.pavlichenko.island.entities.concrete;

import com.javarush.pavlichenko.island.entities.abstr.SomeHerbivore;
import com.javarush.pavlichenko.island.entities.island.Island;

public class Rabbit extends SomeHerbivore {
    protected Rabbit(Island island) {
        super(island);
    }
}
