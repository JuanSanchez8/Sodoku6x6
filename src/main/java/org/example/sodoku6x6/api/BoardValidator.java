package org.example.sodoku6x6.api;

/**
 * Contrato para validar un tablero de Sudoku 6×6.
 *
 * <p><b>Convenciones:</b>
 * <ul>
 *   <li>La matriz {@code grid} es de tamaño 6×6.</li>
 *   <li>El valor {@code 0} representa una celda vacía.</li>
 *   <li>Los valores válidos para celdas llenas son del {@code 1} al {@code 6}.</li>
 *   <li>Los índices de fila y columna están en el rango {@code 0..5}.</li>
 * </ul>
 * </p>
 */
public interface BoardValidator {

    /**
     * Verifica si colocar {@code value} en la posición ({@code row}, {@code col})
     * cumple las reglas del Sudoku 6×6:
     * <ul>
     *   <li>Sin repetidos en la <b>fila</b> correspondiente.</li>
     *   <li>Sin repetidos en la <b>columna</b> correspondiente.</li>
     *   <li>Sin repetidos en el <b>bloque 2×3</b> correspondiente.</li>
     * </ul>
     *
     * <p>La verificación debe <b>ignorar la propia celda</b> ({@code row}, {@code col})
     * para evitar falsos positivos al validar un valor ya colocado.</p>
     *
     * @param grid matriz 6×6 que representa el tablero (0 = vacío)
     * @param row  índice de fila (0..5)
     * @param col  índice de columna (0..5)
     * @param value valor a validar (1..6)
     * @return {@code true} si no se viola ninguna regla en fila, columna y bloque 2×3; {@code false} en caso contrario
     */
    boolean isPlacementValid(int[][] grid, int row, int col, int value);

    /**
     * Verifica si el tablero completo no viola las reglas del Sudoku 6×6.
     * <p>Las celdas con valor {@code 0} (vacías) se ignoran durante la validación.</p>
     *
     * <p><b>Nota:</b> Este método <i>no</i> exige que el tablero esté completo;
     * solo comprueba que el estado actual es válido. Para considerar “resuelto”,
     * puede usarse una verificación adicional que exija ausencia de ceros.</p>
     *
     * @param grid matriz 6×6 que representa el tablero (0 = vacío)
     * @return {@code true} si no hay violaciones en filas, columnas ni bloques 2×3; {@code false} en caso contrario
     */
    boolean isValid(int[][] grid);
}
