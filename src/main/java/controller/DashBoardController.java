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


    public void user_OnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/view/userForm.fxml"));
        Parent load= fxmlLoader.load();
      UserFormController controller=fxmlLoader.getController();
        subpain.getChildren().clear();
        subpain.getChildren().add(load);
    }
}
