package com.javarush.pavlichenko.helpers;

import com.javarush.pavlichenko.entities.concrete.TestAnimal;
import com.javarush.pavlichenko.entities.concrete.TestPlant;
import com.javarush.pavlichenko.entities.concrete.TestPrey;
import com.javarush.pavlichenko.entities.island.Cell;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;
import com.javarush.pavlichenko.exceptions.CellIsFilledException;
import com.javarush.pavlichenko.exceptions.NoAvailiableCellsException;
import com.javarush.pavlichenko.entities.abstr.IslandEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor

public class TestIslandPopulator implements IslandPopulator {
    private static final int MAX_ANIMALS = 1;
    private static final int MAX_PREYS = 1;
    private static final int MAX_PLANTS = 1;

    private final Island island;

    private Set<Coordinate> availiableCoordinates;


    @Override
    public void populateIsland() {
        island.refresh();
        availiableCoordinates = new HashSet<>(island.getMap().keySet());

        for (int i = 0; i < MAX_ANIMALS; i++) {
            Coordinate coordinate;
            try {
                coordinate = getRandomCoordinate();
            } catch (NoAvailiableCellsException e) {
                log.error("No available cells for creating animal");
                return;
            }
            TestAnimal testAnimal = new TestAnimal(island, coordinate);
            try {
                putEntity(testAnimal);
                log.info("Created animal: {}", testAnimal);
            } catch (CellIsFilledException e) {
                log.error("Error while trying to put entity in cell", e);
                throw new RuntimeException(e);
            }
        }

        for (int i = 0; i < MAX_PREYS; i++) {
            Coordinate coordinate;
            try {
                coordinate = getRandomCoordinate();
            } catch (NoAvailiableCellsException e) {
                log.error("No available cells for creating animal");
                return;
            }
            TestPrey testAnimal = new TestPrey(island, coordinate);
            try {
                putEntity(testAnimal);
                log.info("Created animal: {}", testAnimal);
            } catch (CellIsFilledException e) {
                log.error("Error while trying to put entity in cell", e);
                throw new RuntimeException(e);
            }
        }

        for (int i = 0; i < MAX_PLANTS; i++) {
            Coordinate coordinate;
            try {
                coordinate = getRandomCoordinate();
            } catch (NoAvailiableCellsException e) {
                log.error("No available cells for creating animal");
                return;
            }
            TestPlant testAnimal = new TestPlant(island, coordinate);
            try {
                putEntity(testAnimal);
                log.info("Created plant: {}", testAnimal);
            } catch (CellIsFilledException e) {
                log.error("Error while trying to put entity in cell", e);
                throw new RuntimeException(e);
            }
        }
    }

    private void putEntity(IslandEntity entity) throws CellIsFilledException {
        Coordinate coordinate = entity.getCoordinate();
        Cell cell = island.getCell(coordinate);
        cell.put(entity);
        if (cell.isFilledFor(entity)) {
            availiableCoordinates.remove(coordinate);
        }

    }

    private Coordinate getRandomCoordinate() throws NoAvailiableCellsException {
        Optional<Coordinate> randomCoordinate = availiableCoordinates.stream()
                .skip((int) (availiableCoordinates.size() * Math.random()))
                .findFirst();
        if (randomCoordinate.isEmpty())
            throw new NoAvailiableCellsException();
        return randomCoordinate.get();
    }

}



