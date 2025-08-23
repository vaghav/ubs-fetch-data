package org.example;

import org.example.dao.DataStore;
import org.example.services.DataManager;
import org.example.services.DataManagerImpl;
import org.example.util.DataFormater;
import org.example.util.Formatter;
import org.example.util.OutputFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        Optional<String> id = Optional.empty();
        Optional<String> format = Optional.empty();

        /// //// souce code parsing arguments

        DataManager dataManager = new DataManagerImpl(new DataStore());
        if (id.isPresent()) {
            if (format.isPresent()) {
                Formatter formatter = new DataFormater();
                formatter.format(dataManager.getDataById(Integer.parseInt(id.get())), OutputFormat.valueOf(format.get()));
            } else {
                dataManager.getDataById(Integer.parseInt(id.get()));
            }
        } else {
            if (format.isPresent()) {
                Formatter formatter = new DataFormater();
                List<String> formattedData = new ArrayList<>();
                for (var data : dataManager.getAllData()) {
                    formattedData.add(formatter.format(data, OutputFormat.valueOf(format.get())));
                }
            } else {
                dataManager.getAllData();
            }
        }
    }
}