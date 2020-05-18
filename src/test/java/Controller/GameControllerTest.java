package controller;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
public class GameControllerTest {

    GameController gameController;

    public static void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }

    @BeforeEach
    void setUp() {
        gameController = new GameController();
    }

    @Test
    void moveCircleToValidPosition() {
        Circle circle = new Circle();
        circle.setCenterX(50);
        circle.setCenterY(50);
        circle.setLayoutX(0);
        circle.setLayoutY(0);
        double newXCoord = 100.0;
        double newYCoord = 0.0;
        gameController.moveCoordinates(circle, newXCoord, newYCoord);
        setTimeout(() -> assertSame(circle.getLayoutX() == newXCoord && circle.getLayoutY() == newYCoord, true), 1000);
    }

    @Test
    void moveCircleToInvalidPosition() {
        Circle circle = new Circle();
        gameController.randomStartingPosition(circle);
        circle.setCenterX(50);
        circle.setCenterY(50);
        double oldXCoord = circle.getLayoutX();
        double oldYCoord = circle.getLayoutY();
        double newXCoord = 200.0;
        double newYCoord = 300.0;
        gameController.moveCoordinates(circle, newXCoord, newYCoord);
        setTimeout(() -> assertSame(circle.getLayoutX() == oldXCoord && circle.getLayoutY() == oldYCoord, true), 1000);
    }
}