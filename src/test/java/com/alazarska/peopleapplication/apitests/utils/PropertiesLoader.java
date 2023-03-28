package com.alazarska.peopleapplication.apitests.utils;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesLoader {

    @SneakyThrows
    public static String getPropertyValue(String propertyName) {
        String environmentVariableName = propertyName.toUpperCase().replace(".", "_");
        String environmentVariableValue = System.getenv(environmentVariableName);

        if (environmentVariableValue != null) {
            return environmentVariableValue;
        } else {
            FileInputStream configFile = new FileInputStream("src/test/resources/config.properties");
            Properties properties = new Properties();
            properties.load(configFile);
            String propertyValue = properties.getProperty(propertyName);
            if (propertyValue != null) {
                return propertyValue;
            } else {
                throw new PropertyNotFound("Property [" + propertyName + "] not found");
            }
        }
    }

    static public class PropertyNotFound extends RuntimeException {
        public PropertyNotFound(String message) {
            super(message);
        }
    }
}

