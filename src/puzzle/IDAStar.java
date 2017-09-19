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

    private  static final int MAXSTEP = 200;
    private  static final int[] directionX = {1, 0, -1, 0};
    private  static final int[] directionY = {0, 1, 0, -1};

    private  static final char[] direction = {'d', 'r', 'u', 'l'};
    private  static final int[] oppositeDirection = {2, 3, 0, 1};

    private int[][] tile;
    private int upper = 0;
    private boolean pass;
    private int pathOfLength;
    private char[] pathOfMovement = new char[MAXSTEP];

    public IDAStar(int[] array) {
        int order = (int) Math.sqrt(array.length);
        pathOfLength = 0;

        this.tile = new int[order][order];
        for (int i = 0; i < array.length; i++) {
            this.tile[i / order][i % order] = array[i];
        }
    }

    public void IDAS(int depth, int row, int col, int est, int preDirection) {
        int length = this.tile.length * this.tile.length;

        if (this.pass) {
            return;
        }
        if (est == 0) {
            this.pathOfLength = depth;
            this.pass = true;
            return;
        }
        for (int i = 0; i < 4; i++) {
            if (i != preDirection) {
                int newRow = row + directionX[i];
                int newCol = col + directionY[i];
                int oCost = 0, nCost = 0, temp = 0;

                if (isValid(newRow, newCol, tile.length)) {
                    temp = tile[newRow][newCol];
                    int tx = temp / tile.length;
                    int ty = temp % tile.length;

                    oCost = Math.abs(newRow - tx) + Math.abs(newCol - ty);
                    nCost = Math.abs(row - tx) + Math.abs(col - ty);
                    if (depth + est + nCost - oCost + 1 <= upper) {
                        tile[row][col] = temp;
                        tile[newRow][newCol] = length - 1;
                        pathOfMovement[depth] = direction[i];
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

    private boolean isValid(int row, int col, int order) {
        return row >= 0 && row < order && col >= 0 && col < order;
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

    public char[] getPath() {
        char[] path = new char[this.pathOfLength];
        for (int i = 0; i < path.length; i++) {
            path[i] = pathOfMovement[i];
        }
        return path;
 //       return pathOfMovement;
    }

    public void init() {
        int order = this.tile.length;
        int length = order * order;
        int startRow = order - 1;
        int startCol = order - 1;

        for (int i = 0; i < length; i++) {
            if (this.tile[i / order][i % order] == length - 1) {
                startRow = i / order;
                startCol = i % order;
                break;
            }
        }
        int cost = heuristic(this.tile);
        this.upper = Math.min(MAXSTEP, cost + 1);
        while (!this.pass) {
            IDAS(0, startRow, startCol, cost, -1);
            this.upper = Math.min(upper + 1, MAXSTEP);
        }

    }
}
