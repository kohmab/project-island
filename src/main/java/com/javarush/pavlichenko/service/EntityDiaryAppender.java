package com.javarush.pavlichenko.service;

import com.javarush.pavlichenko.entities.abstr.IslandEntity;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Plugin(
        name = "EntityDiary",
        category = Core.CATEGORY_NAME,
        elementType = Appender.ELEMENT_TYPE)
public class EntityDiaryAppender extends AbstractAppender {

    @Getter
    private static EntityDiaryAppender instance;

    @Getter
    private ConcurrentMap<UUID, List<String>> entityLifecycleEvents = new ConcurrentHashMap<>();

    protected EntityDiaryAppender(String name, Filter filter) {
        super(name, filter, null);
    }

    @PluginFactory
    public static EntityDiaryAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Filter") Filter filter) {
        instance = new EntityDiaryAppender(name, filter);
        return instance;
    }


    @Override
    public void append(LogEvent event) {
        String message = event.getMessage().getFormattedMessage();

        Object[] parameters = event.getMessage().getParameters();

        if (parameters.length > 0 && parameters[0] instanceof IslandEntity) {
            IslandEntity entity = (IslandEntity) parameters[0];
            UUID id = entity.getId();
            if (!entityLifecycleEvents.containsKey(id))
                entityLifecycleEvents.put(id, new ArrayList<>());
            else {
                message = message
                        .replace(entity + "'s","")
                        .replace(entity.toString(), "")
                        .replaceAll("( )+", " ")
                        .trim();
                message = StringUtils.capitalize(message);
            }

            entityLifecycleEvents.get(id).add(message);
        }
    }
}
