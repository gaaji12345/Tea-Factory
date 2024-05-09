package controller;

import dto.TsCol_Detail;
import dto.tm.TsColTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.CollectorModel;
import model.SupplierModel;
import model.TsModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TeaCollectDetailController {

    public AnchorPane subpain;
    public ComboBox cmbs_Id;
    public ComboBox cmbC_id;
    public TextField txtamount;
    public Button btnadd;
    public Button btnupdate;
    public Button btndelete;
    public Button btnClear;
    public TableView <TsColTm> tblmain;
    public TableColumn tbsid;
    public TableColumn tblcid;
    public TableColumn tblamnt;

    public void initialize() {
        tbsid.setCellValueFactory(new PropertyValueFactory<>("supId"));
        tblcid.setCellValueFactory(new PropertyValueFactory<>("c_Id"));
        tblamnt.setCellValueFactory(new PropertyValueFactory<>("amount"));

        getAllDetails();
        loadSupplier_Id();
        loadCollector_Id();
        setSelectToTxt();
    }

    private void setSelectToTxt() {
        tblmain.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cmbs_Id.setValue(newSelection.getSupId());
                cmbC_id.setValue(newSelection.getC_Id());
                txtamount.setText(newSelection.getAmount());
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
            cmbC_id.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }

    private void loadSupplier_Id() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> ids = SupplierModel.loadIds();

            for (String id : ids) {
                obList.add(id);
            }
            cmbs_Id.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }

    private void getAllDetails() {
        try {
            ObservableList<TsColTm> obList = FXCollections.observableArrayList();
            List<TsCol_Detail> Tlist = TsModel.getAll();

            for (TsCol_Detail Td : Tlist) {
                obList.add(new TsColTm(
                        Td.getSupId(),
                        Td.getC_Id(),
                        Td.getAmount()
                ));
            }
            tblmain.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }

    public void add_OnAc(ActionEvent actionEvent) {
        try {
            String sid = (String) cmbs_Id.getValue();
            String cid=(String)cmbC_id.getValue();
            String amount = txtamount.getText();


            TsCol_Detail detail = new TsCol_Detail(sid,cid,amount);

            // Save booking to database
            boolean isSaved = TsModel.add(detail);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!").show();
                getAllDetails();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save  to database!").show();
        }
    }

    public void up_OnAc(ActionEvent actionEvent) {
        try {
            String sid = (String) cmbs_Id.getValue();
            String cid=(String)cmbC_id.getValue();
            String amount = txtamount.getText();


            boolean isUpdated = TsModel.update(sid,cid,amount);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, " Updated!").show();
                getAllDetails();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void delete_Onac(ActionEvent actionEvent) {
        String id = (String) cmbs_Id.getValue();
        try {
            boolean isDeleted = TsModel.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                getAllDetails();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void clear_onac(ActionEvent actionEvent) {

        cmbs_Id.setValue(null);
        cmbC_id.setValue(null);
        txtamount.clear();
    }
}
