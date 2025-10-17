package org.example.sodoku6x6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController {
    private static final int SIZE = 6;
    private static final int BOX_ROWS = 2;
    private static final int BOX_COLS = 3;
    private int[][] grid = new int[SIZE][SIZE];

    public int[][] getGrid() {
        return grid;
    }

    public boolean fillGrid(int row, int col) {
        if (row == SIZE) return true;
        int nextRow = (col == SIZE - 1) ? row + 1 : row;
        int nextCol = (col == SIZE - 1) ? 0 : col + 1;

        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= SIZE; i++) nums.add(i);
        Collections.shuffle(nums);

        for (int num : nums) {
            if (isSafe(row, col, num)) {
                grid[row][col] = num;
                if (fillGrid(nextRow, nextCol)) return true;
                grid[row][col] = 0;
            }
        }
        return false;
    }

    private boolean isSafe(int row, int col, int num) {
        for (int i = 0; i < SIZE; i++)
            if (grid[row][i] == num || grid[i][col] == num) return false;

        int boxRow = (row / BOX_ROWS) * BOX_ROWS;
        int boxCol = (col / BOX_COLS) * BOX_COLS;

        for (int i = 0; i < BOX_ROWS; i++)
            for (int j = 0; j < BOX_COLS; j++)
                if (grid[boxRow + i][boxCol + j] == num) return false;

        return true;
    }
}
