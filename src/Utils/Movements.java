package Utils;

import Core.Grid;

public class Movements {

    private Grid grid;

    public Movements(Grid grid) {
        this.grid = grid;
    }

    // perform down action in the grid, just update teh piece value
    // consider teh function for review
    // collision is not done yet
    public int downMovement(int[][] piece, int leftOffset, int topOffset, int colorValue) {
        
        if(topOffset > grid.grid.length - 4) return -1; //block condition

        /*
         * need to detect other collisions also
         * 
         */


        this.clearPieceFromGrid(piece, topOffset, leftOffset);

        // Update offset
        topOffset += 1;

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

        return 0; //non block condition
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

}
