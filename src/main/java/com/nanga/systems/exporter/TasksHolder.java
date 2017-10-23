package com.nanga.systems.exporter;

import com.nanga.systems.model.WinProcess;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("tasks")
public class TasksHolder {

    @XStreamImplicit(itemFieldName="tasks")
    private List<WinProcess> tasks;

    public TasksHolder(){
        tasks = new ArrayList<WinProcess>();
    }

    public void add(WinProcess p){
        tasks.add(p);
    }

    public List<WinProcess> getTasks() {
        return tasks;
    }

}