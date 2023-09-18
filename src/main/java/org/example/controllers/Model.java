package org.example.controllers;

import java.util.HashMap;
import java.util.Map;

public class Model {

    private final Map<String, Object> attributes = new HashMap<>();

    public void addAttribute(String attributeName, Object attributeValue) {
        attributes.put(attributeName, attributeValue);
    }

    public Object getAttribute(String attributeName) {
        return attributes.get(attributeName);
    }

    // Optionally, you can add methods to check for attribute existence or remove attributes if needed.

    public boolean containsAttribute(String attributeName) {
        return attributes.containsKey(attributeName);
    }

    public void removeAttribute(String attributeName) {
        attributes.remove(attributeName);
    }
}