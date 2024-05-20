package controller;

import db.DBConnection;
import dto.Devliverydto;
import dto.Paymentdto;
import dto.Supplier;
import dto.Userdto;
import dto.tm.PaymentTm;
import dto.tm.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class PayMentFormController {

    public AnchorPane subpain;
    public TextField txtpId;
    public TextField txtSid;
    public Label lblName;
    //public Label lblTot;
    public Label oAmnt;
    public Button btnadd;
    public Button btnupdate;
    public Button btndelete;
    public Button btnclear;
    public TableView<PaymentTm> tblMAin;
    public TableColumn tblpId;
    public TableColumn tblSi;
    public TableColumn tblname;
  //  public TableColumn tbltot;
    public TableColumn tblamnt;
    public TextField txtDId;
    public Label lblUserId;
    public Label lbluserName;
    public Label lblConfDate;
    public Label lblDeliverDate;
    public Label lblSupId;
    public Label lblTotal;
    public TableColumn tblUID;
    public TableColumn tblDid;
    public TableColumn tblSid;
    public TableColumn tblCdate;
    public TableColumn tblDdate;
    public TableColumn tblTot;
    public Label lblAmnt;
    public AnchorPane mainpain;


    public void initialize() {
        tblpId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        tblUID.setCellValueFactory(new PropertyValueFactory<>("userId"));
        tblname.setCellValueFactory(new PropertyValueFactory<>("userName"));
        tblDid.setCellValueFactory(new PropertyValueFactory<>("dId"));
        tblSid.setCellValueFactory(new PropertyValueFactory<>("supId"));
        tblCdate.setCellValueFactory(new PropertyValueFactory<>("cDate"));
        tblDdate.setCellValueFactory(new PropertyValueFactory<>("dDate"));
        tblamnt.setCellValueFactory(new PropertyValueFactory<>("orderAm"));
        //colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        getAllPayments();
        setValueFactory();
        setSelectToTxt();
    }

    private void setSelectToTxt() {
        tblMAin.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtpId.setText(newSelection.getPaymentId());
                lblUserId.setText(newSelection.getUserId());
                lbluserName.setText(newSelection.getUserName());
                txtDId.setText(newSelection.getDId());
                lblSupId.setText(newSelection.getSupId());
                lblConfDate.setText(newSelection.getCDate());
                lblDeliverDate.setText(newSelection.getDDate());
                lblAmnt.setText(String.valueOf(newSelection.getOrderAm()));
              //  lblTotal.setText(String.valueOf(newSelection.getTotal()));
            }
        });
    }

    private void setValueFactory() {
        
    }

    private void getAllPayments() {
        try {
            ObservableList<PaymentTm> obList = FXCollections.observableArrayList();
            List<Paymentdto> paymentList = PaymentModel.getAll();

            for (Paymentdto payment : paymentList) {
                obList.add(new PaymentTm(
                        payment.getPaymentId(),
                        payment.getUserId(),
                        payment.getUserName(),
                        payment.getDId(),
                        payment.getSupId(),
                        payment.getCDate(),
                        payment.getDDate(),
                        payment.getOrderAm()
                ));
            }
            tblMAin.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
        
    }

    public void resOnAction(){
        String code = txtDId.getText();
        try {
            Devliverydto res = DeliverModel.searchById(code);
            Double orderAm = getOrderAmmount(code);

            LocalDate checkIn = LocalDate.parse(res.getC_date());
            LocalDate checkOut = LocalDate.parse(res.getD_date());
            Supplier sup = SupplierModel.search(res.getSupId());
            Double price = Double.valueOf(sup.getContact());
            //Double roomPrice = calculateRoomPrice(checkIn,checkOut,price);
            Userdto guest = UserModel.search(res.getUserId());
           // Double finalAmmount = orderAm+roomPrice;
            fillResFields(res,orderAm,guest);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void fillResFields(Devliverydto res, Double orderAm,  Userdto guest) {
        lblUserId.setText(res.getUserId());
        lblSupId.setText(res.getSupId());
       // lblName.setText(guest.getName());
        lbluserName.setText(guest.getName());
        lblConfDate.setText(res.getC_date());
        lblDeliverDate.setText(res.getD_date());
        lblAmnt.setText(String.valueOf(orderAm));
        //lblTotal.setText(String.valueOf(finalAmmount));
    }

    private Double calculateRoomPrice(LocalDate checkIn, LocalDate checkOut, Double price) {
        long days = ChronoUnit.DAYS.between(checkIn, checkOut);
        return price * days;
    }

    private Double getOrderAmmount(String code) {
        Double orderAmmount = 0.0;
        try {
            orderAmmount = PlaceOrderModel.getOrderAm(code);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
        return orderAmmount;

    }


    public void addON(ActionEvent actionEvent) {
        String paymentId = txtpId.getText();
        String userId = lblUserId.getText();
        String Name = lbluserName.getText();
        String deId = txtDId.getText();
        String supId = lblSupId.getText();
        String checkinDate = lblConfDate.getText();
        String checkoutDate = lblDeliverDate.getText();
        Double ordersAm = Double.valueOf(lblAmnt.getText());
        //Double totalPrice = Double.valueOf(lblTotal.getText());
      //  String release = "Available";

        Paymentdto payment = new Paymentdto(paymentId,userId,Name,deId,supId,checkinDate,checkoutDate,ordersAm);
        try {
            boolean isSaved = PaymentModel.add(payment);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment saved!").show();
               // RoomMo.releaseRoom(roomId,release);
                getAllPayments();
                setValueFactory();

                try {
                    JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Jasper/report.jrxml");
                    JRDesignQuery query=new JRDesignQuery();
                    query.setText("select payment.paymentId, payment.d_Id, payment.userId, payment.userName, payment.supId,  payment.checkInDate, payment.checkOutDate, payment.ordersAmount FROM payment INNER JOIN user ON payment.userId=user.userId INNER JOIN deliver ON payment.d_Id=deliver.d_Id INNER JOIN supplier ON payment.supId=supplier.supId WHERE paymentId='"+txtpId.getText()+"';");
                    jasperDesign.setQuery(query);

                    JasperReport jasperReport= JasperCompileManager.compileReport(jasperDesign);
                    JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null, DBConnection.getInstance().getConnection());
                    JasperViewer.viewReport(jasperPrint,false);
                } catch (SQLException | JRException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
              new Alert(Alert.AlertType.ERROR, "Payment Not saved ,Please Check Details & Try Again!").show();
            //clearTxt();
            e.printStackTrace();
        }

    }

    public void updateON(ActionEvent actionEvent) {
        String paymentId = txtpId.getText();
        String userId = lblUserId.getText();
        String Name = lbluserName.getText();
        String deId = txtDId.getText();
        String supId = lblSupId.getText();
        String checkinDate = lblConfDate.getText();
        String checkoutDate = lblDeliverDate.getText();
        Double ordersAm = Double.valueOf(lblAmnt.getText());

        Paymentdto payment = new Paymentdto(paymentId,userId,Name,deId,supId,checkinDate,checkoutDate,ordersAm);
        try {
            boolean isUpdated = PaymentModel.update(payment);
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Payment updated!").show();
                getAllPayments();
                setValueFactory();
            }
            else {
                new Alert(Alert.AlertType.ERROR, "Payment Id Not Exist!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Please Check Details & Try Again!").show();
        }
    }

    public void deleteOn(ActionEvent actionEvent) {
        String paymentId = txtpId.getText();
        try {
            boolean isDeleted = PaymentModel.delete(paymentId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment Deleted!").show();
              //  clearTxt();
                getAllPayments();
                setValueFactory();
            } else {
                new Alert(Alert.AlertType.ERROR, "Payment Id Not Exist!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Please Check Details & Try Again!").show();
           // clearTxt();
        }
    }

    public void clearOn(ActionEvent actionEvent) {
        txtpId.setText("");
        lblUserId.setText("");
        lblName.setText("");
        txtDId.setText("");
        lblSupId.setText("");
        lblConfDate.setText("");
        lblDeliverDate.setText("");
        lblAmnt.setText("");
      //  lblPrice.setText("");
    }

    public void txtSearchOn(ActionEvent actionEvent) {
        resOnAction();
    }
}


