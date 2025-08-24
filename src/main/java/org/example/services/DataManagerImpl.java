package org.example.services;

import org.example.dao.DataStore;

import java.util.List;

public class DataManagerImpl implements DataManager {

    private final DataStore dataStore;

    public DataManagerImpl(final DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public List<Data> getAllData() {
        return dataStore.getAll().entrySet().stream()
                .map(entry -> new Data(entry.getKey(), entry.getValue()))
                .toList();
    }

    @Override
    public Data getDataById(final int id) {
        final var value = dataStore.getValueById(id);
        if (value == null) {
            // TODO: Clarify the case when value is 'null', as it's currently based on assumption.
            throw new IllegalArgumentException("No data found for id: " + id);
        }
        return new Data(id, value);
    }
}
