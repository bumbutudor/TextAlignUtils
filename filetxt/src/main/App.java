package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import algorithm.Algorithm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class App extends Application {
    public Stage stage;
    public VBox layout;
    private Algorithm algorithm = new Algorithm();

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.stage.setTitle("Application");
        layout = new VBox();
        VBox output = new VBox();
        HBox controls = new HBox();

        Button selectFileButton = new Button("Select file");
        selectFileButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File fileToOpen = fileChooser.showOpenDialog(null);
            String path = fileToOpen.getAbsolutePath();

            StringBuilder contentBuilder = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {

                String sCurrentLine;
                while ((sCurrentLine = br.readLine()) != null) {
                    contentBuilder.append(sCurrentLine).append("\n");
                }

                String text = contentBuilder.toString();
                String[] words = text.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
                String result = algorithm.process(words,true);

                BufferedWriter writer;
                writer = new BufferedWriter(new FileWriter(path.substring(0, path.length() - 4) + "-result.txt"));

                writer.write(result);
                writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        controls.getChildren().add(selectFileButton);
        layout.getChildren().addAll(output, controls);

        Scene scene = new Scene(layout, 600, 400);
        this.stage.setScene(scene);
        this.stage.show();
    }
}