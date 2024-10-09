package com.javarush.pavlichenko.service;

import com.javarush.pavlichenko.entities.abstr.IslandEntity;
import com.javarush.pavlichenko.entities.island.Island;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RequiredArgsConstructor
public class DeadEntitiesBiographyCollector {

    private final EntityDiaryAppender appender = EntityDiaryAppender.getInstance();
    private final Map<Class<? extends IslandEntity>, Map<UUID, String>> diaries = new ConcurrentHashMap<>();

    public void collectFor(@NonNull List<IslandEntity> deadEntities, Integer dayNo) {
        for (IslandEntity entity : deadEntities) {
            Class<? extends IslandEntity> aClass = entity.getClass();
            if (!diaries.containsKey(aClass))
                diaries.put(aClass,new ConcurrentHashMap<>());
            UUID id = entity.getId();
            ConcurrentMap<UUID, List<String>> entitiesLifecycleEvents = appender.getEntityLifecycleEvents();
            List<String> biographyItems = entitiesLifecycleEvents.get(id);
            entitiesLifecycleEvents.remove(id);
            String biography = String.format("This story began on day number %d.\n", dayNo) + String.join("\n", biographyItems);
            diaries.get(aClass).put(id,biography);
        }

    }

    public Set<UUID> getIds(Class<? extends IslandEntity> entityClass) {
        return diaries.get(entityClass).keySet();
    }

    public Set<Class<? extends IslandEntity>> getClasses() {
        return diaries.keySet();
    }

    public String getBiography(Class<? extends IslandEntity> entityClass, UUID id) {
        return diaries.get(entityClass).get(id);
    }

    public String getBiography(UUID id) {
        for (Map<UUID, String> value : diaries.values()) {
            if (value.containsKey(id)) {
                return value.get(id);
            }
        }
        return null;
    }


}
