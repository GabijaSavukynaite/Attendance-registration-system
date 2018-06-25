package sample;

import connectionToDatabase.DataManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Controller2 extends Controller implements Initializable {


    @FXML
    private TextField attCourseTextF;

    @FXML
    private TextField dateSTextF;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField dateETextF;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField attGroupTextF;

    protected static String attGroup;
    protected static String attCourse;
    protected static LocalDate date;

    ObservableList<String> attendL =
            FXCollections.observableArrayList (
                    "dalyvavo",
                    "nedalyvavo"
            );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set default value in comboBox
        comboBox.setValue("dalyvavo");
        comboBox.setItems(attendL);
    }

    @FXML
    void showDataClicked(ActionEvent event)  {
        if (attGroupTextF.getText().equals("") || attCourseTextF.getText().equals("") || datePicker.getValue().isEqual(null)) {
            Alert alert = warningMessage();
            alert.showAndWait();
        }
        else {
            attGroup = attGroupTextF.getText();
            attCourse = attCourseTextF.getText();
            date = datePicker.getValue();

            try {
                FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("sample3.fxml")));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Lankomumo žymėjimas");
                stage.setScene(new Scene(root, 529, 413));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void submitGClicked(ActionEvent event) {
        if(attGroupTextF.getText().equals("") || attCourseTextF.getText().equals("") || datePicker.getValue().isEqual(null)) {
            Alert alert = warningMessage();
            alert.showAndWait();
        }
        else {
            String state;
            LocalDate date = datePicker.getValue();
            if (comboBox.getValue() == "dalyvavo") {
                state = "present";
            }
            else {
                state = "absent";
            }
            DataManagement dataM = new DataManagement();
            dataM.insertDate(date);
            dataM.selectG(attGroupTextF.getText(),attCourseTextF.getText(),date,state);
        }
    }

    @FXML
    void createFileClicked(ActionEvent event)  {
        if (attGroupTextF.getText().equals("") || attCourseTextF.getText().equals("") || dateSTextF.getText().equals("") || dateETextF.getText().equals("")) {
            Alert alert = warningMessage();
            alert.showAndWait();
        }
        else {
            DataManagement dataM = new DataManagement();
            dataM.createFile(dateSTextF.getText(), dateETextF.getText(), attGroupTextF.getText(), attCourseTextF.getText());
        }
    }
}





