package org.example;

import org.example.dao.Data;
import org.example.dao.DataStore;
import org.example.services.DataManager;
import org.example.services.DataManagerImpl;
import org.example.util.Formatter;
import org.example.util.OutputFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Main {

    private static final String ARG_KEY_PREFIX = "-";
    private static final String OUTPUT_ARG_KEY = "-o";
    private static final String ID_ARG_KEY = "-id";

    public static void main(String[] args) {
        Optional<String> id = Optional.empty();
        Optional<String> format = Optional.empty();

        // parsing arguments
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case ID_ARG_KEY: {
                    if (i + 1 < args.length && !args[i + 1].startsWith(ARG_KEY_PREFIX)) {
                        id = Optional.of(args[i + 1]);
                    } else {
                        System.err.println("Error: Missing value for argument " + ID_ARG_KEY);
                        return;
                    }
                    break;
                }
                case OUTPUT_ARG_KEY: {
                    if (i + 1 < args.length && !args[i + 1].startsWith(ARG_KEY_PREFIX)) {
                        format = Optional.of(args[i + 1]);
                    } else {
                        System.err.println("Error: Missing value for argument " + OUTPUT_ARG_KEY);
                        return;
                    }
                    break;
                }
                default:
                    break;
            }
        }

        var formatterMap = getFormatterMap();
        DataManager dataManager = new DataManagerImpl(getDataStore());

        if (id.isPresent()) {
            System.out.println(formatterMap.get(OutputFormat.valueOf(format.get())).format(dataManager.getDataById(Integer.parseInt(id.get()))));
        } else {
            for (Data data : dataManager.getAllData()) {
                System.out.println(formatterMap.get(OutputFormat.valueOf(format.get())).format(dataManager.getDataById(data.id())));
            }
        }
    }

    private static Map<OutputFormat, Formatter> getFormatterMap() {
        Formatter jsonFormater = data -> """ 
                {"id": %d, "name": "%s"}""".formatted(data.id(), data.value());
        Formatter csvFormater = data -> "%d,%s".formatted(data.id(), data.value());
        Formatter defaultFormater = Record::toString;

        Map<OutputFormat, Formatter> formatterMap = new HashMap<>();
        formatterMap.put(null, defaultFormater);
        formatterMap.put(OutputFormat.JSON, jsonFormater);
        formatterMap.put(OutputFormat.CSV, csvFormater);
        return formatterMap;
    }

    private static DataStore getDataStore() {
        final var dataStore = new DataStore();
        dataStore.add(1, "Alice");
        dataStore.add(2, "Anna");
        dataStore.add(3, "Arev");
        return dataStore;
    }
}