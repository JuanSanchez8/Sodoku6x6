package org.example.sodoku6x6.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Genera una solución válida para un Sudoku 6×6 mediante backtracking sencillo.
 *
 * <p><b>Reglas que respeta:</b> no hay repetidos en filas, columnas ni en
 * cada bloque 2×3.</p>
 *
 * <p><b>Convenciones:</b> 0 representa “celda vacía” durante el proceso.
 * El resultado retornado es una matriz 6×6 completamente llena con valores 1..6.</p>
 */
public class Generator {

    private static final int N = 6;     // tamaño del tablero
    private static final int BOX_R = 2; // filas por bloque
    private static final int BOX_C = 3; // columnas por bloque

    /**
     * Construye y retorna una solución completa 6×6.
     * @return matriz 6×6 con valores 1..6 que cumple todas las reglas
     */
    public int[][] generateSolution() {
        int[][] grid = new int[N][N];
        fill(grid, 0, 0);
        return grid;
    }

    /**
     * Backtracking: intenta llenar la celda (r,c) y continúa.
     * Baraja los números 1..6 para obtener soluciones variadas.
     */
    private boolean fill(int[][] g, int r, int c) {
        if (r == N) return true; // terminó (pasó de la última fila)

        int nr = (c == N - 1) ? r + 1 : r;   // siguiente fila
        int nc = (c == N - 1) ? 0     : c + 1; // siguiente columna

        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= N; i++) nums.add(i);
        Collections.shuffle(nums); // orden aleatorio para variedad

        for (int v : nums) {
            if (isSafe(g, r, c, v)) {
                g[r][c] = v;
                if (fill(g, nr, nc)) return true; // continuar
                g[r][c] = 0; // backtrack
            }
        }
        return false; // no hubo valor válido aquí
    }

    /**
     * Comprueba si poner v en (r,c) no rompe fila, columna ni bloque 2×3.
     */
    private boolean isSafe(int[][] g, int r, int c, int v) {
        // Fila y columna
        for (int i = 0; i < N; i++) {
            if (g[r][i] == v || g[i][c] == v) return false;
        }

        // Bloque 2×3 que contiene (r,c)
        int br = (r / BOX_R) * BOX_R; // 0,2,4
        int bc = (c / BOX_C) * BOX_C; // 0,3
        for (int i = 0; i < BOX_R; i++) {
            for (int j = 0; j < BOX_C; j++) {
                if (g[br + i][bc + j] == v) return false;
            }
        }
        return true;
    }
}
