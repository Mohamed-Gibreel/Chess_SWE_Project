package Controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

//import java.awt.*;
import java.awt.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController {
    @FXML VBox vbox;

    @FXML public Pane boardPane; // contains circle and rectangleGroup

    @FXML Circle circle;
    @FXML Circle leavingCircle;  // a small circle that appears when moving a circle

    @FXML Group rectangleGroup;

    @FXML Pane bgPane;

    @FXML private Label stepLabel;

    @FXML private Label usernameLabel;

    //
    // bottom row
    //
    @FXML Pane row0;
    @FXML Rectangle sq0_0;
    @FXML Rectangle sq1_0;
    @FXML Rectangle sq2_0;
    @FXML Rectangle sq3_0;
    @FXML Rectangle sq4_0;
    @FXML Rectangle sq5_0;
    @FXML Rectangle sq6_0;
    @FXML Rectangle sq7_0;

    //
    // 2nd from bottom
    //
    @FXML Pane row1;
    @FXML Rectangle sq0_1;
    @FXML Rectangle sq1_1;
    @FXML Rectangle sq2_1;
    @FXML Rectangle sq3_1;
    @FXML Rectangle sq4_1;
    @FXML Rectangle sq5_1;
    @FXML Rectangle sq6_1;
    @FXML Rectangle sq7_1;

    //
    // 3rd from bottom
    //
    @FXML Pane row2;
    @FXML Rectangle sq0_2;
    @FXML Rectangle sq1_2;
    @FXML Rectangle sq2_2;
    @FXML Rectangle sq3_2;
    @FXML Rectangle sq4_2;
    @FXML Rectangle sq5_2;
    @FXML Rectangle sq6_2;
    @FXML Rectangle sq7_2;

    //
    // 4th from bottom
    //
    @FXML Pane row3;
    @FXML Rectangle sq0_3;
    @FXML Rectangle sq1_3;
    @FXML Rectangle sq2_3;
    @FXML Rectangle sq3_3;
    @FXML Rectangle sq4_3;
    @FXML Rectangle sq5_3;
    @FXML Rectangle sq6_3;
    @FXML Rectangle sq7_3;

    //
    // 4th from top
    //
    @FXML Pane row4;
    @FXML Rectangle sq0_4;
    @FXML Rectangle sq1_4;
    @FXML Rectangle sq2_4;
    @FXML Rectangle sq3_4;
    @FXML Rectangle sq4_4;
    @FXML Rectangle sq5_4;
    @FXML Rectangle sq6_4;
    @FXML Rectangle sq7_4;

    //
    // 3rd from top
    //
    @FXML Pane row5;
    @FXML Rectangle sq0_5;
    @FXML Rectangle sq1_5;
    @FXML Rectangle sq2_5;
    @FXML Rectangle sq3_5;
    @FXML Rectangle sq4_5;
    @FXML Rectangle sq5_5;
    @FXML Rectangle sq6_5;
    @FXML Rectangle sq7_5;

    //
    // 2nd from top
    //
    @FXML Pane row6;
    @FXML Rectangle sq0_6;
    @FXML Rectangle sq1_6;
    @FXML Rectangle sq2_6;
    @FXML Rectangle sq3_6;
    @FXML Rectangle sq4_6;
    @FXML Rectangle sq5_6;
    @FXML Rectangle sq6_6;
    @FXML Rectangle sq7_6;

    //
    // top row
    //
    @FXML Pane row7;
    @FXML Rectangle sq0_7;
    @FXML Rectangle sq1_7;
    @FXML Rectangle sq2_7;
    @FXML Rectangle sq3_7;
    @FXML Rectangle sq4_7;
    @FXML Rectangle sq5_7;
    @FXML Rectangle sq6_7;
    @FXML Rectangle sq7_7;

    private final List<Pane> panes = new ArrayList<>();
    private int stepCount;
    private String userName;
    int cells[] = {0,100,200,300,400,500,600,700};


    public static int getRandom(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    int randomNumber = getRandom(cells);

    @FXML
    public void initialize() {

        BackgroundImage bg = new BackgroundImage(new Image(getClass().getResource("/pictures/bg.jpg").toExternalForm()), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        bgPane.setBackground(new Background(bg));

        stepCount = 0;

        leavingCircle.setOpacity(0.0d);

        panes.add( row0 );
        panes.add( row1 );
        panes.add( row2 );
        panes.add( row3 );
        panes.add( row4 );
        panes.add( row5 );
        panes.add( row6 );
        panes.add( row7 );


        circle.setLayoutX(randomNumber);
        if (randomNumber == 700){
            circle.setLayoutY(getRandom(cells));
        }

        vbox.setMaxWidth(800.0d);
    }

    public void initdata (String username) {
        this.userName = username;
        usernameLabel.setText("Current user: " + this.userName);
    }

    @FXML
    public void finishMovingPiece(MouseEvent evt) {
        Point2D mousePoint = new Point2D(evt.getX(), evt.getY());
        System.out.println(mousePoint);
        Point2D mousePointScene = circle.localToScene(mousePoint);
        System.out.println(mousePointScene);

        Rectangle r = pickRectangle( mousePointScene.getX(), mousePointScene.getY() );


        final Timeline timeline = new Timeline();


        if( r != null && (mousePoint.getY() > 0) && (mousePoint.getY() < 200) && (mousePoint.getX() < 100) && (mousePoint.getX() > -100)) {
            Point2D rectScene = r.localToScene(r.getX(), r.getY());
            Point2D parent = boardPane.sceneToLocal(rectScene.getX(), rectScene.getY());
            stepCount++;

            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(100),
                            new KeyValue(circle.layoutXProperty(), parent.getX()),
                            new KeyValue(circle.layoutYProperty(), parent.getY()),
                            new KeyValue(circle.opacityProperty(), 1.0d),
                            new KeyValue(leavingCircle.opacityProperty(), 0.0d),
                            new KeyValue(stepLabel.textProperty(),String.valueOf(stepCount))
                    )
            );

        } else {

            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(100),
                            new KeyValue(circle.opacityProperty(), 1.0d),
                            new KeyValue(leavingCircle.opacityProperty(), 0.0d)
                    )
            );
        }

        timeline.play();
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

}

