package com.javarush.pavlichenko.island.entities.concrete;

import com.javarush.pavlichenko.island.entities.abstr.SomeHerbivore;
import com.javarush.pavlichenko.island.entities.island.Island;

public class Sheep extends SomeHerbivore {
    protected Sheep(Island island) {
        super(island);

    }
}
