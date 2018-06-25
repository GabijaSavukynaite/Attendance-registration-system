package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import connectionToDatabase.DataManagement;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private TextField sGroupTextF;

    @FXML
    private TextField newGroupNameTextF;

    @FXML
    private TextField sNameTextF;

    @FXML
    private TextField courseTextF;

    @FXML
    private TextField fNameTextF;

    @FXML
    private TextField gCourseTextF;

    public Alert warningMessage(){
        // message when some textFields are empty
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Įspėjimas");
        alert.setContentText("Užplidyti ne visi laukai");
        alert.setHeaderText(null);
        return alert;
    }

    @FXML
    void addGroupClicked(ActionEvent event) {
        // add new group
        if (newGroupNameTextF.getText().equals("") || gCourseTextF.getText().equals("")) {
            Alert alert = warningMessage();
            alert.showAndWait();
        }
        else {
            DataManagement dataM = new DataManagement(newGroupNameTextF.getText(), gCourseTextF.getText());
            dataM.InsertDeleteStudentGroup("insertGroup");
        }
    }

    @FXML
    void addStudentClicked(ActionEvent event) {
        // add new student
        if (fNameTextF.getText().equals("") || sNameTextF.getText().equals("") || courseTextF.getText().equals("")  || sGroupTextF.getText().equals("")) {
            Alert alert = warningMessage();
            alert.showAndWait();
        }
        else {
            DataManagement dataM = new DataManagement(fNameTextF.getText(), sNameTextF.getText(), courseTextF.getText(), sGroupTextF.getText());
            dataM.InsertDeleteStudentGroup("insertStudent");
        }
    }

    @FXML
    void attendanceClicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("sample2.fxml")));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Lankomumo žymėjimas");
            stage.setScene(new Scene(root, 733,298));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteGroupClicked(ActionEvent event) {
        // delete existing group
        if (newGroupNameTextF.getText().equals("") || gCourseTextF.getText().equals("")) {
            Alert alert = warningMessage();
            alert.showAndWait();
        }
        else {
            DataManagement dataM = new DataManagement(newGroupNameTextF.getText(), gCourseTextF.getText());
            dataM.InsertDeleteStudentGroup("deleteGroup");
        }
    }

    @FXML
    void deleteStudentClicked(ActionEvent event) {
        // delete existing group
        if(fNameTextF.getText().equals("") || sNameTextF.getText().equals("") || courseTextF.getText().equals("")  || sGroupTextF.getText().equals("")) {
            Alert alert = warningMessage();
            alert.showAndWait();
        }
        else {
            DataManagement dataM = new DataManagement(fNameTextF.getText(), sNameTextF.getText(), courseTextF.getText(), sGroupTextF.getText());
            dataM.InsertDeleteStudentGroup("deleteStudent");
        }
    }
}

