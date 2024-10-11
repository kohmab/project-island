package com.javarush.pavlichenko.island.entities.concrete;

import com.javarush.pavlichenko.island.entities.abstr.SomeHerbivore;
import com.javarush.pavlichenko.island.entities.island.Island;

public class Buffalo extends SomeHerbivore {
    protected Buffalo(Island island) {
        super(island);

    }
}
