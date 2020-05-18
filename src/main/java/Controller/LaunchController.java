package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;

/**
 * Class that controls the logic in launch.fxml.
 */
@Slf4j
public class LaunchController{
        @FXML
        private TextField usernameTextfield1;

        @FXML
        private TextField usernameTextfield2;

        @FXML
        private Label errorLabel;

        /**
         * Checks whether one of the player's names is empty, if so an error label will show up with a message,
         * providing them with the problem,then sets the player's names and
         * loads game.fxml with the player's names accordingly.
         * @param actionEvent A mouse event, in this case onAction.
         * @throws IOException An exception that is caught if an error occurs.
         */
        public void startAction(ActionEvent actionEvent) throws IOException {
                if (usernameTextfield1.getText().isEmpty() || usernameTextfield2.getText().isEmpty()) {
                        errorLabel.setText("* Username is empty! *");
                } else {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/game.fxml"));
                        Parent root = fxmlLoader.load();
                        fxmlLoader.<GameController>getController().initdata(usernameTextfield1.getText(),usernameTextfield2.getText());
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                        log.info("Player 1 is set to {}, loading game scene.", usernameTextfield1.getText());
                        log.info("Player 2 is set to {}, loading game scene.", usernameTextfield2.getText());
                }
        }
}

