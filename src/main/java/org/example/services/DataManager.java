package org.example.services;

import java.util.List;

public interface DataManager {
    List<Data> getAllData();
    Data getDataById(final int id);
}
