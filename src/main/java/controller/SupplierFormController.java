package controller;

import dto.Supplier;
import dto.tm.SupplierTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.SupplierModel;

import java.sql.SQLException;
import java.util.List;

public class SupplierFormController {


    public AnchorPane mainpain;
    public TextField txtid;
    public TextField txtname;
    public TextField txtTel;
    public TextField txtAddress;
    public Button btnadd;
    public Button btnupdate;
    public Button btndelete;
    public Button claearbtn;
    public TableView <SupplierTm>tblMain;
    public TableColumn tblid;
    public TableColumn tblName;
    public TableColumn tblConatct;
    public TableColumn tblAddress;




    public void initialize() {
        tblid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblConatct.setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
       
        getAllSuppliers();
        setSelectToTxt();
    }

    private void setSelectToTxt() {
        tblMain.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtid.setText(newSelection.getId());
                txtname.setText(newSelection.getName());
                txtTel.setText(newSelection.getContact());
                txtAddress.setText(newSelection.getAddress());
            }
        });
    }

    private void getAllSuppliers() {
        try {
            ObservableList<SupplierTm> obList = FXCollections.observableArrayList();
            List<Supplier> supplierList = SupplierModel.getAll();

            for (Supplier supplier : supplierList) {
                obList.add(new SupplierTm(
                        supplier.getId(),
                        supplier.getName(),
                        supplier.getContact(),
                        supplier.getAddress()
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
            Supplier supplier = SupplierModel.search(txtid.getText());
            if (supplier != null) {
                txtid.setText(supplier.getId());
                txtname.setText(supplier.getName());
                txtTel.setText(supplier.getContact());
                txtAddress.setText(supplier.getAddress());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened!").show();
        }
    }


    public void add_OnAC(ActionEvent actionEvent) {
        try {
            String id = txtid.getText();
            String name = txtname.getText();
            String contact =txtTel.getText();
            String details = txtAddress.getText();

            // Validate fields
            //validateFields(id, name, contact, details);

            // Validate supplier ID
            //validateSupplierId(id);

            Supplier supplier = new Supplier(id, name, contact, details);

//            boolean isSaved = ItemModel.save(code, description, unitPrice, qtyOnHand);
            boolean isSaved = SupplierModel.add(supplier);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier saved!").show();
               // setCellValueFactory();
                getAllSuppliers();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void update_OnAc(ActionEvent actionEvent) {
        try {
            String id = txtid.getText();
            String name = txtname.getText();
            String contact =txtTel.getText();
            String details = txtAddress.getText();

            // Validate fields
           // validateFields(id, name, contact, details);

            // Validate Supplier ID
           // validateSupplierId(id);

            Supplier supplier = new Supplier(id, name, contact, details);

//            boolean isUpdated = SupplierModel.update(id, name, contact, details);
            boolean isUpdated = SupplierModel.update(supplier);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier updated!").show();
                //setCellValueFactory();
                initialize();
                getAllSuppliers();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void delete_oNac(ActionEvent actionEvent) {
        String id = txtid.getText();
        try {
            boolean isDeleted = SupplierModel.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
                //        setCellValueFactory();
                //        getAll();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void clear_Onac(ActionEvent actionEvent) {
        txtid.clear();
        txtname.clear();
        txtTel.clear();
        txtAddress.clear();
    }
}
