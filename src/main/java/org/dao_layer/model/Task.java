package org.dao_layer.model;

import java.sql.Date;
import java.util.Calendar;
import java.util.Objects;

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

    public void setAlertReceived() {
        Date currentDate = new Date(Calendar.getInstance().getTime().getTime());
        if (alertTime.compareTo(currentDate) <= 0)
            this.alertReceived = true;
        else
            this.alertReceived = false;
    }

    @Override
    public String toString() {
        String alert = alertReceived ? "yes" : "no";
        return "" +
                "Task ID: " + id +
                "\nName: " + name +
                "\nDescription: " + description +
                "\nAlert date: " + alertTime +
                "\nAlert Received: " + alert +
                "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, alertTime, alertReceived);
    }
}
