package org.example.sodoku6x6.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.sodoku6x6.Launcher;

/**
 * End screen controller (end.fxml).
 * Responsibilities:
 *  - Show a final message.
 *  - Allow: Play Again, Go Home, or Exit.
 *  - No game logic here, only navigation.
 */
public class EndController {

    @FXML private Label labelEnd;
    @FXML private Button buttonAgain;
    @FXML private Button buttonHome;
    @FXML private Button buttonExit;

    @FXML
    public void initialize() {
        // Default message (in case none is set in FXML)
        if (labelEnd != null && (labelEnd.getText() == null || labelEnd.getText().isBlank())) {
            labelEnd.setText("Well played!");
        }

        if (buttonAgain != null) buttonAgain.setOnAction(e -> playAgain());
        if (buttonHome  != null) buttonHome.setOnAction(e -> goHome());
        if (buttonExit  != null) buttonExit.setOnAction(e -> Platform.exit());
    }

    /** Optional: if you want to customize the final message from the game. */
    public void setEndMessage(String message) {
        if (labelEnd != null && message != null && !message.isBlank()) {
            labelEnd.setText(message);
        }
    }

    // ----------------
    // Navigation
    // ----------------

    private void playAgain() {
        try {
            FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("game.fxml"));
            Scene scene = new Scene(loader.load());

            // If your GameController has a method to start a new game explicitly, call it here:
            // GameController game = loader.getController();
            // game.newGame();  // or game.nuevoJuego();

            Stage stage = (Stage) buttonAgain.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Sudoku 6×6");
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void goHome() {
        try {
            FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("start.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) buttonHome.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Sudoku 6×6 — Start");
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
