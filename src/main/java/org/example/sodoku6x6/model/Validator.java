package org.example.sodoku6x6.model;

/**
 * Valida posiciones y el tablero completo para Sudoku 6x6.
 */
public class Validator {

    public boolean isPlacementValid(int[][] g, int r, int c, int v) {
        if (v < 1 || v > 6) return false;

        // Fila (ignora la misma celda)
        for (int x = 0; x < 6; x++) {
            if (x != c && g[r][x] == v) return false;
        }
        // Columna
        for (int y = 0; y < 6; y++) {
            if (y != r && g[y][c] == v) return false;
        }
        // Bloque 2x3
        int br = (r / 2) * 2;
        int bc = (c / 3) * 3;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                int rr = br + i, cc = bc + j;
                if ((rr != r || cc != c) && g[rr][cc] == v) return false;
            }
        }
        return true;
    }

    public boolean isValid(int[][] g) {
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                int v = g[r][c];
                if (v == 0) continue;
                if (!isPlacementValid(g, r, c, v)) return false;
            }
        }
        return true;
    }
}
