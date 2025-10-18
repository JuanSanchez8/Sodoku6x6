package org.example.sodoku6x6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Punto de entrada JavaFX de la aplicación Sudoku 6×6.
 *
 * <p><b>Responsabilidades:</b></p>
 * <ul>
 *   <li>Arrancar JavaFX y cargar la vista inicial {@code start.fxml}.</li>
 *   <li>Configurar la {@link Scene} y mostrar la ventana principal.</li>
 * </ul>
 *
 * <p><b>Convenciones:</b> Se asume que el recurso
 * {@code start.fxml} está en
 * {@code src/main/resources/org/example/sodoku6x6/start.fxml}.</p>
 *
 * <p><b>Nota módulos:</b> si usas {@code module-info.java}, debes incluir:
 * <pre>
 * requires javafx.controls;
 * requires javafx.fxml;
 *
 * opens org.example.sodoku6x6 to javafx.fxml;
 * opens org.example.sodoku6x6.controller to javafx.fxml;
 * </pre>
 * para permitir que FXMLLoader acceda a los controladores y recursos.</p>
 */
public class Launcher extends Application {

    /**
     * Método estándar para iniciar una aplicación JavaFX.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Ciclo de vida JavaFX: se invoca al iniciar la aplicación.
     * Carga {@code start.fxml} y muestra la ventana.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxml = new FXMLLoader(Launcher.class.getResource("start.fxml"));
            Scene scene = new Scene(fxml.load());
            stage.setTitle("Sudoku 6×6");
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            // Manejo simple por si el FXML no se encuentra o falla al cargar.
            ex.printStackTrace();
            // En un proyecto más pulido podrías abrir un Alert de error y cerrar.
        }
    }
}
