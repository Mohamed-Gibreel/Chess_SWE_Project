package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import results.Main;
import results.mainDao;
import java.io.IOException;

public class InsertController {

    @FXML
    private TextField memberField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField professionField;

    @FXML
    private TextField dayField;

    @FXML
    private TextField timeField;

    @FXML
    private TextField availabilityField;

    private mainDao MainDao;

    private Long memberID;
    private String name;
    private String profession;
    private String day;
    private Integer time;
    private String availability;



    public Main setRow() {
        Main result = Main.builder()
                .member_id(memberID)
                .Name(name)
                .profession(profession)
                .Day(day)
                .Time(time)
                .Availability(availability)
                .build();
        return result;
    }

    public void print(ActionEvent actionEvent) throws IOException {
        MainDao = mainDao.getInstance();

        memberID = Long.parseLong(memberField.getText());
        name = nameField.getText();
        profession = professionField.getText();
        day = dayField.getText();
        time = Integer.parseInt(timeField.getText());
        availability = availabilityField.getText();

        MainDao.persist(setRow());
        }

        public void back(ActionEvent actionEvent) throws IOException{
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/launch.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }


}
