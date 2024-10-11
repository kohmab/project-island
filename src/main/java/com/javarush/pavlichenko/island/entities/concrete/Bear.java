package com.javarush.pavlichenko.island.entities.concrete;

import com.javarush.pavlichenko.island.entities.abstr.SomePredator;
import com.javarush.pavlichenko.island.entities.island.Island;

public class Bear extends SomePredator {
    protected Bear(Island island) {
        super(island);
    }
}
