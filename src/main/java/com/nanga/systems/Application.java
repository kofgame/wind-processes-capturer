package com.nanga.systems;

import com.nanga.systems.aggregator.ProcessAggregator;
import com.nanga.systems.excel.ExcelCreator;
import com.nanga.systems.exporter.ProcessExporter;
import com.nanga.systems.grabber.WindowsProcessGrabber;
import com.nanga.systems.model.WinProcess;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.List;

/**
 * Main class that demonstrates grabbing Windows processes, aggregation by memUsage,
 * exporting to file, creates excel & draws Chart i.e implements:
 * Req_001, Req_003, Req_004, Req_006, Req_007, Req_010
 */
public class Application {
    private static final Logger logger = Logger.getLogger(Application.class);
    private static final String NL = System.getProperty("line.separator");

    public static void main(String[] args) throws IOException, InvalidFormatException {
        // run “tasklist” as console command & capture running Windows processes
        List<WinProcess> processes = new WindowsProcessGrabber().listProcesses();
        logger.info(NL + "--- Running Windows processes: ---" + NL + processes);

        List<WinProcess> aggregatedByMemUsage = new ProcessAggregator().groupByMemoryUsage(processes);
        logger.info(NL + "--- Processes aggregated by memory usage: ---" + NL + aggregatedByMemUsage);

        new ProcessExporter().exportToFile(aggregatedByMemUsage, "RunningWindowsTasks.xml");
        logger.info(NL + "--- Tasks exporting in file is finished. Check RunningWindowsTasks.xml ---" + NL);

        new ExcelCreator().generateExcelWithChart(aggregatedByMemUsage, "tasklist.xls");
        logger.info(NL + "--- Exporting to excel / chart drawing done. Check tasklist.xls ---" + NL);

    }
}