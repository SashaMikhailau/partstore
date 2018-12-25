package com.azya.partstore.models;

public enum PartTypes {
    MotherBoard(true,"Материнская карта"),RAM(true,"Оперативная память"),
    CPU(true,"Процессор"),PowerSupply(true,"Блок питания"),
    HardDrive(true,"Жесткий диск"), FanCooling(true,"Кулер"),
    Case(true,"Корпус"),AudioCard(false,"Аудиокарта"),
    OpticalDrive(false,"Дисковод"),GraphicCard(false,"Видеокарта"),
    Other(false,"Прочее");
    ;
    private boolean needed;
    private String name;

    @Override
    public String toString() {
        return name;
    }

    public boolean isNeeded() {
        return needed;
    }

    PartTypes(boolean needed, String name) {
        this.needed = needed;
        this.name = name;
    }
}
