package com.azya.partstore.services;

public enum PartViewMode {
    ALL("Все"),NEEDED("Обязательные"),NOTNEEDED("Опциональные");
    private String description;

    PartViewMode(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
