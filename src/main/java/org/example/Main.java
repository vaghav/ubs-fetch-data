package org.example;

import org.example.services.Data;
import org.example.dao.InMemoryDataStore;
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

        // Since IoC container is not used, manual dependency injection is done here.
        DataManager dataManager = new DataManagerImpl(new InMemoryDataStore());

        if (id.isPresent()) {
            printData(format, dataManager, Integer.parseInt(id.get()));
        } else {
            for (Data data : dataManager.getAllData()) {
                printData(format, dataManager, data.id());
            }
        }
    }

    private static void printData(final Optional<String> format,
                                  final DataManager dataManager, final int id) {
        // The assumption is that the default format is JSON if no parameter is passed.
        OutputFormat outputFormat = format.map(OutputFormat::valueOf).orElse(OutputFormat.JSON);
        System.out.println(getFormatterMap().get(outputFormat).format(dataManager.getDataById(id)));
    }

    private static Map<OutputFormat, Formatter> getFormatterMap() {
        Formatter jsonFormater = data -> """ 
                {"id": %d, "name": "%s"}""".formatted(data.id(), data.value());
        Formatter csvFormater = data -> "%d,%s".formatted(data.id(), data.value());
        return Map.of(OutputFormat.JSON, jsonFormater, OutputFormat.CSV, csvFormater);
    }
}