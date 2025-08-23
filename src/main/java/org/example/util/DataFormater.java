package org.example.util;

import org.example.dao.Data;

public class DataFormater implements Formatter {
    @Override
    public String format(Data data, OutputFormat outputFormat) {
        if (data == null) {
            throw new IllegalArgumentException("data can't be null");
        }

        switch (outputFormat) {
            case JSON -> {
                return "{\"id\": %d, \"name\": \"%s\"}".formatted(data.id(), data.value());
            }
            case CSV -> {
                return data.id() + "," + data.value();
            }
        }
        throw new IllegalArgumentException("unsupported output format");
    }
}
