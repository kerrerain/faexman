package org.github.kerrerain.faexman;

import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executors;

public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    private static void processFile() throws IOException {
        try(var inputStream = Files.newInputStream(Paths.get("output.xlsx"));
            var workbook = new ReadableWorkbook(inputStream)) {
            var sheet = workbook.getFirstSheet();
            try (var rows = sheet.openStream()) {
                rows.forEach(r -> {
                    var ean = r.getCellAsString(0).orElse(null);
                });
            }
        }
    }

    public static void main(String[] args) throws IOException {
        var pool = Executors.newFixedThreadPool(3);
        for (var i = 0; i < 100; i++) {
            pool.submit(() -> {
                LOGGER.info("Starting processing");
                try {
                    processFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                LOGGER.info("Ending processing.");
            });
        }
    }
}
