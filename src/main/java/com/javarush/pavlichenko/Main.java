package com.javarush.pavlichenko;

import com.javarush.pavlichenko.entities.island.Island;
import com.javarush.pavlichenko.service.IslandHistory;
import com.javarush.pavlichenko.service.IslandIterator;
import com.javarush.pavlichenko.entities.island.parameters.GameSettings;
import com.javarush.pavlichenko.service.IslandEntityCreator;
import com.javarush.pavlichenko.service.IslandPopulator;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Slf4j
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Island island = new Island(GameSettings.get().getIslandHeight(), GameSettings.get().getIslandWidth());

        IslandEntityCreator.init(island);

        IslandPopulator islandPopulator = new IslandPopulator(island);

        islandPopulator.populate();

        IslandIterator islandIterator = new IslandIterator(island);

        for (int i = 0; i < 5; i++) {
            islandIterator.step();
            System.out.println(i);
        }


        ConcurrentMap<UUID, List<String>> entityLifecycleEvents = IslandHistory.getInstance().getEntityLifecycleEvents();
        List<List<String>> lists = entityLifecycleEvents.values().stream().sorted((o1, o2) -> {
            Integer l1 = o1.size();
            Integer l2 = o2.size();
            return l2.compareTo(l1);
        }).toList();

        System.out.println(lists.get(lists.size() / 2));
        islandIterator.stop();
    }


}