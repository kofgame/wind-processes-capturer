package com.nanga.systems.grabber;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.nanga.systems.model.WinProcess;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

public class WindowsProcessGrabberTest {

    private final WindowsProcessGrabber grabber = new WindowsProcessGrabber();

    @Test
    public void testListProcesses() throws IOException {
        List<WinProcess> processes = grabber.listProcesses();
        processes.stream().forEach(System.out::println);
        Assert.assertTrue("Grabbed System processes mustn't be empty", isNotEmpty(processes));
        Assert.assertTrue("'System Idle Process' should always run in Windows",
                            Objects.nonNull(
                                Iterables.find(processes,
                                        (WinProcess wp) -> wp.getName().equals("System Idle Process"))
                                ));
    }
}