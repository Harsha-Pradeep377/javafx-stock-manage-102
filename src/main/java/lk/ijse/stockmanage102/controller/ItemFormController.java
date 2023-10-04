package lk.ijse.stockmanage102.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.stockmanage102.db.DBConnection;
import lk.ijse.stockmanage102.dto.Item;
import lk.ijse.stockmanage102.dto.tm.ItemTm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemFormController {
    public AnchorPane itemNode;
    public TextField txtItemCode;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQty;
    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;
    @FXML
    private TableView<ItemTm> itemTable;

    public void initialize() throws SQLException {
        System.out.println("Item form just loaded");

        setCellValueFactory();
        ArrayList<Item> itemList = loadAllItems();
        setTableData(itemList);
    }

    private void setTableData(ArrayList<Item> itemList) {
        ObservableList<ItemTm> obList = FXCollections.observableArrayList();

        for(Item item : itemList){
            ItemTm tm = new ItemTm(item.getItemCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand());
            obList.add(tm);
        }
        itemTable.setItems(obList);

    }

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
    }

    private ArrayList<Item> loadAllItems() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT*FROM item";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rst = preparedStatement.executeQuery();
        ArrayList<Item> items = new ArrayList<>();
        while (rst.next()) {
            Item item = new Item(rst.getString(1), rst.getString(2), rst.getDouble(3), rst.getInt(4));
            items.add(item);
        }
        return items;

    }

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
                String txtDescription = rst.getString(2);
                Double txtUnitPrice = rst.getDouble(3);
                Integer txtQty = rst.getInt(4);

                Item item = new Item(txtItemCode,txtDescription,txtUnitPrice,txtQty);

                setFields(item);
            }else{
                new Alert(Alert.AlertType.WARNING,"Item not found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void setFields(Item item) {
        this.txtItemCode.setText(item.getItemCode());
        this.txtDescription.setText(item.getDescription());
        this.txtUnitPrice.setText(Double.toString(item.getUnitPrice()));
        this.txtQty.setText(Integer.toString(item.getQtyOnHand()));
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
