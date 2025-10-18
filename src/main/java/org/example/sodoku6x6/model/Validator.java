package org.example.sodoku6x6.model;

import org.example.sodoku6x6.api.BoardValidator;

/**
 * Validador de Sudoku 6×6.
 *
 * <p>Reglas verificadas:</p>
 * <ul>
 *   <li>Cada fila contiene valores únicos entre 1 y 6.</li>
 *   <li>Cada columna contiene valores únicos entre 1 y 6.</li>
 *   <li>Cada bloque 2×3 contiene valores únicos entre 1 y 6.</li>
 * </ul>
 *
 * <p>Convenciones:</p>
 * <ul>
 *   <li>La matriz {@code g} es de tamaño 6×6.</li>
 *   <li>El valor {@code 0} representa una celda vacía.</li>
 *   <li>Índices de fila/columna en el rango {@code 0..5}.</li>
 * </ul>
 */
public class Validator implements BoardValidator {

    private static final int N = 6;     // tamaño del tablero
    private static final int BOX_R = 2; // filas por bloque
    private static final int BOX_C = 3; // columnas por bloque

    /**
     * Verifica si colocar {@code v} en (r,c) respeta fila, columna y bloque 2×3.
     * <p><b>Importante:</b> ignora la propia celda (r,c) para evitar falsos positivos.</p>
     *
     * @param g tablero 6×6 (0 = vacío)
     * @param r fila 0..5
     * @param c columna 0..5
     * @param v valor 1..6
     * @return {@code true} si no hay duplicados en fila/columna/bloque; {@code false} en caso contrario
     */
    @Override
    public boolean isPlacementValid(int[][] g, int r, int c, int v) {
        if (v < 1 || v > N) return false;

        // Fila (omite la propia columna)
        for (int x = 0; x < N; x++) {
            if (x != c && g[r][x] == v) return false;
        }

        // Columna (omite la propia fila)
        for (int y = 0; y < N; y++) {
            if (y != r && g[y][c] == v) return false;
        }

        // Bloque 2×3 que contiene (r,c)
        int br = (r / BOX_R) * BOX_R; // 0,2,4
        int bc = (c / BOX_C) * BOX_C; // 0,3
        for (int i = 0; i < BOX_R; i++) {
            for (int j = 0; j < BOX_C; j++) {
                int rr = br + i, cc = bc + j;
                if ((rr != r || cc != c) && g[rr][cc] == v) return false;
            }
        }
        return true;
    }

    /**
     * Recorre todo el tablero y verifica que no existan violaciones de reglas.
     * <p>Las celdas con 0 (vacías) se ignoran.</p>
     *
     * @param g tablero 6×6 (0 = vacío)
     * @return {@code true} si no hay violaciones; {@code false} si encuentra alguna
     */
    @Override
    public boolean isValid(int[][] g) {
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int v = g[r][c];
                if (v == 0) continue;                 // ignorar vacías
                if (!isPlacementValid(g, r, c, v))     // validar respetando (r,c)
                    return false;
            }
        }
        return true;
    }
}
