package com.java.zendesk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticket {
    private int id;

    private String created_at;

    private String updated_at;

    private String type;

    private String subject;

    private String priority;

    private String status;

    public Ticket() {}

    public Ticket(int id, String created_at, String updated_at, String type, String subject, String priority,
            String status) {
        this.id = id;

        String[] tempStringArray = created_at.split("T");
        tempStringArray[1] = tempStringArray[1].substring(0, 8);
        this.created_at = tempStringArray[0] + " " + tempStringArray[1];

        tempStringArray = updated_at.split("T");
        tempStringArray[1] = tempStringArray[1].substring(0, 8);
        this.updated_at = tempStringArray[0] + " " + tempStringArray[1];

        this.type = type;
        this.subject = subject;
        this.priority = priority;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getCreatedDate() {
        return created_at;
    }

    public String getUpdatedDate() {
        return updated_at;
    }

    public String getType() {
        return type;
    }

    public String getSubject() {
        return subject;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return String.format("%-5d%-50s%-15s%-15s%-25s%-25s%s\n", this.getId(), this.getSubject(), this.getType(),
                this.getPriority(), this.getCreatedDate(), this.getUpdatedDate(), this.getStatus());
    }
}