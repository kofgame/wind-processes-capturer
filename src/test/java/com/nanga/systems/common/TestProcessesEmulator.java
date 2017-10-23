package com.nanga.systems.common;


import com.google.common.collect.Lists;
import com.nanga.systems.model.WinProcess;

import java.util.List;

public final class TestProcessesEmulator {
    private TestProcessesEmulator() {}

    public static List<WinProcess> getTestProcesses() {
        return Lists.newArrayList(
                new WinProcess("idea.exe", "3896", 647180L),
                new WinProcess("chrome.exe", "3508", 479488L),
                new WinProcess("svchost.exe", "936", 155020L),
                new WinProcess("chrome.exe", "3372", 234112L)
        );
    }

}