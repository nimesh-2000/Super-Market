package controller;

import bo.BOFactory;
import bo.custom.PurchaseOrderBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailsDTO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import view.tdm.OrderDetailsTM;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderFormController {
    public Label lblId;
    PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PURCHASE_ORDER);
    public AnchorPane manageOrderContext;
    public JFXTextField txtDescription;
    public JFXTextField txtCustomerName;
    public JFXTextField txtQty;
    public JFXTextField txtDiscount;
    public TableView<OrderDetailsTM> tblOrder;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtUnitPrice;
    public Label lblOrderId;
    public Label lblDate;
    public JFXButton btnPlaceOrder;
    public JFXButton btnAdd;
    private String orderId;
    public Label lblTotal;
    public JFXComboBox<String > cmbCustomerId;
    public JFXComboBox<String> cmbItemCode;

    public void initialize() throws SQLException, ClassNotFoundException {
        tblOrder.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblOrder.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblOrder.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        tblOrder.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrder.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("discount"));
        tblOrder.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumn<OrderDetailsTM, Button> lastCol = (TableColumn<OrderDetailsTM, Button>) tblOrder.getColumns().get(6);
        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");
            btnDelete.setOnAction(event -> {
                tblOrder.getItems().remove(param.getValue());
                tblOrder.getSelectionModel().clearSelection();
                calculateTotal();
                enableOrDisablePlaceOrderButton();
            });

            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

        orderId = generateNewOrderId();
        lblId.setText("Order ID: " + orderId);
        lblDate.setText(LocalDate.now().toString());
        btnPlaceOrder.setDisable(true);
        txtCustomerName.setFocusTraversable(false);
        txtCustomerName.setEditable(false);
        txtDescription.setFocusTraversable(false);
        txtDescription.setEditable(false);
        txtUnitPrice.setFocusTraversable(false);
        txtUnitPrice.setEditable(false);
        txtDiscount.setFocusTraversable(false);
        txtDiscount.setEditable(false);
        txtQtyOnHand.setFocusTraversable(false);
        txtQtyOnHand.setEditable(false);
        txtQty.setOnAction(event -> btnAdd.fire());
        txtQty.setEditable(false);
        btnAdd.setDisable(true);

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableOrDisablePlaceOrderButton();

            if (newValue != null) {
                try {
                    /*Search Customer*/
                    Connection connection = DBConnection.getDbConnection().getConnection();
                    try {
                        if (!existCustomer(newValue + "")) {
//                            "There is no such customer associated with the id " + id
                            new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + newValue + "").show();
                        }

                        CustomerDTO search = purchaseOrderBO.searchCustomer(newValue + "");
                        txtCustomerName.setText(search.getCustomerName());

                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, "Failed to find the customer " + newValue + "" + e).show();
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                txtCustomerName.clear();
            }
        });


        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newItemCode) -> {
            txtQty.setEditable(newItemCode != null);
            btnAdd.setDisable(newItemCode == null);

            if (newItemCode != null) {

                /*Find Item*/
                try {
                    if (!existItem(newItemCode + "")) {
//                        throw new NotFoundException("There is no such item associated with the id " + code);
                    }

                    //Search Item
                    ItemDTO item = purchaseOrderBO.searchItem(newItemCode + "");
                    txtDescription.setText(item.getDescription());
                    txtUnitPrice.setText(item.getUnitPrice().setScale(2).toString());
                    txtDiscount.setText(item.getDiscount().setScale(2).toString());
//                    txtQtyOnHand.setText(tblOrderDetails.getItems().stream().filter(detail-> detail.getCode().equals(item.getCode())).<Integer>map(detail-> item.getQtyOnHand() - detail.getQty()).findFirst().orElse(item.getQtyOnHand()) + "");
                    Optional<OrderDetailsTM> optOrderDetail = tblOrder.getItems().stream().filter(detail -> detail.getItemCode().equals(newItemCode)).findFirst();
                    txtQtyOnHand.setText((optOrderDetail.isPresent() ? item.getQtyOnHand() - optOrderDetail.get().getOrderQty() : item.getQtyOnHand()) + "");

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                txtDescription.clear();
                txtQty.clear();
                txtDiscount.clear();
                txtQtyOnHand.clear();
                txtUnitPrice.clear();
            }
        });

        tblOrder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetail) -> {

            if (selectedOrderDetail != null) {
                cmbItemCode.setDisable(true);
                cmbItemCode.setValue(selectedOrderDetail.getItemCode());
                btnAdd.setText("Update");
                txtQtyOnHand.setText(Integer.parseInt(txtQtyOnHand.getText()) + selectedOrderDetail.getOrderQty() + "");
                txtQty.setText(selectedOrderDetail.getOrderQty() + "");
            } else {
                btnAdd.setText("Add");
                cmbItemCode.setDisable(false);
                cmbItemCode.getSelectionModel().clearSelection();
                txtQty.clear();
            }

        });

        loadAllCustomerIds();
        loadAllItemCodes();
    }

    private void loadAllItemCodes() {
        try {
            /*Get all items*/
            ArrayList<ItemDTO> all = purchaseOrderBO.getAllItems();
            for (ItemDTO dto : all) {
                cmbItemCode.getItems().add(dto.getItemCode());
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllCustomerIds() {
        try {
            ArrayList<CustomerDTO> all = purchaseOrderBO.getAllCustomers();
            for (CustomerDTO customerDTO : all) {
                cmbCustomerId.getItems().add(customerDTO.getCustomerId());
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customer ids").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return purchaseOrderBO.checkItemIsAvailable(code);
    }

    private boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return purchaseOrderBO.checkCustomerIsAvailable(id);
    }

    private String generateNewOrderId() {
        try {
            return purchaseOrderBO.generateNewOrderID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "OID-001";
    }

    private void enableOrDisablePlaceOrderButton() {
        btnPlaceOrder.setDisable(!(cmbCustomerId.getSelectionModel().getSelectedItem() != null && !tblOrder.getItems().isEmpty()));
    }

    private void calculateTotal() {
        BigDecimal total = new BigDecimal(0);
        for (OrderDetailsTM detail : tblOrder.getItems()) {
            total = total.add(detail.getTotal());
        }
        lblTotal.setText("Total: " + total);
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        if (!txtQty.getText().matches("\\d+") || Integer.parseInt(txtQty.getText()) <= 0 ||
            Integer.parseInt(txtQty.getText()) > Integer.parseInt(txtQtyOnHand.getText())) {
        new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
        txtQty.requestFocus();
        txtQty.selectAll();
        return;
    }

        String itemCode = cmbItemCode.getSelectionModel().getSelectedItem();
        String description = txtDescription.getText();
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);
        int qty = Integer.parseInt(txtQty.getText());
        BigDecimal discount= new BigDecimal(txtDiscount.getText()).setScale(2);
        BigDecimal newdiscount = discount.multiply(new BigDecimal(qty)).setScale(2);
        BigDecimal total = unitPrice.multiply(new BigDecimal(qty)).setScale(2);
        BigDecimal newTotal = total.subtract(new BigDecimal(String.valueOf(newdiscount)).setScale(2));

        boolean exists = tblOrder.getItems().stream().anyMatch(detail -> detail.getItemCode().equals(itemCode));

        if (exists) {
            OrderDetailsTM orderDetailTM = tblOrder.getItems().stream().filter(detail -> detail.getItemCode().equals(itemCode)).findFirst().get();

            if (btnAdd.getText().equalsIgnoreCase("Update")) {
                orderDetailTM.setOrderQty(qty);
                orderDetailTM.setTotal(newTotal);
                orderDetailTM.setDiscount(newdiscount);
                tblOrder.getSelectionModel().clearSelection();
            } else {
                orderDetailTM.setOrderQty(orderDetailTM.getOrderQty() + qty);
                newTotal = new BigDecimal(orderDetailTM.getOrderQty()).multiply(unitPrice).subtract(newdiscount).setScale(2);
                orderDetailTM.setTotal(newTotal);
            }
            tblOrder.refresh();
        } else {
            tblOrder.getItems().add(new OrderDetailsTM(itemCode, description, qty, unitPrice,newdiscount, newTotal));
        }
        cmbItemCode.getSelectionModel().clearSelection();
        cmbItemCode.requestFocus();
        calculateTotal();
        enableOrDisablePlaceOrderButton();


    }

    public void btnNewOrderOnAction(ActionEvent actionEvent) {
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        boolean b = saveOrder(orderId, LocalDate.now(), cmbCustomerId.getValue(),
                tblOrder.getItems().stream().map(tm -> new OrderDetailsDTO(orderId, tm.getItemCode(), tm.getOrderQty(), tm.getDiscount())).collect(Collectors.toList()));
        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
        }

        orderId = (String) generateNewOrderId();
        lblId.setText("Order Id: " + orderId);
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbItemCode.getSelectionModel().clearSelection();
        tblOrder.getItems().clear();
        txtQty.clear();
        txtCustomerName.clear();
        calculateTotal();

    }

    public boolean saveOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailsDTO> orderDetails) {
        try {
            return purchaseOrderBO.purchaseOrder(new OrderDTO(orderId, orderDate, customerId, orderDetails));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
