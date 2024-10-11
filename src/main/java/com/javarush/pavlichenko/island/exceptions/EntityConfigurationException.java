package com.javarush.pavlichenko.island.exceptions;

public class EntityConfigurationException extends RuntimeException {
    public EntityConfigurationException() {
    }

    public EntityConfigurationException(String message) {
        super(message);

    }

    public EntityConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityConfigurationException(Throwable cause) {
        super(cause);
    }
}
