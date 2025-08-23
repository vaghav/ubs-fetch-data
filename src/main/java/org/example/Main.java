package org.example;

import org.example.dao.Data;
import org.example.dao.DataStore;
import org.example.services.DataManager;
import org.example.services.DataManagerImpl;
import org.example.util.Formatter;
import org.example.util.OutputFormat;

import java.util.Map;
import java.util.Optional;

public class Main {

    private static final String ARG_KEY_PREFIX = "-";
    private static final String OUTPUT_ARG_KEY = "-o";
    private static final String ID_ARG_KEY = "-id";

    public static void main(String[] args) {
        Optional<String> id = Optional.empty();
        Optional<String> format = Optional.empty();

        /// //// souce code parsing arguments
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

        Formatter jsonFormater = data -> """ 
                {"id": %d, "name": "%s"}""".formatted(data.id(), data.value());
        Formatter csvFormater = data -> "%d,%s".formatted(data.id(), data.value());
        Formatter defaultFormater = Record::toString;

        Map<OutputFormat, Formatter> formatterMap = Map.of(null, defaultFormater, OutputFormat.JSON, jsonFormater, OutputFormat.CSV, csvFormater);

        DataManager dataManager = new DataManagerImpl(new DataStore());
        if (id.isPresent()) {
            System.out.println(formatterMap.get(OutputFormat.valueOf(format.get())).format(dataManager.getDataById(Integer.parseInt(id.get()))));
        } else {
            for (Data data : dataManager.getAllData()) {
                System.out.println(formatterMap.get(OutputFormat.valueOf(format.get())).format(dataManager.getDataById(data.id())));
            }
        }
    }
}