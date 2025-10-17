package org.example.sodoku6x6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxml = new FXMLLoader(Launcher.class.getResource("game.fxml"));
        Scene scene = new Scene(fxml.load());
        stage.setTitle("Sudoku 6x6");
        stage.setScene(scene);
        stage.show();
    }
}
