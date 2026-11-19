package com.appregistros.servicioweb;

import javafx.application.Application;

/** Punto de entrada para ejecutar desde un JAR sin módulos. */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(MainApp.class, args);
    }
}
