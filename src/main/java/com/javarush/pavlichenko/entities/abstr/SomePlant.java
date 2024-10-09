package com.javarush.pavlichenko.entities.abstr;

import com.javarush.pavlichenko.entities.abilities.Edible;
import com.javarush.pavlichenko.entities.abilities.Growth;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;
import com.javarush.pavlichenko.entities.abstr.entitiesmarkers.Plant;

public abstract class SomePlant extends SomeIslandEntity implements Plant {


    public SomePlant(Island island) {
        super(island);

        new Growth(this);
        new Edible(this);
}

//


}
