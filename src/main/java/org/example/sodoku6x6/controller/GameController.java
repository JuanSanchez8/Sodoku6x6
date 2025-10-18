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

public class GameController {

    // ids a1..f6 (por filas)
    @FXML private TextField a1,b1,c1,d1,e1,f1;
    @FXML private TextField a2,b2,c2,d2,e2,f2;
    @FXML private TextField a3,b3,c3,d3,e3,f3;
    @FXML private TextField a4,b4,c4,d4,e4,f4;
    @FXML private TextField a5,b5,c5,d5,e5,f5;
    @FXML private TextField a6,b6,c6,d6,e6,f6;
    @FXML private Button buttonCheck;
    @FXML private Button buttonHelp;

    private TextField[][] cells;
    private int[][] board; // guarda los números actuales
    private int[][] solution;
    private Validator validator = new Validator();
    private String[][] baseStyles;



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
        solution = new Generator().generateSolution(); // aleatorio cada vez
        showTwoCluesPerBlock(solution);                   // 2 pistas por bloque 2×3
        addInputListeners();
        baseStyles = new String[6][6];
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                baseStyles[r][c] = cells[r][c].getStyle(); // guarda el color original (azul/blanco)
            }
        }
    }

    //Click al boton para comprobar si ya esta el tablero
    @FXML void onMouseCheck(MouseEvent event) {
        checkFullBoard();
    }

    //Accion de boton para las pistas
    @FXML void onActionAyuda(ActionEvent event) {
        giveHint();
    }


    /** Genera tablero inicial (2 pistas por bloque 2x3) */
    private void showTwoCluesPerBlock(int[][] sol) {

        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                TextField tf = cells[r][c];
                tf.setText("");
                tf.setEditable(true);
                board[r][c] = 0;
            }
        }

        // Por cada bloque 2×3, deja 2 números (bloqueados)
        for (int br = 0; br < 6; br += 2) {      // filas: 0-1, 2-3, 4-5
            for (int bc = 0; bc < 6; bc += 3) {  // cols: 0-2, 3-5
                List<int[]> pos = Arrays.asList(
                        new int[]{br,bc},     new int[]{br,bc+1},   new int[]{br,bc+2},
                        new int[]{br+1,bc},   new int[]{br+1,bc+1}, new int[]{br+1,bc+2}
                );
                Collections.shuffle(pos);
                for (int i = 0; i < 2; i++) { // 2 pistas
                    int r = pos.get(i)[0], c = pos.get(i)[1];
                    TextField tf = cells[r][c];
                    tf.setText(String.valueOf(sol[r][c]));
                    tf.setEditable(false);
                    board[r][c] = sol[r][c];
                }
            }
        }
    }
    /** Permite ingreso y validación en tiempo real */
    private void addInputListeners() {
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                TextField tf = cells[r][c];
                if (tf.isEditable()) {
                    final int row = r;
                    final int col = c;

                    tf.addEventFilter(KeyEvent.KEY_TYPED, e -> {
                        String ch = e.getCharacter();

                        // Permitir borrar
                        if (ch.equals("\b") || ch.equals("\u007F")) return;

                        // Solo aceptar 1–6
                        if (!ch.matches("[1-6]")) {
                            e.consume();
                            return;
                        }

                        tf.setText(ch);
                        e.consume();

                        board[row][col] = Integer.parseInt(ch);
                        validateCell(row, col); // validar al escribir
                    });

                    // Si se borra manualmente
                    tf.textProperty().addListener((obs, oldVal, newVal) -> {
                        if (newVal.isEmpty()) {
                            board[row][col] = 0;
                            tf.setStyle(baseStyles[row][col]); // vuelve al color original guardado
                        }
                    });
                }
            }
        }
    }

    /** Valida una celda y marca si hay error */
    private void validateCell(int r, int c) {
        int value = board[r][c];
        TextField tf = cells[r][c];
        String base = baseStyles[r][c]; // ahora tomamos el estilo original real

        if (value == 0) {
            tf.setStyle(base); // restaura su color base (azul o blanco)
            return;
        }

        boolean valid = validator.isPlacementValid(board, r, c, value);

        if (!valid) {
            tf.setStyle(base + "; -fx-background-color: lightcoral;"); // sobrepone rojo
        } else {
            tf.setStyle(base); // vuelve a su color original
        }
    }

    private void checkFullBoard() {
        boolean completo = true;
        boolean valido = true;

        // Revisa todas las celdas del tablero
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                int v = board[r][c];
                if (v == 0) {
                    completo = false; // hay una casilla vacía
                } else if (!validator.isPlacementValid(board, r, c, v)) {
                    valido = false; // hay una violación de reglas
                }
            }
        }

        // Si el tablero está completo y correcto → ir a pantalla final (ganador)
        if (completo && valido) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/sodoku6x6/end.fxml"));
                Scene scene = new Scene(loader.load());

                // Pasar mensaje personalizado al EndController
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
            // Si hay errores o está incompleto → mostrar alerta
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

    /** Da una pista (rellena una celda correcta aleatoria con color según su fondo original) */
    private void giveHint() {
        // Contar cuántas celdas vacías hay
        int emptyCount = 0;
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                if (board[r][c] == 0) emptyCount++;
            }
        }

        // Si solo queda una celda vacía, no permitir pista
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

        // Detectar color base del cuadro
        String base = baseStyles[r][c];

        // Determinar tono de verde según fondo original
        if (base.contains("skyblue")) {
            tf.setStyle(base.replace("skyblue", "#33cc33")); // verde suave sobre azul
        } else {
            tf.setStyle(base + "; -fx-background-color: #B8F7B8;"); // verde pastel para fondo blanco
        }
    }

}
