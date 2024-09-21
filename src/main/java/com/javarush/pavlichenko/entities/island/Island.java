package com.javarush.pavlichenko.entities.island;

import com.javarush.pavlichenko.interfaces.IslandEntity;
import lombok.Getter;

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
        refresh();
    }

    public Set<IslandEntity> getAllEntities() {
        // TODO thread Safety
        Set<IslandEntity> entities = ConcurrentHashMap.newKeySet();
        for (Cell cell : map.values()) {
            entities.addAll(cell.getEntities());

        }
        return entities;
    }

    public Cell getCell(Coordinate coordinate) {
        return map.get(coordinate);
    }

    public boolean isForbiddenCoordinate(Coordinate coordinate) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        boolean outOfBounds = x >= this.width || y >= this.height || x < 0 || y < 0;
        if (outOfBounds)
            return true;

//        boolean cellIsFilled = map.get(coordinate).isFilled();
//        return cellIsFilled;
        return false;
    }

    public void refresh() {
        this.map = new ConcurrentHashMap<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map.put(new Coordinate(i, j), new Cell(2)); // @TODO: add capacity according to the game rules
            }
        }
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
                .filter((entry) -> !entry.getValue().getEntities().isEmpty())
                .forEach(
                        (entry) -> stringBuilder.append(entry.getKey())
                                .append(" : ")
                                .append(entry.getValue())
                                .append(";\n"));

        return stringBuilder.toString();


    }
}
