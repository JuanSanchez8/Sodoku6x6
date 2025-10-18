package org.example.sodoku6x6.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.sodoku6x6.Launcher;

public class StartController {

    @FXML private Button buttonPlay;
    @FXML private Button buttonHelpStart; // optional

    @FXML
    public void initialize() {
        buttonPlay.setOnAction(e -> openGame());

        if (buttonHelpStart != null) {
            buttonHelpStart.setOnAction(e -> new javafx.scene.control.Alert(
                    javafx.scene.control.Alert.AlertType.INFORMATION,
                    "¿Cómo jugar?\n\n" +
                            "• Completa la cuadrícula 6×6 con números del 1 al 6.\n" +
                            "• No se puede repetir un número en la misma fila.\n" +
                            "• No se puede repetir un número en la misma columna.\n" +
                            "• No se puede repetir un número en cada bloque 2×3.\n\n" +
                            "Controles:\n" +
                            "• Teclas 1–6: ingresar número.\n" +
                            "• DEL / BACKSPACE: borrar.\n" +
                            "• Puedes hacer clic en una celda para seleccionarla."
            ).showAndWait());
        }
    }


    private void openGame() {
        try {
            FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("game.fxml"));
            Scene scene = new Scene(loader.load());

            // If your GameController doesn't auto-generate in initialize(),
            // uncomment the next two lines to start a new game explicitly:
            // GameController game = loader.getController();
            // game.nuevoJuego();

            Stage stage = (Stage) buttonPlay.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Sudoku 6×6");
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace(); // minimal logging
        }
    }
}
