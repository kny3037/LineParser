package com.line.domain;

public class Hospital {
    private String id;

    public Hospital(String id) {
        this.id = id.replaceAll("\"","");
    }

    public String getId() {
        return id;
    }
}
