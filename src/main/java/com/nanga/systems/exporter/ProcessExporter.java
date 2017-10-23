package com.nanga.systems.exporter;

import com.nanga.systems.model.WinProcess;
import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class ProcessExporter {
    private static final Logger logger = Logger.getLogger(ProcessExporter.class);

    public void exportToFile(List<WinProcess> processes, String fileName) {
        XStream xstream = new XStream();
        xstream.alias("tasks", TasksHolder.class);
        xstream.alias("task", WinProcess.class);
        xstream.addImplicitCollection(TasksHolder.class, "tasks");

        String xml = xstream.toXML(processes);
        try {
            PrintWriter out = new PrintWriter(fileName);
            out.println(xml);
            out.close();
        } catch (FileNotFoundException ex) {
            logger.error(ex.getLocalizedMessage());
        }

    }
}