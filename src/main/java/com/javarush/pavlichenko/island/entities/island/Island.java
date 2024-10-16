package com.javarush.pavlichenko.island.entities.island;

import com.javarush.pavlichenko.island.entities.abstr.IslandEntity;
import com.javarush.pavlichenko.island.exceptions.ImpossibleCoordinateException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class Island {

    private Map<Coordinate, Cell> map;

    private final Integer height;
    private final Integer width;

    public Island(Integer height, Integer width) {
        this.height = height;
        this.width = width;
        clear();
        clear();
    }

    public Set<IslandEntity> getAllEntities() {
        Set<IslandEntity> entities = ConcurrentHashMap.newKeySet();
        map.values().forEach((cell -> entities.addAll(cell.getAllEntities())));
        return entities;
    }

    public Cell getCell(Coordinate coordinate) {
        if (!isPossibleCoordinate(coordinate))
            throw new ImpossibleCoordinateException(String.format("Trying get cell at impossible coordinate %s", coordinate));
        return map.get(coordinate);
    }


    public boolean isPossibleCoordinate(Coordinate coordinate) {
        return map.containsKey(coordinate);
    }

    public void clear() {
        this.map = new ConcurrentHashMap<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map.put(new Coordinate(i, j), new Cell());
            }
        }
    }

    public List<IslandEntity> collectAndRemoveDead() {
        List<IslandEntity> result = new ArrayList<>();
        map.values().forEach(cell -> {
            result.addAll(cell.collectAndRemoveDead());
        });
        return result;
    }

    @Override
    public String toString() {
        String[][] islandArray = new String[width][height];
        for (Entry<Coordinate, Cell> entry : map.entrySet()) {
            Coordinate coordinate = entry.getKey();
            Cell cell = entry.getValue();
            int x = coordinate.getX();
            int y = coordinate.getY();
            String str = String.format("%2d", cell.getCreatureCount());
            islandArray[x][y] = str;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String[] row : islandArray) {
            stringBuilder.append(String.join(" ", row)).append("\n");
        }
        map.entrySet().stream()
                .filter((entry) -> !entry.getValue().getEntitiesMap().isEmpty())
                .forEach(
                        (entry) -> stringBuilder.append(entry.getKey())
                                .append(" : ")
                                .append(entry.getValue())
                                .append(";\n"));

        return stringBuilder.toString();


    }
}
