package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;

@Slf4j
public class LaunchController{
        @FXML
        private Label errorLabel;

        @FXML
        private ComboBox Choice_1;

        @FXML
        private ComboBox Choice_2;

        @FXML
        private ComboBox timeChoice;

        @FXML
        private Pane bgPane;

        ObservableList<String> professionList = FXCollections.observableArrayList("IT","Accountant","Engineer","Mechanic");
        ObservableList<String> timeList = FXCollections.observableArrayList("Friday 8 AM","Monday 10 AM","Tuesday 12 PM","Monday 2 PM","Thursday 4 PM");

        public static void setTimeoutSync(Runnable runnable, int delay) {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }

        public void initialize(){
            Choice_1.setItems(professionList);
            Choice_2.setItems(professionList);
            timeChoice.setItems(timeList);

            BackgroundImage bg = new BackgroundImage(new Image(getClass().getResource("/pictures/bg.jpg").toExternalForm()), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, false));
            bgPane.setBackground(new Background(bg));
        }

        public void startAction(ActionEvent actionEvent) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/topten.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }

        public void insertAction(ActionEvent actionEvent) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Insert.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }



        public void logDetails (String one, String two, String three){
            log.info(one);
            log.info(two);
            log.info(three);
        }

        public void checkMeeting() {
            String firstChoice;
            String secondChoice;
            String time;

            firstChoice = Choice_1.getValue().toString();
            secondChoice = Choice_2.getValue().toString();
            time = timeChoice.getValue().toString();

            if (firstChoice.equals("IT") && secondChoice.equals("Accountant") && time.equals("Friday 8 AM")){
                logDetails(firstChoice,secondChoice,time);
                setTimeoutSync(() -> errorLabel.setText("The meeting will be scheduled at 8 AM !"),1500);
            }

            else if (firstChoice.equals("Accountant") && secondChoice.equals("Engineer") && time.equals("Monday 10 AM")){
                logDetails(firstChoice,secondChoice,time);
                setTimeoutSync(() -> errorLabel.setText("The meeting can be held but it is not the ideal time !"),1500);
            }

            else{
                logDetails(firstChoice,secondChoice,time);
                setTimeoutSync(() -> errorLabel.setText("The meeting cannot be held, due to a busy schedule."),2000);
            }
        }
}

