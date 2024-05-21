package controller;

import dto.tm.OrderTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
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
        loadDeliveryingIds();
        loadTeaIds();
        setOrderDate();

    }

    private void setOrderDate() {
    }

    private void loadTeaIds() {

    }

    private void loadDeliveryingIds() {

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
    }


    private void calculateNetTotal() {
    }

    private void setRemoveBtnOnAction(Button removeBtn) {
    }

}
