package com.javarush.pavlichenko.island.entities.concrete;

import com.javarush.pavlichenko.island.entities.abstr.SomePredator;
import com.javarush.pavlichenko.island.entities.island.Island;

public class Eagle extends SomePredator {
    protected Eagle(Island island) {
        super(island);

    }

}
