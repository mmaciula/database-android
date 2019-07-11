package com.example.database;

public class Phone {
    private long id;
    private String name;
    private String model;
    private String description;

    public Phone(long id, String name, String model, String description){
        this.id = id;
        this.name = name;
        this.model = model;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public String getDescription() {
        return description;
    }
}
