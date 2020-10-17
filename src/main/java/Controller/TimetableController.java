package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import results.Main;
import results.Timetable;
import results.mainDao;

import java.io.IOException;
import java.time.Duration;
import java.util.List;


@Slf4j
public class TimetableController {
    @FXML
    private TableView<Main> schedule;

    @FXML
    private TableColumn<Main, Long> ID;

    @FXML
    private TableColumn<Main, String> Name;

    @FXML
    private TableColumn<Main, String> Profession;

    @FXML
    private TableColumn<Main, String> Day;

    @FXML
    private TableColumn<Main, Integer> Time;

    @FXML
    private TableColumn<Main, String> Availability;

    private mainDao MainDao;

    public void back(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/launch.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
//        log.info("Loading launch scene.");
    }

    @FXML
    public void initialize() {
        MainDao = mainDao.getInstance();

        List<Main> toptenList = MainDao.findAll();

        ID.setCellValueFactory(new PropertyValueFactory<>("member_id"));
        Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Profession.setCellValueFactory(new PropertyValueFactory<>("profession"));
        Day.setCellValueFactory(new PropertyValueFactory<>("Day"));
        Time.setCellValueFactory(new PropertyValueFactory<>("Time"));
        Availability.setCellValueFactory(new PropertyValueFactory<>("Availability"));


        ObservableList<Main> observableResult = FXCollections.observableArrayList();
        observableResult.addAll(toptenList);

        schedule.setItems(observableResult);
    }

}
