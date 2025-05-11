package Utils;

import Core.Grid;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
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
    public boolean isGameOver = false; // to check game status (active|game over)
    public boolean pieceActive = false; // to know wehther there is a piece currently faling

    public GameEngine(Grid grid) {
        this.grid = grid;
        this.random = new Random();

    }

    public int[][] generatePiece() {
        int[][] original = Tetrispiece.pieces[random.nextInt(Tetrispiece.pieces.length)];
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone(); // fast and safe
        }
        return copy;
    }

    // no need to return the String color, just retunr the index of the color
    public int generateColor() {
        int randomColor = random.nextInt(Tetrispiece.colors.length);
        return randomColor + 1; // return 1+ indicate grid is not empty
    }

    // this methode will start the gameloop(thread)
    public void startGame() {

        GameRunner gameThread = new GameRunner(this);

        gameThread.start();

        // things to do after game over
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

    private final long FALL_INTERVAL = 600_000_000; // 700 ms
    private final long DOWN_INTERVAL = 40_000_000; // 40 ms (for fast forward -down movement)
    private final long RIGHT_INTERVAL = 20_000_000;

    private Set<KeyCode> userInputs;

    private void fastForwardDownMovement(long now) {
        int block = movementController.downMovement(currentPiece, leftOffset, topOffset, colorValue);
        if (block == -1) {
            gameLoop.pieceActive = false;
        }
        topOffset++;
        lastMoveDown = now;
        Platform.runLater(() -> gameLoop.grid.updateGrid());
        lastFallUpdate = now;
        // this is made, so that user will get some more time after releasing the
        // control
    }

    private void rightMove() {
        int block = movementController.rightMovement(currentPiece, leftOffset, topOffset, colorValue);
        if (block == 0)
            leftOffset++;
        Platform.runLater(() -> gameLoop.grid.updateGrid());
    }

    private void leftMove() {
        int block = movementController.leftMovement(currentPiece, leftOffset, topOffset, colorValue);
        if (block == 0)
            leftOffset--;
        Platform.runLater(() -> gameLoop.grid.updateGrid());
    }

    private void rotate() {
        // current piece is updated to it's transpose if successfull rotation
        this.currentPiece = movementController.rotation(currentPiece, topOffset, leftOffset, false, this.colorValue);
        Platform.runLater(() -> gameLoop.grid.updateGrid());
    }

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

        if (!gameLoop.pieceActive) {

            // game over check!
            if (new CollisionChecker().gameEndCheck(this.gameLoop.grid.grid, currentPiece,
                    leftOffset, topOffset)) {
                System.out.println("Game over!");
                gameLoop.isGameOver = true;
                this.stop();
                return;
            }

            // clear old references to help GC
            currentPiece = null;
            // now assign new ones
            gameLoop.pieceActive = true;
            currentPiece = gameLoop.generatePiece();
            colorValue = gameLoop.generateColor();
            topOffset = 0;
            leftOffset = 4;
            lastFallUpdate = now;
            lastMoveDown = now;
        }

        // Fast down movement (when holding S key)
        if (userInputs.contains(KeyCode.S) && now - lastMoveDown > DOWN_INTERVAL) {
            this.fastForwardDownMovement(now);
            return;
        } else if (userInputs.contains(KeyCode.D) && now - lastMoveDown > RIGHT_INTERVAL) {
            userInputs.remove(KeyCode.D);
            this.rightMove();
            return;
        } else if (userInputs.contains(KeyCode.A) && now - lastMoveDown > RIGHT_INTERVAL) {
            userInputs.remove(KeyCode.A);
            this.leftMove();
            return;
            // same interval for left and right (RIGHT_INTERVAL)
        } else if (userInputs.contains(KeyCode.W) && now - lastMoveDown > RIGHT_INTERVAL) {
            userInputs.remove(KeyCode.W);
            this.rotate();
            return;
        }

        // Auto fall
        if (now - lastFallUpdate > FALL_INTERVAL) {
            lastFallUpdate = now;
            int block = movementController.downMovement(currentPiece, leftOffset, topOffset, colorValue);
            if (block == -1) {
                gameLoop.pieceActive = false;
                Platform.runLater(() -> gameLoop.grid.updateGrid());
                return;
            }
            topOffset++;
            Platform.runLater(() -> gameLoop.grid.updateGrid());
        }
    }
}
