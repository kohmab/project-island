package com.javarush.pavlichenko.interfaces.abilities;

import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.interfaces.IslandEntity;

public interface CanMove extends IslandEntity{
    void moveTo(Coordinate coordinate);
}
