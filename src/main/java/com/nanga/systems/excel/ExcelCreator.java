package com.nanga.systems.excel;

import com.nanga.systems.model.WinProcess;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static java.util.Arrays.asList;

public class ExcelCreator {

    private static final Logger logger = Logger.getLogger(ExcelCreator.class);

    /**
     * Create excel sheet & draw chart with following data:
        +--------------------------+
        |    Name    | UsedMemory  |
        +--------------------------+
        | idea.exe   | 652         |
        | chrome.exe | 3704        |
        +--------------------------+
     */
    public void generateExcelWithChart(List<WinProcess> processes, String excelFileName) {
        // Prepare the excel workbook
        Workbook workbook = new HSSFWorkbook();
        final Sheet sheet = workbook.createSheet("TaskList");

        // Create DataSet for chart data
        DefaultCategoryDataset chartDataset = new DefaultCategoryDataset();

        fillSheetWithData(processes, sheet, chartDataset);

        try {
            createChart(workbook, chartDataset);
        } catch (IOException e) {
            logger.error("Failed to generate chart with tasks Memory usage due: " + e.getLocalizedMessage());
        }
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(excelFileName);
        } catch (FileNotFoundException e) {
            logger.error(e.getLocalizedMessage());
        }
        try {
            workbook.write(fileOut);
        } catch (IOException ioExc) {
            logger.error("Failed to persist in FS excel workbook due: " + ioExc.getLocalizedMessage());
        }
        finally {
            IOUtils.closeQuietly(fileOut);
        }

    }

    private void fillSheetWithData(List<WinProcess> processes, Sheet sheet, DefaultCategoryDataset chartDataset) {
        Row headerRow = sheet.createRow(0);
        Cell nameHeaderCell = headerRow.createCell(0);
        nameHeaderCell.setCellValue("Name");
        Cell usedMemHeaderCell = headerRow.createCell(1);
        usedMemHeaderCell.setCellValue("UsedMemory");

        Iterator<WinProcess> procIter = processes.iterator();

        for (int i = 0; i < processes.size() && procIter.hasNext(); i++) {
            WinProcess process = procIter.next();
            Row dataRow = sheet.createRow(i+1); // 0-th is for header
            Cell procNameCell = dataRow.createCell(0);
            procNameCell.setCellValue(process.getName());
            Cell usedMemoryCell = dataRow.createCell(1);
            usedMemoryCell.setCellValue(process.getUsedMemory());
            // populate chart DataSet
            chartDataset.addValue(process.getUsedMemory(),"Used Memory", process.getName());
        }
    }

    private void createChart(Workbook workbook, DefaultCategoryDataset chartDataset) throws IOException {
        Sheet sheet = workbook.getSheetAt(0);
        JFreeChart chartObject = ChartFactory.createBarChart("Tasks Memory Usage",
                "ProcessName", "Memory Usage", chartDataset, PlotOrientation.VERTICAL, true, true, false);
        // Chart Dimensions
        int width = 4500;
        int height = 500;
        ByteArrayOutputStream chartOut = new ByteArrayOutputStream();
        ChartUtilities.writeChartAsPNG(chartOut, chartObject, width, height);
        // We can now read the byte data from output stream and stamp the chart to Excel worksheet
        int pictureId = workbook.addPicture(chartOut.toByteArray(), Workbook.PICTURE_TYPE_PNG);
        chartOut.close();
        // Create drawing container
        Drawing drawing = sheet.createDrawingPatriarch();
        ClientAnchor clientAnchor = new HSSFClientAnchor();
        // Define top left corner
        clientAnchor.setCol1(4);
        clientAnchor.setRow1(5);
        Picture chartImage = drawing.createPicture(clientAnchor, pictureId);
        // resize Chart image
        chartImage.resize();
    }
}