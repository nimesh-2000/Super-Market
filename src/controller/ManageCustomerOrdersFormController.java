package controller;

import bo.BOFactory;
import bo.custom.OrderBO;
import bo.custom.OrderDetailBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.OrderDTO;
import dto.OrderDetailsDTO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import view.tdm.OrderDetailsTM;
import view.tdm.OrderTM;
import view.tdm.SearchOrderDetailsTM;
import view.tdm.SearchOrderTM;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageCustomerOrdersFormController {
    public AnchorPane manageCustomerOrderContext;
    public JFXTextField txtTotal;
    public JFXTextField txtOrderId;
    public JFXTextField txtQty;
    public JFXTextField txtDiscount;
    public JFXButton btnRemoveOrder;
    public TableView<OrderTM> tblOrder;
    public JFXTextField txtItemCode;
    public JFXTextField txtUnitPrice;
    public JFXButton btnConfirmEdit;
    public TableView<OrderDetailsTM> tblOrderDetail;
    public Label lblTotal;
    public TextField txtOrderDetailSearch;
    public TextField txtOrderSearch;

    OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Order);
    OrderDetailBO orderDetailBO = (OrderDetailBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.OrderDetail);

    public void initialize() throws SQLException, ClassNotFoundException {
        tblOrder.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tblOrder.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("customerId"));

        tblOrderDetail.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblOrderDetail.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblOrderDetail.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        tblOrderDetail.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrderDetail.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("discount"));
        tblOrderDetail.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumn<OrderDetailsTM, Button> lastCol = (TableColumn<OrderDetailsTM, Button>) tblOrderDetail.getColumns().get(6);
        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");
            btnDelete.setOnAction(event -> {
                tblOrderDetail.getItems().remove(param.getValue());
                tblOrderDetail.getSelectionModel().clearSelection();

            });

            return new ReadOnlyObjectWrapper<>(btnDelete);
        });
//add Orders to The Table
        ArrayList<OrderDTO> allOrders = orderBO.getAllOrders();

        for (OrderDTO allOrder : allOrders) {
            tblOrder.getItems().add(new OrderTM(allOrder.getOrderId(), allOrder.getCustomerId()));
        }
        tblOrder.refresh();

        tblOrder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {

                searchOrderDetails(newValue.getOrderId());
            }

        });
    }

    public void btnRemoveOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        OrderTM selectedItem = tblOrder.getSelectionModel().getSelectedItem();
        boolean b = orderBO.deleteOrders(selectedItem.getOrderId());

        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Deleted SussesFull").show();
            tblOrderDetail.getItems().clear();
            tblOrder.getItems().clear();
            initialize();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Something Went Wrong..").show();
        }
    }

    public void btnConfirmEditOnAction(ActionEvent actionEvent) {
    }

    private void searchOrderDetails(String newValue) {
        //Search by ID
        String value = "%" + newValue + "%";

        ArrayList<OrderDetailsDTO> oDetailDto = null;

        try {

            oDetailDto = orderDetailBO.searchOrderDetails(value);


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ObservableList<OrderDetailsTM> orderDetailsTMS = FXCollections.observableArrayList();

        double allTotal = 0;

        for (OrderDetailsDTO od : oDetailDto) {
            orderDetailsTMS.add(new OrderDetailsTM(od.getOrderId(),
                    od.getItemCode(),
                    od.getOrderQty(),
                    od.getUnitPrice(),
                    od.getDiscount(),
                    BigDecimal.valueOf(od.getTotal())
            ));

            allTotal += od.getTotal();
        }

        tblOrderDetail.getItems().clear();
        tblOrderDetail.getItems().addAll(orderDetailsTMS);
        tblOrderDetail.refresh();
        lblTotal.setText(String.valueOf(allTotal));


    }

    public void searchOrderDetailOnKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            tblOrderDetail.getItems().clear();
            searchOrderDetails(txtOrderDetailSearch.getText());
        }
    }

    public void searchOrderOnKeyReleased(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            String value = "%" + txtOrderSearch.getText() + "%";

            ArrayList<OrderDTO> orderDto = orderBO.getAllSearchOrder(value);


            ObservableList<OrderTM> orderTMS = FXCollections.observableArrayList();

            //System.out.println(orderDto);
            for (OrderDTO od : orderDto) {
                orderTMS.add(new OrderTM(
                        od.getOrderId(),
                        od.getCustomerId()
                ));

            }

            tblOrder.getItems().clear();
            tblOrder.getItems().addAll(orderTMS);
            tblOrder.refresh();
        }

    }
}
