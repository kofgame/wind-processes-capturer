package com.nanga.systems.grabber;

import com.google.common.collect.ImmutableList;
import com.nanga.systems.model.WinProcess;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.substringBetween;

/**
 * Runs “tasklist” as a console command and returns running processes
 */
public class WindowsProcessGrabber {
    private static final Logger logger = Logger.getLogger(WindowsProcessGrabber.class);

    private static final String TASKLIST_FMT_OUTPUT_CSV = " /FO csv";
    private static final String TASKLIST_OUTPUT_NO_HEADERS = " /NH";
    private static final String TASKLIST_CMD_PATH = System.getenv("windir") + "\\system32\\" +
            "tasklist.exe" + TASKLIST_FMT_OUTPUT_CSV + TASKLIST_OUTPUT_NO_HEADERS;
    private static final String CODEPAGE_CMD_PATH = System.getenv("windir") + "\\system32\\" + "chcp.com";
    private static final List<String> charsetPrefixes = ImmutableList.of("", "windows-", "x-windows-", "IBM", "x-IBM");

    /**
     * For peculiarities of Windows 'tasklist' command check https://ss64.com/nt/tasklist.html
     * @return {@link List<WinProcess>}
     */
    public List<WinProcess> listProcesses() {
        List<WinProcess> processes = new ArrayList<WinProcess>();
        BufferedReader reader = null;
        try {
            Charset charset = determineCharset();
            String line;
            Process proc = Runtime.getRuntime().exec(TASKLIST_CMD_PATH);
            reader = new BufferedReader(new InputStreamReader(proc.getInputStream(), charset));
            while ((line = reader.readLine()) != null) {
                String[] procDetails = StringUtils.split(line, ",");
                WinProcess winProc = new WinProcess();
                winProc.setName(substringBetween(procDetails[0], "\""));
                winProc.setPid(substringBetween(procDetails[1], "\""));
                winProc.setUsedMemory(Long.valueOf(
                        substringBetween(procDetails[4], "\"").replaceAll("[^0-9]", "")));
                processes.add(winProc);
            }
            reader.close();
        } catch (IOException e) {
            logger.error("Failed to list Windows running processes due: " + e.getLocalizedMessage());
        } finally {
            IOUtils.closeQuietly(reader);
        }
        Collections.sort(processes,
                (WinProcess p1, WinProcess p2) -> p2.getUsedMemory().compareTo(p1.getUsedMemory()));
        return processes;
    }

    private Charset determineCharset() throws IOException {
        Charset charset = null;
        Process p = Runtime.getRuntime().exec(CODEPAGE_CMD_PATH);
        String windowsCodePage = new Scanner(new InputStreamReader(p.getInputStream())).skip(".*:").next();
        for (String charsetPrefix : charsetPrefixes) {
            try {
                charset = Charset.forName(charsetPrefix + windowsCodePage);
                break;
            } catch (Throwable t) {
                logger.warn("Failed to determine charset for charsetPrefix/windowsCodePage: " +
                charsetPrefix + "/" + windowsCodePage + ". Exception: " + t.getLocalizedMessage());
            }
        }
        return charset;
    }
}