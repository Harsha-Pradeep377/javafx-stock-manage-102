package lk.ijse.stockmanage102.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.stockmanage102.db.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemFormController {
    public AnchorPane itemNode;
    public TextField txtItemCode;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQty;

    public void btnDachboardOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/stock_manage.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) this.itemNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Stock Manage Form");
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String itemCode = txtItemCode.getText();
        String descrition = txtDescription.getText();
        Double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        Integer qtyOnHand = Integer.parseInt(txtQty.getText());
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO item VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,itemCode);
            preparedStatement.setString(2,descrition);
            preparedStatement.setDouble(3,unitPrice);
            preparedStatement.setInt(4,qtyOnHand);

            boolean isSaved = preparedStatement.executeUpdate()>0;
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Item saved!").show();
                clearFields();
            }


        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
    private void clearFields() {
        txtItemCode.setText("");
        txtDescription.setText("");
        txtUnitPrice.setText("");
        txtQty.setText("");
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();

    }
    public void txtItemCodeOnAction(ActionEvent actionEvent) {
        String itemCode = txtItemCode.getText();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT*FROM item WHERE itemcode = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,itemCode);
            ResultSet rst = preparedStatement.executeQuery();

            if(rst.next()){
                String txtItemCode = rst.getString(1);
                String txtDescription = (rst.getString(2));
                String txtUnitPrice = Double.toString(rst.getDouble(3));
                String txtQty = Integer.toString(rst.getInt(4));

                setFields(txtItemCode,txtDescription,txtUnitPrice,txtQty);
            }else{
                new Alert(Alert.AlertType.WARNING,"Item not found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void setFields(String txtItemCode, String txtDescription, String txtUnitPrice, String txtQty) {
        this.txtItemCode.setText(txtItemCode);
        this.txtDescription.setText(txtDescription);
        this.txtUnitPrice.setText(txtUnitPrice);
        this.txtQty.setText(txtQty);
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String itemCode = txtItemCode.getText();
        String description = txtDescription.getText();
        Double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        Integer qtyOnHand = Integer.parseInt(txtQty.getText());
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "UPDATE item SET description=?, unitPrize=?, qtyOnHand=? WHERE itemCode=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,description);
            preparedStatement.setDouble(2,unitPrice);
            preparedStatement.setInt(3,qtyOnHand);
            preparedStatement.setString(4,itemCode);
            boolean isUpdated = preparedStatement.executeUpdate()>0;
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Item updated!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String itemCode = txtItemCode.getText();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "DELETE FROM item WHERE itemCode=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,itemCode);
            boolean isDeleted = preparedStatement.executeUpdate()>0;
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Item deleted!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


}
