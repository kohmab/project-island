package com.javarush.pavlichenko.service;

import com.javarush.pavlichenko.entities.abstr.IslandEntity;
import com.javarush.pavlichenko.entities.abstr.SomeIslandEntity;
import lombok.Getter;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Plugin(
        name = "IslandHistory",
        category = Core.CATEGORY_NAME,
        elementType = Appender.ELEMENT_TYPE)
public class IslandHistory extends AbstractAppender {

    @Getter
    private static IslandHistory instance;

    private ConcurrentMap<Integer, String> allEvents = new ConcurrentHashMap<>();
    @Getter
    private ConcurrentMap<UUID, List<String>> entityLifecycleEvents = new ConcurrentHashMap<>();
    private AtomicInteger messageCount = new AtomicInteger(0);

    public synchronized void clear(){
        allEvents = new ConcurrentHashMap<>();
        messageCount = new AtomicInteger(0);
    }

    protected IslandHistory(String name, Filter filter, Layout<? extends Serializable> layout) {
        super(name, filter, layout);
    }

    @PluginFactory
    public static IslandHistory createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Filter") Filter filter,
            @PluginElement("PatternLayout") Layout<? extends Serializable>  layout) {
        instance = new IslandHistory(name, filter, layout);
        instance.clear();
        return instance;
    }


    @Override
    public void append(LogEvent event) {
        String message = event.getMessage().getFormattedMessage();
        allEvents.put(messageCount.incrementAndGet(), message);
        Object[] parameters = event.getMessage().getParameters();
        if (parameters.length > 0 && parameters[0] instanceof IslandEntity){
            IslandEntity entity = (IslandEntity) parameters[0];
            UUID id = entity.getId();
            if (!entityLifecycleEvents.containsKey(id))
                entityLifecycleEvents.put(id, new ArrayList<>());
            else {
                message = message.replace(entity.toString(),"")
                        .replaceAll("( )+", " ")
                        .trim();
                message = message.substring(0,1).toUpperCase() + message.substring(1);
            }
            entityLifecycleEvents.get(id).add(message);
        }
    }
}
