package com.javarush.pavlichenko.island.entities.concrete;

import com.javarush.pavlichenko.island.entities.abstr.SomeHerbivore;
import com.javarush.pavlichenko.island.entities.island.Island;

public class Caterpillar extends SomeHerbivore {
    protected Caterpillar(Island island) {
        super(island);

    }

}
