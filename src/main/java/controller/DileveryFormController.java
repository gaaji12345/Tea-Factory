package controller;

import dto.Devliverydto;
import dto.tm.DeliverTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.DeliverModel;
import model.SupplierModel;
import model.UserModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DileveryFormController {

    public AnchorPane subpain;
    public ComboBox cmbuserId;
    public TextField txtDId;
    public DatePicker pckOrderDate;
    public DatePicker pckDeleverDate;
    public DatePicker pckConfromdate;
    public ComboBox cmbSid;
    public Button btnAdd;
    public Button btnupdate;
    public Button btndelete;
    public Button btnclear;
    public TableView <DeliverTm>tblMain;
    public TableColumn tblUId;
    public TableColumn tblDid;
    public TableColumn tblOrdeDate;
    public TableColumn tblsiD;
    public TableColumn tblCdate;
    public TableColumn tbldeleryDate;

    public void initialize() {
        tblUId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        tblDid.setCellValueFactory(new PropertyValueFactory<>("d_Id"));
        tblOrdeDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        tblsiD.setCellValueFactory(new PropertyValueFactory<>("supId"));
        tblCdate.setCellValueFactory(new PropertyValueFactory<>("c_date"));
        tbldeleryDate.setCellValueFactory(new PropertyValueFactory<>("d_date"));
    
        getAllDeliver();
        loadUserIds();
        loadSupIds();
        setSelectToTxt();
    }

    private void setSelectToTxt() {
        tblMain.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cmbuserId.setValue(newSelection.getUserId());
                txtDId.setText(newSelection.getD_Id());
                pckOrderDate.setValue(LocalDate.parse(newSelection.getOrderDate()));
                cmbSid.setValue(newSelection.getSupId());
                pckConfromdate.setValue(LocalDate.parse(newSelection.getC_date()));
                pckDeleverDate.setValue(LocalDate.parse(newSelection.getD_date()));
            }
        });
    }

    private void loadSupIds() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> ids = UserModel.loadIds();

            for (String id : ids) {
                obList.add(id);
            }
            cmbuserId.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

        
    }

    private void loadUserIds() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> ids = SupplierModel.loadIds();

            for (String id : ids) {
                obList.add(id);
            }
            cmbSid.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
        
    }

    private void getAllDeliver() {
        try {
            ObservableList<DeliverTm> obList = FXCollections.observableArrayList();
            List<Devliverydto> dto = DeliverModel.getAll();

            for (Devliverydto d : dto) {
                obList.add(new DeliverTm(
                      d.getUserId(),
                        d.getD_Id(),
                        d.getOrderDate(),
                        d.getSupId(),
                        d.getC_date(),
                        d.getD_date()
                ));
            }
            tblMain.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void addOnAc(ActionEvent actionEvent) {
        try {
            String userId = (String) cmbuserId.getValue();
            String dId = txtDId.getText();
            LocalDate orderDate = pckOrderDate.getValue();
            String odate = orderDate.toString();
            String supId = (String) cmbSid.getValue();
            LocalDate conformDate = pckConfromdate.getValue();
            String cdate = conformDate.toString();
            LocalDate delDate = pckDeleverDate.getValue();
            String devliverDate = delDate.toString();

            // Validate fields
           // validateFields(guestId, bookingId, bookingDate, roomId, checkIn, checkOut);

            // Validate booking ID
           // validateBookingId(bookingId);

          //  Booking booking = new Booking(guestId, bookingId, bookingDate, roomId, checkIn, checkOut);

            Devliverydto devliverydto=new Devliverydto(userId,dId,odate,supId,cdate,devliverDate);

            // Save booking to database
            boolean isSaved = DeliverModel.add(devliverydto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Booking saved!").show();
               // DeliverModel.releaseRoom(roomId);
                getAllDeliver();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save employee to database!").show();
        }

    }

    public void updateOnAc(ActionEvent actionEvent) {
        try {
            String usertId = (String) cmbuserId.getValue();
            String devlerId = txtDId.getText();
            String supId = (String) cmbSid.getValue();
            LocalDate selectedOutDate = pckDeleverDate.getValue();
            String checkOut = selectedOutDate.toString();

            // Validate booking ID
           // validateBookingId(bookingId);

            boolean isUpdated = DeliverModel.update(usertId, devlerId, supId, checkOut);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Booking updated!").show();
                getAllDeliver();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void deleteOn(ActionEvent actionEvent) {
        String id = txtDId.getText();
        try {
            boolean isDeleted = DeliverModel.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
                getAllDeliver();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void claeronAc(ActionEvent actionEvent) {
        cmbuserId.setValue(null);
        txtDId.clear();
        pckDeleverDate.setValue(null);
        cmbSid.setValue(null);
        pckConfromdate.setValue(null);
        pckOrderDate.setValue(null);
    }
}
