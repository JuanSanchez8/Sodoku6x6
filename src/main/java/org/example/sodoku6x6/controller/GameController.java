package org.example.sodoku6x6.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.sodoku6x6.model.Generator;

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

    private TextField[][] cells;

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

        int[][] solution = new Generator().generateSolution(); // aleatorio cada vez
        showTwoCluesPerBlock(solution);                        // 2 pistas por bloque 2×3
    }

    private void showTwoCluesPerBlock(int[][] sol) {
        // Limpiar
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                TextField tf = cells[r][c];
                tf.setText("");
                tf.setEditable(true);
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
                    tf.getStyleClass().add("cell-fixed"); // si tienes este estilo en CSS
                }
            }
        }
    }
}
