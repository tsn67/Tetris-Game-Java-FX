package Utils;

import Core.Grid;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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
        
        
        GameRunner gameThread = new GameRunner(this);
       
        gameThread.start();

        //things to do after game over
    }

}
// GameRunner.java (inner class inside GameEngine)
class GameRunner extends AnimationTimer {

    private GameEngine gameLoop;
    private Movements movementController;

    private int[][] currentPiece;
    private int colorValue;

    private int leftOffset = 4;
    private int topOffset = 0;

    private long lastFallUpdate = 0;
    private long lastMoveDown = 0;

    private final long FALL_INTERVAL = 700_000_000;  // 500 ms
    private final long DOWN_INTERVAL = 40_000_000;  // 100 ms (for held key repeat)

    private Set<KeyCode> userInputs;

    public GameRunner(GameEngine gameLoop) {
        this.gameLoop = gameLoop;
        this.movementController = new Movements(this.gameLoop.grid);
        this.userInputs = new HashSet<>();

        // Allow Pane to receive key inputs
        this.gameLoop.grid.mainContainer.setFocusTraversable(true);

        // Handle key press
        this.gameLoop.grid.mainContainer.setOnKeyPressed(event -> {
            userInputs.add(event.getCode());
        });

        // Handle key release
        this.gameLoop.grid.mainContainer.setOnKeyReleased(event -> {
            userInputs.remove(event.getCode());
        });
    }

    @Override
    public void handle(long now) {
        if (gameLoop.isGameOver) {
            stop();
            return;
        }

        // Generate new piece
        if (!gameLoop.pieceActive) {
            gameLoop.pieceActive = true;
            currentPiece = gameLoop.generatePiece();
            colorValue = gameLoop.generateColor();
            topOffset = 0;
            leftOffset = 4;
            lastFallUpdate = now;
            lastMoveDown = now;
        }

        // Fast down movement (when holding D key)
        if (userInputs.contains(KeyCode.S) && now - lastMoveDown > DOWN_INTERVAL) {
            int block = movementController.downMovement(currentPiece, leftOffset, topOffset, colorValue);
            if (block == -1) {
                gameLoop.pieceActive = false;
            }
            topOffset++;
            lastMoveDown = now;
            Platform.runLater(() -> gameLoop.grid.updateGrid());
            lastFallUpdate = now; //this is made, so that user will get some more time after releasing the control
            return;
        }

        // Auto fall
        if (now - lastFallUpdate > FALL_INTERVAL) {
            lastFallUpdate = now;
            int block = movementController.downMovement(currentPiece, leftOffset, topOffset, colorValue);
            if (block == -1) {
                gameLoop.pieceActive = false;
            }
            topOffset++;
            Platform.runLater(() -> gameLoop.grid.updateGrid());
        }
    }
}
