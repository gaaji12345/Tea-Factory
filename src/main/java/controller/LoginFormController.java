package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.LoginModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LoginFormController {
    public AnchorPane mainPane;
    public TextField txtuser;
    //public TextField txtusername;
    public ComboBox cmbid;
    public TextField txtPassword;
    public Button btnlog;

    public void initialize() {
        loadTitles();
    }

    private void loadTitles() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> titles = LoginModel.loadTitles();

            for (String title : titles) {
                obList.add(title);
            }
            cmbid.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void btnlogin(ActionEvent actionEvent) {

        String title = (String) cmbid.getValue();
        String userName = txtuser.getText();
        String password = txtPassword.getText();


        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM user WHERE title = ? AND userName = ? AND password = ?");
            stm.setString(1, title);
            stm.setString(2, userName);
            stm.setString(3, password);
            ResultSet resultSet = stm.executeQuery();

            if (resultSet.next()) {
                // Login successful, load appropriate form
                String path = "/view/";
                switch(title) {
                    case "Manager":
                        path += "DashBoard.fxml";
                        break;
                    case "Employee":
                        path += "receptionist_form.fxml";
                        break;
                    // add more cases as needed for other user types
                }
                Stage stage = (Stage) mainPane.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(path))));
                stage.setTitle("Manage");
                stage.centerOnScreen();
                stage.show();
            } else {
                // Login failed, show error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password!");
                alert.showAndWait();
            }
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while trying to login!");
            alert.showAndWait();
        }


    }

//    public void btnLoginOnAction(javafx.event.ActionEvent actionEvent) throws IOException {
//String title = (String) cmbid.getValue();
//        String userName = txtuser.getText();
//        String password = txtPassword.getText();
//
//
//        try {
//            Connection conn = DBConnection.getInstance().getConnection();
//            PreparedStatement stm = conn.prepareStatement("SELECT * FROM user WHERE title = ? AND userName = ? AND password = ?");
//            stm.setString(1, title);
//            stm.setString(2, userName);
//            stm.setString(3, password);
//            ResultSet resultSet = stm.executeQuery();
//
//            if (resultSet.next()) {
//                // Login successful, load appropriate form
//                String path = "/view/";
//                switch(title) {
//                    case "Manager":
//                        path += "DashBoard.fxml";
//                        break;
//                    case "Employee":
//                        path += "receptionist_form.fxml";
//                        break;
//                    // add more cases as needed for other user types
//                }
//                Stage stage = (Stage) mainPane.getScene().getWindow();
//                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(path))));
//                stage.setTitle("Manage");
//                stage.centerOnScreen();
//                stage.show();
//            } else {
//                // Login failed, show error message
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Login Failed");
//                alert.setHeaderText(null);
//                alert.setContentText("Invalid username or password!");
//                alert.showAndWait();
//            }
//        } catch (SQLException | IOException ex) {
//            ex.printStackTrace();
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText(null);
//            alert.setContentText("An error occurred while trying to login!");
//            alert.showAndWait();
//        }
//
//    }
}

