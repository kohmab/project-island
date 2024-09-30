package com.javarush.pavlichenko;

import com.javarush.pavlichenko.entities.concrete.TestPrey;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;
import com.javarush.pavlichenko.entities.island.IslandIterator;
import com.javarush.pavlichenko.entities.islandentitycreator.IslandEntityCreator;
import com.javarush.pavlichenko.exceptions.CellIsFilledException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) throws InterruptedException {
        log.info("<<<<<<<<<<< START >>>>>>>>>>>>");

        Island island = new Island(3, 4);

        IslandEntityCreator.init(island);
        IslandEntityCreator islandEntityCreator = IslandEntityCreator.getInstance();
        try {
            islandEntityCreator.create(TestPrey.class, new Coordinate(0, 0));
        } catch (CellIsFilledException e) {
            throw new RuntimeException(e);
        }


        IslandIterator islandIterator = new IslandIterator(island);

        for (int i = 0; i < 10; i++) {
            islandIterator.step();
        }

        islandIterator.stop();
    }
}