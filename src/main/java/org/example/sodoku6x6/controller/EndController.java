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
 * Controlador de la pantalla final (vista: {@code end.fxml}).
 *
 * <p><b>Responsabilidades:</b></p>
 * <ul>
 *   <li>Mostrar un mensaje final (por ejemplo, al completar correctamente el Sudoku).</li>
 *   <li>Permitir navegar: <i>Jugar de nuevo</i>, <i>Ir al inicio</i> o <i>Salir</i>.</li>
 *   <li>No contiene lógica del juego; únicamente navegación y presentación.</li>
 * </ul>
 *
 * <p><b>Convenciones:</b> Este controlador asume que los recursos FXML
 * {@code game.fxml} y {@code start.fxml} están en
 * {@code src/main/resources/org/example/sodoku6x6/} y que el módulo abre
 * el paquete de controladores a JavaFX (si se usa {@code module-info.java}).</p>
 */
public class EndController {

    /** Etiqueta principal que muestra el mensaje final. */
    @FXML private Label labelEnd;

    /** Botón para volver a jugar (cargar la vista del juego). */
    @FXML private Button buttonAgain;

    /** Botón para ir a la pantalla de inicio. */
    @FXML private Button buttonHome;

    /** Botón para salir de la aplicación. */
    @FXML private Button buttonExit;

    /**
     * Método de ciclo de vida llamado automáticamente por JavaFX al cargar el FXML.
     * <ul>
     *   <li>Establece un mensaje por defecto si el {@link #labelEnd} está vacío.</li>
     *   <li>Conecta los manejadores de eventos de los botones.</li>
     * </ul>
     */
    @FXML
    public void initialize() {
        // Mensaje por defecto si no fue configurado por el GameController
        if (labelEnd != null && (labelEnd.getText() == null || labelEnd.getText().isBlank())) {
            labelEnd.setText("Well played!"); // Puedes cambiarlo por "¡Bien jugado!" si prefieres español.
        }

        // Wiring de botones (navegación simple)
        if (buttonAgain != null) buttonAgain.setOnAction(e -> playAgain());
        if (buttonHome  != null) buttonHome.setOnAction(e -> goHome());
        if (buttonExit  != null) buttonExit.setOnAction(e -> Platform.exit());
    }

    /**
     * Permite personalizar el mensaje final mostrado en {@link #labelEnd}.
     *
     * @param message texto a mostrar; si es {@code null} o en blanco, se ignora
     */
    public void setEndMessage(String message) {
        if (labelEnd != null && message != null && !message.isBlank()) {
            labelEnd.setText(message);
        }
    }

    /**
     * Carga la vista del juego ({@code game.fxml}) en la misma ventana.
     * <p>Nota: la inicialización del tablero se realiza en el {@code initialize()}
     * del {@code GameController}; no es necesario invocar nada extra aquí.</p>
     */
    private void playAgain() {
        try {
            FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("game.fxml"));
            Scene scene = new Scene(loader.load());

            // Reemplaza la escena actual por la del juego
            Stage stage = (Stage) buttonAgain.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Sudoku 6×6");
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace(); // En producción podrías mostrar un Alert de error
        }
    }

    /**
     * Carga la vista de inicio ({@code start.fxml}) en la misma ventana.
     */
    private void goHome() {
        try {
            FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("start.fxml"));
            Scene scene = new Scene(loader.load());

            // Reemplaza la escena actual por la de inicio
            Stage stage = (Stage) buttonHome.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Sudoku 6×6 — Start");
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace(); // En producción podrías mostrar un Alert de error
        }
    }
}
