package ru.cbr;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.cbr.models.Root;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class FilesParsingTests {

    ObjectMapper objectMapper = new ObjectMapper();

    private final ClassLoader cl = FilesParsingTests.class.getClassLoader();

    private InputStream extractFileFromZip(String fileNameToExtract, String zipFilePath) throws IOException {
        ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream(zipFilePath));
        ZipEntry zipEntry;
        while ((zipEntry = zis.getNextEntry()) != null) {
            if (zipEntry.getName().equals(fileNameToExtract)) {
                return zis;
            }
        }
        return InputStream.nullInputStream();
    }

    @Test
    @DisplayName("Проверка отчета службы по ЗПП на 09.2024")
    void pdfFileParsingTest() throws Exception {
        try (InputStream pdfInputStream = extractFileFromZip("report_2024_3.pdf", "report.zip")) {
            PDF pdf = new PDF(pdfInputStream);

            assertThat(pdf.text).contains("ОТЧЕТ О РАБОТЕ",
                    "ЯНВАРЬ – СЕНТЯБРЬ 2024 ГОДА",
                    "Служба по защите прав потребителей и обеспечению доступности финансовых услуг",
                    "Основные тенденции января-сентября 2024 года");
        }
    }

    @Test
    @DisplayName("Проверка отчета по процентным ставка на 11.2024")
    void xlsFileParsingTest() throws Exception {
        try (InputStream xlsInputStream = extractFileFromZip("rates_table.xlsx", "report.zip")) {
            XLS xls = new XLS(xlsInputStream);

            assertThat(xls.excel.getSheet("2024").getRow(0).getCell(0).toString())
                    .contains("Процентные ставки по операциям Банка России");

            assertThat(xls.excel.getSheet("2024").getRow(2).getCell(5).toString())
                    .contains("с 18.12.2023");
            assertThat(xls.excel.getSheet("2024").getRow(3).getCell(5).toString())
                    .isEqualTo("17.0");

            assertThat(xls.excel.getSheet("2024").getRow(2).getCell(8).toString())
                    .contains("с 28.10.2024");
            assertThat(xls.excel.getSheet("2024").getRow(3).getCell(8).toString())
                    .isEqualTo("22.0");
            assertThat(xls.excel.getSheet("2024").getRow(4).getCell(8).toString())
                    .isEqualTo("22.75");
            assertThat(xls.excel.getSheet("2024").getRow(5).getCell(8).toString())
                    .isEqualTo("21.25");
            assertThat(xls.excel.getSheet("2024").getRow(8).getCell(8).toString())
                    .isEqualTo("21.0");
            assertThat(xls.excel.getSheet("2024").getRow(9).getCell(8).toString())
                    .contains("ключевая ставка");
            assertThat(xls.excel.getSheet("2024").getRow(10).getCell(8).toString())
                    .isEqualTo("20.0");
        }
    }

    @Test
    @DisplayName("Проверка курса AUD на 10.2024")
    void csvFileParsingTest() throws Exception {
        try (InputStream is = extractFileFromZip("AUD_F01_10_2024_T31_10_2024.csv", "report.zip");
             CSVReader csvReader = new CSVReader(new InputStreamReader(is), ';')) {
            csvReader.skip(1);

            List<String[]> data = csvReader.readAll();

            assertThat(data)
                    .hasSize(23)
                    .allMatch(arr -> arr.length == 4)
                    .allMatch(arr -> arr[0].equals("1")
                                  && arr[1].endsWith(".10.2024")
                                  && arr[2].matches("\\d+,\\d{1,4}")
                                  && arr[3].equals("Австралийский доллар")
                    );
        }
    }

    @Test
    void jsonFileParsingTest() throws Exception {
        try (Reader reader = new InputStreamReader(
                cl.getResourceAsStream("statistics-data-service.json")
        )) {
            File file = new File("statistics-data-service.json");


            ObjectMapper om = new ObjectMapper();
            Root root = objectMapper.readValue(file, Root.class);
            Employee employee = objectMapper.readValue(employeeJson, Employee.class);
            ObjectMapper om = new ObjectMapper();
            Root root = om.readValue(myJsonString, Root.class);


//            assertThat(actual.get("title").getAsString()).isEqualTo("example glossary");
//
//            assertThat(actual.get("Glossary").getAsJsonObject()
//                    .get("title").getAsString())
//                    .isEqualTo("S");
//
//            assertThat(actual
//                    .get("GlossDiv").getAsJsonObject()
//                    .get("flag").getAsBoolean())
//                    .isTrue();
        }
    }
}
