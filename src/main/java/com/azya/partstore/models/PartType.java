package com.azya.partstore.models;

public enum PartType {
    MOTHERBOARD("Материнская плата", true),CPU("Процессор", true),
    GRAPHICCARD("Видеокарта", false), HARDDRIVE("Жесткий диск", true);
    private String name;
    private boolean needed;

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public boolean isNeeded() {
        return needed;
    }

    PartType(String name, boolean needed) {
        this.name = name;
        this.needed = needed;
    }
}
