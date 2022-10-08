package view.tdm;

import java.math.BigDecimal;

public class OrderDetailsTM {
    private String itemCode;
    private String description;
    private int orderQty;
    private BigDecimal unitPrice;
    private BigDecimal discount;
    private BigDecimal total;

    public OrderDetailsTM() {
    }

    public OrderDetailsTM(String itemCode, String description, int orderQty, BigDecimal unitPrice, BigDecimal discount, BigDecimal total) {
        this.itemCode = itemCode;
        this.description = description;
        this.orderQty = orderQty;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.total = total;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderDetailsTM{" +
                "itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", orderQty=" + orderQty +
                ", unitPrice=" + unitPrice +
                ", discount=" + discount +
                ", total=" + total +
                '}';
    }
}
