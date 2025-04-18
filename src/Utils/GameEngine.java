package Utils;

import Core.Grid;
import javafx.concurrent.Task;
import java.util.Random;
import Assets.Tetrispiece;

/*
 * This class contains the thread(UI), that update the grid, current tetris peice, 
 * User control (rotation, left or right movement)
 * Collision detection control (may be collision detection performed by other classes, (within util))
 * All functions should be real time
 */

public class GameEngine {
    
    Grid grid;
    Random random;
    
    public GameEngine(Grid grid) {
        this.grid = grid;
        random = new Random();
    }

    
    public int[][] generatePiece() {
        int[][] testrisPiece = Tetrispiece.pieces[random.nextInt(Tetrispiece.pieces.length)]; 
        return testrisPiece;
    }


    //no need to return the String color, just retunr the index of the color
    public int generateColor() {
        int randomColor = random.nextInt(Tetrispiece.colors.length);
        return randomColor + 1; //return 1+ indicate grid is not empty
    }

    //this methode will start the gameloop(thread)
    public void startGame() {
        
        Task<Void> task = new Task<>() {
            
            
            @Override
            protected Void call() throws Exception {
                
                /*importan: only one infinite loop in the game */
                while(true) {
                
                    Thread.sleep(100);
                    break;
                }

                return null;
            }
        };

        Thread gameThread = new Thread(task);
        gameThread.setDaemon(true); // close thread when main window is closed
        gameThread.start();
    }

}


