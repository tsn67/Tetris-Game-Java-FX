package Utils;

import Core.Grid;
import javafx.application.Platform;
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
    
    public Grid grid;
    private Random random;
    public boolean isGameOver = false; //to check game status (active|game over)
    public boolean pieceActive = false; //to know wehther there is a piece currently faling

    public GameEngine(Grid grid) {
        this.grid = grid;
        this.random = new Random();
       
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
        
        
        Thread gameThread = new Thread(new GameRunner(this));
        gameThread.setDaemon(true); // close thread when main window is closed
        gameThread.start();

        //things to do after game over
    }

}

//entire game logic will be found inside this Task class (Thread)
class GameRunner extends Task<Void> {

    private GameEngine gameLoop;

    public GameRunner(GameEngine gameLoop) {
        this.gameLoop = gameLoop;
        this.movementController = new Movements(this.gameLoop.grid);
    }

    private int colorValue;
    private int[][] currentPiece; //always 3 x 3 matrix
    //private int[][] prevPiece; //dimension of the current piece , before updation

    private int timer = 0;
    private int leftOffset = 2;
    private int topOffset = 0;
    private Movements movementController;

    int testTimer = 0;

    @Override
    protected Void call() throws Exception {
        while (true) {
            if(!gameLoop.pieceActive) {
                gameLoop.pieceActive = true;
                colorValue = gameLoop.generateColor();
                currentPiece = gameLoop.generatePiece(); 
                topOffset = 0;
                leftOffset = 5; 
                timer = 0;
            } 

            //logic to perform actions againts user inputs

            
            if(timer > 2) {
                timer = 0;
                //call down motion
                int block =  this.movementController.downMovement(currentPiece, leftOffset, topOffset, colorValue);
                if(block == -1) {
                    this.gameLoop.pieceActive = false;
                }
                topOffset++;
                
                
                Platform.runLater(() -> {
                    gameLoop.grid.updateGrid();
                });
            } else {
                timer++;
            }
           
            testTimer++;
            if(gameLoop.isGameOver) break; //for testing only
            Thread.sleep(100); 
        }

        gameLoop.isGameOver = true;
        return null;
    }
}