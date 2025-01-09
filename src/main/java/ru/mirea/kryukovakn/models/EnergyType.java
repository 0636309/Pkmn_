package ru.mirea.kryukovakn.models;

public enum EnergyType {
    FIRE,
    GRASS,
    WATER,
    LIGHTNING,
    PSYCHIC,
    FIGHTING,
    DARKNESS,
    METAL,
    FAIRY,
    DRAGON,
    COLORLESS;

    EnergyType() {

    }

    @Override
    public String toString() {
        return name();
    }


}
