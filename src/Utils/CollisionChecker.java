package Utils;

public class CollisionChecker {
    
    // down collision checker, top, left => offsets , determines current position
    public boolean downCollisionCheck(int[][] grid, int[][] piece, int top, int left) {

        //bottom boundary
        if(top > grid.length - 4) return true; 

        //check down has any blocks
        for(int i = 0;i < piece.length;i++) {
            for(int j = 0;j < piece[0].length;j++) {
                if(piece[i][j] == 0) continue;
                else {
                    boolean isBottomMost = true;
                    for(int k = i + 1; k < piece.length;k++) {
                        if(piece[k][j] != 0) {
                            isBottomMost = false;
                            break;
                        }
                    }
                    if(isBottomMost) {
                        if(grid[i + top + 1][j + left] != 0) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }


}
