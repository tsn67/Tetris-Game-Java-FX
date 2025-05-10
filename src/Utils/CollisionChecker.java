package Utils;

public class CollisionChecker {
    
    public boolean rightCollisionCheck(int[][] grid, int[][] piece, int top, int left) {
        
        
        for(int i = 0;i < piece.length;i++) {
            for(int j = 0;j < piece[0].length;j++) {
                if(piece[i][j] == 0) continue;
                else {
                    boolean isRightMost = true;
                    for(int k = j + 1; k < piece[0].length;k++) {
                        if(piece[i][k] != 0) {
                            isRightMost = false;
                            break;
                        }
                    }

 
			
		    //second condition is the right boundary
                    if(isRightMost) {
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
        
        
        for(int i = 0;i < piece.length;i++) {
            for(int j = 0;j < piece[0].length;j++) {
                if(piece[i][j] == 0) continue;
                else {
                    boolean isLeftMost = true;
                    for(int k = j - 1; k >= 0;k--) {
                        if(piece[i][k] != 0) {
                            isLeftMost = false;
                            break;
                        }
                    }

                    //second condition is the right boundary
                    if(isLeftMost) {
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
