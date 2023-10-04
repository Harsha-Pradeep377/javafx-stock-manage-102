package lk.ijse.stockmanage102.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.stockmanage102.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class CustomerFormController {

    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtTel;

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO customer VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,address);
            preparedStatement.setString(4,tel);

            boolean isSaved = preparedStatement.executeUpdate()>0;
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer saved!").show();
                clearFields();
            }


        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtTel.setText("");
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void txtIdOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT*FROM customer WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            ResultSet rst = preparedStatement.executeQuery();

            if(rst.next()){
                String txtId = rst.getString(1);
                String txtName = rst.getString(2);
                String txtAddress = rst.getString(3);
                String txtTel = rst.getString(4);

                setFields(txtId,txtName,txtAddress,txtTel);
            }else{
                new Alert(Alert.AlertType.WARNING,"Customer not found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void setFields(String txtId, String txtName, String txtAddress, String txtTel) {
        this.txtId.setText(txtId);
        this.txtName.setText(txtName);
        this.txtAddress.setText(txtAddress);
        this.txtTel.setText(txtTel);
    }
}
