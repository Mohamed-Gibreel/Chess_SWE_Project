package Controller;

import Model.GameResult;
import Model.GameResultDao;
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
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

//import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController {
    @FXML private VBox vbox;

    @FXML private Pane boardPane; // contains circle and rectangleGroup

    @FXML public Circle circle;
    @FXML private Circle leavingCircle;  // a small circle that appears when moving a circle

    @FXML private Label stepLabel;

    @FXML private Label usernameLabel1;
    @FXML private Label usernameLabel2;
    private String winner;
    private String winnerName;

    @FXML private Label solvedLabel;

    @FXML private Circle player1turn;
    @FXML private Circle player2turn;

    @FXML private Button doneButton;

    private GameResultDao gameResultDao;

    //
    // bottom row
    //
    @FXML private Pane row0;

    //
    // 2nd from bottom
    //
    @FXML private Pane row1;

    //
    // 3rd from bottom
    //
    @FXML private Pane row2;

    //
    // 4th from bottom
    //
    @FXML private Pane row3;

    //
    // 4th from top
    //
    @FXML private Pane row4;


    //
    // 3rd from top
    //
    @FXML private Pane row5;


    //
    // 2nd from top
    //
    @FXML private Pane row6;

    //
    // top row
    //
    @FXML private Pane row7;


    private final List<Pane> panes = new ArrayList<>();
    private int stepCount;
    private String userName1;
    private String userName2;
    private Instant beginGame;
    private boolean isSolved = false;
    private static int cells[] = {0,100,200,300,400,500,600,700};


    private static int getRandom(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    private void randomStartingPosition(){
        int randNum = getRandom(cells);
        if (randNum == 700){
            circle.setLayoutX(700);
            circle.setLayoutY(getRandom(cells));
        }
        else {
            circle.setLayoutX(randNum);
            circle.setLayoutY(0);
        }
    }

    private void switchPlayerTurn(){
        if (player1turn.getOpacity() == 1) {
            player1turn.setOpacity(0);
            player2turn.setOpacity(1);
        }
        else{
            player1turn.setOpacity(1);
            player2turn.setOpacity(0);
        }
    }


    @FXML
    public void initialize() {
        gameResultDao = GameResultDao.getInstance();

        stepCount = 0;
        solvedLabel.setText("");
        beginGame = Instant.now();

        leavingCircle.setOpacity(0.0d);
        player1turn.setOpacity(1);
        player2turn.setOpacity(0);



        panes.add( row0 );
        panes.add( row1 );
        panes.add( row2 );
        panes.add( row3 );
        panes.add( row4 );
        panes.add( row5 );
        panes.add( row6 );
        panes.add( row7 );

        randomStartingPosition();

        vbox.setMaxWidth(800.0d);
    }

    public void initdata (String username1 , String username2) {
        this.userName1 = username1;
        this.userName2 = username2;

        usernameLabel1.setText("Player 1: " + this.userName1);
        usernameLabel2.setText("Player 2: " + this.userName2);
    }

    @FXML
    public void movePiece(MouseEvent evt) {
        try {
            Point2D mousePoint = new Point2D(evt.getX(), evt.getY());
            Point2D mousePointScene = circle.localToScene(mousePoint);

            Rectangle r = pickRectangle(mousePointScene.getX(), mousePointScene.getY());
            Point2D rectScene = r.localToScene(r.getX(), r.getY());
            Point2D parent = boardPane.sceneToLocal(rectScene.getX(), rectScene.getY());


            final Timeline timeline = new Timeline();


            if (r != null && (mousePoint.getY() > 0) && (mousePoint.getY() < 200) && (mousePoint.getX() < 100) && (mousePoint.getX() > -100)) {
                stepCount++;
                Point2D newCirclePos = new Point2D(parent.getX(),parent.getY());

                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(100),
                                new KeyValue(circle.layoutXProperty(), parent.getX()),
                                new KeyValue(circle.layoutYProperty(), parent.getY()),
                                new KeyValue(circle.opacityProperty(), 1.0d),
                                new KeyValue(leavingCircle.opacityProperty(), 0.0d),
                                new KeyValue(stepLabel.textProperty(), String.valueOf(stepCount))
                        )
                );
                switchPlayerTurn();
                if (newCirclePos.getX() == 0 && newCirclePos.getY() == 700) {
                    isSolved = true;
                    doneButton.setText("Finish");
                    winner = player1turn.getOpacity() == 1 ? "Winner is " + userName1 : "Winner is " + userName2;
                    winnerName = player1turn.getOpacity() == 1 ? userName1 : userName2;
                    timeline.getKeyFrames().add(
                            new KeyFrame(Duration.millis(100),
                                    new KeyValue(solvedLabel.textProperty(),winner.toString()),
                                    new KeyValue(circle.disableProperty(), true)
                            )
                    );
                }

            } else {
                System.out.println("Invalid Move!");
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(100),
                                new KeyValue(circle.opacityProperty(), 1.0d),
                                new KeyValue(leavingCircle.opacityProperty(), 0.0d)
                        )
                );
            }
            timeline.play();
        }
        catch(Exception e){
            System.out.println("An error Occured!");
        }
    }

    private Rectangle pickRectangle(double sceneX, double sceneY) {
        Rectangle pickedRectangle = null;
        for( Pane row : panes ) {

            Point2D mousePoint = new Point2D(sceneX, sceneY);
            Point2D mpLocal = row.sceneToLocal(mousePoint);

            if( row.contains(mpLocal) ) {
                for( Node cell : row.getChildrenUnmodifiable() ) {

                    Point2D mpLocalCell = cell.sceneToLocal(mousePoint);

                    if( cell.contains(mpLocalCell) ) {
                        pickedRectangle = (Rectangle)cell;
                        break;
                    }
                }
                break;
            }
        }
        return pickedRectangle;
    }

    public void resetGame(ActionEvent actionEvent) {
        initialize();
        beginGame = Instant.now();
        stepCount = 0;
        stepLabel.setText(String.valueOf(stepCount));
        solvedLabel.setText("");
        circle.setDisable(false);
        doneButton.setText("Give Up");
        switchPlayerTurn();
//        log.info("Game reset.");
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

    public void finishGame(ActionEvent actionEvent) throws IOException {
        if (isSolved) {
            gameResultDao.persist(getResult());
        }

        Parent root = (Parent) FXMLLoader.load(getClass().getResource("/fxml/topten.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
//        log.info("Finished game, loading Top Ten scene.");
    }


}

