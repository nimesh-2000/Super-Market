package dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class OrderDTO implements Serializable {
    private String orderId;
    private LocalDate orderDate;
    private String customerId;

    private List<OrderDetailsDTO> orderDetails;

    private String customerName;
    private BigDecimal orderTotal;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, String customerId) {
        this.orderId = orderId;
        this.customerId = customerId;
    }

    public OrderDTO(String orderId, LocalDate orderDate, String customerId) {
        this.setOrderId(orderId);
        this.setOrderDate(orderDate);
        this.setCustomerId(customerId);
    }

    public OrderDTO(String orderId, LocalDate orderDate, String customerId, List<OrderDetailsDTO> orderDetails) {
        this.setOrderId(orderId);
        this.setOrderDate(orderDate);
        this.setCustomerId(customerId);
        this.setOrderDetails(orderDetails);
    }

    public OrderDTO(String orderId, LocalDate orderDate, String customerId, String customerName, BigDecimal orderTotal) {
        this.setOrderId(orderId);
        this.setOrderDate(orderDate);
        this.setCustomerId(customerId);
        this.setCustomerName(customerName);
        this.setOrderTotal(orderTotal);
    }



    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<OrderDetailsDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailsDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", customerId='" + customerId + '\'' +
                ", orderDetails=" + orderDetails +
                ", customerName='" + customerName + '\'' +
                ", orderTotal=" + orderTotal +
                '}';
    }
}
