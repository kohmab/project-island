package com.javarush.pavlichenko.helpers;

import com.javarush.pavlichenko.entities.abstr.IslandEntity;
import com.javarush.pavlichenko.entities.island.parameters.GameSettings;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Helpers {

    private static Map<String, Class<? extends IslandEntity>> classMap;
    public static <T> T getRandom(Collection<T> collection) {
        int size = collection.size();
        Optional<T> random = collection.stream().skip(ThreadLocalRandom.current().nextInt(size)).findFirst();
        return random.orElse(null);
    }

    public static Class<? extends IslandEntity> getClassNamed(String name){
        return classMap.get(name);
    }

    public static Set<String> getClassNames(){
        return classMap.keySet();
    }

    static {
        classMap = new HashMap<>();
        GameSettings.get().getInitialCreaturesCount().keySet()
                .forEach(
                        aClass -> classMap.put(aClass.getSimpleName(), aClass)
                );
    }
}
