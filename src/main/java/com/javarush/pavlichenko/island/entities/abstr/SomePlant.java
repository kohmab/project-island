package com.javarush.pavlichenko.island.entities.abstr;

import com.javarush.pavlichenko.island.entities.abilities.Edible;
import com.javarush.pavlichenko.island.entities.abilities.Growth;
import com.javarush.pavlichenko.island.entities.island.Island;
import com.javarush.pavlichenko.island.entities.abstr.entitiesmarkers.Plant;

public abstract class SomePlant extends SomeIslandEntity implements Plant {


    public SomePlant(Island island) {
        super(island);

        new Growth(this);
        new Edible(this);
}

//


}
