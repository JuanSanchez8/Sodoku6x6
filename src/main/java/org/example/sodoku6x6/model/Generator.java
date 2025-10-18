package org.example.sodoku6x6.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Genera una solución válida 6×6 (filas, columnas y bloques 2×3) con backtracking sencillo. */
public class Generator {

    public int[][] generateSolution() {
        int[][] grid = new int[6][6];
        fill(grid, 0, 0);
        return grid;
    }

    private boolean fill(int[][] g, int r, int c) {
        if (r == 6) return true; // terminó
        int nr = (c == 5) ? r + 1 : r;
        int nc = (c == 5) ? 0 : c + 1;

        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= 6; i++) nums.add(i);
        Collections.shuffle(nums); // aleatorio

        for (int v : nums) {
            if (isSafe(g, r, c, v)) {
                g[r][c] = v;
                if (fill(g, nr, nc)) return true;
                g[r][c] = 0;
            }
        }
        return false;
    }

    private boolean isSafe(int[][] g, int r, int c, int v) {
        // fila y columna
        for (int i = 0; i < 6; i++) {
            if (g[r][i] == v || g[i][c] == v) return false;
        }
        // bloque 2×3
        int br = (r / 2) * 2; // 0,2,4
        int bc = (c / 3) * 3; // 0,3
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 3; j++)
                if (g[br + i][bc + j] == v) return false;

        return true;
    }
}
