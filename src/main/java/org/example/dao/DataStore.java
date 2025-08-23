package org.example.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DataStore {

    private Map<Integer, String> dataMap = new HashMap<>();

    public Data getValueById(int id) {
        return new Data(id, dataMap.get(id));
    }

    public Map<Integer, String> getAll() {
        return Map.copyOf(dataMap);
    }

    public void putValue(int id, String value) {
        dataMap.put(id, value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DataStore dataStore = (DataStore) o;
        return Objects.equals(dataMap, dataStore.dataMap);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dataMap);
    }
}
