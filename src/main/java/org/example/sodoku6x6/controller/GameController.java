package org.example.sodoku6x6.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.example.sodoku6x6.model.Generator;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import org.example.sodoku6x6.model.Validator;
import javafx.event.ActionEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Controlador de la vista de juego (game.fxml).
 *
 * <p><b>Responsabilidades:</b></p>
 * <ul>
 *   <li>Preparar el tablero 6×6: mostrar 2 pistas por bloque 2×3 usando una solución válida.</li>
 *   <li>Gestionar entrada del usuario (teclas 1–6 y borrado) y validar en tiempo real.</li>
 *   <li>Comprobar el tablero completo al pulsar “Listo” y navegar a la pantalla final.</li>
 *   <li>Dar “pistas” controladas sin permitir completar el tablero solo con la ayuda.</li>
 * </ul>
 *
 * <p>La lógica de generación de soluciones y validación está en el <i>modelo</i>:
 * {@link Generator} y {@link Validator}.</p>
 */
public class GameController {

    // --- Inyección de las 36 celdas (nomenclatura tipo ajedrez: a..f columnas, 1..6 filas) ---
    @FXML private TextField a1,b1,c1,d1,e1,f1;
    @FXML private TextField a2,b2,c2,d2,e2,f2;
    @FXML private TextField a3,b3,c3,d3,e3,f3;
    @FXML private TextField a4,b4,c4,d4,e4,f4;
    @FXML private TextField a5,b5,c5,d5,e5,f5;
    @FXML private TextField a6,b6,c6,d6,e6,f6;

    // Botones de acción (Ayuda y Listo)
    @FXML private Button buttonCheck;
    @FXML private Button buttonHelp;

    /** Matriz de referencias a las 36 celdas (facilita recorrer filas/columnas). */
    private TextField[][] cells;

    /** Estado actual que escribe el jugador (0 = vacío). */
    private int[][] board;

    /** Solución completa generada aleatoriamente (verdad del tablero). */
    private int[][] solution;

    /** Validador de reglas (filas/columnas/bloques). */
    private Validator validator = new Validator();

    /**
     * Estilo base (cadena CSS) de cada celda al iniciar.
     * Se usa para restaurar el color original después de resaltar errores.
     */
    private String[][] baseStyles;

    /**
     * Ciclo de vida JavaFX: se llama al cargar el FXML.
     * <ul>
     *   <li>Construye la matriz {@link #cells} para manejar las 36 celdas.</li>
     *   <li>Genera una solución 6×6 y deja 2 pistas por bloque 2×3.</li>
     *   <li>Configura listeners de teclado para entrada y validación en tiempo real.</li>
     *   <li>Guarda los estilos iniciales de cada celda para poder restaurarlos.</li>
     * </ul>
     */
    @FXML
    public void initialize() {
        cells = new TextField[][]{
                {a1,b1,c1,d1,e1,f1},
                {a2,b2,c2,d2,e2,f2},
                {a3,b3,c3,d3,e3,f3},
                {a4,b4,c4,d4,e4,f4},
                {a5,b5,c5,d5,e5,f5},
                {a6,b6,c6,d6,e6,f6}
        };

        board = new int[6][6];

        // Solución válida aleatoria (backtracking en Generator)
        solution = new Generator().generateSolution();

        // HU-2: inicia el juego con 2 pistas por bloque 2×3
        showTwoCluesPerBlock(solution);

        // HU-3 y HU-4: entrada por teclado y validación en tiempo real
        addInputListeners();

        // Guardar estilo inicial de cada celda (para restaurar tras marcar errores)
        baseStyles = new String[6][6];
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                baseStyles[r][c] = cells[r][c].getStyle();
            }
        }
    }

    /**
     * Handler del botón “Listo” (si en FXML usas onMouseClicked).
     * Llama a {@link #checkFullBoard()} para validar tablero completo.
     */
    @FXML void onMouseCheck(MouseEvent event) {
        checkFullBoard();
    }

    /**
     * Handler del botón “Ayuda” (si en FXML usas onAction).
     * Llama a {@link #giveHint()} para rellenar una celda vacía aleatoria con la solución.
     */
    @FXML void onActionAyuda(ActionEvent event) {
        giveHint();
    }

    /**
     * Llena el tablero visual con 2 pistas fijas por cada bloque 2×3.
     * <p>Limpia todas las celdas y marca como no editables únicamente las pistas.</p>
     *
     * @param sol solución completa 6×6 (valores 1..6)
     */
    private void showTwoCluesPerBlock(int[][] sol) {
        // Limpia tablero y habilita edición en todas
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                TextField tf = cells[r][c];
                tf.setText("");
                tf.setEditable(true);
                board[r][c] = 0;
            }
        }

        // Por cada bloque 2×3 (filas 0-1, 2-3, 4-5; columnas 0-2, 3-5) coloca 2 pistas al azar
        for (int br = 0; br < 6; br += 2) {
            for (int bc = 0; bc < 6; bc += 3) {
                List<int[]> pos = Arrays.asList(
                        new int[]{br,bc},     new int[]{br,bc+1},   new int[]{br,bc+2},
                        new int[]{br+1,bc},   new int[]{br+1,bc+1}, new int[]{br+1,bc+2}
                );
                Collections.shuffle(pos); // aleatoriza posiciones del bloque
                for (int i = 0; i < 2; i++) { // deja 2 pistas
                    int r = pos.get(i)[0], c = pos.get(i)[1];
                    TextField tf = cells[r][c];
                    tf.setText(String.valueOf(sol[r][c]));
                    tf.setEditable(false);     // pista no editable
                    board[r][c] = sol[r][c];   // refleja en estado lógico
                }
            }
        }
    }

    /**
     * Configura entrada por teclado en cada celda editable:
     * <ul>
     *   <li>Acepta solo dígitos 1–6.</li>
     *   <li>Permite borrar con Backspace/Delete.</li>
     *   <li>Actualiza {@link #board} y valida la celda al escribir.</li>
     *   <li>Si se borra el contenido, restaura el estilo base de la celda.</li>
     * </ul>
     */
    private void addInputListeners() {
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                TextField tf = cells[r][c];
                if (tf.isEditable()) {
                    final int row = r;
                    final int col = c;

                    // Filtra teclas: solo 1..6 y permite borrar
                    tf.addEventFilter(KeyEvent.KEY_TYPED, e -> {
                        String ch = e.getCharacter();

                        // Permitir borrar/backspace
                        if (ch.equals("\b") || ch.equals("\u007F")) return;

                        // Aceptar únicamente 1–6
                        if (!ch.matches("[1-6]")) {
                            e.consume();
                            return;
                        }

                        // Escribe el dígito y consume el evento (para evitar doble inserción)
                        tf.setText(ch);
                        e.consume();

                        // Actualiza estado y valida en tiempo real
                        board[row][col] = Integer.parseInt(ch);
                        validateCell(row, col);
                    });

                    // Si el usuario borra manualmente el contenido
                    tf.textProperty().addListener((obs, oldVal, newVal) -> {
                        if (newVal.isEmpty()) {
                            board[row][col] = 0;
                            tf.setStyle(baseStyles[row][col]); // restaura color original
                        }
                    });
                }
            }
        }
    }

    /**
     * Valida una celda concreta y marca visualmente si hay error.
     * <ul>
     *   <li>Si está vacía (0), restaura estilo base.</li>
     *   <li>Si viola reglas, superpone fondo rojo suave.</li>
     *   <li>Si es válida, vuelve a su estilo base.</li>
     * </ul>
     *
     * @param r fila (0..5)
     * @param c columna (0..5)
     */
    private void validateCell(int r, int c) {
        int value = board[r][c];
        TextField tf = cells[r][c];
        String base = baseStyles[r][c]; // estilo original guardado al iniciar

        if (value == 0) {
            tf.setStyle(base);
            return;
        }

        boolean valid = validator.isPlacementValid(board, r, c, value);
        if (!valid) {
            tf.setStyle(base + "; -fx-background-color: lightcoral;"); // resalta error
        } else {
            tf.setStyle(base);
        }
    }

    /**
     * Comprueba el tablero completo cuando el usuario pulsa “Listo”.
     * <ul>
     *   <li>Si hay celdas vacías → alerta “Sudoku incompleto”.</li>
     *   <li>Si hay violaciones de reglas → alerta “Hay errores”.</li>
     *   <li>Si está completo y válido → navega a end.fxml con mensaje de éxito.</li>
     * </ul>
     */
    private void checkFullBoard() {
        boolean completo = true;
        boolean valido = true;

        // Revisa todas las celdas del tablero
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                int v = board[r][c];
                if (v == 0) {
                    completo = false;          // hay una casilla vacía
                } else if (!validator.isPlacementValid(board, r, c, v)) {
                    valido = false;            // hay una violación de reglas
                }
            }
        }

        // Si el tablero está completo y correcto → ir a pantalla final (ganador)
        if (completo && valido) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/sodoku6x6/end.fxml"));
                Scene scene = new Scene(loader.load());

                // Personaliza mensaje en la pantalla final
                EndController endController = loader.getController();
                endController.setEndMessage("¡Felicidades! Sudoku completo y correcto 🎉");

                Stage stage = (Stage) buttonCheck.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Fin del juego");
                stage.show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else {
            // Si hay errores o está incompleto → mostrar alerta informativa
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Resultado");

            if (!completo) {
                alert.setHeaderText("Sudoku incompleto");
                alert.setContentText("Aún tienes casillas vacías. ¡Sigue intentando!");
            } else {
                alert.setHeaderText("Hay errores");
                alert.setContentText("Algunas casillas no cumplen las reglas del Sudoku.");
            }

            alert.showAndWait();
        }
    }

    /**
     * Da una pista: rellena una celda vacía aleatoria con el valor correcto de la solución.
     * <ul>
     *   <li>No permite usar pista si solo queda una celda vacía (para no “cerrar” el tablero por ayuda).</li>
     *   <li>Marca la celda como no editable y tiñe de verde respetando su color base.</li>
     * </ul>
     */
    private void giveHint() {
        // Contar cuántas celdas vacías hay
        int emptyCount = 0;
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                if (board[r][c] == 0) emptyCount++;
            }
        }

        // Si solo queda una celda vacía, no permitir pista (HU-5)
        if (emptyCount <= 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sin más pistas");
            alert.setHeaderText("Ya no puedes usar más pistas");
            alert.setContentText("Debes completar el Sudoku sin ayuda.");
            alert.showAndWait();
            return;
        }

        // Buscar celdas vacías
        java.util.List<int[]> vacias = new java.util.ArrayList<>();
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                if (board[r][c] == 0) vacias.add(new int[]{r, c});
            }
        }
        if (vacias.isEmpty()) return;

        // Elegir una celda vacía al azar
        java.util.Collections.shuffle(vacias);
        int[] pos = vacias.get(0);
        int r = pos[0], c = pos[1];

        // Colocar valor correcto desde la solución
        int valor = solution[r][c];
        board[r][c] = valor;

        TextField tf = cells[r][c];
        tf.setText(String.valueOf(valor));
        tf.setEditable(false);

        // Detectar color base y aplicar un verde agradable encima
        String base = baseStyles[r][c];
        if (base.contains("skyblue")) {
            tf.setStyle(base.replace("skyblue", "#33cc33")); // verde sobre celda azul
        } else {
            tf.setStyle(base + "; -fx-background-color: #B8F7B8;"); // verde pastel sobre blanco
        }
    }
}
