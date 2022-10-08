package entity;

import java.time.LocalDate;

public class Orders {
    private String orderId;
    private LocalDate orderDate;
    private String customerId;

    public Orders() {
    }

    public Orders(String orderId, String customerId) {
        this.orderId = orderId;
        this.customerId = customerId;
    }

    public Orders(String orderId, LocalDate orderDate, String customerId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
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

    @Override
    public String toString() {
        return "OrderTM{" +
                "orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", customerId='" + customerId + '\'' +
                '}';
    }
}
