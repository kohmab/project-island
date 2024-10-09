package com.javarush.pavlichenko.entities.concrete;

import com.javarush.pavlichenko.entities.abstr.SomeAnimal;
import com.javarush.pavlichenko.entities.abstr.SomePredator;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;

public class Eagle extends SomePredator {
    protected Eagle(Island island) {
        super(island);

    }

}
