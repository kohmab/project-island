package com.javarush.pavlichenko.helpers;

import com.javarush.pavlichenko.entities.creatures.TestAnimal;
import com.javarush.pavlichenko.entities.island.Cell;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;
import com.javarush.pavlichenko.enums.Gender;
import com.javarush.pavlichenko.exceptions.CellIsFilledException;
import com.javarush.pavlichenko.exceptions.NoAvailiableCellsException;
import com.javarush.pavlichenko.interfaces.IslandEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.isNull;

@Slf4j
@RequiredArgsConstructor

public class TestIslandPopulator implements IslandPopulator {
    private static final int MAX_ANIMALS = 30;

    private final Island island;

    private Set<Coordinate> availiableCoordinates;


    @Override
    public void populateIsland() {
        island.refresh();
        availiableCoordinates = new HashSet<>(island.getMap().keySet());

        for (int i = 0; i < MAX_ANIMALS; i++) {
            Gender sex = Gender.values()[(int) (Math.random() * Gender.values().length)];
            Coordinate coordinate;
            try {
                coordinate = getRandomCoordinate();
            } catch (NoAvailiableCellsException e) {
                log.error("No available cells for creating animal");
                return;
            }
            TestAnimal testAnimal = new TestAnimal(sex, island, coordinate);
            try {
                putEntity(testAnimal);
                log.info("Created animal: {}", testAnimal);
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
        if (cell.isFilled()) {
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



