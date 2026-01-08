package org.example;

/**
 * Cette fonction est le principale du code.
 * Elle permet de créer un fichier "metrics.json".
 * Elle lance l'application générale avec app.start()
 * */

public class Main {
    public static void main(String[] args) {
        JsonFileManager manager = new JsonFileManager("metrics.json");
        MonitorApp app = new MonitorApp(manager);
        app.start();
    }
}