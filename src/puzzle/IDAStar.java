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
    private static final int[] directionX = {1, 0, -1, 0};       //分别对应下右上左
    private static final int[] directionY = {0, 1, 0, -1};

    private static final char[] direction = {'d', 'r', 'u', 'l'};   //d=0,r=1,u=2,l=3
    private static final int[] oppositeDirection = {2, 3, 0, 1};    //drul的反方向为uldr,分别对应2301

    private int[][] tile;               //存放排列的矩阵
    private int ORDER;                  //游戏的难度，即阶数
    private int upper = 0;              //
    private boolean pass;               //记录是否找到路径
    private int pathOfLength;           //路径长度
    private StringBuilder routine;      //路径

    public IDAStar(int[] array) {

        ORDER = (int) Math.sqrt(array.length);
        pathOfLength = 0;
        initializeTile(array);
    }

    /**
     *
     * @param depth             //函数调用深度
     * @param row               //空格子所在行数，这里对应排列中的最大数
     * @param col               //空格子所在列数
     * @param est               //代价
     * @param preDirection      //上一个方向，避免走回头路
     */
    public void IDAS(int depth, int row, int col, int est, int preDirection) {
        int length = ORDER * ORDER;

        if (est == 0 || this.pass) {
            this.pathOfLength = depth;
            this.pass = true;
            return;
        }
        for (int i = 0; i < 4; i++) {
            if (i != preDirection) {                //不走回头路
                int newRow = row + directionX[i];
                int newCol = col + directionY[i];
                int oCost = 0, nCost = 0, temp = 0;

                if (isValid(newRow, newCol)) {      //判断移动是否有效
                    temp = tile[newRow][newCol];
                    int tx = temp / tile.length;
                    int ty = temp % tile.length;

                    oCost = getManhattanDistance(newRow, newCol, tx, ty);   //未移动前，被交换数的曼哈顿距离
                    nCost = getManhattanDistance(row, col, tx, ty);         //移动后，被交换数的曼哈顿距离
                    int h = est + nCost - oCost + 1 ;                       //移动后的曼哈顿距离
                    if (depth + h<= upper) {         //当前调用深度+移动后的曼哈顿距离<=之前的曼哈顿距离则接着走
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

    //代价函数
    private int heuristic(int[][] tile) {
        int manhattanDistance = 0;
        int length = tile.length * tile.length - 1;
        for (int i = 0; i < tile.length; i++) {
            for (int j = 0; j < tile[i].length; j++) {
                if (tile[i][j] != length) {
                    int tx = tile[i][j] / tile.length;
                    int ty = tile[i][j] % tile.length;

                    manhattanDistance += getManhattanDistance(i, j, tx, ty);
                }
            }
        }
        return manhattanDistance;
    }

    //曼哈顿距离
    private int getManhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private void initializeTile(int[] array) {
        this.tile = new int[ORDER][ORDER];
        for (int i = 0; i < array.length; i++) {
            this.tile[i / ORDER][i % ORDER] = array[i];
        }
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
