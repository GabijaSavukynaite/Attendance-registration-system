package dataModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;


public class StudentDetails  {

    private SimpleStringProperty fName;
    private SimpleStringProperty sName;
    private CheckBox checkBox;

    public  StudentDetails(String fName, String sName)
    {
        this.fName =  new SimpleStringProperty(fName);
        this.sName = new SimpleStringProperty(sName);
        this.checkBox = new CheckBox();
    }

    public String getfName() {
        return fName.get();
    }

    public SimpleStringProperty fNameProperty() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName.set(fName);
    }

    public String getsName() {
        return sName.get();
    }

    public SimpleStringProperty sNameProperty() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName.set(sName);
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }



}
