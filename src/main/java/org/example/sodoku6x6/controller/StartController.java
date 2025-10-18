package org.example.sodoku6x6.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.sodoku6x6.Launcher;

/**
 * Controlador de la pantalla de inicio (vista: {@code start.fxml}).
 *
 * <p><b>Responsabilidades:</b></p>
 * <ul>
 *   <li>Navegar hacia la vista de juego al pulsar <i>Jugar</i>.</li>
 *   <li>Mostrar un diálogo informativo con las reglas al pulsar <i>¿Cómo jugar?</i> (opcional).</li>
 * </ul>
 *
 * <p><b>Convenciones:</b> Este controlador asume que el recurso {@code game.fxml}
 * está ubicado en {@code src/main/resources/org/example/sodoku6x6/} y que el
 * paquete de controladores está abierto a JavaFX si se usa {@code module-info.java}.</p>
 */
public class StartController {

    /** Botón principal para iniciar el juego. */
    @FXML private Button buttonPlay;

    /** Botón opcional para mostrar las reglas básicas. */
    @FXML private Button buttonHelpStart;

    /**
     * Ciclo de vida JavaFX: se invoca al cargar el FXML.
     * <ul>
     *   <li>Conecta el botón <i>Jugar</i> para abrir la vista del juego.</li>
     *   <li>Si existe el botón <i>¿Cómo jugar?</i>, muestra un {@code Alert} con las reglas.</li>
     * </ul>
     */
    @FXML
    public void initialize() {
        // Navegar al tablero
        buttonPlay.setOnAction(e -> openGame());

        // Mostrar reglas (si el botón está presente en el FXML)
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

    /**
     * Carga la vista del juego ({@code game.fxml}) en la misma ventana.
     * <p>La preparación del tablero (pistas y validación) ocurre en el {@code initialize()}
     * del {@code GameController}.</p>
     */
    private void openGame() {
        try {
            FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("game.fxml"));
            Scene scene = new Scene(loader.load());

            // Si quisieras forzar un nuevo juego explícitamente (no necesario ahora):
            // GameController game = loader.getController();
            // game.nuevoJuego();

            Stage stage = (Stage) buttonPlay.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Sudoku 6×6");
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace(); // En producción, mostrar un Alert de error amigable
        }
    }
}
