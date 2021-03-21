package org.github.kerrerain.faexman;

import com.github.javafaker.Faker;
import org.dhatim.fastexcel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GenerateFakeExcelApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateFakeExcelApplication.class);

    public static void main(String[] args) throws IOException {
        var faker = new Faker();
        var numberOfRows = 500000;

        LOGGER.info(String.format("Creating a fake xlsx file with %d rows.", numberOfRows));

        try(var outputStream = Files.newOutputStream(Paths.get("output.xlsx"))) {
            var workbook = new Workbook(outputStream, "Fake workbook", "1.0");
            var worksheet = workbook.newWorksheet("Fake sheet");

            for (var i = 0; i < numberOfRows; i++) {
                worksheet.value(i, 0, faker.code().ean13());
                worksheet.value(i, 1, faker.name().fullName());
                worksheet.value(i, 2, faker.internet().emailAddress());
                worksheet.value(i, 3, faker.book().title());
            }

            workbook.finish();
        }

        LOGGER.info("The file has been successfully created.");
    }
}
