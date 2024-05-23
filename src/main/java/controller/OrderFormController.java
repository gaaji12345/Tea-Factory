package controller;

import dto.Devliverydto;
import dto.OrderDetails;
import dto.Tea;
import dto.Userdto;
import dto.tm.OrderTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.DeliverModel;
import model.OrderModel;
import model.TeaModel;
import model.UserModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrderFormController {

    public AnchorPane subPian;
    public TextField txtoId;
    public ComboBox<String> cmbDID;
    public ComboBox<String> cmbTd;
    public Label lbluserId;
    public Label lblUserName;
    public Label lblSupId;
    public Label lblOdate;
    public Label lblPrice;
    public TextField txtQty;
    public Button btnaddToCart;
    public Button btnPlaceOrder;
    public Label lblTtype;
    public TableView<OrderTm> tblMain;
    public TableColumn tblOId;
    public TableColumn tblDId;
    public TableColumn tblUID;
    public TableColumn tblUserName;
    public TableColumn tblDate;
    public TableColumn tblTiD;
    public TableColumn tblType;
    public TableColumn tblQty;
    public TableColumn tblPrice;
    public TableColumn tblTotal;
    public TableColumn tblAction;

    public void initialize() {
        tblOId.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        tblDId.setCellValueFactory(new PropertyValueFactory<>("deliverID"));
        tblUID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        tblUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        tblDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        tblTiD.setCellValueFactory(new PropertyValueFactory<>("teaID"));
        tblType.setCellValueFactory(new PropertyValueFactory<>("TeaType"));
        tblPrice.setCellValueFactory(new PropertyValueFactory<>("TeaPrice"));
        tblQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tblAction.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));
        loadDeliveryingIds();
        loadTeaIds();
        setOrderDate();

    }

    private void setOrderDate() {
        lblOdate.setText(String.valueOf(LocalDate.now()));
    }

    private void loadTeaIds() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> ids = TeaModel.loadIds();

            for (String id : ids) {
                obList.add(id);
            }
            cmbTd.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }

    private void loadDeliveryingIds() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> ids = DeliverModel.loadIds();

            for (String id : ids) {
                obList.add(id);
            }
            cmbDID.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }

    private ObservableList<OrderTm> obList = FXCollections.observableArrayList();

    public void cartOnAc(ActionEvent actionEvent) {
        if (!txtQty.getText().isBlank() && !txtoId.getText().isBlank()) {
            String code = cmbTd.getValue();
            String description = lblTtype.getText();
            int qty = Integer.parseInt(txtQty.getText());
            double unitPrice = Double.parseDouble(lblPrice.getText());
            double total = qty * unitPrice;
            Button removeBtn = new Button("Remove");
            removeBtn.setCursor(Cursor.HAND);
            setRemoveBtnOnAction(removeBtn);

            if (!obList.isEmpty()) {
                for (int i = 0; i < tblMain.getItems().size(); i++) {
                    if (tblTiD.getCellData(i).equals(code)) {
                        qty += (int) tblQty.getCellData(i);
                        total = qty * unitPrice;

                        obList.get(i).setQty(qty);
                        obList.get(i).setTotal(total);

                        tblMain.refresh();
                        calculateNetTotal();
                        return;
                    }
                }
            }

            OrderTm tm = new OrderTm(txtoId.getText(), cmbDID.getValue(), lbluserId.getText(), lblUserName.getText(), lblOdate.getText(), code, description, unitPrice, qty, total, removeBtn);

            obList.add(tm);
            tblMain.setItems(obList);
            calculateNetTotal();
        }
    }


    public void placeOrderOnAc(ActionEvent actionEvent) {
        String oId = txtoId.getText();
        String tId = cmbTd.getValue();

        List<OrderDetails> cartDTOList = new ArrayList<>();

        for (int i = 0; i < tblMain.getItems().size(); i++) {
            OrderTm orderTM = obList.get(i);

            OrderDetails dto = new OrderDetails(
                    orderTM.getOrderID(),
                    orderTM.getTeaID(),
                    orderTM.getQty(),
                    orderTM.getTotal(),
                    orderTM.getOrderDate()
            );
            cartDTOList.add(dto);
        }
        String date = lblOdate.getText();
        boolean isPlaced = false;
        try {
            isPlaced = OrderModel.placeOrder(cartDTOList,oId,tId);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed").show();
                obList.clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order Not Placed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error").show();
        }
    }


    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < tblMain.getItems().size(); i++) {
            double total = (double) tblTotal.getCellData(i);
            netTotal += total;
        }
    }

    private void setRemoveBtnOnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (result.orElse(no) == yes) {
                int index=tblMain.getItems().size()-1;
                obList.remove(index);

                tblMain.refresh();
                calculateNetTotal();
            }
        });
    }

    public void deliveryOnAc(ActionEvent actionEvent) {
        String code = cmbDID.getValue();
        try {
            Devliverydto res = DeliverModel.searchById(code);
            String cod = res.getUserId();
            Userdto ges = UserModel.search(cod);
            String gesCode = ges.getName();
            fillBookFields(res,gesCode);

            txtQty.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void fillBookFields(Devliverydto res, String gesCode) {
        lbluserId.setText(res.getUserId());
        lblSupId.setText(res.getSupId());
        lblUserName.setText(gesCode);
    }


    public void teaOnAc(ActionEvent actionEvent) {
        String code = cmbTd.getValue();
        try {
            Tea tea = TeaModel.searchById(code);
            filltaeFields(tea);

            txtQty.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void filltaeFields(Tea tea) {

        lblTtype.setText(tea.getName());
        lblPrice.setText(String.valueOf(tea.getPrice()));
    }
}
