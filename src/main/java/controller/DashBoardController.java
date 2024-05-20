package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashBoardController {

    //h-758
    //w-943
    public AnchorPane mainpane;
    public Button btnuser;
    public AnchorPane subpain;
    public Button btnsup;
    public Button btncollector;
    public Button btnDetails;
    public Button btnemployee;
    public Button btndeliver;
    public Button btnPayment;


    public void user_OnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/view/userForm.fxml"));
        Parent load= fxmlLoader.load();
      UserFormController controller=fxmlLoader.getController();
        subpain.getChildren().clear();
        subpain.getChildren().add(load);
    }

    public void sup_ONAc(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/view/SupplierForm.fxml"));
        Parent load= fxmlLoader.load();
       SupplierFormController controller=fxmlLoader.getController();
        subpain.getChildren().clear();
        subpain.getChildren().add(load);
    }

    public void collect_OnAc(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/view/CollectorForm.fxml"));
        Parent load= fxmlLoader.load();
       CollectorFormController controller=fxmlLoader.getController();
        subpain.getChildren().clear();
        subpain.getChildren().add(load);
    }

    public void detailAc(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/view/TeaCollect_Detail.fxml"));
        Parent load= fxmlLoader.load();
        TeaCollectDetailController controller=fxmlLoader.getController();
        subpain.getChildren().clear();
        subpain.getChildren().add(load);
    }

    public void employeeOnAC(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/view/EmployeeForm.fxml"));
        Parent load= fxmlLoader.load();
       EmployeeFormController controller=fxmlLoader.getController();
        subpain.getChildren().clear();
        subpain.getChildren().add(load);
    }

    public void deliverOn(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/view/DileveryForm.fxml"));
        Parent load= fxmlLoader.load();
        DileveryFormController controller=fxmlLoader.getController();
        subpain.getChildren().clear();
        subpain.getChildren().add(load);
    }

    public void payOn(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/view/PayMentForm.fxml"));
        Parent load= fxmlLoader.load();
      PayMentFormController controller=fxmlLoader.getController();
        subpain.getChildren().clear();
        subpain.getChildren().add(load);
    }
}
