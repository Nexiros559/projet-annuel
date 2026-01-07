package org.example;

public class Main {
    public static void main(String[] args) {
        JsonFileManager manager = new JsonFileManager("metrics.json");
        MonitorApp app = new MonitorApp(manager);
        app.start();
    }
}