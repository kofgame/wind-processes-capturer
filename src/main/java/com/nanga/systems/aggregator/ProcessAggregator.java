package com.nanga.systems.aggregator;

import com.google.common.collect.Lists;
import com.nanga.systems.model.WinProcess;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProcessAggregator {

    public List<WinProcess> groupByMemoryUsage(List<WinProcess> processes) {
        Map<String, List<WinProcess>> groupedProcesses = processes.stream().collect(Collectors.groupingBy(p -> p.getName()));
        List<WinProcess> aggregatedItems = Lists.newArrayList();
        for (String procName : groupedProcesses.keySet()) {
            WinProcess aggregatedItem = new WinProcess();
            aggregatedItem.setName(procName);
            aggregatedItem.setUsedMemory(
                    groupedProcesses.get(procName).stream().mapToLong(item -> item.getUsedMemory()).sum());
            aggregatedItem.setPid(
                    groupedProcesses.get(procName).stream().
                            map(item -> item.getPid()).collect(Collectors.joining(","))
            );
            aggregatedItems.add(aggregatedItem);
        }
        Collections.sort(aggregatedItems,
                (WinProcess wp1, WinProcess wp2) -> wp2.getUsedMemory().compareTo(wp1.getUsedMemory()));
        return aggregatedItems;
    }
}