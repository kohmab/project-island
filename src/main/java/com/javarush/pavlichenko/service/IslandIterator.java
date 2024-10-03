package com.javarush.pavlichenko.service;

import com.javarush.pavlichenko.entities.abstr.IslandEntity;
import com.javarush.pavlichenko.entities.island.Island;
import com.javarush.pavlichenko.entities.island.parameters.GameSettings;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.LoggerContext;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class IslandIterator {

    private final IslandHistory history = IslandHistory.getInstance();

    private final Island island;
    private final ExecutorService executor;
    private int stepNo = 0;

    public void step() {
        history.clear();
        log.info("<<<<<<<\t iteration #{} started \t>>>>>>>", ++stepNo);
        Set<IslandEntity> allEntities = island.getAllEntities();
        List<Future<IslandEntity>> futures;
        try {
            futures = executor.invokeAll(allEntities);
        } catch (InterruptedException e) {
            log.error("Error in main cycle. {}", e.toString());
            throw new RuntimeException(e);
        }
        try {
            for (Future<IslandEntity> future : futures)
                future.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error while receiving entity from future after lifecycle");
            throw new RuntimeException(e);
        }
        log.info("<<<<<<<\t all entities performed lifecycle \t>>>>>>>");
        log.info("<<<<<<<\t iteration #{} finished \t>>>>>>>", stepNo);

    }

    public void stop() {
        executor.shutdown();
    }

    public IslandIterator(Island island) {
        this.island = island;
        this.executor = Executors.newFixedThreadPool(GameSettings.get().getThreadsCount());



    }


}
