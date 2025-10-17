package org.example.sodoku6x6;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class StartController {

    // === Todas las celdas del tablero ===
    @FXML private TextField cell00; @FXML private TextField cell01; @FXML private TextField cell02;
    @FXML private TextField cell03; @FXML private TextField cell04; @FXML private TextField cell05;

    @FXML private TextField cell10; @FXML private TextField cell11; @FXML private TextField cell12;
    @FXML private TextField cell13; @FXML private TextField cell14; @FXML private TextField cell15;

    @FXML private TextField cell20; @FXML private TextField cell21; @FXML private TextField cell22;
    @FXML private TextField cell23; @FXML private TextField cell24; @FXML private TextField cell25;

    @FXML private TextField cell30; @FXML private TextField cell31; @FXML private TextField cell32;
    @FXML private TextField cell33; @FXML private TextField cell34; @FXML private TextField cell35;

    @FXML private TextField cell40; @FXML private TextField cell41; @FXML private TextField cell42;
    @FXML private TextField cell43; @FXML private TextField cell44; @FXML private TextField cell45;

    @FXML private TextField cell50; @FXML private TextField cell51; @FXML private TextField cell52;
    @FXML private TextField cell53; @FXML private TextField cell54; @FXML private TextField cell55;

    @FXML
    public void initialize() {
        // Generar Sudoku resuelto
        GameController sudoku = new GameController();
        sudoku.fillGrid(0, 0);
        int[][] tablero = sudoku.getGrid();

        // Cargar los valores en las celdas
        TextField[][] cells = {
                {cell00, cell01, cell02, cell03, cell04, cell05},
                {cell10, cell11, cell12, cell13, cell14, cell15},
                {cell20, cell21, cell22, cell23, cell24, cell25},
                {cell30, cell31, cell32, cell33, cell34, cell35},
                {cell40, cell41, cell42, cell43, cell44, cell45},
                {cell50, cell51, cell52, cell53, cell54, cell55}
        };

        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                cells[r][c].setText(String.valueOf(tablero[r][c]));
                cells[r][c].setEditable(false);
                cells[r][c].setStyle("-fx-alignment: center; -fx-font-size: 40;");
            }
        }
    }
}
