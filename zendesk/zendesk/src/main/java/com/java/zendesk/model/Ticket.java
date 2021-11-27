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
        this.created_at = created_at;
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
}