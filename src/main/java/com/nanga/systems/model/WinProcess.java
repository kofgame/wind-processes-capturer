package com.nanga.systems.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("task")
public class WinProcess {

    @XStreamAsAttribute
    private String name;

    @XStreamAsAttribute
    private String pid;

    @XStreamAsAttribute
    private Long usedMemory;

    public WinProcess() {}

    public WinProcess(String name, String pid, Long usedMemory) {
        this.name = name;
        this.pid = pid;
        this.usedMemory = usedMemory;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }
    public void setPid(String pid) {
        this.pid = pid;
    }

    public Long getUsedMemory() {
        return usedMemory;
    }
    public void setUsedMemory(Long usedMemory) {
        this.usedMemory = usedMemory;
    }

    @Override
    public String toString() {
        return "WinProcess{" +
                "name='" + name + '\'' +
                ", pid='" + pid + '\'' +
                ", usedMemory='" + usedMemory + '\'' +
                '}';
    }
}