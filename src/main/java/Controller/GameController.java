package controller;

import results.GameResult;
import results.GameResultDao;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class that provides game logic for game.fxml.
 */
@Slf4j
public class GameController {
    /**
     * An array that contains all the possible starting positions.
     */
    private static int cells[] = {0, 100, 200, 300, 400, 500, 600, 700};

    /**
     * An array containing all the cells that are each row.
     */
    private final List<Pane> panes = new ArrayList<>();

    @FXML
    public Circle circle;
    @FXML
    private VBox vbox;
    @FXML
    private Pane boardPane;
    @FXML
    private Label stepLabel;
    @FXML
    private Label usernameLabel1;
    @FXML
    private Label usernameLabel2;
    private String winner;
    private String winnerName;
    @FXML
    private Label solvedLabel;
    @FXML
    private Circle player1turn;
    @FXML
    private Circle player2turn;
    @FXML
    private Button doneButton;
    private GameResultDao gameResultDao;
    //
    // bottom row
    //
    @FXML
    private Pane row0;
    //
    // 2nd from bottom
    //
    @FXML
    private Pane row1;
    //
    // 3rd from bottom
    //
    @FXML
    private Pane row2;
    //
    // 4th from bottom
    //
    @FXML
    private Pane row3;
    //
    // 4th from top
    //
    @FXML
    private Pane row4;
    //
    // 3rd from top
    //
    @FXML
    private Pane row5;
    //
    // 2nd from top
    //
    @FXML
    private Pane row6;
    //
    // top row
    //
    @FXML
    private Pane row7;

    private int stepCount;
    private String userName1;
    private String userName2;
    private Instant beginGame;
    private boolean isSolved = false;
    /**
     * Returns a random number from the array cells that can be used as a starting position for the queen.
     * @return random number from array cells
     */
    private static int getRandom() {
        int rnd = new Random().nextInt(cells.length);
        return cells[rnd];
    }

    /**
     * A void method that puts a circle in a random starting position using getRandom to generate a random number.
     * @param circle a circle that needs to be set in a random position.
     */
    public void randomStartingPosition(Circle circle) {
        int randNum = getRandom();
        if (randNum == 700) {
            circle.setLayoutX(700);
            circle.setLayoutY(getRandom());
        } else {
            circle.setLayoutX(randNum);
            circle.setLayoutY(0);
        }
    }

    /**
     * Switches player turns and changes the small red circle to the current player to indicate that it is the player's turn.
     */
    private void switchPlayerTurn() {
        if (player1turn.getOpacity() == 1) {
            player1turn.setOpacity(0);
            player2turn.setOpacity(1);
        } else {
            player1turn.setOpacity(1);
            player2turn.setOpacity(0);
        }
    }

    /**
     * Initializes the game by setting a circle in a random position in the board,
     * recording the date of when the game was started and setting the stepCount to 0.
     */

    @FXML
    public void initialize() {
        gameResultDao = GameResultDao.getInstance();

        stepCount = 0;
        solvedLabel.setText("");
        beginGame = Instant.now();

        player1turn.setOpacity(1);
        player2turn.setOpacity(0);


        panes.add(row0);
        panes.add(row1);
        panes.add(row2);
        panes.add(row3);
        panes.add(row4);
        panes.add(row5);
        panes.add(row6);
        panes.add(row7);

        randomStartingPosition(circle);

        vbox.setMaxWidth(800.0d);
    }

    /**
     * Sets the name of the players in game.fxml.
     * @param username1 the name of the player 1.
     * @param username2 the name of player 2.
     */
    public void initdata(String username1, String username2) {
        this.userName1 = username1;
        this.userName2 = username2;

        usernameLabel1.setText("Player 1: " + this.userName1);
        usernameLabel2.setText("Player 2: " + this.userName2);
    }

    /**
     * Moves a circle to coordinate(x,y).
     * @param gameCircle the circle that needs to be moved.
     * @param x x-coordinate of the new cell.
     * @param y y-coordinate of the new cell.
     */
    public void moveCoordinates(Circle gameCircle, Double x, Double y) {
        final Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(100),
                        new KeyValue(gameCircle.layoutXProperty(), x),
                        new KeyValue(gameCircle.layoutYProperty(), y),
                        new KeyValue(gameCircle.opacityProperty(), 1.0d)
                )
        );
        timeline.play();
    }

    /**
     * Increments the stepCount and changes it in game.fxml.
     */
    public void incrementStepCount() {
        stepCount++;
        stepLabel.setText(String.valueOf(stepCount));
    }

    /**
     * A void method that checks whether the user is trying to move the circle to an invalid position,
     * if so then the program will send an log with a message saying Invalid Move, else we will get a message,
     * with the new circle coordinates.
     * @param evt A mouse event, in this case onMouseReleased.
     */
    public void movePiece(MouseEvent evt) {
        Point2D mousePoint = new Point2D(evt.getX(), evt.getY());
        Point2D mousePointScene = circle.localToScene(mousePoint);

        Rectangle r = pickRectangle(mousePointScene.getX(), mousePointScene.getY());

        Point2D rectScene = r.localToScene(r.getX(), r.getY());
        Point2D newCirclePos = boardPane.sceneToLocal(rectScene.getX(), rectScene.getY());

        if (r != null && (mousePoint.getY() > 0) && (mousePoint.getX() < 100)) {
            if (circle.getLayoutX() == newCirclePos.getX() && circle.getLayoutY() != newCirclePos.getY()) {
                moveCoordinates(circle, newCirclePos.getX(), newCirclePos.getY());
                incrementStepCount();
                switchPlayerTurn();
                log.info("Circle moved to ({},{}) coordinates", newCirclePos.getX(),newCirclePos.getY());
                isSolved(newCirclePos.getX(), newCirclePos.getY());
            } else if (circle.getLayoutY() == newCirclePos.getY() && circle.getLayoutX() != newCirclePos.getX()) {
                moveCoordinates(circle, newCirclePos.getX(), newCirclePos.getY());
                incrementStepCount();
                log.info("Circle moved to ({},{}) coordinates", newCirclePos.getX(),newCirclePos.getY());
                switchPlayerTurn();
                isSolved(newCirclePos.getX(), newCirclePos.getY());
            } else if ((circle.getLayoutX() - newCirclePos.getX()) == 100 && (newCirclePos.getY() - circle.getLayoutY()) == 100) {
                moveCoordinates(circle, newCirclePos.getX(), newCirclePos.getY());
                incrementStepCount();
                switchPlayerTurn();
                log.info("Circle moved to ({},{}) coordinates", newCirclePos.getX(),newCirclePos.getY());
                isSolved(newCirclePos.getX(), newCirclePos.getY());
            }
        }
        else {
            log.warn("Invalid Move");
        }
    }

    /**
     * Checks whether the game reached a goal state.
     * @param x x-coordinate of the circle.
     * @param y y-coordinate of the circle.
     * @return true, if circle is in the first cell of the last row,
     *          false, otherwise.
     */
    public boolean isSolved(Double x, Double y) {
        final Timeline timeline = new Timeline();
        if (x == 0 && y == 700) {
            doneButton.setText("Finish");
            winner = player1turn.getOpacity() == 1 ? "Winner is " + userName1 : "Winner is " + userName2;
            winnerName = player1turn.getOpacity() == 1 ? userName1 : userName2;
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(100),
                            new KeyValue(solvedLabel.textProperty(), winner.toString()),
                            new KeyValue(circle.disableProperty(), true)
                    )
            );
            timeline.play();
            log.info("Player {} won the game in {} steps.",winnerName,stepCount);
            return true;
        }
        return false;
    }

    private Rectangle pickRectangle(Double sceneX, Double sceneY) {
        Rectangle pickedRectangle = null;
        for (Pane row : panes) {
            Point2D mousePoint = new Point2D(sceneX, sceneY);
            Point2D mpLocal = row.sceneToLocal(mousePoint);
            if (row.contains(mpLocal)) {
                for (Node cell : row.getChildrenUnmodifiable()) {
                    Point2D mpLocalCell = cell.sceneToLocal(mousePoint);
                    if (cell.contains(mpLocalCell)) {
                        pickedRectangle = (Rectangle) cell;
                        break;
                    }
                }
                break;
            }
        }
        return pickedRectangle;
    }

    /**
     * Resets the game to a starting state.
     * @param actionEvent An action which is sent when a button is pressed.
     */
    public void resetGame(ActionEvent actionEvent) {
        initialize();
        beginGame = Instant.now();
        stepCount = 0;
        stepLabel.setText(String.valueOf(stepCount));
        solvedLabel.setText("");
        circle.setDisable(false);
        doneButton.setText("Give Up");
        player1turn.setOpacity(1.0);
        player2turn.setOpacity(0.0);
        log.info("Game reset.");
    }

    private GameResult getResult() {

        GameResult result = GameResult.builder()
                .player(winnerName)
                .solved(isSolved)
                .duration(java.time.Duration.between(beginGame, Instant.now()))
                .steps(stepCount)
                .build();
        return result;
    }

    /**
     *
     * @param actionEvent An action which is sent when a button is pressed.
     * @throws IOException An exception that is caught if an error occurs.
     */
    public void finishGame(ActionEvent actionEvent) throws IOException {
        if (isSolved) {
            gameResultDao.persist(getResult());
        }

        Parent root = (Parent) FXMLLoader.load(getClass().getResource("/fxml/topten.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        log.info("Finished game, loading Top Ten scene.");
    }


}

