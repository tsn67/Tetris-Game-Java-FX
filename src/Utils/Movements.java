package Utils;

import java.util.ArrayList;
import java.util.List;

import Core.Grid;

public class Movements {

    private Grid grid;
    private CollisionChecker collisionController;

    public Movements(Grid grid) {
        this.grid = grid;
        this.collisionController = new CollisionChecker();
    }

    public int rightMovement(int[][] piece, int leftOffset, int topOffset, int colorValue) {
        if (this.collisionController.rightCollisionCheck(grid.grid, piece, topOffset, leftOffset))
            return -1;
        this.clearPieceFromGrid(piece, topOffset, leftOffset);
        leftOffset += 1;
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[0].length; j++) {
                if (piece[i][j] != 0) {
                    if (i + topOffset < grid.grid.length && j + leftOffset < grid.grid[0].length) {
                        grid.grid[i + topOffset][j + leftOffset] = colorValue;
                    }
                }
            }
        }
        return 0;
    }

    public int leftMovement(int[][] piece, int leftOffset, int topOffset, int colorValue) {
        if (this.collisionController.leftCollisionCheck(grid.grid, piece, topOffset, leftOffset))
            return -1;
        this.clearPieceFromGrid(piece, topOffset, leftOffset);
        leftOffset -= 1;
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[0].length; j++) {
                if (piece[i][j] != 0) {
                    if (i + topOffset < grid.grid.length && j + leftOffset < grid.grid[0].length) {
                        grid.grid[i + topOffset][j + leftOffset] = colorValue;
                    }
                }
            }
        }
        return 0;
    }

    // perform down action in the grid, just update teh piece value
    // consider teh function for review
    // collision is not done yet
    public int downMovement(int[][] piece, int leftOffset, int topOffset, int colorValue) {

        // if(topOffset > grid.grid.length - 4) return -1; //block condition

        if (this.collisionController.downCollisionCheck(grid.grid, piece, topOffset, leftOffset))
            return -1;

        this.clearPieceFromGrid(piece, topOffset, leftOffset);

        // Update offset
        topOffset += 1;
        if (topOffset <= 3) {

        }

        // Place piece one row down
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[0].length; j++) {
                if (piece[i][j] != 0) {

                    if (i + topOffset < grid.grid.length && j + leftOffset < grid.grid[0].length) {
                        grid.grid[i + topOffset][j + leftOffset] = colorValue;
                    }
                }
            }
        }

        return 0; // non block condition
    }

    public void clearPieceFromGrid(int[][] piece, int topOffset, int leftOffset) {

        // Clear current piece from grid
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[0].length; j++) {
                if (piece[i][j] != 0) {
                    grid.grid[i + topOffset][j + leftOffset] = 0;
                }
            }
        }

    }

    // rotation using the w and s keys only
    public int[][] rotation(int[][] piece, int topOffset, int leftOffset, boolean isRight, int colorValue) {

        int rows = piece.length;
        int cols = piece[0].length;

        // Step 1: Transpose
        int[][] rotated = new int[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][i] = piece[i][j];
            }
        }

        // Step 2: Reverse each row
        for (int i = 0; i < rotated.length; i++) {
            for (int j = 0, k = rotated[i].length - 1; j < k; j++, k--) {
                int temp = rotated[i][j];
                rotated[i][j] = rotated[i][k];
                rotated[i][k] = temp;
            }
        }

        // call rotate collisioin check, if not collision return the transpose
        if (!collisionController.rotateCollisionCheck(grid.grid, rotated, piece, topOffset, leftOffset)) {
            clearPieceFromGrid(piece, topOffset, leftOffset);
            for (int i = 0; i < rotated.length; i++) {
                for (int j = 0; j < rotated[0].length; j++) {
                    if (rotated[i][j] != 0) {
                        if (i + topOffset < grid.grid.length && j + leftOffset < grid.grid[0].length) {
                            grid.grid[i + topOffset][j + leftOffset] = colorValue;
                        }
                    }
                }
            }
            return rotated;
        } else {
            return piece;
        } // till now only left rotation will change later
    }

    public List<Integer> checkRowFilled() {

        List<Integer> list = new ArrayList<>();
        for (int i = 3; i < grid.grid.length; i++) {
            int flag = 0;
            for (int j = 0; j < grid.grid[0].length; j++) {
                if (grid.grid[i][j] == 0) {
                    flag++;
                    break;
                }
            }

            if (flag == 0) {
                list.add(i);
            }
        }
        return list;
    }

    public void removeRow(int row) {

        for (int i = grid.grid.length - 1; i >= 3; i--) {
            if (i <= row) {
                for (int j = 0; j < grid.grid[0].length; j++) {
                    grid.grid[i][j] = grid.grid[i - 1][j];
                }
            }
        }

    }

}
