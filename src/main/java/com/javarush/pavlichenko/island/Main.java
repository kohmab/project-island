package com.javarush.pavlichenko.island;

import com.javarush.pavlichenko.island.controller.GameController;
import com.javarush.pavlichenko.island.controller.IslandIterator;
import com.javarush.pavlichenko.island.entities.abstr.IslandEntity;
import com.javarush.pavlichenko.island.entities.island.Island;
import com.javarush.pavlichenko.island.entities.island.parameters.GameSettings;
import com.javarush.pavlichenko.island.service.*;
import com.javarush.pavlichenko.island.view.ConsoleView;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.List;

public class Main {

    private static final Island island = new Island(GameSettings.get().getIslandHeight(), GameSettings.get().getIslandWidth());
    private static final IslandIterator iterator;
    private static final IslandPopulator populator;
    private static final IslandStatsCollector stats;
    private static final DeadEntitiesBiographyCollector biographies;
    private static final GameController controller;

    public static void main(String[] args) {
        populator.populate();
        System.out.println("Game started.");
        while (true) {
            gameStep();
            controller.invoke();
        }
    }

    private static void gameStep() {
        iterator.step();
        List<IslandEntity> dead = island.collectAndRemoveDead();
        biographies.collectFor(dead, iterator.getStepNo());
        stats.collect();
    }

    static {
        IslandEntityCreator.init(island);
        populator = new IslandPopulator(island);

        iterator = new IslandIterator(island);
        stats = new IslandStatsCollector(island);
        biographies = new DeadEntitiesBiographyCollector();

        Terminal terminal;
        try {
            terminal = TerminalBuilder.terminal();
        } catch (IOException var1) {
            throw new RuntimeException(var1);
        }

        ConsoleView view = new ConsoleView(terminal, stats, biographies);
        controller = new GameController(terminal, view);
    }


}