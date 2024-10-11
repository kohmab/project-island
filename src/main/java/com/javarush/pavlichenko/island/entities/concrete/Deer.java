package com.javarush.pavlichenko.island.entities.concrete;

import com.javarush.pavlichenko.island.entities.abstr.SomeHerbivore;
import com.javarush.pavlichenko.island.entities.island.Island;

public class Deer extends SomeHerbivore {
    protected Deer(Island island) {
        super(island);

    }

}
