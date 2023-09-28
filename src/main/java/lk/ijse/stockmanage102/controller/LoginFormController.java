package lk.ijse.stockmanage102.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.stockmanage102.db.Db;

import java.io.IOException;

public class LoginFormController {

    public TextField userId;
    public TextField password;
    public AnchorPane loginNode;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        String user = userId.getText();
        String pwd = password.getText();

        if(user.equals(Db.userName) && pwd.equals(Db.password)){
            navigate();
        }else {
            new Alert(Alert.AlertType.ERROR,"Credential Invalid").show();
        }

    }

    private void navigate() throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/stock_manage.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) this.loginNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Stock Manage Form");

    }
}
