package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MonitorWindow extends Application {
    @Override
    public void start(Stage stage){
        Label messageLabel = new Label("En attente de mesures...");
        messageLabel.setStyle("-fx-font-size: 16px;"); // Un peu plus gros c'est mieux

        VBox root = new VBox(messageLabel);
        // On centre le contenu pour faire joli
        root.setStyle("-fx-alignment: center; -fx-padding: 20px;");

        JsonFileManager jsonFileManager = new JsonFileManager("metrics.json");
        MonitorApp engine = new MonitorApp(jsonFileManager);

        engine.addListener(metric -> {javafx.application.Platform.runLater(()-> {
            String text = String.format("Température: %.1f°C | Charge: %.1f%%",
                    metric.temperature(), metric.cpuLoad());
            messageLabel.setText(text);
        });
        });

        engine.start();

        stage.setOnCloseRequest(event -> engine.stop());

        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("Moniteur CPU");
        stage.setScene(scene);
        stage.show();
    }
}
