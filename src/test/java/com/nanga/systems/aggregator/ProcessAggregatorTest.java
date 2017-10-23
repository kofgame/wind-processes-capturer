package com.nanga.systems.aggregator;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.nanga.systems.common.TestProcessesEmulator;
import com.nanga.systems.model.WinProcess;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ProcessAggregatorTest {
    private final ProcessAggregator aggregator = new ProcessAggregator();
    List<WinProcess> testProcesses = TestProcessesEmulator.getTestProcesses();

    @Test
    public void testGroupByMemoryUsage() throws IOException {
        List<WinProcess> processes = aggregator.groupByMemoryUsage(testProcesses);
        WinProcess chrome = Iterables.find(processes, new Predicate<WinProcess>() {
            @Override
            public boolean apply(WinProcess wp) {
                return wp.getName().equals("chrome.exe");
            }
        });
        System.out.println(processes);
        Assert.assertTrue(Objects.nonNull(chrome));
        Assert.assertEquals(Long.valueOf(479488L + 234112L), chrome.getUsedMemory());
    }

}