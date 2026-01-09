package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MonitorWindow extends Application {
    @Override
    public void start(Stage stage){

        Label tempTitle = new Label("Température");
        Label loadTitle = new Label("Charge CPU");
        Label tempValue = new Label("--");
        Label loadValue = new Label("--");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(20);

        grid.add(tempTitle, 0, 0);
        grid.add(loadTitle,0, 1);
        grid.add(tempValue, 1, 0);
        grid.add(loadValue, 1, 1);

        JsonFileManager jsonFileManager = new JsonFileManager("metrics.json");
        MonitorApp engine = new MonitorApp(jsonFileManager);

        engine.addListener(metric -> {javafx.application.Platform.runLater(()-> {
            tempValue.setText(String.format("%.1f°C", metric.temperature()));
            loadValue.setText(String.format("%.1f %%", metric.cpuLoad() * 100));

            if (metric.isOverheating()){
                    tempValue.setTextFill(Color.RED);
            }else{
                tempValue.setTextFill(Color.GREEN);
            }
            if (metric.isOverloaded()){
                loadValue.setTextFill(Color.RED);
            }else {
                loadValue.setTextFill(Color.GREEN);
            }
        });
        });

        engine.start();

        stage.setOnCloseRequest(event -> engine.stop());

        Scene scene = new Scene(grid, 400, 300);
        stage.setTitle("Moniteur CPU");
        stage.setScene(scene);
        stage.show();
    }
}
