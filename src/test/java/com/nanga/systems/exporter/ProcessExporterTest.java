package com.nanga.systems.exporter;

import com.nanga.systems.common.TestProcessesEmulator;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ProcessExporterTest {

    private final ProcessExporter procExporter = new ProcessExporter();

    @Test
    public void testExportToFile() throws URISyntaxException, IOException {
        procExporter.exportToFile(TestProcessesEmulator.getTestProcesses(), "RunningWindowsTasks.xml");

        String projectHomeDir = new File("").getAbsolutePath();
        String filePath = projectHomeDir + "\\RunningWindowsTasks.xml";
        String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));

        Assert.assertTrue(StringUtils.isNotEmpty( fileContent ));
    }

}