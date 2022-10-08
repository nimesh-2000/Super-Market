package entity;

import java.math.BigDecimal;

public class OrderDetails {
    private String orderId;
    private String itemCode;
    private int orderQty;
    private BigDecimal unitPrice;
    private BigDecimal discount;
    private double total;


    public OrderDetails() {
    }


    public OrderDetails(String orderId, String itemCode, int orderQty, BigDecimal unitPrice, BigDecimal discount, double total) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.orderQty = orderQty;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.total = total;
    }

    public OrderDetails(String orderId, String itemCode, int orderQty, BigDecimal discount) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.orderQty = orderQty;
        this.discount = discount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "OrderDetailsTM{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", orderQty=" + orderQty +
                ", discount=" + discount +
                '}';
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
