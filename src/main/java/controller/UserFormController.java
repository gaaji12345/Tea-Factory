package controller;

import dto.Userdto;
import dto.tm.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.UserModel;

import java.sql.SQLException;
import java.util.List;

public class UserFormController {


    public AnchorPane mainpane;
    public TextField txtuserId;
    public TextField txtUsername;
    public TextField txtpasswordP;
    public TextField postiontxt;
    public Button btnadd;
    public Button btnupdate;
    public Button btndelete;
    public Button btnclear;
    public TableView<User> mainTable;
    public TableColumn cblid;
    public TableColumn tblname;
    public TableColumn tblpassword;
    public TableColumn tblpostion;


    public void initialize() {
        cblid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblname.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblpassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        tblpostion.setCellValueFactory(new PropertyValueFactory<>("title"));

        getAllUsers();
        setSelectToTxt();
    }

    private void setSelectToTxt() {

           mainTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    txtuserId.setText(newSelection.getId());
                    txtUsername.setText(newSelection.getName());
                    txtpasswordP.setText(newSelection.getPassword());
                    postiontxt.setText(newSelection.getTitle());
                }
            });

    }

    private void getAllUsers() {
        try {
            ObservableList<User> obList = FXCollections.observableArrayList();
            List<Userdto> userList = UserModel.getAll();

            for (Userdto user : userList) {
                obList.add(new User(
                        user.getId(),
                        user.getName(),
                        user.getPassword(),
                        user.getTitle()
                ));
            }
            mainTable.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }

    public void txtpassword(ActionEvent actionEvent) {
    }

    public void txtPostion(ActionEvent actionEvent) {
    }

    public void add_OnAc(ActionEvent actionEvent) {
        try {
            String id = txtuserId.getText();
            String name = txtUsername.getText();
            String password =txtpasswordP.getText();
            String title = postiontxt.getText();

            // Validate fields
           // validateFields(id, name, password, title);

            // Validate user ID
          //  validateUserId(id);

            Userdto user = new Userdto(id, name, password, title);

            boolean isSaved = UserModel.add(user);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "User saved!").show();
                getAllUsers();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void deleete_OnAc(ActionEvent actionEvent) {
        String id = txtuserId.getText();
        try {
            boolean isDeleted = UserModel.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
                getAllUsers();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void clear_OnAc(ActionEvent actionEvent) {
        txtuserId.clear();
        txtUsername.clear();
        txtpasswordP.clear();
        postiontxt.clear();
    }

    public void update_OnAc(ActionEvent actionEvent) {
        String id = txtuserId.getText();
        String name = txtUsername.getText();
        String password =txtpasswordP.getText();
        String title = postiontxt.getText();

        try {
            boolean isUpdated = UserModel.update(id, name, password, title);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "User updated!").show();
                getAllUsers();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }
}
