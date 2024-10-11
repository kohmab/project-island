package com.javarush.pavlichenko.island.entities.concrete;

import com.javarush.pavlichenko.island.entities.abstr.SomePredator;
import com.javarush.pavlichenko.island.entities.island.Island;

public class Wolf extends SomePredator {

    protected Wolf(Island island) {
        super(island);
    }
}
