package org.example.util;

import org.example.dao.Data;

public interface Formatter {
    String format(final Data data, final OutputFormat outputFormat);
}
