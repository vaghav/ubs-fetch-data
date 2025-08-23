package org.example.services;

import org.example.dao.Data;
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
    public Data getDataById(int id) {
        return dataStore.getValueById(id);
    }
}
