package org.model;

import java.util.Date;

public class Task extends Entity{
    private int id;
    private String name;
    private String description;
    private Date alertTime;
    private boolean alertReceived;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(Date alertTime) {
        this.alertTime = alertTime;
    }

    public boolean isAlertReceived() {
        return alertReceived;
    }

    public void setAlertReceived(boolean alertReceived) {
        this.alertReceived = alertReceived;
    }

    @Override
    public String toString() {
        return "" +
                "Task ID: " + id +
                "\nName'" + name +
                "\ndescription='" + description +
                "\nalertTime=" + alertTime +
                "\nalertReceived=" + alertReceived;
    }
}
