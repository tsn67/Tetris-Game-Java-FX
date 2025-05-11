package Utils;

public class CollisionChecker {

    public boolean rightCollisionCheck(int[][] grid, int[][] piece, int top, int left) {

        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[0].length; j++) {
                if (piece[i][j] == 0)
                    continue;
                else {
                    boolean isRightMost = true;
                    for (int k = j + 1; k < piece[0].length; k++) {
                        if (piece[i][k] != 0) {
                            isRightMost = false;
                            break;
                        }
                    }

                    // second condition is the right boundary
                    if (isRightMost) {
                        if ((j + left + 1) >= grid[0].length || grid[i + top][j + left + 1] != 0) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean leftCollisionCheck(int[][] grid, int[][] piece, int top, int left) {

        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[0].length; j++) {
                if (piece[i][j] == 0)
                    continue;
                else {
                    boolean isLeftMost = true;
                    for (int k = j - 1; k >= 0; k--) {
                        if (piece[i][k] != 0) {
                            isLeftMost = false;
                            break;
                        }
                    }

                    // second condition is the left boundary
                    if (isLeftMost) {
                        if ((j + left - 1) < 0 || grid[i + top][j + left - 1] != 0) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    // down collision checker, top, left => offsets , determines current position
    public boolean downCollisionCheck(int[][] grid, int[][] piece, int top, int left) {

        // check down has any blocks
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[0].length; j++) {
                if (piece[i][j] == 0)
                    continue;
                else {
                    boolean isBottomMost = true;
                    for (int k = i + 1; k < piece.length; k++) {
                        if (piece[k][j] != 0) {
                            isBottomMost = false;
                            break;
                        }
                    }
                    if (isBottomMost) {
                        if (i + top + 1 >= grid.length || grid[i + top + 1][j + left] != 0) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean rotateCollisionCheck(int[][] grid, int[][] rotatedPiece, int[][] piece, int top, int left) {
        int newRows = rotatedPiece.length;
        int newCols = rotatedPiece[0].length;

        int oldRows = piece.length;
        int oldCols = piece[0].length;

        for (int i = 0; i < newRows; i++) {
            for (int j = 0; j < newCols; j++) {

                int gridRow = top + i;
                int gridCol = left + j;

                // Check if rotated piece goes out of bounds
                if (gridRow < 0 || gridRow >= grid.length || gridCol < 0 || gridCol >= grid[0].length) {
                    return true;
                }

                // Allow overlapping with its current position (i.e., ignore cells from the old
                // piece)
                boolean isInOldPiece = false;
                int oldI = gridRow - top;
                int oldJ = gridCol - left;
                if (oldI >= 0 && oldI < oldRows && oldJ >= 0 && oldJ < oldCols) {
                    if (piece[oldI][oldJ] != 0) {
                        isInOldPiece = true;
                    }
                }

                if (!isInOldPiece && grid[gridRow][gridCol] != 0) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean gameEndCheck(int[][] grid, int[][] piece, int leftOffset, int topOffset) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != 0)
                    return true;
            }
        }
        return false;
    }

}
