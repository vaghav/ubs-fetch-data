package org.example.services;

import org.example.dao.Data;

import java.util.List;

public interface DataManager {
    List<Data> getAllData();
    Data getDataById(int id);
}
