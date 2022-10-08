package controller;

import bo.BOFactory;
import bo.custom.CustomerBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import view.tdm.CustomerTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerFormController {
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCity;
    public AnchorPane customerContext;
    public TableColumn colCustomerTitle;
    public TableColumn colCustomerId;
    public TableColumn colCustomerName;
    public TableColumn colCustomerAddress;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colPostalCode;
    public JFXTextField txtPostalCode;

    public JFXComboBox<String> cmbCustomerTitle;
    public JFXComboBox<String> cmbProvince;

    private final CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    public TableView<CustomerTM> tblCustomer;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public TextField txtSearchCustomer;


    public void initialize() {
        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("customerTitle"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tblCustomer.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        tblCustomer.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("city"));
        tblCustomer.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("province"));
        tblCustomer.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        initUI();

        ObservableList<String> obl = FXCollections.observableArrayList();

        obl.add("Mr");
        obl.add("Mrs");
        obl.add("Miss");

        cmbCustomerTitle.setItems(obl);

        ObservableList<String> ob = FXCollections.observableArrayList();

        ob.add("Southern");
        ob.add("Western");
        ob.add("Eastern");
        ob.add("Northern");
        ob.add("Uva");
        ob.add("Central");
        ob.add("North Central");
        ob.add("North Western");
        ob.add("Sabaragamuwa");

        cmbProvince.setItems(ob);

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtCustomerId.setText(newValue.getCustomerId());
                cmbCustomerTitle.setValue(newValue.getCustomerTitle());
                txtCustomerName.setText(newValue.getCustomerName());
                txtCustomerAddress.setText(newValue.getCustomerAddress());
                txtCity.setText(newValue.getCity());
                cmbProvince.setValue(newValue.getProvince());
                txtPostalCode.setText(newValue.getPostalCode());

                txtCustomerId.setDisable(false);
                cmbCustomerTitle.setDisable(false);
                txtCustomerName.setDisable(false);
                txtCustomerAddress.setDisable(false);
                txtCity.setDisable(false);
                cmbProvince.setDisable(false);
                txtPostalCode.setDisable(false);

            }
        });

        //txtCustomerAddress.setOnAction(event -> btnsaveCustomer.fire());
        loadAllCustomers();
    }

    private void loadAllCustomers() {
        tblCustomer.getItems().clear();
        /*Get all customers*/
        try {
            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomers();
            for (CustomerDTO customer : allCustomers) {
                tblCustomer.getItems().add(new CustomerTM(customer.getCustomerId(), customer.getCustomerTitle(), customer.getCustomerName(), customer.getCustomerAddress(), customer.getCity(), customer.getProvince(), customer.getPostalCode()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void initUI() {
        txtCustomerId.clear();
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtCity.clear();
        txtPostalCode.clear();
        txtCustomerId.setDisable(true);
        txtCustomerName.setDisable(true);
        txtCustomerAddress.setDisable(true);
        txtCity.setDisable(true);
        cmbProvince.setDisable(true);
        txtPostalCode.setDisable(true);
        txtCustomerId.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
        cmbCustomerTitle.setDisable(true);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtCustomerId.getText();
        String cusTitle = (String) cmbCustomerTitle.getValue();
        String name = txtCustomerName.getText();
        String address = txtCustomerAddress.getText();
        String city = txtCity.getText();
        String province = (String) cmbProvince.getValue();
        String postalCode = txtPostalCode.getText();

        if (!name.matches("^[A-Z ][a-z]{1,}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtCustomerName.requestFocus();
            return;
        } else if (!address.matches(".{3,}")) {
            new Alert(Alert.AlertType.ERROR, "Address should be at least 3 characters long").show();
            txtCustomerAddress.requestFocus();
            return;
        }else if (!city.matches("^[A-Z ][a-z]{1,}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid city").show();
            txtCity.requestFocus();
            return;
        }else if (!postalCode.matches("^[A-z0-9 ,/]{4,20}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid postal code").show();
            txtPostalCode.requestFocus();
            return;
        }

        if (btnSave.getText().equalsIgnoreCase("save")) {
            /*Save Customer*/
            try {
                if (existCustomer(id)) {
                    new Alert(Alert.AlertType.ERROR, id + " already exists").show();
                }

                customerBO.saveCustomer(new CustomerDTO(id,cusTitle, name, address,city,province,postalCode));
                tblCustomer.getItems().add(new CustomerTM(id,cusTitle, name, address,city,province,postalCode));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            /*Update customer*/
            try {
                if (!existCustomer(id)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
                }
                //Customer update
                customerBO.updateCustomer(new CustomerDTO(id,cusTitle, name, address,city,province,postalCode));

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + id + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            CustomerTM selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();
            selectedCustomer.setCustomerTitle(cusTitle);
            selectedCustomer.setCustomerName(name);
            selectedCustomer.setCustomerAddress(address);
            selectedCustomer.setCity(city);
            selectedCustomer.setProvince(province);
            selectedCustomer.setPostalCode(postalCode);
            tblCustomer.refresh();
        }
        //initUI();
        // btnNewCustomerOnAction().fire();

    }

    private boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerBO.customerExist(id);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = tblCustomer.getSelectionModel().getSelectedItem().getCustomerId();
        try {
            if (!existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }
            customerBO.deleteCustomer(id);
            tblCustomer.getItems().remove(tblCustomer.getSelectionModel().getSelectedItem());
            tblCustomer.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnNewCustomerOnAction(ActionEvent actionEvent) {
        txtCustomerId.setDisable(false);
        txtCustomerName.setDisable(false);
        txtCustomerAddress.setDisable(false);
        txtCity.setDisable(false);
        txtPostalCode.setDisable(false);

        txtCustomerId.clear();
        txtCustomerId.setText(generateNewId());
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtCity.clear();
        txtPostalCode.clear();

        cmbCustomerTitle.requestFocus();
        cmbProvince.requestFocus();
        //txtCustomerName.requestFocus();
        cmbCustomerTitle.setDisable(false);
        cmbProvince.setDisable(false);
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblCustomer.getSelectionModel().clearSelection();
    }

    private String generateNewId() {
        try {
            return customerBO.generateNewCustomerID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (tblCustomer.getItems().isEmpty()) {
            return "C00-001";
        } else {
            String id = getLastCustomerId();
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        }
    }

    private String getLastCustomerId() {
        List<CustomerTM> tempCustomersList = new ArrayList<>(tblCustomer.getItems());
        Collections.sort(tempCustomersList);
        return tempCustomersList.get(tempCustomersList.size() - 1).getCustomerId();
    }

    public void txtSearchCustomerOnKeyReleased(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        String search = "%" + txtSearchCustomer.getText() + "%";

        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            ArrayList<CustomerDTO> customerDTOS = customerBO.searchCustomers(search);
            ObservableList<CustomerTM> oBCustomerTMS = FXCollections.observableArrayList();

            for (CustomerDTO cusDto : customerDTOS) {
                oBCustomerTMS.add(new CustomerTM(cusDto.getCustomerId(),
                        cusDto.getCustomerTitle(),
                        cusDto.getCustomerName(),
                        cusDto.getCustomerAddress(),
                        cusDto.getCity(),
                        cusDto.getProvince(),
                        cusDto.getPostalCode()));
            }
            tblCustomer.getItems().clear();
            tblCustomer.getItems().addAll(oBCustomerTMS);
            tblCustomer.refresh();
        }
    }
}
