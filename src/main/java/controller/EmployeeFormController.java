package controller;

import dto.Employeedto;
import dto.tm.EmployeeTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.CollectorModel;
import model.EmployeeModel;

import java.sql.SQLException;
import java.util.List;

public class EmployeeFormController {

    public AnchorPane subpain;
    public TextField txteid;
    public TextField txtsalary;
    public CheckBox chkmale;
    public CheckBox chkfemale;
    public ComboBox cmbc_id;
    public Button btnadd;
    public Button btnupdate;
    public Button btndelete;
    public Button btnclear;
    public TableView <EmployeeTm>tblMain;
    public TableColumn tbleId;
    public TableColumn tblname;
    public TableColumn tblsalary;
    public TableColumn tblgendewr;
    public TableColumn tblCid;
    public TextField txtname;

    public void initialize() {
        tbleId.setCellValueFactory(new PropertyValueFactory<>("e_Id"));
        tblname.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblgendewr.setCellValueFactory(new PropertyValueFactory<>("gender"));
        tblsalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        tblCid.setCellValueFactory(new PropertyValueFactory<>("c_Id"));



        getAllEmployee();
        loadCollector_Id();
        setSelectToTxt();
    }

    private void setSelectToTxt() {
        tblMain.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txteid.setText(newSelection.getE_Id());
                txtname.setText(newSelection.getName());
                txtsalary.setText(newSelection.getSalary());
                String gen = (newSelection.getGender());
                if(gen.equals("Female")){
                    chkmale.setSelected(false);
                    chkfemale.setSelected(true);
                }
                if(gen.equals("Male")){
                    chkmale.setSelected(true);
                    chkfemale.setSelected(false);
                }
                cmbc_id.setValue(newSelection.getC_Id());

            }
        });
    }

    private void loadCollector_Id() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> ids = CollectorModel.loadIds();

            for (String id : ids) {
                obList.add(id);
            }
            cmbc_id.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }

    private void getAllEmployee() {
        try {
            ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();
            List<Employeedto> employeeList = EmployeeModel.getAll();

            for (Employeedto employee : employeeList) {
                obList.add(new EmployeeTm(
                        employee.getE_Id(),
                        employee.getName(),
                        employee.getSalary(),
                        employee.getGender(),
                        employee.getC_Id()

                ));
            }
            tblMain.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }

    public void searchon_AC(ActionEvent actionEvent) {
        try {
            Employeedto employee = EmployeeModel.search(txteid.getText());
            if (employee != null) {
                txteid.setText(employee.getE_Id());
                txtname.setText(employee.getName());
                String gender = employee.getGender();
                if (gender != null && gender.equals("Male")) {
                    chkmale.setSelected(true);
                } else if (gender != null && gender.equals("Female")) {
                    chkfemale.setSelected(true);
                }
                txtsalary.setText(employee.getSalary());
                cmbc_id.setValue(employee.getC_Id());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened!").show();
        }
    }

    public void addonac(ActionEvent actionEvent) {
        try {
            String Id = txteid.getText();
            String name = txtname.getText();
            String salary = txtsalary.getText();
            String gender = "";
            if (chkmale.isSelected()) {
                gender = "Male";
                chkfemale.setSelected(false);
            } else if (chkfemale.isSelected()) {
                gender = "Female";
                chkmale.setSelected(false);
            }
            String cid=(String)cmbc_id.getValue();


            Employeedto employee = new Employeedto(Id,name,salary,gender,cid);

            // Save employee to database
            boolean isSaved = EmployeeModel.add(employee);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee saved!").show();
                getAllEmployee();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save employee to database!").show();
            e.printStackTrace();
        }
    }

    public void update(ActionEvent actionEvent) {
        String Id = txteid.getText();
        String name = txtname.getText();
        String salary = txtsalary.getText();
        String gender = "";
        if (chkmale.isSelected()) {
            gender = "Male";
            chkfemale.setSelected(false);
        } else if (chkfemale.isSelected()) {
            gender = "Female";
            chkmale.setSelected(false);
        }
        String cid=(String)cmbc_id.getValue();

        try {
            boolean isUpdated = EmployeeModel.update(Id,name,salary,gender,cid);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee updated!").show();
                getAllEmployee();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void deleteonAc(ActionEvent actionEvent) {
        String id = txteid.getText();
        try {
            boolean isDeleted = EmployeeModel.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
                getAllEmployee();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void claeronAc(ActionEvent actionEvent) {
        txteid.clear();
        txtsalary.clear();
        txtname.clear();
        chkfemale.setSelected(false);
        chkmale.setSelected(false);
        cmbc_id.setValue(null);
    }
}
