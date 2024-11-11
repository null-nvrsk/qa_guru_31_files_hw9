package ru.cbr;

import com.codeborne.pdftest.PDF;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class FilesParsingTests {

    private ClassLoader cl = FilesParsingTests.class.getClassLoader();

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
    void pdfFileParsingTest() throws Exception {
        try (InputStream pdfInputStream = extractFileFromZip("report_2024_3.pdf", "report.zip")) {
            PDF pdf = new PDF(pdfInputStream);

            assertThat(pdf.text).contains("ОТЧЕТ О РАБОТЕ",
                    "ЯНВАРЬ – СЕНТЯБРЬ 2024 ГОДА",
                    "Служба по защите прав потребителей и обеспечению доступности финансовых услуг",
                    "Основные тенденции января-сентября 2024 года");
        }
    }


}
