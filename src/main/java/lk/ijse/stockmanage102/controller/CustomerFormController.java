package lk.ijse.stockmanage102.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.stockmanage102.db.DBConnection;
import lk.ijse.stockmanage102.dto.Customer;
import lk.ijse.stockmanage102.dto.Item;
import lk.ijse.stockmanage102.dto.tm.CustomerTm;
import lk.ijse.stockmanage102.dto.tm.ItemTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

public class CustomerFormController {

    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtTel;
    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TableView<CustomerTm> customerTable;

    public void initialize() throws SQLException {
        System.out.println("Customer form just loaded");

        setCellValueFactory();
        ArrayList<Customer> customerList = loadAllCustomers();
        setTableData(customerList);
    }

    private void setTableData(ArrayList<Customer> customerList) {
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        for(Customer customer : customerList){
            CustomerTm tm = new CustomerTm(customer.getId(), customer.getName(), customer.getAddress(), customer.getTel());
            obList.add(tm);
        }
        customerTable.setItems(obList);

    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }

    private ArrayList<Customer> loadAllCustomers() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT*FROM customer";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rst = preparedStatement.executeQuery();
        ArrayList<Customer> customers = new ArrayList<>();
        while (rst.next()) {
            Customer customer = new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4));
            customers.add(customer);
        }
        return customers;

    }


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
                Customer customer = new Customer(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4));
                setFields(customer);
            }else{
                new Alert(Alert.AlertType.WARNING,"Customer not found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void setFields(Customer customer) {
        this.txtId.setText(customer.getId());
        this.txtName.setText(customer.getName());
        this.txtAddress.setText(customer.getAddress());
        this.txtTel.setText(customer.getTel());
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "UPDATE customer SET name=?, address=?, tel=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,address);
            preparedStatement.setString(3,tel);
            preparedStatement.setString(4,id);
            boolean isUpdated = preparedStatement.executeUpdate()>0;
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer updated!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "DELETE FROM customer WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            boolean isDeleted = preparedStatement.executeUpdate()>0;
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer deleted!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
