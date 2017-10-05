/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

/**
 *
 * @author zpppppp
 */
public class IDAStar {

    private static final int MAXSTEP = 200;
    private static final int[] directionX = {1, 0, -1, 0};
    private static final int[] directionY = {0, 1, 0, -1};

    private static final char[] direction = {'d', 'r', 'u', 'l'};
    private static final int[] oppositeDirection = {2, 3, 0, 1};

    private int[][] tile;
    private int ORDER;
    private int upper = 0;
    private boolean pass;
    private int pathOfLength;
    private StringBuilder routine;

    /**
     *
     * @param array
     */
    public IDAStar(int[] array) {

        ORDER = (int) Math.sqrt(array.length);
        pathOfLength = 0;

        this.tile = new int[ORDER][ORDER];
        for (int i = 0; i < array.length; i++) {
            this.tile[i / ORDER][i % ORDER] = array[i];
        }
    }

    /**
     *
     * @param depth
     * @param row
     * @param col
     * @param est
     * @param preDirection
     */
    public void IDAS(int depth, int row, int col, int est, int preDirection) {
        int length = ORDER * ORDER;

        if (est == 0 || this.pass) {
            this.pathOfLength = depth;
            this.pass = true;
            return;
        }
        for (int i = 0; i < 4; i++) {
            if (i != preDirection) {
                int newRow = row + directionX[i];
                int newCol = col + directionY[i];
                int oCost = 0, nCost = 0, temp = 0;

                if (isValid(newRow, newCol)) {
                    temp = tile[newRow][newCol];
                    int tx = temp / tile.length;
                    int ty = temp % tile.length;

                    oCost = Math.abs(newRow - tx) + Math.abs(newCol - ty);
                    nCost = Math.abs(row - tx) + Math.abs(col - ty);
                    if (depth + est + nCost - oCost + 1 <= upper) {
                        tile[row][col] = temp;
                        tile[newRow][newCol] = length - 1;

                        routine.append(direction[i]);
                        routine.setCharAt(depth, direction[i]);

                        IDAS(depth + 1, newRow, newCol, est + nCost - oCost, oppositeDirection[i]);
                        tile[row][col] = length - 1;
                        tile[newRow][newCol] = temp;
                        if (pass) {
                            return;
                        }
                    }
                }
            }
        }

    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < ORDER && col >= 0 && col < ORDER;
    }

    private int heuristic(int[][] tile) {
        int manhattanDistance = 0;
        int length = tile.length * tile.length - 1;
        for (int i = 0; i < tile.length; i++) {
            for (int j = 0; j < tile[i].length; j++) {
                if (tile[i][j] != length) {
                    int tx = tile[i][j] / tile.length;
                    int ty = tile[i][j] % tile.length;
                    manhattanDistance += Math.abs(i - tx) + Math.abs(j - ty);
                }
            }
        }
        return manhattanDistance;
    }

    public String getPath() {
        return routine.substring(0, pathOfLength);
    }

    public void init() {
        int length = ORDER * ORDER;
        int startRow = ORDER - 1;
        int startCol = ORDER - 1;

        for (int i = 0; i < length; i++) {
            if (this.tile[i / ORDER][i % ORDER] == length - 1) {
                startRow = i / ORDER;
                startCol = i % ORDER;
                break;
            }
        }
        routine = new StringBuilder();

        int cost = heuristic(this.tile);
        this.upper = Math.min(MAXSTEP, cost + 1);
        while (!this.pass) {
            IDAS(0, startRow, startCol, cost, -1);
            this.upper = Math.min(upper + 1, MAXSTEP);
        }
    }
}
