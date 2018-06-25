package sample;

import connectionToDatabase.DataManagement;
import dataModel.StudentDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static connectionToDatabase.Connect.getConnection;

public class Controller3 extends Controller2 implements Initializable{

    @FXML
    private TableColumn<StudentDetails, String> sNameColumn;

    @FXML
    private TableColumn<StudentDetails, String> fNameColumn;

    @FXML
    private TableView<StudentDetails> tableView;

    @FXML
    private TableColumn<StudentDetails, String> attendanceColumn;

    private ObservableList<StudentDetails> data = FXCollections.observableArrayList();
    private Connection connection;

    @Override
    public void initialize(URL location, ResourceBundle resource) {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();

            //Execute query and store results in a table
            String sql = "SELECT * FROM studentTable WHERE student_group =? AND student_course =? ";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,attGroup);
            preparedStatement.setInt(2,Integer.parseInt(attCourse));
            ResultSet resultSet = preparedStatement.executeQuery() ;

            while(resultSet.next())
            {
                data.add(new StudentDetails(resultSet.getString(2), resultSet.getString(3)));
            }

        } catch (SQLException e) {

        }
        fNameColumn.setCellValueFactory(new PropertyValueFactory<StudentDetails,String>("fName"));
        sNameColumn.setCellValueFactory(new PropertyValueFactory<StudentDetails,String>("sName"));
        attendanceColumn.setCellValueFactory(new PropertyValueFactory<StudentDetails, String>("checkBox"));

        tableView.setItems(data);
    }

    @FXML
    void submitClicked(ActionEvent event) {
        DataManagement dataM = new DataManagement();
        dataM.insertDate(date);
        // get info from checkBox
        for (StudentDetails d:data) {
            if (d.getCheckBox().isSelected()) {
                dataM.selectS(d.getfName(),  d.getsName(),  date,  "present");
            }
            else {
                dataM.selectS(d.getfName(),  d.getsName(),  date,  "absent");
            }
        }
   }


}
