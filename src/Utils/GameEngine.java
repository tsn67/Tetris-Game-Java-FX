package Utils;

import Core.Grid;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import Assets.SoundPlayer;
import Assets.Tetrispiece;
import Controller.ButtonContainerController;
import Controller.GameOverLabelController;
import Controller.PreviewController;
import Controller.ScoreController;

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
    public GameOverLabelController gameOverLabel;
    public ButtonContainerController highLightController;
    public PreviewController previewController;
    public ScoreController scoreController;

    public GameEngine(Grid grid, GameOverLabelController gameOverLabel, ButtonContainerController higLightController,
            PreviewController previewController, ScoreController scoreController) {
        this.grid = grid;
        this.random = new Random();
        this.gameOverLabel = gameOverLabel;
        this.highLightController = higLightController;
        this.previewController = previewController;
        this.scoreController = scoreController;
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
    private int[][] nextPiece;
    private int colorValue;
    private int nextColorValue;

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
            gameLoop.scoreController.increment();
            this.handleRowClear();
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

        this.nextPiece = gameLoop.generatePiece();
        this.nextColorValue = gameLoop.generateColor();
    }

    private void handleRowClear() {
        List<Integer> rowsFilled = movementController.checkRowFilled();
        // System.out.println(rowsFilled);
        if (rowsFilled.size() != 0) {
            for (int j = 0; j < rowsFilled.size(); j++) {
                gameLoop.grid.clearRow(rowsFilled.get(j));
                movementController.removeRow(rowsFilled.get(j));
                SoundPlayer.play("/Assets/dngSound.wav");
            }
        }
        Platform.runLater(() -> gameLoop.grid.updateGrid());
        // update the entire grid so that no grid has blank row down
    }

    @Override
    public void handle(long now) {
        if (!gameLoop.pieceActive) {
            // game over check!
            if (new CollisionChecker().gameEndCheck(this.gameLoop.grid.grid, currentPiece,
                    leftOffset, topOffset)) {
                // System.out.println("Game over!");
                gameLoop.isGameOver = true;
                Platform.runLater(() -> {
                    this.stop(); // only stop after showing label
                    this.gameLoop.gameOverLabel.showLabel();
                });
                SoundPlayer.play("/Assets/beepSound.wav");
                currentPiece = null;
                return;
            }
            // clear old references to help GC
            currentPiece = nextPiece;
            nextPiece = gameLoop.generatePiece();
            // now assign new ones
            gameLoop.pieceActive = true;
            colorValue = nextColorValue;
            nextColorValue = gameLoop.generateColor();
            gameLoop.previewController.redraw(nextPiece, nextColorValue);
            topOffset = 0;
            leftOffset = 4;
            lastFallUpdate = now;
            lastMoveDown = now;
        }

        // Fast down movement (when holding S key)
        if (userInputs.contains(KeyCode.S) && now - lastMoveDown > DOWN_INTERVAL) {
            this.gameLoop.highLightController.buttonFadeInOutAnimation("s");
            this.fastForwardDownMovement(now);
            return;
        } else if (userInputs.contains(KeyCode.D) && now - lastMoveDown > RIGHT_INTERVAL) {
            this.gameLoop.highLightController.buttonFadeInOutAnimation("d");
            userInputs.remove(KeyCode.D);
            this.rightMove();
            return;
        } else if (userInputs.contains(KeyCode.A) && now - lastMoveDown > RIGHT_INTERVAL) {
            this.gameLoop.highLightController.buttonFadeInOutAnimation("a");
            userInputs.remove(KeyCode.A);
            this.leftMove();
            return;
            // same interval for left and right (RIGHT_INTERVAL)
        } else if (userInputs.contains(KeyCode.W) && now - lastMoveDown > RIGHT_INTERVAL) {
            this.gameLoop.highLightController.buttonFadeInOutAnimation("w");
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
                gameLoop.scoreController.increment();
                // check for any row is completely filled or not
                this.handleRowClear();
                return;
            }
            topOffset++;
            Platform.runLater(() -> gameLoop.grid.updateGrid());
        }
    }
}
