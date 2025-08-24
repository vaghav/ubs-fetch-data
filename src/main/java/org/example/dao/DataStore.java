package org.example.dao;

import java.util.Map;

public interface DataStore {
    String getValueById(final int id);

    Map<Integer, String> getAll();

    void add(final int id, final String value);
}
