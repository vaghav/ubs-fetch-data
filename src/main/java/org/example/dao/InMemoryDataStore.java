package org.example.dao;

import java.util.HashMap;
import java.util.Map;

// Disclaimer: This implementation is not thread-safe.
public class InMemoryDataStore implements DataStore {

    //TODO: Replace 'Map' data structure with 'EntityData' class for better
    // maintainability and readability.
    private final Map<Integer, String> dataMap = new HashMap<>();

    @Override
    public String getValueById(int id) {
        return dataMap.get(id);
    }

    @Override
    public Map<Integer, String> getAll() {
        return Map.copyOf(dataMap);
    }

    @Override
    public void add(int id, String value) {
        dataMap.put(id, value);
    }
}
