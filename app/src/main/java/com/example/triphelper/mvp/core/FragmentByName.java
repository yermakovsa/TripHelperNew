package com.example.triphelper.mvp.core;

import org.jetbrains.annotations.Contract;

public enum FragmentByName {
    LIST_OF_PLACES_FRAGMENT("LIST_OF_PLACES_FRAGMENT"),
    MAIN_MENU_FRAGMENT("MAIN_MENU_FRAGMENT"),
    LIST_OF_HOTELS("LIST_OF_HOTELS"),
    MAP_FRAGMENT("MAP_FRAGMENT"),
    LONG_DESCRIPTION_FRAGMENT("LONG_DESCRIPTION_FRAGMENT");

    private final String name;

    FragmentByName(String fragmentName) {
        name = fragmentName;
    }

    @Contract(value = "null -> false", pure = true)
    public boolean equalsName(String compareName) {
        return name.equals(compareName);
    }

    @Contract(pure = true)
    public String toString() {
        return this.name;
    }
}
