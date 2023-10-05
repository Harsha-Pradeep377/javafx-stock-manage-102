package lk.ijse.stockmanage102.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.stockmanage102.db.DBConnection;
import lk.ijse.stockmanage102.dto.Customer;
import lk.ijse.stockmanage102.dto.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierFormController {
    public TextField txtSupId;
    public TextField txtSupName;
    public TextField txtShop;
    public TextField txtTel;

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtSupId.getText();
        String name = txtSupName.getText();
        String shop = txtShop.getText();
        String tel = txtTel.getText();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO supplier VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,shop);
            preparedStatement.setString(4,tel);

            boolean isSaved = preparedStatement.executeUpdate()>0;
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier saved!").show();
                clearFields();
            }


        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void clearFields() {
        txtSupId.setText("");
        txtSupName.setText("");
        txtShop.setText("");
        txtTel.setText("");
    }

    public void btnClear(ActionEvent actionEvent) {
        clearFields();
    }
    public void txtOnAction(ActionEvent actionEvent) {
        String id = txtSupId.getText();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT*FROM supplier WHERE supId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            ResultSet rst = preparedStatement.executeQuery();

            if(rst.next()){
                Supplier supplier = new Supplier(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4));
                setFields(supplier);
            }else{
                new Alert(Alert.AlertType.WARNING,"Supplier not found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    private void setFields(Supplier supplier) {
        this.txtSupId.setText(supplier.getSupId());
        this.txtSupName.setText(supplier.getSupName());
        this.txtShop.setText(supplier.getShop());
        this.txtTel.setText(supplier.getTel());
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtSupId.getText();
        String name = txtSupName.getText();
        String address = txtShop.getText();
        String tel = txtTel.getText();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "UPDATE supplier SET name=?, shop=?, tel=? WHERE supId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,address);
            preparedStatement.setString(3,tel);
            preparedStatement.setString(4,id);
            boolean isUpdated = preparedStatement.executeUpdate()>0;
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Supplier updated!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtSupId.getText();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "DELETE FROM supplier WHERE supId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            boolean isDeleted = preparedStatement.executeUpdate()>0;
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Supplier deleted!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


}
