package org.example;

import javafx.application.Application;

/**
 * Cette fonction est le principale du code.
 * Elle permet de créer un fichier "metrics.json".
 * Elle lance l'application générale avec app.start()
 * */

public class Main {
    public static void main(String[] args) {
        Application.launch(MonitorWindow.class, args);
    }
}