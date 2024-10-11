package com.javarush.pavlichenko.view;

import com.javarush.pavlichenko.entities.abstr.IslandEntity;
import com.javarush.pavlichenko.entities.abstr.entitiesmarkers.Herbivore;
import com.javarush.pavlichenko.entities.abstr.entitiesmarkers.Plant;
import com.javarush.pavlichenko.entities.abstr.entitiesmarkers.Predator;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.parameters.CellCapacity;
import com.javarush.pavlichenko.entities.island.parameters.GameSettings;
import com.javarush.pavlichenko.service.DeadEntitiesBiographyCollector;
import com.javarush.pavlichenko.service.IslandStatsCollector;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jline.terminal.Terminal;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.javarush.pavlichenko.helpers.Helpers.getRandom;

@RequiredArgsConstructor
public class ConsoleView {

    private final Terminal terminal;
    private final IslandStatsCollector stats;
    private final DeadEntitiesBiographyCollector biographies;

    public void printCountOf(Class<? extends IslandEntity> entityClass) {
        Map<Coordinate, Integer> countOf = stats.getCountOf(entityClass);
        String[][] numbers = new String[GameSettings.get().getIslandHeight()][GameSettings.get().getIslandWidth()];
        int maxStrLen = CellCapacity.getCapacityMap().get(entityClass).toString().length();
        countOf.forEach((key, value) -> {
            int x = key.getX();
            int y = key.getY();
            String s = StringUtils.center(value.toString(), maxStrLen + 2);
            numbers[y][x] = s;
        });
        List<String> rows = new ArrayList<>();
        for (String[] r : numbers) {
            rows.add(String.join("", r));
        }
        String result = String.join("\n\n", rows);
        terminal.writer().println("Distribution of %ss on the island.\n%n".formatted(entityClass.getSimpleName()));
        terminal.writer().println(result);
        terminal.writer().println("\n");
    }

    public void printDayStats() {
        String TEMPLATE = "Days left: %d. There are %d plants, %d herbivores and %d predators on the island.";
        Map<Class<? extends IslandEntity>, Integer> totalCount = stats.getTotalCount();
        AtomicInteger herbivores = new AtomicInteger();
        AtomicInteger plants = new AtomicInteger();
        AtomicInteger predators = new AtomicInteger();
        totalCount.forEach((clazz, count) -> {
            if (Herbivore.class.isAssignableFrom(clazz))
                herbivores.addAndGet(count);
            if (Plant.class.isAssignableFrom(clazz))
                plants.addAndGet(count);
            if (Predator.class.isAssignableFrom(clazz))
                predators.addAndGet(count);
        });
        String message = TEMPLATE.formatted(stats.getDayCount(), plants.get(), herbivores.get(), predators.get());
        terminal.writer().println(message);
    }

    public void printTotalCount() {
        Map<Class<? extends IslandEntity>, Integer> totalCount = stats.getTotalCount();
        final String TEMPLATE = "%s - %d;\n";
        StringBuilder sb = new StringBuilder("Entities count:\n");
        totalCount.forEach((key, value) -> sb.append(TEMPLATE.formatted(key.getSimpleName(), value)));
        terminal.writer().println(sb);
    }

    public void printRandomBiography() {
        Set<Class<? extends IslandEntity>> classes = biographies.getClasses();
        Class<? extends IslandEntity> randomClass;
        try {
            randomClass = getRandom(classes);
        } catch (IllegalArgumentException e) {
            terminal.writer().println("No stories currently available.");
            return;
        }

        UUID randomId = getRandom(biographies.getIds(randomClass));

        String biography = biographies.getBiography(randomClass, randomId);

        terminal.writer().println(biography);
    }


}
