package controller;

import dto.Collectordto;
import dto.Supplier;
import dto.tm.Collector;
import dto.tm.SupplierTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.CollectorModel;
import model.SupplierModel;

import java.sql.SQLException;
import java.util.List;

public class CollectorFormController {
    public AnchorPane subpain;
    public TextField txtId;
    public TextField txtTel;
    public TextField txtName;
    public TextField txtAddress;
    public Button btnAdd;
    public Button btnupdate;
    public Button btndelte;
    public Button btnClear;
    public TableView<Collector> tblMain;
    public TableColumn tblid;
    public TableColumn tblName;
    public TableColumn tblAddress;
    public TableColumn tblTel;

    public void initialize() {
        tblid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tblTel.setCellValueFactory(new PropertyValueFactory<>("contact"));

        getAllCollectors();
        setSelectToTxt();
    }

    private void setSelectToTxt() {
        tblMain.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(newSelection.getId());
                txtName.setText(newSelection.getName());
                txtAddress.setText(newSelection.getAddress());
                txtTel.setText(newSelection.getContact());
            }
        });
    }

    private void getAllCollectors() {
        try {
            ObservableList<Collector> obList = FXCollections.observableArrayList();
            List<Collectordto> c = CollectorModel.getAll();

            for (Collectordto col : c) {
                obList.add(new Collector(
                        col.getId(),
                       col.getName(),
                        col.getAddress(),
                        col.getContact()

                ));
            }
            tblMain.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }


    public void search_ONAC(ActionEvent actionEvent) {
        try {
            Collectordto col = CollectorModel.search(txtId.getText());
            if (col != null) {
                txtId.setText(col.getId());
                txtName.setText(col.getName());
                txtAddress.setText(col.getAddress());
                txtTel.setText(col.getContact());

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened!").show();
        }
    }

    public void add_OnAc(ActionEvent actionEvent) {
        try {
            String id = txtId.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String contact =txtTel.getText();



            Collectordto col = new Collectordto(id, name, address,contact);

//            boolean isSaved = ItemModel.save(code, description, unitPrice, qtyOnHand);
            boolean isSaved = CollectorModel.add(col);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Collector saved!").show();
                initialize();
                getAllCollectors();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void update_ON(ActionEvent actionEvent) {
        try {
            String id = txtId.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String contact =txtTel.getText();

            Collectordto col = new Collectordto(id, name, address,contact);


//            boolean isUpdated = SupplierModel.update(id, name, contact, details);
            boolean isUpdated = CollectorModel.update(col);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Collector updated!").show();
                //setCellValueFactory();
                initialize();
                getAllCollectors();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void delete_oNAc(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            boolean isDeleted =  CollectorModel.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void clear_OnAc(ActionEvent actionEvent) {
        txtId.clear();
        txtName.clear();
        txtTel.clear();
        txtAddress.clear();

    }
}
