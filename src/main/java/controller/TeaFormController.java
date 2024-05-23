package controller;

import dto.Tea;
import dto.tm.TeaTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.TeaModel;

import java.sql.SQLException;
import java.util.List;

public class TeaFormController {

    public AnchorPane subPain;
    public TextField txtTid;
    public TextField txtType;
    public TextField txtDetaisl;
    public TextField txtPrice;
    public Button btnadd;
    public Button update;
    public Button btnDelete;
    public Button btnClear;
    public TableView<TeaTm> tblMaim;
    public TableColumn tblTId;
    public TableColumn tblType;
    public TableColumn tblDec;
    public TableColumn tblPrice;

    public void initialize() {
        tblTId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblType.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblDec.setCellValueFactory(new PropertyValueFactory<>("details"));
       tblPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
       
        getAllTea();
        setSelectToTxt();
    }

    private void setSelectToTxt() {
        tblMaim.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtTid.setText(newSelection.getId());
                txtType.setText(newSelection.getName());
                txtDetaisl.setText(newSelection.getDetails());
                txtPrice.setText(String.valueOf(newSelection.getPrice()));
            }
        });
    }

    private void getAllTea() {
        try {
            ObservableList<TeaTm> obList = FXCollections.observableArrayList();
            List<Tea> teaList = TeaModel.getAll();

            for (Tea tea : teaList) {
                obList.add(new TeaTm(
                        tea.getId(),
                        tea.getName(),
                        tea.getDetails(),
                        tea.getPrice()
                ));
            }
            tblMaim.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
        
    }

    public void searcOn(ActionEvent actionEvent) {
        try {
            Tea tea = TeaModel.search(txtTid.getText());
            if (tea != null) {
                txtTid.setText(tea.getId());
                txtType.setText(tea.getName());
                txtDetaisl.setText(tea.getDetails());
                txtPrice.setText(String.valueOf(tea.getPrice()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened!").show();
        }
    }


    public void addOn(ActionEvent actionEvent) {
        try{
            String id = txtTid.getText();
            String name = txtType.getText();
            String details =txtDetaisl.getText();
            Double price = Double.parseDouble(txtPrice.getText());

            // Validate fields
           // validateFields(id, name, details);

            // Validate food ID
           // validateFoodId(id);

            Tea food = new Tea(id, name, details, price);
            boolean isSaved = TeaModel.add(food);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Food saved!").show();
                getAllTea();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save Food to database!").show();
        }
    }

    public void updateOnAc(ActionEvent actionEvent) {
        String id = txtTid.getText();
        String name = txtType.getText();
        String details =txtDetaisl.getText();
        Double price = Double.parseDouble(txtPrice.getText());

        try {
            boolean isUpdated = TeaModel.update(id, name, details, price);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Food updated!").show();
                getAllTea();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void deleteONAc(ActionEvent actionEvent) {
        String id = txtTid.getText();
        try {
            boolean isDeleted = TeaModel.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
                getAllTea();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void clearOnAc(ActionEvent actionEvent) {
        txtTid.clear();
        txtPrice.clear();
        txtDetaisl.clear();
        txtType.clear();
    }
}
