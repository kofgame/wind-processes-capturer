package com.nanga.systems.excel;

import com.nanga.systems.model.WinProcess;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.util.Arrays.asList;

public class ExcelCreatorTest {
    private ExcelCreator excelCreator = new ExcelCreator();

    @Test
    public void testGenerateExcelWithChart() throws IOException {
        String testExcelName = "GeneratedByTestTasksWithChart.xls";

        excelCreator.generateExcelWithChart(
                asList(new WinProcess("idea.exe", "3896", 647180L),
                        new WinProcess("chrome.exe", "3508", 479488L),
                        new WinProcess("svchost.exe", "936", 155020L),
                        new WinProcess("java.exe", "4524", 61740L),
                        new WinProcess("WINWORD.EXE", "2280", 41908L),
                        new WinProcess("uTorrent.exe", "1724", 27588L),
                        new WinProcess("notepad++.exe", "1652", 14500L)),
                testExcelName);

        String projectHomeDir = new File("").getAbsolutePath();
        String filePath = projectHomeDir + "\\".concat(testExcelName);
        String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));

        Assert.assertTrue(StringUtils.isNotEmpty( fileContent ));
    }

}