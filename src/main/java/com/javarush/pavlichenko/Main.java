package com.javarush.pavlichenko;

import com.javarush.pavlichenko.entities.island.Island;
import com.javarush.pavlichenko.helpers.TestIslandPopulator;
import com.javarush.pavlichenko.helpers.IslandPopulator;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;
@Slf4j
public class Main {
    public static void main(String[] args) throws InterruptedException {
        log.info("<<<<<<<<<<< START >>>>>>>>>>>>");
        Island island = new Island(5, 5);

        IslandPopulator islandPopulator = new TestIslandPopulator(island);
        islandPopulator.populateIsland();
        System.out.println("AFTER POPULATION");
        System.out.println(island);
        System.out.println("\n\n");


        ExecutorService executor = Executors.newFixedThreadPool(2);
        List<Future<Boolean>> futures = executor.invokeAll(island.getAllEntities());
        executor.shutdown();

        System.out.println("AFTER ONE STEP");
        System.out.println(island);
        System.out.println("\n\n");

    }
}